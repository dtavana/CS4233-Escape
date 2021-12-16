package escape.manager;

import escape.component.MyMove;
import escape.component.MyPiece;
import escape.exception.EscapeException;
import escape.gamedef.EscapePiece;
import escape.util.EscapeGameInitializer;
import java.util.ArrayList;
import java.util.List;

public class EscapeGameManagerImplHex extends EscapeGameManagerImpl {
    /**
     * The constructor takes a escape game config
     * @param config the config
     */
    public EscapeGameManagerImplHex(EscapeGameInitializer config) {
        super(config);
        setupBoard();
    }

    /**
     * Generate a list of valid neighbors from a location based on board type
     *
     * @param source the source location
     * @param pattern the movement pattern for the source piece
     * @return a list of valid neighbors
     */
    @Override
    public List<MyMove> validNeighbors(MyPiece sourcePiece, MyMove source, EscapePiece.MovementPattern pattern) {
        ArrayList<MyMove> neighbors = new ArrayList<>();
        if(pattern == EscapePiece.MovementPattern.LINEAR) {
            switch (source.getMovementDirection()) {
                case VERTICAL:
                    // UP
                    this.addNeighbor(sourcePiece, source, 1, 0, neighbors);
                    // DOWN
                    this.addNeighbor(sourcePiece, source, -1, 0, neighbors);
                    break;
                case RIGHT_DIAGONAL:
                    // UP RIGHT
                    this.addNeighbor(sourcePiece, source, 1, 1, neighbors);
                    // DOWN LEFT
                    this.addNeighbor(sourcePiece, source, -1, -1, neighbors);
                    break;
                case LEFT_DIAGONAL:
                    // UP LEFT
                    this.addNeighbor(sourcePiece, source, 1, -1, neighbors);
                    // DOWN RIGHT
                    this.addNeighbor(sourcePiece, source, -1, 1, neighbors);
                    break;
                case NOT_SPECIFIED:
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.VERTICAL, 1, 0, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.VERTICAL,-1, 0, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.RIGHT_DIAGONAL, 1, 1, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.RIGHT_DIAGONAL, -1, -1, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.LEFT_DIAGONAL, 1, -1, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.LEFT_DIAGONAL,  -1, 1, neighbors);

            }
        } else if (pattern == EscapePiece.MovementPattern.OMNI) {
            for(int x = -1; x <= 1; x++) {
                int[] validY = getYFromX(x);
                for(int y : validY) {
                    this.addNeighbor(sourcePiece, source, x, y, neighbors);
                }
            }
        }
        else {
            this.observerManager.notify("Unsupported piece movement pattern: " + source.getLocation().getPiece().getDescriptor().getMovementPattern().name(),
                    new EscapeException("Unsupported piece movement pattern"));
        }
        return neighbors;
    }

    /**
     * Generate valid y values from a given x value
     * @param x given x coordinate
     * @return an array of possible valid y values
     */
    private int[] getYFromX(int x) {
        if(x == -1) {
            return new int[] {0, 1};
        } else if(x == 0) {
            return new int[] {-1, 1};
        }
        return new int[] {-1, 0};
    }
}