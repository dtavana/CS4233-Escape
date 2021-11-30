package escape.custom;

import escape.required.LocationType;

import java.util.Objects;

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

    public MyPiece setPiece(MyPiece piece) {
        MyPiece oldPiece = this.piece;
        this.piece = piece;
        return oldPiece;
    }

    public MyCoordinate getCoordinate() {
        return coordinate;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MyLocation)) {
            return false;
        }
        MyLocation locationObj = (MyLocation) obj;
        return coordinate.equals(locationObj.getCoordinate());
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
