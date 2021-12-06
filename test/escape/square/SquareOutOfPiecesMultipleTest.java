package escape.square;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.stream.Stream;

import escape.BaseEscapeMultipleTest;
import escape.EscapeGameBuilder;
import escape.component.MyCoordinate;
import escape.manager.EscapeGameManagerImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Test cases that are run in the superclass
 */
public class SquareOutOfPiecesMultipleTest
{
    private static EscapeGameManagerImpl manager;

    @BeforeAll
    static void classSetup() throws Exception {
        EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/RunOutOfMoves.egc");
        manager = (EscapeGameManagerImpl) egb.makeGameManager();
        assertNotNull(manager);
    }

    @ParameterizedTest
    @MethodSource("runOutOfMovesProvider")
    void playerTwoWinTurnLimitProvider(String testName, int x1, int y1, int x2, int y2, int player1score, int player2score, boolean expected) throws Exception {
        multipleMoveHelper(x1, y1, x2, y2, player1score, player2score, expected);
    }

    static Stream<Arguments> runOutOfMovesProvider()
    {
        return Stream.of(
                arguments("Move frog to exit", 1, 1, 1, 3, 1, 0, true),
                arguments("Move frog to exit", 1, 2, 1, 3, 1, 1, true),
                arguments("Winner already present", 1, 1, 1, 1, 1, 1, false));

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