package escape.manager;

import escape.component.MyCoordinate;
import escape.component.MyLocation;
import escape.gamedef.LocationType;
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
     * @return a list of valid neighbors
     */
    @Override
    public List<MyLocation> validNeighbors(MyLocation source) {
        ArrayList<MyLocation> neighbors = new ArrayList<>();
        for(int x = -1; x <= 1; x++) {
            int[] validY = getYFromX(x);
            for(int y : validY) {
                MyCoordinate newCoordinate = new MyCoordinate(source.getCoordinate().getX() + x, source.getCoordinate().getY() + y);
                MyLocation newLocation = this.board.getLocation(newCoordinate);
                if(newLocation != null && (newLocation.canMoveOver(source.getPiece()) || newLocation.getLocationType() == LocationType.EXIT)) {
                    neighbors.add(newLocation);
                }
            }

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