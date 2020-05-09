import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays; 

/**
 * This class provides unit test cases for the CheckSolution class.
 * @author Lyndon While
 * @version 1.0
 */
public class CheckSolutionTest
{
    private Aquarium a4, a6, ax;

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
        ax = new Aquarium("Examples/a4_2.txt");
    }
    
    @Test
    public void testrowCounts()
    {
        assertEquals("wrong entry", -1, Arrays.mismatch(CheckSolution.rowCounts(a6), new int[] {0,0,0,0,0,0})); 
        for (int r = 0; r < 6; r++)
            for (int c = r; c < 6; c++) // mark a decreasing number of squares in each row
                a6.leftClick(r,c);
        assertEquals("wrong entry", -1, Arrays.mismatch(CheckSolution.rowCounts(a6), new int[] {6,5,4,3,2,1}));
        for (int r = 0; r < 6; r++)
            for (int c = r+1; c < 6; c++) // leave only the main diagonal
                a6.leftClick(r,c);
        assertEquals("wrong entry", -1, Arrays.mismatch(CheckSolution.rowCounts(a6), new int[] {1,1,1,1,1,1}));
    }
    
    @Test
    public void testcolumnCounts()
    {
        assertEquals("wrong entry", -1, Arrays.mismatch(CheckSolution.columnCounts(a6), new int[] {0,0,0,0,0,0})); 
        for (int r = 0; r < 6; r++)
            for (int c = r; c < 6; c++) // mark an increasing number of squares in each column
                a6.leftClick(r,c);
        assertEquals("wrong entry", -1, Arrays.mismatch(CheckSolution.columnCounts(a6), new int[] {1,2,3,4,5,6}));
        for (int r = 0; r < 6; r++)
            for (int c = r+1; c < 6; c += 2) // leave alternating diagonals
                a6.leftClick(r,c);
        assertEquals("wrong entry", -1, Arrays.mismatch(CheckSolution.columnCounts(a6), new int[] {1,1,2,2,3,3}));
    }

    @Test
    public void testrowStatus() 
    {
        // click entire alternating rows
        for (int r = 0; r < 6; r += 2)
            for (int c = 0; c < 6; c++)
                a6.leftClick(r,c);
        // Aquarium 5, small one in the middle
        for (int r : new int[] {2,3}) // rows containing Aqm 5
        {
            assertEquals("wrong classification", r-1, CheckSolution.rowStatus(a6,5,r)[0]);
            assertEquals("wrong index",            1, CheckSolution.rowStatus(a6,5,r)[1]);
        }
        for (int r : new int[] {0,1,4,5}) // no Aqm 5
        {
            assertEquals("no aquarium",  0, CheckSolution.rowStatus(a6,5,r)[0]);
            assertEquals("wrong index", -1, CheckSolution.rowStatus(a6,5,r)[1]);
        }  
        // Aquarium 2, top-right
        assertEquals("wrong classification", 1, CheckSolution.rowStatus(a6,2,0)[0]); // the wide row in Aqm 2
        assertTrue  ("wrong index",             CheckSolution.rowStatus(a6,2,0)[1] >= 2);
        for (int r : new int[] {1,2})
        {
            assertEquals("wrong classification", 3-r, CheckSolution.rowStatus(a6,2,r)[0]); // the narrow rows in Aqm 2
            assertEquals("wrong index",            3, CheckSolution.rowStatus(a6,2,r)[1]);
        }  
        for (int r : new int[] {3,4,5})
        {
            assertEquals("no aquarium",  0, CheckSolution.rowStatus(a6,2,r)[0]); // no Aqm 2
            assertEquals("wrong index", -1, CheckSolution.rowStatus(a6,2,r)[1]);
        }  

        // individual clicks to create mixed rows
        a6.leftClick(1,1);
        assertEquals("mixed row",  3, CheckSolution.rowStatus(a6,1,1)[0]); // Aqm 1, Row 1
        assertTrue  ("wrong index",   CheckSolution.rowStatus(a6,1,1)[1] <= 2); 
        a6.leftClick(0,5);
        assertEquals("mixed row",  3, CheckSolution.rowStatus(a6,2,0)[0]); // Aqm 2, Rpw 0
        assertTrue  ("wrong index",   CheckSolution.rowStatus(a6,2,0)[1] >= 2); 
        a6.leftClick(4,4);
        assertEquals("mixed row",  3, CheckSolution.rowStatus(a6,6,4)[0]); // Aqm 6, Row 4
        assertTrue  ("wrong index",   CheckSolution.rowStatus(a6,6,4)[1] >= 4); 
    }

    @Test
    public void testisAquariumOK() 
    {
        // all aquariums empty; all aquariums OK
        for (int t = 1; t <= 6; t++)
            assertTrue("Aquariums ok", CheckSolution.isAquariumOK(a6,t).isEmpty());
        // fill all rows entirely from the bottom up; all aquariums always OK
        for (int r = 5; r >= 0; r--)
        {
            for (int c = 0; c < 6; c++)
                a6.leftClick(r,c);
            for (int t = 1; t <= 6; t++)
                assertTrue("Aquariums ok", CheckSolution.isAquariumOK(a6,t).isEmpty());
        }
        // air at the top of Aquarium 5; all aquariums OK
        a6.leftClick(2,1);
        for (int t = 1; t <= 6; t++)
            assertTrue("Aquariums ok", CheckSolution.isAquariumOK(a6,t).isEmpty());
        a6.leftClick(2,1);
        // air at the bottom of Aquarium 5; all aquariums OK except 5
        a6.leftClick(3,1);
        for (int t = 1; t <= 6; t++)
            assertTrue("Aquariums ok except 5", t == 5 != CheckSolution.isAquariumOK(a6,t).isEmpty());
        String s = CheckSolution.isAquariumOK(a6,5);
        assertTrue  ("null String",       s != null);
        assertEquals("wrong length",   3, s.length());
        assertEquals("wrong String", ',', s.charAt(1));
        assertTrue  ("wrong String",  "1".contains(s.substring(2)));
        assertTrue  ("wrong String", "23".contains(s.substring(0,1)));
        // Aquarium 5 all air; all aquariums OK
        a6.leftClick(3,1);
        for (int t = 1; t <= 6; t++)
            assertTrue("Aquariums ok", CheckSolution.isAquariumOK(a6,t).isEmpty());
        // air at the top of Aquarium 3; all aquariums OK
        a6.leftClick(2,2);
        for (int t = 1; t <= 6; t++)
            assertTrue("Aquariums ok", CheckSolution.isAquariumOK(a6,t).isEmpty());
        // one air space elsewhere in Aquarium 3, all aquariums ok except 3
        int[][] xss = {{3,2},{3,3},{3,4},{4,2},{4,3},{5,1},{5,2}};
        for (int[] xs : xss)
        {
            a6.rightClick(xs[0], xs[1]);
            for (int t = 1; t <= 6; t++)
                assertTrue("Aquariums ok except 3", t == 3 != CheckSolution.isAquariumOK(a6,t).isEmpty());
            s = CheckSolution.isAquariumOK(a6,3);
            assertTrue  ("null String",       s != null);
            assertEquals("wrong length",   3, s.length());
            assertEquals("wrong String", ',', s.charAt(1));
            boolean b = false;
            for (int[] ys : xss)
                if (s.substring(0,1).equals(ys[0] + "") && s.substring(2).equals(ys[1] + ""))
                   b = true;
            assertTrue  ("wrong String", b);
            a6.leftClick(xs[0], xs[1]);
        }
    }

    @Test
    public void testisSolution() 
    {
        String s;
        String threeticks = "\u2713\u2713\u2713";
        // set up solution
        ax.leftClick(1,0);
        ax.leftClick(2,0);
        ax.leftClick(1,3);
        ax.leftClick(2,3);
        ax.leftClick(0,1);
        ax.leftClick(0,2);
        ax.leftClick(3,1);
        ax.leftClick(3,2);
        s = CheckSolution.isSolution(ax);
        assertTrue("solution correct", s.equals(threeticks));
        // break Rows 0 & 1, all else ok
        ax.leftClick(0,0);
        ax.rightClick(1,0);
        ax.leftClick(0,3);
        ax.rightClick(1,3);
        s = CheckSolution.isSolution(ax);
        assertTrue("Rows 0,1 wrong", s.equals("Row 0 is wrong") || s.equals("Row 1 is wrong"));
        // fix Rows 0 & 1
        ax.rightClick(0,0);
        ax.leftClick(1,0);
        ax.rightClick(0,3);
        ax.leftClick(1,3);
        s = CheckSolution.isSolution(ax);
        assertTrue("solution correct", s.equals(threeticks));
        // break Columns 0 & 1, all else ok
        ax.rightClick(1,0);
        ax.leftClick(1,1);
        ax.rightClick(1,3);
        ax.leftClick(1,2);
        s = CheckSolution.isSolution(ax);
        assertTrue("Columns 0,1 wrong", s.equals("Column 0 is wrong") || s.equals("Column 1 is wrong"));
        // fix Columns 0 & 1
        ax.leftClick(1,0);
        ax.rightClick(1,1);
        ax.leftClick(1,3);
        ax.rightClick(1,2);
        s = CheckSolution.isSolution(ax);
        assertTrue("solution correct", s.equals(threeticks));
        // break aquarium at bottom, all else ok
        ax.leftClick(3,0);
        ax.rightClick(3,1);
        ax.rightClick(1,0);
        ax.leftClick(1,1);
        s = CheckSolution.isSolution(ax);
        assertTrue("Aquarium at bottom wrong", s.equals("The aquarium at 3,1 is wrong")
                                            || s.equals("The aquarium at 3,2 is wrong"));
        // fix aquarium at bottom
        ax.rightClick(3,0);
        ax.leftClick(3,1);
        ax.leftClick(1,0);
        ax.rightClick(1,1);
        s = CheckSolution.isSolution(ax);
        assertTrue("solution correct", s.equals(threeticks));
        // break aquarium on left, all else ok
        ax.leftClick(0,0);
        ax.rightClick(2,0);
        ax.rightClick(0,1);
        ax.leftClick(2,1);
        s = CheckSolution.isSolution(ax);
        assertTrue("Aquarium at left wrong", s.equals("The aquarium at 1,0 is wrong")
                                          || s.equals("The aquarium at 2,0 is wrong"));
        // fix aquarium on left
        ax.rightClick(0,0);
        ax.leftClick(2,0);
        ax.leftClick(0,1);
        ax.rightClick(2,1);
        s = CheckSolution.isSolution(ax);
        assertTrue("solution correct", s.equals(threeticks));
    }
}
