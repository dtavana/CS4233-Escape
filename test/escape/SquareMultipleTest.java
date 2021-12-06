package escape;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.stream.Stream;
import escape.manager.EscapeGameManagerImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;

/**
 * Test cases that are run in the superclass
 */
public class SquareMultipleTest extends BaseEscapeMultipleTest
{
    @BeforeAll
    static void classSetup() throws Exception {
        configFile = "config/egc/SquareGame.egc";
        currentTests = "Square Game Multiple Moves Tests";
        EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
        manager = (EscapeGameManagerImpl) egb.makeGameManager();
        assertNotNull(manager);
    }

    static Stream<Arguments> playerOneWinTurnLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move Horse to exit", 8, 17, 8, 18, 1, 0, true),
                arguments("Move from location with no piece", 10, 18, 11, 18, 1, 0, false),
                arguments("Move bird", 8, 5, 5, 7, 1, 0, true),
                arguments("Move bird", 5, 13, 12, 12, 1, 0, true),
                arguments("Move snail more than specified distance", 7, 9, 7, 11, 1, 0, false),
                arguments("Move snail", 7, 9, 7, 10, 1, 0, true),
                arguments("Move horse", 4, 14, 9, 12, 1, 0, true),
                arguments("Winner already present", 1, 1, 1, 1, 1, 0, false));
    }

    static Stream<Arguments> playerTwoWinTurnLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move Horse to exit", 8, 17, 7, 17, 0, 0, true),
                arguments("Move from location with no piece", 10, 18, 11, 18, 0, 0, false),
                arguments("Move bird", 8, 5, 12, 4, 0, 1, true),
                arguments("Move bird", 5, 13, 12, 12, 0, 1, true),
                arguments("Move snail more than specified distance", 7, 9, 7, 11, 0, 1, false),
                arguments("Move snail", 7, 9, 7, 10, 0, 1, true),
                arguments("Move horse", 4, 14, 9, 12, 0, 1, true),
                arguments("Winner already present", 1, 1, 1, 1, 0, 1, false));
    }

    static Stream<Arguments> playerOneWinScoreLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move Horse to exit", 8, 17, 8, 18, 1, 0, true),
                arguments("Move Dog", 10, 4, 12, 5, 1, 0, true),
                arguments("Move Bird to exit", 9, 18, 8, 18, 2, 0, true),
                arguments("Winner already present", 1, 1, 1, 1, 2, 0, false));
    }

    static Stream<Arguments> playerTwoWinScoreLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move Horse to exit", 8, 17, 8, 18, 1, 0, true),
                arguments("Move Dog to exit", 10, 4, 12, 4, 1, 2, true),
                arguments("Winner already present", 1, 1, 1, 1, 1, 2, false));
    }

    static Stream<Arguments> drawTurnLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move Horse to exit", 8, 17, 8, 18, 1, 0, true),
                arguments("Move from location with no piece", 10, 18, 11, 18, 1, 0, false),
                arguments("Move bird to exit", 8, 2, 12, 4, 1, 1, true),
                arguments("Move bird", 5, 13, 12, 12, 1, 1, true),
                arguments("Move snail more than specified distance", 7, 9, 7, 11, 1, 1, false),
                arguments("Move snail", 7, 9, 7, 10, 1, 1, true),
                arguments("Move horse", 4, 14, 9, 12, 1, 1, true),
                arguments("Draw already present", 1, 1, 1, 1, 1, 1, false));
    }
}