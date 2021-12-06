package escape.manager;

import escape.component.MyCoordinate;
import escape.component.MyLocation;
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
     * @return a list of valid neighbors
     */
    @Override
    public List<MyLocation> validNeighbors(MyLocation source) {
        ArrayList<MyLocation> neighbors = new ArrayList<>();
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                if(x == 0 && y == 0) {
                    continue;
                }
                MyCoordinate newCoordinate = new MyCoordinate(source.getCoordinate().getX() + x, source.getCoordinate().getY() + y);
                MyLocation newLocation = this.board.getLocation(newCoordinate);
                if(newLocation != null && (newLocation.canMoveOver(source.getPiece()) || newLocation.getLocationType() == LocationType.EXIT)) {
                    neighbors.add(newLocation);
                }
            }
        }
        return neighbors;
    }

}
