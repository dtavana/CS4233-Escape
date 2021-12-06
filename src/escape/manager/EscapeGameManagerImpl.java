package escape.manager;

import escape.EscapeGameManager;
import escape.component.MyBoard;
import escape.component.MyCoordinate;
import escape.component.MyLocation;
import escape.component.MyPiece;
import escape.gamedef.EscapePiece;
import escape.gamedef.LocationType;
import escape.gamedef.Player;
import escape.gamedef.Rule;
import escape.util.*;

import java.util.*;

public abstract class EscapeGameManagerImpl implements EscapeGameManager<MyCoordinate> {

    private final EscapeGameInitializer config;
    private final HashMap<EscapePiece.PieceName, PieceTypeDescriptor> piecesMap;
    private final HashMap<Rule.RuleID, Integer> ruleMap;
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
            System.out.println("Game is over and results in a draw");
            return false;
        }
        else if(this.winner != null) {
            // Game is already over
            System.out.println("Game is over and PLAYER" + (this.winner == Player.PLAYER1 ? "1" : "2") + " has won");
            return false;
        }

        MyLocation fromLocation, toLocation;
        fromLocation = this.board.getLocation(from);
        toLocation = this.board.getLocation(to);
        if(checkBaseConditions(fromLocation, toLocation)) {
            List<MyLocation> path = findPath(fromLocation, toLocation);
            if(path != null) {
                // Actually perform the piece move
                performMove(path);
                // Check for draw conditions
                if(checkDrawConditions()) {
                    System.out.println("Game is over and results in a draw");
                } else {
                    // Check for win conditions
                    Player newWinner = checkWinConditions();
                    if(newWinner != null) {
                        this.winner = newWinner;
                        System.out.println("PLAYER" + (this.winner == Player.PLAYER1 ? "1" : "2") + " wins");
                    }
                }
                return true;
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
     * Actually update the board and point scores for players once a move has been verified
     *
     * @param path path that the move took
     */
    public void performMove(List<MyLocation> path) {
        MyLocation from = path.get(0);
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
    private MyLocation getEndLocationFromPath(List<MyLocation> path) {
        for(MyLocation l : path) {
            if(l.getLocationType() == LocationType.EXIT) {
                return l;
            }
        }
        return path.get(path.size() - 1);
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
            System.out.println("From location was not occupied by a piece " + from.getCoordinate());
            return false;
        }
        if(from.getPiece().getPlayer() != currentPlayer) {
            System.out.println("From location piece did not match player " + from);
            return false;
        }
        if(from.equals(to)) {
            System.out.println("Moving to same location " + from.getCoordinate() + ":" + to.getCoordinate());
            return false;
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
     * @param source the source location
     * @return a list of valid neighbors
     */
    public abstract List<MyLocation> validNeighbors(MyLocation source);

    /**
     * Perform BFS on a graph and return the first found valid path
     *
     * @param from source location
     * @param to destination location
     * @return path or null if no path exists
     */
    private List<MyLocation> findPath(MyLocation from, MyLocation to) {
        Queue<List<MyLocation>> queue = new LinkedList<>();
        List<MyLocation> exitPath = null;
        queue.add(new ArrayList<>(Collections.singletonList(from)));
        while(!queue.isEmpty()) {
            List<MyLocation> path = queue.remove();
            MyLocation currentLocation = path.get(path.size() - 1);
            if(currentLocation.equals(to)) {
                if(to.getLocationType() == LocationType.EXIT) {
                    return path;
                } else {
                    if(path.stream().anyMatch(l -> l.getLocationType() == LocationType.EXIT)) {
                        exitPath = path;
                    } else {
                        return path;
                    }
                }

            }
            int pieceDistance = from.getPiece().getDescriptor().getAttribute(EscapePiece.PieceAttributeID.DISTANCE).getValue();
            if(path.size() > pieceDistance) {
                continue;
            }
            List<MyLocation> neighbors = this.validNeighbors(currentLocation);
            for(MyLocation l : neighbors) {
                List<MyLocation> newPath = new ArrayList<>(path);
                newPath.add(l);
                queue.add(newPath);
            }
        }
        return exitPath;
    }
}
