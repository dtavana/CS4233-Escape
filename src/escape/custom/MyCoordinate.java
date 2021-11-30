package escape.custom;

import escape.required.Coordinate;
import escape.required.LocationType;

import java.util.Objects;

public class MyCoordinate implements Coordinate {

    private final int x;
    private int y;

    public MyCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

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

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "MyCoordinate [x=" + x + ", y=" + y + "]";
    }
}
