package escape.custom;

import escape.util.EscapeGameInitializer;

public class EscapeGameManagerImplSquare extends EscapeGameManagerImpl {
    public EscapeGameManagerImplSquare(EscapeGameInitializer config) {
        super(config);
        setupBoard();
    }

    /**
     * Make the move in the current game.
     *
     * @param from starting location
     * @param to   ending location
     * @return true if the move was legal, false otherwise
     */
    @Override
    public boolean move(MyCoordinate from, MyCoordinate to) {
        if(to.equals(from)) {
            // Moving to the same location
            return false;
        }
        return false;
    }

    public boolean move()
}
