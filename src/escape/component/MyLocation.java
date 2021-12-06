package escape.component;

import escape.gamedef.LocationType;

public class MyLocation {
    private final MyCoordinate coordinate;
    private MyPiece piece;
    private LocationType locationType = LocationType.CLEAR;

    /**
     * The constructor takes a coordinate
     * @param coordinate the coordinate
     */
    public MyLocation(MyCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * The constructor takes an coordinate, location type, and a piece
     * @param coordinate the coordinate
     * @param locationType the location type
     * @param piece the piece
     */
    public MyLocation(MyCoordinate coordinate, LocationType locationType, MyPiece piece) {
        this.coordinate = coordinate;
        if(locationType != null) {
            this.locationType = locationType;
        }
        this.piece = piece;
    }

    /**
     * Get the piece for this location
     * @return the piece
     */
    public MyPiece getPiece() {
        return piece;
    }

    /**
     * Set the piece for this location
     * @param piece the piece
     */
    public void setPiece(MyPiece piece) {
        this.piece = piece;
    }

    /**
     * Get the coordinate for this location
     * @return the coordinate
     */
    public MyCoordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Get the location type for this location
     * @return the location type
     */
    public LocationType getLocationType() {
        return locationType;
    }

    /**
     * Check if a piece can move over this location
     *
     * @param sourcePiece the piece
     * @return true if it can, false otherwise
     */
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
