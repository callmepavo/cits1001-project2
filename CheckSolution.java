
/**
 * CheckSolution is a utility class which can check if
 * a board position in an Aquarium puzzle is a solution.
 *
 * @author Lyndon While
 * @version 2020
 * 
 * @author Zach Manson (22903345), Oliver Lynch (22989775)
 * @version 20200512
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
                if (spaces[r][c] == Space.WATER) {
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
        for (int c = 0; c < spaces.length; c++) {
            int count = 0;
            for (int r = 0; r < spaces[c].length; r++) {
                if (spaces[r][c] == Space.WATER) {
                    count++;
                }
            }
            columnCount[c] = count;
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
        
        /* Horrible ugly debug loop  - can remove later
         * 
         * for (int[] i :aquariums)
            {for (int j: i)
                System.out.print(j); System.out.println();}
        for (Space[] s :spaces)
            {for (Space s2: s)
                if (s2 == Space.WATER)
                    System.out.print("W");
                else
                    System.out.print("-"); System.out.println();}*/
        int tSpaces = 0;
        int tSpacesWithWater = 0;
        
        status[0] = 0;
        for (int i = 0; i < aquariums[r].length; i++)
        {
            if (aquariums[r][i] == t)
            {
                //System.out.print("("+r+","+i+")"); debug print
                status[1] = i;
                tSpaces++;
                if (spaces[r][i] == Space.WATER) { tSpacesWithWater++; }
            }
        }

        if (tSpaces == 0)
        {
            status[1] = -1;
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
        /* debug prints System.out.println("tSpaces " + tSpaces + " tspaceswithwater "+ tSpacesWithWater);
        System.out.println("row "+ r +" group " + t+ " status "+status[0]+" " +status[1]);*/
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
        // TODO 19 - complete
        int[][] aquariums = p.getAquariums();
        boolean waterBegun = false;
        boolean isOK = true;
        int[] rowInfo = new int[2];
        String coords = "";
        for (int i = 0; i < aquariums.length; i++)
        {
            rowInfo = rowStatus(p, t, i);
            coords = i + "," + rowInfo[1];
            
            if (rowInfo[0] == -1) // if theres no tSpaces on this row
            {
                if (waterBegun)
                {
                    isOK = true;
                }
            }
            else if (rowInfo[0] == 1) // if all tSpaces are water
            {
                waterBegun = true;
            }
            else if (rowInfo[0] == 2) // if all tSpaces are air
            {
                if (waterBegun)
                {
                    isOK = false;
                    break;
                }
            }
            else if (rowInfo[0] == 3)
            {
                isOK = false;
                break;
            } 
        }
        
        if (!isOK)
        {
            return coords;
        }
        return "";
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
        // TODO 20 - complete
        int[] correctRowTotals = p.getRowTotals();
        int[] correctColumnTotals = p.getColumnTotals();
        int[] actualRowTotals = rowCounts(p);
        int[] actualColumnTotals = columnCounts(p);
                
        for (int i = 0; i < p.getSize(); i++)
        {
            if (correctColumnTotals[i] != actualColumnTotals[i])
            {
                return "Column " + i + " is wrong";
            }
            if (correctRowTotals[i] != actualRowTotals[i])
            {
                return "Row " + i + " is wrong";
            }
        }
        
        // Find highest group number
        int[][] aquariums = p.getAquariums();
        int highestGroup = 0;
        for (int[] row : aquariums)
        {
            for (int cell : row)
            {
                if (cell > highestGroup)
                {
                    highestGroup = cell;
                }
            }
        }
        
        for (int t = 0; t < highestGroup; t++)
        {
            String coords = isAquariumOK(p, t);
            if (!coords.isEmpty())
            {
                return "The aquarium at " + coords + " is wrong";
            }
        }
        return "\u2713\u2713\u2713";
    }
}
