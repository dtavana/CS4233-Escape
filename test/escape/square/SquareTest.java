package escape.square;

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
public class SquareTest extends BaseEscapeTest
{
    @BeforeAll
    static void classSetup()
    {
        configFile = "config/egc/SquareGame.egc";
        currentTests = "Square Game Tests";
    }

    static Stream<Arguments> initialPiecesProvider()
    {
        return Stream.of(
                arguments(8, 13, FROG, PLAYER1), arguments(8, 15, FROG, PLAYER1),
                arguments(7, 15, FROG, PLAYER1), arguments(6, 13, FROG, PLAYER1),
                arguments(6, 17, FROG, PLAYER1),
                arguments(8, 17, HORSE, PLAYER1), arguments(5, 15, HORSE, PLAYER1),
                arguments(4, 14, HORSE, PLAYER1),
                arguments(7, 16, BIRD, PLAYER1), arguments(5, 13, BIRD, PLAYER1),
                arguments(9, 18, BIRD, PLAYER1),
                arguments(10, 13, SNAIL, PLAYER1), arguments(10, 14, SNAIL, PLAYER1),
                arguments(10, 15, SNAIL, PLAYER1), arguments(10, 16, SNAIL, PLAYER1),
                arguments(10, 17, SNAIL, PLAYER1), arguments(10, 18, SNAIL, PLAYER1),
                arguments(9, 17, SNAIL, PLAYER1),
                arguments(9, 13, DOG, PLAYER1), arguments(7, 13, DOG, PLAYER1),
                arguments(5, 14, DOG, PLAYER1), arguments(4, 13, DOG, PLAYER1),
                arguments(3, 13, DOG, PLAYER1), arguments(3, 14, DOG, PLAYER1),
                arguments(4, 15, DOG, PLAYER1),

                arguments(8, 3, FROG, PLAYER2), arguments(8, 7, FROG, PLAYER2),
                arguments(7, 2, FROG, PLAYER2), arguments(7, 4, FROG, PLAYER2),
                arguments(7, 6, FROG, PLAYER2),
                arguments(6, 8, HORSE, PLAYER2), arguments(5, 5, HORSE, PLAYER2),
                arguments(4, 7, HORSE, PLAYER2),
                arguments(8, 2, BIRD, PLAYER2), arguments(8, 5, BIRD, PLAYER2),
                arguments(10, 8, SNAIL, PLAYER2), arguments(10, 9, SNAIL, PLAYER2),
                arguments(10, 10, SNAIL, PLAYER2), arguments(9, 9, SNAIL, PLAYER2),
                arguments(8, 9, SNAIL, PLAYER2), arguments(7, 9, SNAIL, PLAYER2),
                arguments(10, 4, DOG, PLAYER2), arguments(10, 5, DOG, PLAYER2));
    }

    static Stream<Arguments> basicOneMoveProvider()
    {
        return Stream.of(
                // valid moves
                arguments("Frog left move", 8, 15, 8, 14, true),
                arguments("Bird forced exit path move", 9, 18, 7, 18, true),
                // invalid moves
                arguments("Horse no valid move", 4, 14, 4, 1, false),
                arguments("Move off board", 10, 15, 12, 19, false),
                arguments("No piece on source", 1, 1, 1, 2, false),
                arguments("Dog exceeds distance limit", 7, 13, 11, 14, false),
                arguments("Move wrong player 1", 10, 10, 11, 10, false),
                arguments("Move to same location", 10, 14, 10, 14, false));
    }
}