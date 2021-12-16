package escape.manager;

import escape.component.MyCoordinate;
import escape.component.MyLocation;
import escape.component.MyMove;
import escape.component.MyPiece;
import escape.exception.EscapeException;
import escape.gamedef.EscapePiece;
import escape.gamedef.LocationType;
import escape.util.EscapeGameInitializer;
import java.util.*;

public class EscapeGameManagerImplSquare extends EscapeGameManagerImpl {
    /**
     * The constructor takes a escape game config
     * @param config the config
     */
    public EscapeGameManagerImplSquare(EscapeGameInitializer config) {
        super(config);
        setupBoard();
    }

    /**
     * Generate a list of valid neighbors from a location based on board type
     *
     * @param source the source location
     * @param pattern the movement pattern
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
                case HORIZONTAL:
                    // LEFT
                    this.addNeighbor(sourcePiece, source, 0, -1, neighbors);
                    // RIGHT
                    this.addNeighbor(sourcePiece, source, 0, 1, neighbors);
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
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.HORIZONTAL, 0, -1, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.HORIZONTAL, 0, 1, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.RIGHT_DIAGONAL, 1, 1, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.RIGHT_DIAGONAL, -1, -1, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.LEFT_DIAGONAL, 1, -1, neighbors);
                    this.addNeighbor(sourcePiece, source, MyMove.MovementDirections.LEFT_DIAGONAL,  -1, 1, neighbors);

            }
        } else if (pattern == EscapePiece.MovementPattern.OMNI) {
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    if(x == 0 && y == 0) {
                        continue;
                    }
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
}
