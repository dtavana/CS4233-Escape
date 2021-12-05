package escape;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import escape.gamedef.Coordinate;
import escape.gamedef.EscapePiece;
import escape.gamedef.Player;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Base Escape test file. This sets up the tests for all of the other tests.
 */
abstract class BaseEscapeTest
{

    protected static EscapeGameManager manager;
    protected static String configFile;
    protected static String currentTests;


    @BeforeEach
    void loadGame() throws Exception
    {
        EscapeGameBuilder egb = new EscapeGameBuilder(configFile);
        manager = egb.makeGameManager();
        assertNotNull(manager);
    }

    // Shared tests
    @ParameterizedTest
    @MethodSource("initialPiecesProvider")
    void initialPiecesTest(int x1, int y1, EscapePiece.PieceName type, Player player)
    {
        Coordinate c = manager.makeCoordinate(x1, y1);
        EscapePiece p = manager.getPieceAt(c);
        assertNotNull(p);
        assertEquals(p.getName(), type);
        assertEquals(p.getPlayer(), player);
    }

    @ParameterizedTest
    @MethodSource("basicOneMoveProvider")
    void basicOneMove(String testName, int x1, int y1, int x2, int y2,
                      boolean expected)
    {
        oneMoveHelper(x1, y1, x2, y2, expected);
    }

    // Helpers
    // Make a list (ml) -- the name is short since we want to keep the
    // tests
    static List<Coordinate> ml(int... ints)
    {
        int i = 0;
        List<Coordinate> coords = new LinkedList<Coordinate>();
        while (i < ints.length) {
            coords.add(manager.makeCoordinate(ints[i++], ints[i++]));
        }
        return coords;
    }

    void oneMoveHelper(int x1, int y1, int x2, int y2, boolean expected)
    {
        Coordinate c1 = manager.makeCoordinate(x1, y1);
        Coordinate c2 = manager.makeCoordinate(x2, y2);
        EscapePiece p = manager.getPieceAt(c1);
        assertEquals(expected, manager.move(c1, c2));
    }
}