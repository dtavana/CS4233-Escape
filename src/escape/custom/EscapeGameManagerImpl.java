package escape.custom;

import escape.EscapeGameManager;
import escape.required.EscapePiece;
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
     * @param from starting location
     * @param to   ending location
     * @return true if the move was legal, false otherwise
     */
    @Override
    public abstract boolean move(MyCoordinate from, MyCoordinate to);


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
