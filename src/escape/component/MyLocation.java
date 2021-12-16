package escape.component;

import escape.gamedef.EscapePiece;
import escape.gamedef.LocationType;
import escape.gamedef.Rule;

import java.util.HashMap;

public class MyLocation {
    protected final MyCoordinate coordinate;
    protected MyPiece piece;
    protected LocationType locationType = LocationType.CLEAR;

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
     * @param ruleMap the rule map of the game
     * @return true if it can, false otherwise
     */
    public boolean canMoveOver(MyPiece sourcePiece, HashMap<Rule.RuleID, Integer> ruleMap) {
        if(sourcePiece.getDescriptor().getAttribute(EscapePiece.PieceAttributeID.FLY) != null) {
            return true;
        }
        if(sourcePiece.getDescriptor().getAttribute(EscapePiece.PieceAttributeID.UNBLOCK) == null && this.getLocationType() == LocationType.BLOCK) {
            // Can not move over a block location if do not have unblock
            return false;
        }
        if(this.locationType != LocationType.CLEAR) {
            // Can not move over a non-clear location
            return false;
        }
        if(!ruleMap.containsKey(Rule.RuleID.REMOVE) && this.piece != null) {
            // Can not move over a location with a piece on it if no remove
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
