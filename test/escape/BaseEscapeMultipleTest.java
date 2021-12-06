package escape;

import static org.junit.jupiter.api.Assertions.*;
import escape.component.MyCoordinate;
import escape.manager.EscapeGameManagerImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Base Escape test file. This sets up the tests for all of the other tests.
 */
public abstract class BaseEscapeMultipleTest
{
    protected static EscapeGameManagerImpl manager;
    protected static String configFile;
    protected static String currentTests;
    protected static String dummyTestName = "Dummy Test";

    @ParameterizedTest
    @MethodSource("playerOneWinTurnLimitProvider")
    void playerOneWinTurnLimitProvider(String testName, int x1, int y1, int x2, int y2, int player1score, int player2score, boolean expected) throws Exception {
        if(testName.equals(dummyTestName)) {
            EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
            manager = (EscapeGameManagerImpl) egb.makeGameManager();
            assertNotNull(manager);
        } else {
            multipleMoveHelper(x1, y1, x2, y2, player1score, player2score, expected);
        }
    }

    @ParameterizedTest
    @MethodSource("playerTwoWinTurnLimitProvider")
    void playerTwoWinTurnLimitProvider(String testName, int x1, int y1, int x2, int y2, int player1score, int player2score, boolean expected) throws Exception {
        if(testName.equals(dummyTestName)) {
            EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
            manager = (EscapeGameManagerImpl) egb.makeGameManager();
            assertNotNull(manager);
        } else {
            multipleMoveHelper(x1, y1, x2, y2, player1score, player2score, expected);
        }
    }

    @ParameterizedTest
    @MethodSource("playerOneWinScoreLimitProvider")
    void playerOneWinScoreLimitProvider(String testName, int x1, int y1, int x2, int y2, int player1score, int player2score, boolean expected) throws Exception {
        if(testName.equals(dummyTestName)) {
            EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
            manager = (EscapeGameManagerImpl) egb.makeGameManager();
            assertNotNull(manager);
        } else {
            multipleMoveHelper(x1, y1, x2, y2, player1score, player2score, expected);
        }
    }

    @ParameterizedTest
    @MethodSource("playerTwoWinScoreLimitProvider")
    void playerTwoWinScoreLimitProvider(String testName, int x1, int y1, int x2, int y2, int player1score, int player2score, boolean expected) throws Exception {
        if(testName.equals(dummyTestName)) {
            EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
            manager = (EscapeGameManagerImpl) egb.makeGameManager();
            assertNotNull(manager);
        } else {
            multipleMoveHelper(x1, y1, x2, y2, player1score, player2score, expected);
        }
    }

    @ParameterizedTest
    @MethodSource("drawTurnLimitProvider")
    void drawTurnLimitProvider(String testName, int x1, int y1, int x2, int y2, int player1score, int player2score, boolean expected) throws Exception {
        if(testName.equals(dummyTestName)) {
            EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
            manager = (EscapeGameManagerImpl) egb.makeGameManager();
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