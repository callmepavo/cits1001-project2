import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays; 

/**
 * This class provides unit test cases for the Aquarium class.
 * @author Lyndon While
 * @version 1.0
 */
public class AquariumTest
{
    private Aquarium a4, a6;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        a4 = new Aquarium("Examples/a4_1.txt");
        a6 = new Aquarium("Examples/a6_1.txt");
    }

    @Test
    public void testAquarium() 
    {
        // check a4
        int[][] xss = {{1,3,1,3}
                      ,{1,4,2,1}
                      ,{1,2,1,3}
                      ,{1,1,1,3}
                      ,{4,1,4,3}
                      ,{4,4,4,5}};
        assertEquals("wrong size",   4,                 a4.getSize()); 
        assertTrue  ("null array",                      a4.getColumnTotals() != null);
        assertEquals("wrong size",   4,                 a4.getColumnTotals().length);
        assertEquals("wrong entry", -1, Arrays.mismatch(a4.getColumnTotals(), xss[0])); 
        assertTrue  ("null array",                      a4.getRowTotals() != null);
        assertEquals("wrong size",   4,                 a4.getRowTotals().length);
        assertEquals("wrong entry", -1, Arrays.mismatch(a4.getRowTotals(), xss[1]));
        assertTrue  ("null array",                      a4.getAquariums() != null);
        assertEquals("wrong size",   4,                 a4.getAquariums().length);
        for (int r = 0; r < 4; r++)
        {
            assertTrue  ("null array",                      a4.getAquariums()[r] != null);
            assertEquals("wrong size",   4,                 a4.getAquariums()[r].length);
            assertEquals("wrong entry", -1, Arrays.mismatch(a4.getAquariums()[r], xss[r+2]));
        }
        assertTrue  ("null array",    a4.getSpaces() != null);
        assertEquals("wrong size", 4, a4.getSpaces().length);
        for (int r = 0; r < 4; r++)
        {
            assertTrue  ("null array",    a4.getSpaces()[r] != null);
            assertEquals("wrong size", 4, a4.getSpaces()[r].length);
            for (int c = 0; c < 4; c++)
                assertEquals("wrong entry", Space.EMPTY, a4.getSpaces()[r][c]);
        }
        
        // check a6
        xss = new int[][] {{2,3,4,5,2,1}
                          ,{2,4,1,3,2,5}
                          ,{1,1,2,2,2,2}
                          ,{1,1,1,2,6,6}
                          ,{4,5,3,2,6,6}
                          ,{4,5,3,3,3,6}
                          ,{4,4,3,3,6,6}
                          ,{4,3,3,6,6,6}};
        assertEquals("wrong size",   6,                 a6.getSize()); 
        assertTrue  ("null array",                      a6.getColumnTotals() != null);
        assertEquals("wrong size",   6,                 a6.getColumnTotals().length);
        assertEquals("wrong entry", -1, Arrays.mismatch(a6.getColumnTotals(), xss[0]));
        assertTrue  ("null array",                      a6.getRowTotals() != null);
        assertEquals("wrong size",   6,                 a6.getRowTotals().length);
        assertEquals("wrong entry", -1, Arrays.mismatch(a6.getRowTotals(), xss[1]));
        assertTrue  ("null array",                      a6.getAquariums() != null);
        assertEquals("wrong size",   6,                 a6.getAquariums().length);
        for (int r = 0; r < 6; r++)
        {
            assertTrue  ("null array",                      a6.getAquariums()[r] != null);
            assertEquals("wrong size",   6,                 a6.getAquariums()[r].length);
            assertEquals("wrong entry", -1, Arrays.mismatch(a6.getAquariums()[r], xss[r+2]));
        }
        assertTrue  ("null array",    a6.getSpaces() != null);
        assertEquals("wrong size", 6, a6.getSpaces().length);
        for (int r = 0; r < 6; r++)
        {
            assertTrue  ("null array",    a6.getSpaces()[r] != null);
            assertEquals("wrong size", 6, a6.getSpaces()[r].length);
            for (int c = 0; c < 6; c++)
                assertEquals("wrong entry", Space.EMPTY, a6.getSpaces()[r][c]);
        }
    }

    @Test
    public void testparseLine() 
    {
        int[] xs = Aquarium.parseLine("1 23 456 7890");
        assertTrue  ("null array",      xs != null);
        assertEquals("wrong size",   4, xs.length);
        assertEquals("wrong entry", -1, Arrays.mismatch(xs, new int[] {1,23,456,7890}));
        
        xs = Aquarium.parseLine("1 0 -1"); // needs to cope with all integers
        assertTrue  ("null array",      xs != null);
        assertEquals("wrong size",   3, xs.length);
        assertEquals("wrong entry", -1, Arrays.mismatch(xs, new int[] {1,0,-1}));
    }

    @Test
    public void testleftClick() 
    {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
            {
                assertEquals("wrong entry", Space.EMPTY, a6.getSpaces()[i][j]);
                a6.leftClick(i,j);
                assertEquals("wrong entry", Space.WATER, a6.getSpaces()[i][j]);
                a6.leftClick(i,j);
                assertEquals("wrong entry", Space.EMPTY, a6.getSpaces()[i][j]);
            }
    }

    @Test
    public void testrightClick() 
    {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
            {
                assertEquals("wrong entry", Space.EMPTY, a6.getSpaces()[i][j]);
                a6.rightClick(i,j);
                assertEquals("wrong entry", Space.AIR,   a6.getSpaces()[i][j]);
                a6.rightClick(i,j);
                assertEquals("wrong entry", Space.EMPTY, a6.getSpaces()[i][j]);
            }
    }

    @Test
    public void testleftandrightClick() 
    {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
            {
                assertEquals("wrong entry", Space.EMPTY, a6.getSpaces()[i][j]);
                a6.rightClick(i,j);
                assertEquals("wrong entry", Space.AIR,   a6.getSpaces()[i][j]);
                a6.leftClick(i,j);
                assertEquals("wrong entry", Space.WATER, a6.getSpaces()[i][j]);
                a6.rightClick(i,j);
                assertEquals("wrong entry", Space.AIR,   a6.getSpaces()[i][j]);
            }
    }

    @Test
    public void testclear() 
    {
        testAquarium();
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                // mix up the clicks for fun
                if ((i + j) % 2 == 0) 
                {
                    a6.leftClick(i,j);
                    a4.rightClick(i,j);
                }
                else
                {
                    a6.rightClick(i,j);
                    a4.leftClick(i,j);
                }
        a6.clear();
        a4.clear();
        testAquarium();
    }
}
