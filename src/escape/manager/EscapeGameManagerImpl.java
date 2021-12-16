package escape.manager;

import escape.EscapeGameManager;
import escape.component.*;
import escape.gamedef.*;
import escape.util.*;
import java.util.*;

public abstract class EscapeGameManagerImpl implements EscapeGameManager<MyCoordinate> {

    protected final EscapeGameObserverManager observerManager;
    private final EscapeGameInitializer config;
    private final HashMap<EscapePiece.PieceName, PieceTypeDescriptor> piecesMap;
    protected final HashMap<Rule.RuleID, Integer> ruleMap;
    protected final MyBoard board;
    private Player currentPlayer, winner;
    private int playerOneScore, playerTwoScore, turnCount;
    private boolean isDraw;

    /**
     * The constructor takes a escape game config
     * @param config the config
     * @throws Exception on any errors
     */
    public EscapeGameManagerImpl(EscapeGameInitializer config) {
        this.observerManager = new EscapeGameObserverManager();
        this.config = config;
        this.board = new MyBoard(config.getxMax(), config.getyMax());
        this.piecesMap = new HashMap<>();
        this.ruleMap = new HashMap<>();
        this.currentPlayer = Player.PLAYER1;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.turnCount = 0;
        this.isDraw = false;
        for(PieceTypeDescriptor p : config.getPieceTypes())  {
            // Setup pieceName:pieceDescriptor map
            this.piecesMap.put(p.getPieceName(), p);
        }
        for(RuleDescriptor r : config.getRules()) {
            this.ruleMap.put(r.ruleId, r.ruleValue);
        }

    }

    /**
     * Set up the board and other implementation specific attributes
     */
    protected void setupBoard() {
        for(LocationInitializer i : this.config.getLocationInitializers()) {
            MyCoordinate c = makeCoordinate(i.x, i.y);
            PieceTypeDescriptor pieceTypeDescriptor = this.piecesMap.get(i.pieceName);
            MyPiece p = null;
            if(pieceTypeDescriptor != null) {
                p = new MyPiece(pieceTypeDescriptor, i.player);
            }
            MyLocation l = new MyLocation(c, i.locationType, p);
            this.board.addPair(c, l);
        }
    }

    /**
     * Make the move in the current game.
     *
     * @param from starting coordinate
     * @param to   ending coordinate
     * @return true if the move was legal, false otherwise
     */
    @Override
    public boolean move(MyCoordinate from, MyCoordinate to) {
        if(isDraw) {
            // Game is over but was a draw
            this.observerManager.notify("Game is over and results in a draw");
            return false;
        }
        else if(this.winner != null) {
            // Game is already over
            this.observerManager.notify("Game is over and PLAYER" + (this.winner == Player.PLAYER1 ? "1" : "2") + " has won");
            return false;
        }

        MyLocation fromLocation, toLocation;
        fromLocation = this.board.getLocation(from);
        toLocation = this.board.getLocation(to);
        if(checkBaseConditions(fromLocation, toLocation)) {
            List<MyMove> path = findPath(fromLocation, toLocation);
            if(path != null) {
                // Actually perform the piece move
                performMove(path);
                // Check for draw conditions
                if(checkDrawConditions()) {
                    this.observerManager.notify("Game is over and results in a draw");
                } else {
                    // Check for win conditions
                    Player newWinner = checkWinConditions();
                    if(newWinner != null) {
                        this.winner = newWinner;
                        this.observerManager.notify("PLAYER" + (this.winner == Player.PLAYER1 ? "1" : "2") + " wins");
                    }
                }
                return true;
            } else {
                this.observerManager.notify("No path could be found");
                return false;
            }

        }
        return false;
    }

    /**
     * Return the piece located at the specified coordinate. If executing
     * this method in the game instance causes an exception, then this method
     * returns null and sets the status message appropriately. The status message
     * is not used in the initial release(s) of the game.
     *
     * @param coordinate the location to probe
     * @return the piece at the specified location or null if there is none
     */
    @Override
    public EscapePiece getPieceAt(MyCoordinate coordinate) {
        return this.board.getLocation(coordinate).getPiece();
    }

    /**
     * Returns a coordinate of the appropriate type. If the coordinate cannot be
     * created, then null is returned and the status message is set appropriately
     * if the observer is used.
     *
     * @param x the x component
     * @param y the y component
     * @return the coordinate or null if the coordinate cannot be implemented
     */
    @Override
    public MyCoordinate makeCoordinate(int x, int y) {
        return new MyCoordinate(x, y);
    }

    /**
     * Add an observer to this manager. Whenever the move() method returns
     * false, the observer will be notified with a message indication the
     * problem.
     * @param observer
     * @return the observer
     */
    public GameObserver addObserver(MyObserver observer) {
        this.observerManager.addObserver(observer);
        return observer;
    }

    /**
     * Remove an observer from this manager. The observer will no longer
     * receive notifications from this game manager.
     * @param observer
     * @return the observer that was removed or null if it had not previously
     *     been registered
     */
    public GameObserver removeObserver(MyObserver observer) {
        return this.observerManager.removeObserver(observer);
    }

    /**
     * Actually update the board and point scores for players once a move has been verified
     *
     * @param path path that the move took
     */
    public void performMove(List<MyMove> path) {
        MyLocation from = path.get(0).getLocation();
        MyLocation to = getEndLocationFromPath(path);
        MyPiece piece = from.getPiece();
        from.setPiece(null);
        if(to.getLocationType() == LocationType.EXIT) {
            PieceAttribute pieceValueAttribute = piece.getDescriptor().getAttribute(EscapePiece.PieceAttributeID.VALUE);
            int pieceValue;
            if(pieceValueAttribute == null) {
                // Default piece value
                pieceValue = 1;
            } else {
                pieceValue = pieceValueAttribute.getValue();
            }
            if(currentPlayer == Player.PLAYER1) {
                playerOneScore += pieceValue;
            } else {
                playerTwoScore += pieceValue;
            }
        } else {
            to.setPiece(piece);
        }
        turnCount++;
        currentPlayer = currentPlayer == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
    }

    /**
     * Get the ending location from the path
     * which could be the final element in the list
     * or the first EXIT location found in the path
     *
     * @param path path that the move took
     * @return the ending location for the path
     */
    private MyLocation getEndLocationFromPath(List<MyMove> path) {
        for(MyMove l : path) {
            if(l.getLocation().getLocationType() == LocationType.EXIT) {
                return l.getLocation();
            }
        }
        return path.get(path.size() - 1).getLocation();
    }

    /**
     * Get Player 1s current score
     *
     * @return current player one score
     */
    public int getPlayerOneScore() {
        return playerOneScore;
    }

    /**
     * Get Player 2s current score
     *
     * @return current player two score
     */
    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    /**
     * Check if the next player has available pieces to move
     *
     * @return true if they do, false otherwise
     */
    public boolean checkAvailablePieces() {
        return this.board.hasAvailablePieces(this.currentPlayer);
    }

    /**
     * Check the base move conditions for a proposed move
     *
     * @param from the source location
     * @param to the destination location
     * @return true if valid, false otherwise
     */
    public boolean checkBaseConditions(MyLocation from, MyLocation to) {
        if(from.getPiece() == null) {
            this.observerManager.notify("Attempted to move a piece that does not exist");
            return false;
        }
        if(from.getPiece().getPlayer() != currentPlayer) {
            this.observerManager.notify("Attempted to move a piece that does not match the current player");
            return false;
        }
        if(from.equals(to)) {
            this.observerManager.notify("Attempted to move to the same location");
            return false;
        }
        if(to.getLocationType() == LocationType.BLOCK) {
            this.observerManager.notify("Attempted to move to a BLOCK location");
            return false;
        }
        if(to.getPiece() != null) {
            if(!ruleMap.containsKey(Rule.RuleID.REMOVE)) {
                this.observerManager.notify("Attempted to move to a location occupied with a piece");
                return false;
            } else {
                if(to.getPiece().getPlayer() == currentPlayer) {
                    this.observerManager.notify("Attempted to remove a piece own piece");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if specific draw conditions are present
     *
     * @return true if drawn, false otherwise
     */
    public boolean checkDrawConditions() {
        Integer turnLimit = this.ruleMap.get(Rule.RuleID.TURN_LIMIT);
        if(turnLimit != null) {
            if(turnCount >= turnLimit) {
                if(playerOneScore == playerTwoScore) {
                    this.isDraw = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if a specific player has one the game
     *
     * @return the player that has won or null if no player has won
     */
    public Player checkWinConditions() {
        // Check turn limit
        Integer turnLimit = this.ruleMap.get(Rule.RuleID.TURN_LIMIT);
        if(turnLimit != null) {
            if(turnCount >= turnLimit) {
                if(playerOneScore > playerTwoScore) {
                    return Player.PLAYER1;
                } else if(playerTwoScore > playerOneScore) {
                    return Player.PLAYER2;
                }
            }
        }
        Integer scoreLimit = this.ruleMap.get(Rule.RuleID.SCORE);
        if(scoreLimit != null) {
            if(playerOneScore >= scoreLimit) {
                return Player.PLAYER1;
            }
            else if(playerTwoScore >= scoreLimit) {
                return Player.PLAYER2;
            }
        }
        if(!checkAvailablePieces()) {
            return this.currentPlayer == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
        }
        return null;
    }

    /**
     * Generate a list of valid neighbors from a location based on board type
     *
     * @param source the source move
     * @param pattern the movement pattern
     * @return a list of valid neighbors
     */
    public abstract List<MyMove> validNeighbors(MyPiece piece, MyMove source, EscapePiece.MovementPattern pattern);

    /**
     * Adds a neighbor to the list of neighbors if the new location is valid
     *
     * @param sourcePiece the initial source piece
     * @param source the source location
     * @param x the x difference
     * @param y the y difference
     * @param neighbors the list of neighbors to update
     * @return a list of valid neighbors
     */
    public void addNeighbor(MyPiece sourcePiece, MyMove source, int x, int y, List<MyMove> neighbors) {
        MyCoordinate newCoordinate = new MyCoordinate(source.getLocation().getCoordinate().getX() + x, source.getLocation().getCoordinate().getY() + y);
        MyLocation newLocation = this.board.getLocation(newCoordinate);
        if(newLocation != null) {
            if(newLocation.canMoveOver(sourcePiece, this.ruleMap)) {
                neighbors.add(new MyMove(source.getMovementDirection(), newLocation));
            } else if(newLocation.getLocationType() == LocationType.EXIT) {
                neighbors.add(new MyMove(source.getMovementDirection(), newLocation));
            }
        }
    }

    /**
     * Adds a neighbor to the list of neighbors if the new location is valid
     *
     * @param source the source location
     * @param movementDirection the movementDirection to use for new moves
     * @param x the x difference
     * @param y the y difference
     * @param neighbors the list of neighbors to update
     * @return a list of valid neighbors
     */
    public void addNeighbor(MyPiece sourcePiece, MyMove source, MyMove.MovementDirections movementDirection, int x, int y, List<MyMove> neighbors) {
        MyCoordinate newCoordinate = new MyCoordinate(source.getLocation().getCoordinate().getX() + x, source.getLocation().getCoordinate().getY() + y);
        MyLocation newLocation = this.board.getLocation(newCoordinate);
        if(newLocation != null) {
            if(newLocation.canMoveOver(sourcePiece, this.ruleMap)) {
                neighbors.add(new MyMove(movementDirection, newLocation));
            } else if(newLocation.getLocationType() == LocationType.EXIT) {
                neighbors.add(new MyMove(movementDirection, newLocation));
            }
        }
    }

    /**
     * Perform BFS on a graph and return the first found valid path
     *
     * @param from source location
     * @param to destination location
     * @return path or null if no path exists
     */
    private List<MyMove> findPath(MyLocation from, MyLocation to) {
        Queue<List<MyMove>> queue = new LinkedList<>();
        List<MyMove> exitPath = null;
        EscapePiece.MovementPattern movementPattern = from.getPiece().getDescriptor().getMovementPattern();
        queue.add(new ArrayList<>(Collections.singletonList(new MyMove(MyMove.MovementDirections.NOT_SPECIFIED, from))));
        while(!queue.isEmpty()) {
            List<MyMove> path = queue.remove();
            MyMove currentMove = path.get(path.size() - 1);
            if(currentMove.getLocation().equals(to)) {
                if(to.getLocationType() == LocationType.EXIT) {
                    return path;
                } else {
                    if(path.stream().anyMatch(l -> l.getLocation().getLocationType() == LocationType.EXIT)) {
                        exitPath = path;
                    } else {
                        return path;
                    }
                }

            }
            int pieceDistance;
            if(from.getPiece().getDescriptor().getAttribute(EscapePiece.PieceAttributeID.DISTANCE) != null) {
                pieceDistance = from.getPiece().getDescriptor().getAttribute(EscapePiece.PieceAttributeID.DISTANCE).getValue();
            } else {
                pieceDistance = from.getPiece().getDescriptor().getAttribute(EscapePiece.PieceAttributeID.FLY).getValue();
            }
            if(path.size() > pieceDistance) {
                continue;
            }
            List<MyMove> neighbors = this.validNeighbors(from.getPiece(), currentMove, movementPattern);
            for(MyMove m : neighbors) {
                List<MyMove> newPath = new ArrayList<>(path);
                newPath.add(m);
                queue.add(newPath);
            }
        }
        return exitPath;
    }
}
