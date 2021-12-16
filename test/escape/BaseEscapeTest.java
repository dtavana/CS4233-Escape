package escape;

import static org.junit.jupiter.api.Assertions.*;
import escape.component.MyCoordinate;
import escape.component.MyObserver;
import escape.gamedef.EscapePiece;
import escape.gamedef.GameObserver;
import escape.gamedef.Player;
import escape.manager.EscapeGameManagerImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Base Escape test file. This sets up the tests for all of the other tests.
 */
public abstract class BaseEscapeTest
{
    protected static EscapeGameManagerImpl manager;
    protected static MyObserver observer;
    protected static String configFile;
    protected static String currentTests;

    @BeforeEach
    void loadGame() throws Exception
    {
        EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
        manager = (EscapeGameManagerImpl) egb.makeGameManager();
        observer = new MyObserver();
        manager.addObserver(observer);
        assertNotNull(manager);
    }

    // Shared tests
    @ParameterizedTest
    @MethodSource("initialPiecesProvider")
    void initialPiecesTest(int x1, int y1, EscapePiece.PieceName type, Player player)
    {
        MyCoordinate c = manager.makeCoordinate(x1, y1);
        EscapePiece p = manager.getPieceAt(c);
        assertNotNull(p);
        assertEquals(p.getName(), type);
        assertEquals(p.getPlayer(), player);
    }

    @ParameterizedTest
    @MethodSource("basicOneMoveProvider")
    void basicOneMove(String testName, int x1, int y1, int x2, int y2,
                      boolean expected) {
        oneMoveHelper(x1, y1, x2, y2, expected);
    }
    // Helpers
    void oneMoveHelper(int x1, int y1, int x2, int y2, boolean expected)
    {
        MyCoordinate c1 = manager.makeCoordinate(x1, y1);
        MyCoordinate c2 = manager.makeCoordinate(x2, y2);
        assertEquals(expected, manager.move(c1, c2));
    }
}