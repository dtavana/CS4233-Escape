package escape.hex;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.stream.Stream;

import escape.BaseEscapeRelease2Test;
import escape.EscapeGameBuilder;
import escape.component.MyObserver;
import escape.manager.EscapeGameManagerImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;

/**
 * Test cases that are run in the superclass
 */
public class Release2HexMultipleTest extends BaseEscapeRelease2Test
{
    @BeforeAll
    static void classSetup() throws Exception {
        configFile = "config/egc/Release2HexTests.egc";
        currentTests = "Release 2 Hex Game Multiple Moves Tests";
        EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
        manager = (EscapeGameManagerImpl) egb.makeGameManager();
        observer = new MyObserver();
        manager.addObserver(observer);
        assertNotNull(manager);
    }

    static Stream<Arguments> release2TestProvider()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move bird to exit", 1, 1, 1, 6, 1, 0, true),
                arguments("Move dog over block", 2, 1, 2, 3, 1, 0, true),
                arguments("Move horse linear", 2, 6, 2, 10, 1, 0, true),
                arguments("Move frog to exit", 1, 2, 1, 3, 1, 3, true),
                arguments("Winner already present", 1, 1, 1, 1, 1, 3, false));
    }

    static Stream<Arguments> release2TestProviderTestNewBaseConditions()
    {
        return Stream.of(
                arguments(dummyTestName, -1, -1, -1, -1, -1, -1, false),
                arguments("Move bird to frog", 1, 1, 1, 2, 0, 0, true),
                arguments("Move dog to block", 2, 1, 2, 2, 0, 0, false));
    }
}