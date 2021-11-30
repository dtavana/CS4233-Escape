package escape.custom;

import escape.util.EscapeGameInitializer;

public class EscapeGameManagerImplHex extends EscapeGameManagerImpl {
    public EscapeGameManagerImplHex(EscapeGameInitializer config) {
        super(config);
        setupBoard();
    }

    @Override
    public boolean isValidMoveOnBoard(MyLocation from, MyLocation to) {
        return false;
    }
}