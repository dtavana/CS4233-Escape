package escape.component;

import escape.gamedef.Player;

import java.util.Arrays;
import java.util.HashMap;

public class MyBoard {
    private final int xMax;
    private final int yMax;
    private HashMap<MyCoordinate, MyLocation> map;

    /**
     * The constructor takes an xMax and yMax
     * @param xMax the max x value
     * @param yMax the max y value
     */
    public MyBoard(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        map = new HashMap<>();
    }

    /**
     * Adds a coordinate->location pair in the map
     * @param c the coordinate
     * @param l the location
     */
    public void addPair(MyCoordinate c, MyLocation l) {
        this.map.put(c, l);
    }

    /**
     * Determines if a target coordinate is in the bounds of the board
     * @param c the coordinate
     * @return true if in bounds, false otherwise
     */
    private boolean inBounds(MyCoordinate c) {
        boolean xInBounds = false, yInBounds = false;
        if(xMax == 0) {
            xInBounds = true;
        } else {
            xInBounds = c.getX() >= 1 && c.getX() <= this.xMax;
        }
        if(yMax == 0) {
            yInBounds = true;
        } else {
            yInBounds = c.getY() >= 1 && c.getY() <= this.yMax;
        }
        return xInBounds && yInBounds;
    }

    /**
     * Return an existing location on the map from a given coordinate
     * or generate a new location
     * @param c the coordinate
     * @return a new location or null if the given coordinate
     * can not be mapped to a valid location
     */
    public MyLocation getLocation(MyCoordinate c) {
        MyLocation existingLocation = this.map.get(c);
        if(existingLocation != null) {
            return existingLocation;
        }
        if(this.inBounds(c)) {
            return new MyLocation(c);
        }
        return null;
    }

    /**
     * Query the map for a piece that is owned by the given player
     *
     * @param player the player
     * @return true if they have a piece to move, false otherwise
     */
    public boolean hasAvailablePieces(Player player) {
        return this.map.values().stream().anyMatch(l -> l.getPiece() != null && l.getPiece().getPlayer() == player);
    }
}
