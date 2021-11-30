package escape.custom;

import escape.required.LocationType;

public class MyLocation {
    private MyCoordinate coordinate;
    private MyPiece piece;
    private LocationType locationType;

    public MyLocation(MyCoordinate coordinate, LocationType locationType, MyPiece piece) {
        this.coordinate = coordinate;
        this.locationType = locationType;
        this.piece = piece;
    }

    public MyPiece getPiece() {
        return piece;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "MyLocation [coordinate=" + coordinate + ", piece=" + piece + ", locationType=" + locationType + "]";
    }
}
