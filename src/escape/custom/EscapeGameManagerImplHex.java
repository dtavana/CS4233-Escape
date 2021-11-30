package escape.custom;

import escape.util.EscapeGameInitializer;

public class EscapeGameManagerImplHex extends EscapeGameManagerImpl {
    public EscapeGameManagerImplHex(EscapeGameInitializer config) {
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
        return false;
    }
}