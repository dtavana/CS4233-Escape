package escape;

import static escape.gamedef.EscapePiece.PieceName.*;
import static escape.gamedef.Player.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

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
                arguments(6, 17, FROG, PLAYER1), arguments(8, 17, HORSE, PLAYER1),
                arguments(5, 15, HORSE, PLAYER1), arguments(4, 14, HORSE, PLAYER1),
                arguments(7, 16, BIRD, PLAYER1), arguments(5, 13, BIRD, PLAYER1),
                arguments(10, 13, SNAIL, PLAYER1), arguments(10, 14, SNAIL, PLAYER1),
                arguments(10, 15, SNAIL, PLAYER1), arguments(10, 16, SNAIL, PLAYER1),
                arguments(10, 17, SNAIL, PLAYER1), arguments(10, 18, SNAIL, PLAYER1),
                arguments(9, 13, DOG, PLAYER1), arguments(7, 13, DOG, PLAYER1),

                arguments(8, 3, FROG, PLAYER2), arguments(8, 7, FROG, PLAYER2),
                arguments(7, 2, FROG, PLAYER2), arguments(7, 4, FROG, PLAYER2),
                arguments(7, 6, FROG, PLAYER2), arguments(6, 8, HORSE, PLAYER2),
                arguments(5, 5, HORSE, PLAYER2), arguments(4, 7, HORSE, PLAYER2),
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
                arguments("Bird omni move", 7, 16, 1, 7, true));
                /*
                // invalid moves
                arguments("Move off board", 12, 17, 12, 19, false),
                arguments("No piece on source", 1, 1, 1, 2, false),
                arguments("Horse exceeds limit", 10, 13, 17, 6, false),
                arguments("Move wrong player 1", 10, 10, 11, 10, false),
                arguments("Bird not linear", 5, 13, 4, 11, false));
                */
    }
}