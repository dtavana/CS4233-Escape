package escape.hex;

import static escape.gamedef.EscapePiece.PieceName.*;
import static escape.gamedef.Player.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.stream.Stream;

import escape.BaseEscapeTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;

/**
 * Test cases that are run in the superclass
 */
public class HexTest extends BaseEscapeTest
{
    @BeforeAll
    static void classSetup()
    {
        configFile = "config/egc/HexGameInfinite.egc";
        currentTests = "Hex Infinite Game Tests";
    }

    static Stream<Arguments> initialPiecesProvider()
    {
        return Stream.of(
                arguments(0, 0, FROG, PLAYER1), arguments(0, -1, FROG, PLAYER1),
                arguments(1, -1, FROG, PLAYER1), arguments(-1, 0, FROG, PLAYER1),
                arguments(0, 1, FROG, PLAYER1),
                arguments(1, -3, HORSE, PLAYER1), arguments(2, -3, HORSE, PLAYER1),
                arguments(0, -2, HORSE, PLAYER1),
                arguments(0, -4, BIRD, PLAYER1),
                arguments(-1, -1, SNAIL, PLAYER1), arguments(-4, 4, SNAIL, PLAYER1),
                arguments(-2, -2, DOG, PLAYER1), arguments(-2, 1, DOG, PLAYER1),

                arguments(3, 0, FROG, PLAYER2), arguments(2, 0, FROG, PLAYER2),
                arguments(4, 0, FROG, PLAYER2), arguments(3, -1, FROG, PLAYER2),
                arguments(4, -1, FROG, PLAYER2),
                arguments(2, 1, HORSE, PLAYER2), arguments(3, 1, HORSE, PLAYER2),
                arguments(4, -4, HORSE, PLAYER2),
                arguments(4, -6, BIRD, PLAYER2),
                arguments(1, 4, SNAIL, PLAYER2), arguments(4, 3, SNAIL, PLAYER2),
                arguments(6, 1, DOG, PLAYER2), arguments(-1, 3, DOG, PLAYER2));
    }

    static Stream<Arguments> basicOneMoveProvider()
    {
        return Stream.of(
                // valid moves
                arguments("Frog up left move", 0, 1, -1, 2, true),
                arguments("Frog forced exit path move", 0, -1, 2, -2, true),
                // invalid moves
                arguments("No piece on source", 0, -5, 1, 2, false),
                arguments("Dog exceeds distance limit", -2, -2, 11, 14, false),
                arguments("Move wrong player 1", 4, -6, 11, 10, false),
                arguments("Move to same location", 0, -4, 0, -4, false));
    }
}