
/**
 * CheckSolution is a utility class which can check if
 * a board position in an Aquarium puzzle is a solution.
 *
 * @author Lyndon While
 * @version 2020
 */
import java.util.Arrays; 

public class CheckSolution
{
    /**
     * Non-constructor for objects of class CheckSolution
     */
    private CheckSolution(){}
    
    /**
     * Returns the number of water squares in each row of Aquarium puzzle p, top down.
     */
    public static int[] rowCounts(Aquarium p)
    {
        // TODO 16 - complete
        Space[][] spaces = p.getSpaces();
        int[] rowCount = new int[p.getSize()];
        for (int r = 0; r < spaces.length; r++) {
            int count = 0;
            for (int c = 0; c < spaces[r].length; c++) {
                if (spaces[c][r] == Space.WATER) {
                    count++;
                }
            }
            rowCount[r] = count;
        }
        return rowCount;
    }
    
    /**
     * Returns the number of water squares in each column of Aquarium puzzle p, left to right.
     */
    public static int[] columnCounts(Aquarium p)
    {
        // TODO 17 - complete
        Space[][] spaces = p.getSpaces();
        int[] columnCount = new int[p.getSize()];
        for (int r = 0; r < spaces.length; r++) {
            int count = 0;
            for (int c = 0; c < spaces[r].length; c++) {
                if (spaces[r][c] == Space.WATER) {
                    count++;
                }
            }
            columnCount[r] = count;
        }
        return columnCount;
    }
    
    /**
     * Returns a 2-int array denoting the collective status of the spaces 
     * in the aquarium numbered t on Row r of Aquarium puzzle p. 
     * The second element will be the column index c of any space r,c which is in t, or -1 if there is none. 
     * The first element will be: 
     * 0 if there are no spaces in t on Row r; 
     * 1 if they're all water; 
     * 2 if they're all not-water; or 
     * 3 if they're a mixture of water and not-water. 
     */
    public static int[] rowStatus(Aquarium p, int t, int r)
    {
        // TODO 18 - completed
        int[] status = new int[2];
        int[][] aquariums = p.getAquariums();
        Space[][] spaces = p.getSpaces();
        
        int tSpaces = 0;
        int tSpacesWithWater = 0;
        
        status[0] = 0;
        
        for (int i = 0; i < aquariums[r].length; i++)
        {
            if (aquariums[r][i] == t)
            {
                status[1] = i;
                tSpaces++;
                if (spaces[r][i] == Space.WATER) { tSpacesWithWater++; }
            }
        }
        
        if (tSpaces == 0)
        {
            status[1] = 0;
        }
        else if (tSpacesWithWater == tSpaces)
        {
            status[0] = 1;
        }
        else if (tSpacesWithWater == 0)
        {
            status[0] = 2;
        }
        else
        {
            status[0] = 3;
        }

        return status;
    }
    
    /**
     * Returns a statement on whether the aquarium numbered t in Aquarium puzzle p is OK. 
     * Every row must be either all water or all not-water, 
     * and all water must be below all not-water. 
     * Returns "" if the aquarium is ok; otherwise 
     * returns the indices of any square in the aquarium, in the format "r,c". 
     */
    public static String isAquariumOK(Aquarium p, int t)
    {
        // TODO 19
        return null;
    }
    
    /**
     * Returns a statement on whether we have a correct solution to Aquarium puzzle p. 
     * Every row and column must have the correct number of water squares, 
     * and all aquariums must be OK. 
     * Returns three ticks if the solution is correct; 
     * otherwise see the LMS page for the expected results. 
     */
    public static String isSolution(Aquarium p)
    {
        // TODO 20
        return null;
    }
}
