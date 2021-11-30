package escape.custom;

import escape.util.EscapeGameInitializer;

public class EscapeGameManagerImplSquare extends EscapeGameManagerImpl {
    public EscapeGameManagerImplSquare(EscapeGameInitializer config) {
        super(config);
        setupBoard();
    }

    @Override
    public boolean isValidMoveOnBoard(MyLocation from, MyLocation to) {
        return false;
    }

}
