package escape.manager;

import escape.component.MyLocation;
import escape.util.EscapeGameInitializer;

import java.util.List;

public class EscapeGameManagerImplHex extends EscapeGameManagerImpl {
    public EscapeGameManagerImplHex(EscapeGameInitializer config) {
        super(config);
        setupBoard();
    }

    @Override
    public boolean isValidMoveOnBoard(MyLocation from, MyLocation to) {
        return false;
    }

    @Override
    public List<MyLocation> validNeighbors(MyLocation source) {
        return null;
    }
}