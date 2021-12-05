package escape.component;

import escape.gamedef.LocationType;

public class MyLocation {
    private MyCoordinate coordinate;
    private MyPiece piece;
    private LocationType locationType = LocationType.CLEAR;

    public MyLocation(MyCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    public MyLocation(MyCoordinate coordinate, LocationType locationType, MyPiece piece) {
        this.coordinate = coordinate;
        if(locationType != null) {
            this.locationType = locationType;
        }
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

    public boolean canMoveOver(MyPiece sourcePiece) {
        if(this.locationType != LocationType.CLEAR) {
            return false;
        }
        if(this.piece != null) {
            return false;
        }
        return true;
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
