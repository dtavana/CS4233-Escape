package escape.component;

import escape.gamedef.LocationType;

public class MyMove {
    public static enum MovementDirections {
        VERTICAL,
        HORIZONTAL,
        RIGHT_DIAGONAL,
        LEFT_DIAGONAL,
        NOT_SPECIFIED
    }

    private final MovementDirections movementDirection;
    private final MyLocation location;

    /**
     * The constructor takes a movement direction and location
     * @param movementDirection the movement direction
     * @param location the location
     */
    public MyMove(MovementDirections movementDirection, MyLocation location) {
        this.movementDirection = movementDirection;
        this.location = location;
    }

    /**
     * Get the movementDirection for this move
     * @return the movementDirection
     */
    public MovementDirections getMovementDirection() {
        return this.movementDirection;
    }

    /**
     * Get the movementDirection for this move
     * @return the movementDirection
     */
    public MyLocation getLocation() {
        return this.location;
    }

    public String toString() {
        return "[MyMove movementDirection=" + movementDirection + ", location=" + location + "]";
    }
}
