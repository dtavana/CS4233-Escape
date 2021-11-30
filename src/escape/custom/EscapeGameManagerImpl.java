package escape.custom;

import escape.EscapeGameManager;
import escape.exception.EscapeException;
import escape.required.EscapePiece;
import escape.required.LocationType;
import escape.required.Player;
import escape.required.Rule;
import escape.util.EscapeGameInitializer;
import escape.util.LocationInitializer;
import escape.util.PieceTypeDescriptor;
import escape.util.RuleDescriptor;

import java.util.HashMap;

public abstract class EscapeGameManagerImpl implements EscapeGameManager<MyCoordinate> {

    private final EscapeGameInitializer config;
    private final HashMap<EscapePiece.PieceName, PieceTypeDescriptor> piecesMap;
    private final HashMap<Rule.RuleID, Integer> ruleMap;
    private final MyBoard board;
    private Player currentPlayer;
    private int playerOneScore, playerTwoScore, turnCount;

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
        for(PieceTypeDescriptor p : config.getPieceTypes())  {
            // Setup pieceName:pieceDescriptor map
            this.piecesMap.put(p.getPieceName(), p);
        }
        for(RuleDescriptor r : config.getRules()) {
            this.ruleMap.put(r.ruleId, r.ruleValue);
        }

    }

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
        System.out.println(board.toString());
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
        MyLocation fromLocation, toLocation;
        fromLocation = this.board.getLocation(from);
        toLocation = this.board.getLocation(to);
        boolean valid = isValidMove(fromLocation, toLocation);
        if(valid) {
            // Actually perform the piece move
            performMove(fromLocation, toLocation);
        }
        return valid;
    }

    public boolean isValidMove(MyLocation from, MyLocation to) {
        if(from.getPiece() == null) {
            return false;
        }
        if(from.getPiece().getPlayer() != currentPlayer) {
            return false;
        }
        if(from.equals(to)) {
            return false;
        }
        return isValidMoveOnBoard(from, to);
    }

    public abstract boolean isValidMoveOnBoard(MyLocation from, MyLocation to);

    public void performMove(MyLocation from, MyLocation to) {
        if(to.getLocationType() == LocationType.EXIT) {
            // Remove piece as its moving
            from.setPiece(null);
            int pieceValue = to.getPiece().getDescriptor().getAttribute(EscapePiece.PieceAttributeID.VALUE).getValue();
            if(pieceValue <= 0) {
                // Default piece value
                pieceValue = 1;
            }
            if(currentPlayer == Player.PLAYER1) {
                playerOneScore += pieceValue;
            } else {
                playerTwoScore += pieceValue;
            }
        }
        turnCount++;
        currentPlayer = currentPlayer == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
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
}
