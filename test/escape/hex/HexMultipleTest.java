package escape.hex;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.stream.Stream;

import escape.BaseEscapeMultipleTest;
import escape.EscapeGameBuilder;
import escape.component.MyObserver;
import escape.manager.EscapeGameManagerImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;

/**
 * Test cases that are run in the superclass
 */
public class HexMultipleTest extends BaseEscapeMultipleTest
{
    @BeforeAll
    static void classSetup() throws Exception {
        configFile = "config/egc/HexGameInfinite.egc";
        currentTests = "Hex Infinite Game Multiple Moves Tests";
        EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
        manager = (EscapeGameManagerImpl) egb.makeGameManager();
        observer = new MyObserver();
        manager.addObserver(observer);
        assertNotNull(manager);
    }

    static Stream<Arguments> playerOneWinTurnLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move Frog to exit", 0, -1, 2, -2, 1, 0, true),
                arguments("Move from location with no piece", 10, 18, 11, 18, 1, 0, false),
                arguments("Move bird", 4, -6, 11, -6, 1, 0, true),
                arguments("Move bird", 0, -4, -1, -4, 1, 0, true),
                arguments("Move snail more than specified distance", 4, 3, 4, 6, 1, 0, false),
                arguments("Move snail", 4, 3, 4, 4, 1, 0, true),
                arguments("Move horse", 2, -3, 2, -2, 1, 0, true),
                arguments("Winner already present", 1, 1, 1, 1, 1, 0, false));
    }

    static Stream<Arguments> playerTwoWinTurnLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move Frog", 1, -1, 2, -2, 0, 0, true),
                arguments("Move from location with no piece", 10, 18, 11, 18, 0, 0, false),
                arguments("Move bird to exit", 4, -6, 3, -7, 0, 1, true),
                arguments("Move bird", 0, -4, -1, -4, 0, 1, true),
                arguments("Move snail", 4, 3, 4, 4, 0, 1, true),
                arguments("Move horse", 2, -3, 2, -2, 0, 1, true),
                arguments("Winner already present", 1, 1, 1, 1, 0, 1, false));
    }

    static Stream<Arguments> playerOneWinScoreLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move dog to exit", -2, -2, -3, 0, 2, 0, true),
                arguments("Winner already present", 1, 1, 1, 1, 2, 0, false));
    }

    static Stream<Arguments> playerTwoWinScoreLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move Frog", 1, -1, 2, -2, 0, 0, true),
                arguments("Move bird to exit", 4, -6, 3, -7, 0, 1, true),
                arguments("Move bird", 0, -4, -1, -4, 0, 1, true),
                arguments("Move frog to exit", 3, -1, 1, -2, 0, 2, true),
                arguments("Winner already present", 1, 1, 1, 1, 0, 2, false));
    }

    static Stream<Arguments> drawTurnLimitProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move frog to exit", 0, -1, 1, -2, 1, 0, true),
                arguments("Move bird to exit", 4, -6, 3, -7, 1, 1, true),
                arguments("Frog up left move", 0, 1, -1, 2, 1, 1, true),
                arguments("Move snail", 4, 3, 4, 4, 1, 1, true),
                arguments("Move Frog", 1, -1, 2, -2, 1, 1, true),
                arguments("Draw already present", 1, 1, 1, 1, 1, 1, false));
    }
}