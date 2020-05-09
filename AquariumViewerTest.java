import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays; 

/**
 * This class provides unit test cases for the AquariumViewer class.
 * @author Lyndon While
 * @version 1.0
 */
public class AquariumViewerTest
{
    private AquariumViewer b4, b6;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        b4 = new AquariumViewer(41);
        b6 = new AquariumViewer(61);
    }

    @Test
    public void testBoardViewer() 
    {
        // check b4
        assertTrue("null object", b4.getCanvas() != null);
        assertTrue("wrong size",  b4.getSize() == 4);
        Aquarium a = b4.getPuzzle();
        assertTrue("null object", a != null);
        int[][] xss = {{1,3,1,3}
                      ,{1,4,2,1}
                      ,{1,2,1,3}
                      ,{1,1,1,3}
                      ,{4,1,4,3}
                      ,{4,4,4,5}};
        assertEquals("wrong size",   4,                 a.getSize()); 
        assertTrue  ("null array",                      a.getColumnTotals() != null);
        assertEquals("wrong size",   4,                 a.getColumnTotals().length);
        assertEquals("wrong entry", -1, Arrays.mismatch(a.getColumnTotals(), xss[0]));
        assertTrue  ("null array",                      a.getRowTotals() != null);
        assertEquals("wrong size",   4,                 a.getRowTotals().length);
        assertEquals("wrong entry", -1, Arrays.mismatch(a.getRowTotals(), xss[1]));
        assertTrue  ("null array",                      a.getAquariums() != null);
        assertEquals("wrong size",   4,                 a.getAquariums().length);
        for (int r = 0; r < 4; r++)
        {
            assertTrue  ("null array",                      a.getAquariums()[r] != null);
            assertEquals("wrong size",   4,                 a.getAquariums()[r].length);
            assertEquals("wrong entry", -1, Arrays.mismatch(a.getAquariums()[r], xss[r+2]));
        }
        assertTrue  ("null array",    a.getSpaces() != null);
        assertEquals("wrong size", 4, a.getSpaces().length);
        for (int r = 0; r < 4; r++)
        {
            assertTrue  ("null array",    a.getSpaces()[r] != null);
            assertEquals("wrong size", 4, a.getSpaces()[r].length);
            for (int c = 0; c < 4; c++)
                assertEquals("wrong entry", Space.EMPTY, a.getSpaces()[r][c]);
        }
        
        // check b6
        assertTrue("null object", b6.getCanvas() != null);
        assertTrue("wrong size",  b6.getSize() == 6);
        a = b6.getPuzzle();
        assertTrue("null object", a != null);
        xss = new int[][] {{2,3,4,5,2,1}
                          ,{2,4,1,3,2,5}
                          ,{1,1,2,2,2,2}
                          ,{1,1,1,2,6,6}
                          ,{4,5,3,2,6,6}
                          ,{4,5,3,3,3,6}
                          ,{4,4,3,3,6,6}
                          ,{4,3,3,6,6,6}};
        assertEquals("wrong size",   6,                 a.getSize()); 
        assertTrue  ("null array",                      a.getColumnTotals() != null);
        assertEquals("wrong size",   6,                 a.getColumnTotals().length);
        assertEquals("wrong entry", -1, Arrays.mismatch(a.getColumnTotals(), xss[0]));
        assertTrue  ("null array",                      a.getRowTotals() != null);
        assertEquals("wrong size",   6,                 a.getRowTotals().length);
        assertEquals("wrong entry", -1, Arrays.mismatch(a.getRowTotals(), xss[1]));
        assertTrue  ("null array",                      a.getAquariums() != null);
        assertEquals("wrong size",   6,                 a.getAquariums().length);
        for (int r = 0; r < 6; r++)
        {
            assertTrue  ("null array",                      a.getAquariums()[r] != null);
            assertEquals("wrong size",   6,                 a.getAquariums()[r].length);
            assertEquals("wrong entry", -1, Arrays.mismatch(a.getAquariums()[r], xss[r+2]));
        }
        assertTrue  ("null array",    a.getSpaces() != null);
        assertEquals("wrong size", 6, a.getSpaces().length);
        for (int r = 0; r < 6; r++)
        {
            assertTrue  ("null array",    a.getSpaces()[r] != null);
            assertEquals("wrong size", 6, a.getSpaces()[r].length);
            for (int c = 0; c < 6; c++)
                assertEquals("wrong entry", Space.EMPTY, a.getSpaces()[r][c]);
        }
    }

    @Test
    public void testdisplaymethods() 
    {
        assertFalse("THE DISPLAY METHODS ARE NOT TESTED", true);
    }
}
