package escape;

import static org.junit.jupiter.api.Assertions.*;

import escape.EscapeGameBuilder;
import escape.component.MyCoordinate;
import escape.component.MyObserver;
import escape.gamedef.GameObserver;
import escape.manager.EscapeGameManagerImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Base Escape test file. This sets up the tests for all of the other tests.
 */
public abstract class BaseEscapeRelease2Test
{
    protected static EscapeGameManagerImpl manager;
    protected static MyObserver observer;
    protected static String configFile;
    protected static String currentTests;
    protected static String dummyTestName = "Dummy Test";

    @ParameterizedTest
    @MethodSource("release2TestProvider")
    void release2TestProvider(String testName, int x1, int y1, int x2, int y2, int player1score, int player2score, boolean expected) throws Exception {
        if(testName.equals(dummyTestName)) {
            EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
            manager = (EscapeGameManagerImpl) egb.makeGameManager();
            observer = new MyObserver();
            manager.addObserver(observer);
            assertNotNull(manager);
        } else {
            multipleMoveHelper(x1, y1, x2, y2, player1score, player2score, expected);
        }
    }

    @ParameterizedTest
    @MethodSource("release2TestProviderTestNewBaseConditions")
    void release2TestProviderTestNewBaseConditions(String testName, int x1, int y1, int x2, int y2, int player1score, int player2score, boolean expected) throws Exception {
        if(testName.equals(dummyTestName)) {
            EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
            manager = (EscapeGameManagerImpl) egb.makeGameManager();
            observer = new MyObserver();
            manager.addObserver(observer);
            assertNotNull(manager);
        } else {
            multipleMoveHelper(x1, y1, x2, y2, player1score, player2score, expected);
        }
    }

    // Helpers
    void multipleMoveHelper(int x1, int y1, int x2, int y2, int player1score, int player2score, boolean expected)
    {
        MyCoordinate c1 = manager.makeCoordinate(x1, y1);
        MyCoordinate c2 = manager.makeCoordinate(x2, y2);
        assertEquals(expected, manager.move(c1, c2));
        assertEquals(player1score, manager.getPlayerOneScore());
        assertEquals(player2score, manager.getPlayerTwoScore());
    }
}