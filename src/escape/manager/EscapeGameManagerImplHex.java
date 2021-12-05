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
    public List<MyLocation> findPath(MyLocation from, MyLocation to) {
        return null;
    }

    @Override
    public List<MyLocation> validNeighbors(MyLocation source) {
        return null;
    }
}