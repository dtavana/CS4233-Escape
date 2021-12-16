package escape.component;

import escape.gamedef.Coordinate;

import java.util.Objects;

public class MyCoordinate implements Coordinate {

    private final int x;
    private int y;

    /**
     * The constructor takes an x and y value
     * @param x the x value
     * @param y the y value
     */
    public MyCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x value
     * @return the x value
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y value
     * @return the y value
     */
    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MyCoordinate)) {
            return false;
        }
        MyCoordinate coordinateObj = (MyCoordinate) obj;
        return x == coordinateObj.x && y == coordinateObj.y;
    }
}
