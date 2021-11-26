package escape.custom;

import escape.required.Coordinate;
import escape.required.LocationType;

import java.util.Objects;

public class MyCoordinate implements Coordinate {

    private int x, y;
    private LocationType locationType;

    public MyCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
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
        return "MyCoordinate [x=" + x + ", y=" + y + ", locationType=" + locationType + "]";
    }
}
