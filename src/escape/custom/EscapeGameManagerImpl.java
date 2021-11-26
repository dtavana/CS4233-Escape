package escape.custom;

import escape.EscapeGameManager;
import escape.exception.EscapeException;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;
import escape.util.EscapeGameInitializer;
import escape.util.LocationInitializer;
import escape.util.PieceTypeDescriptor;

import java.util.HashMap;

public abstract class EscapeGameManagerImpl implements EscapeGameManager<MyCoordinate> {

    private EscapeGameInitializer config;
    private HashMap<EscapePiece.PieceName, PieceTypeDescriptor> piecesMap;
    private Board board;

    /**
     * The constructor takes a escape game config
     * @param config the config
     * @throws Exception on any errors
     */
    public EscapeGameManagerImpl(EscapeGameInitializer config) {
        this.config = config;
        this.board = new Board(config.getxMax(), config.getyMax());
        this.piecesMap = new HashMap<>();
        for(PieceTypeDescriptor p : config.getPieceTypes())  {
            // Setup pieceName:pieceDescriptor map
            this.piecesMap.put(p.getPieceName(), p);
        }
    }

    protected void setupBoard() {
        for(LocationInitializer i : this.config.getLocationInitializers()) {
            MyCoordinate c = makeCoordinate(i.x, i.y);
            c.setLocationType(i.locationType);
            MyPiece p = new MyPiece(this.piecesMap.get(i.pieceName), i.player);
            this.board.addPair(c, p);
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
        return this.board.get(coordinate);
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
        MyCoordinate c = new MyCoordinate(x, y);
        return c;
    }
}
