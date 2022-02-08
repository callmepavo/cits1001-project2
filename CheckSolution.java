import java.util.Random;
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
        
        int tSpaces = 0;
        int tSpacesWithWater = 0;
        
        status[0] = 0; // Set default value for status[0]
        // iterate through columns
        for (int i = 0; i < aquariums[r].length; i++)
        {
            if (aquariums[r][i] == t)
            {
                status[1] = i; // Sets status[1] to a row number
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
            
            if (rowInfo[0] == -1) // iff theres no tSpaces on this row
            {
                if (waterBegun)
                {
                    isOK = false;
                    break;
                }
            }
            else if (rowInfo[0] == 1) // iff all tSpaces are water
            {
                waterBegun = true;
            }
            else if (rowInfo[0] == 2) // iff all tSpaces are air
            {
                if (waterBegun)
                {
                    isOK = false;
                    break;
                }
            }
            else if (rowInfo[0] == 3) // iff there's a mix
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
        int highestGroup = aquariums[aquariums.length-1][aquariums[0].length-1];
        
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
        
    /**
     * Returns a new aquarium object with a randomly generated
     * puzzle of size size.
     */
    public static Aquarium newPuzzle(int size)
    {
        System.out.println("Generating new puzzle");
        int[][] groups = generateGroups(size);
        Space[][] spaces = new Space[size][size];
        fillAllGroups(groups, spaces);
        int[][] totals = generateTotals(spaces);
        Aquarium p = new Aquarium(groups, totals, spaces);

        return p;
    }
    

    public static int[][] generateGroups(int size) {
        Random rng = new Random();
        int[][] groups = new int[size][size];
        int cellCount = size * size;
        int groupCount = size*size/5;

        // Set all cells to negative group
        for (int i = 0; i < cellCount; i++) {
            int r = i / size;
            int c = i % size;
            groups[r][c] = -1;
            //System.out.println(groups[r][c]);
        }
        System.out.println("groupCount: "+ groupCount);
        // Generate group roots
        for (int i = 0; i < groupCount;) {
            int r = rng.nextInt(size);
            int c = rng.nextInt(size);
            System.out.println("Attempting to place root at " + r + " " + c);
            System.out.println(groups[r][c] == -1);
            if (groups[r][c] == -1) {
                groups[r][c] = i;
                i++;
                System.out.println("Placed root!");
            }
        }
        System.out.println("Set group roots");

        int assignedCells = groupCount;
        while (assignedCells < cellCount) {
            for (int i = 0; i < cellCount; i++) {
                int r = i / size;
                int c = i % size;
                // Skips cells already filled in
                if (groups[r][c] != -1) continue;
                
                int up = -1;
                int down = -1;
                int left = -1;
                int right = -1;
                if (r-1 >= 0) {
                    up = groups[r-1][c];
                }

                if (r+1 < size) {
                    down = groups[r+1][c];
                }

                if (c-1 >= 0) {
                    left = groups[r][c-1];
                }

                if (c+1 < size) {
                    right = groups[r][c+1];
                }

                if (up != -1) {
                    if (rng.nextInt(4) == 0) {
                        System.out.println("Assigning " + r + " " + c);
                        groups[r][c] = up;
                        assignedCells++;
                        continue;
                    }
                }

                if (down != -1) {
                    if (rng.nextInt(4) == 0) {
                        System.out.println("Assigning " + r + " " + c);
                        groups[r][c] = down;
                        assignedCells++;
                        continue;
                    }
                }

                if (left != -1) {
                    if (rng.nextInt(4) == 0) {
                        System.out.println("Assigning " + r + " " + c);
                        groups[r][c] = left;
                        assignedCells++;
                        continue;
                    }
                }

                if (right != -1) {
                    if (rng.nextInt(4) == 0) {
                        System.out.println("Assigning " + r + " " + c);
                        groups[r][c] = right;
                        assignedCells++;
                        continue;
                    }
                }

            }
        }

        return groups;
    }
    
    /**
     * Iterates through groups and calls fillGroup() for each
     */
    private static void fillAllGroups(int[][] groups, Space[][] spaces)
    {
        
        int groupCount = spaces.length*spaces.length / 5;
        System.out.println("spaces.lenght in fillAllGroups: " + spaces.length);
        for (int groupNum = 0; groupNum < groupCount; groupNum++)
        {
            fillGroup(groupNum, groups, spaces);
        }
    }
    
    /**
     * Fills a random number of rows from bottom up in group groupToFill with water
     */
    private static void fillGroup(int groupToFill, int[][] groups, Space[][] spaces)
    {
        
        // Calculate group top and bottom
        boolean foundTop = false;
        int topRow = 0;
        int bottomRow = groups.length-1;
        for (int r = 0; r < groups.length; r++)
        {
            boolean anyInRow = false;
            for (int c = 0; c < groups.length; c++)
            {
                if (!foundTop)
                {
                    if (groups[r][c] == groupToFill)
                    {
                        topRow = r;
                        foundTop = true;
                        anyInRow = true;
                        break;
                    }
                    
                }
                if (groups[r][c] == groupToFill) {anyInRow = true;}
            }
            if (foundTop && !anyInRow)
            {
                bottomRow = r-1;
                break;
            }
        }
        
        Random rnd = new Random();
        
        int height = bottomRow-topRow+1;
        int rowsToFill = rnd.nextInt(height+1); //number of rows to fill
        for (int i = 0; i < rowsToFill; i++)
        {
            int r = bottomRow - i;
            if (rowsToFill < 0) {break;} // in case bottom row error
            for (int c = 0; c < groups.length; c++)
            {
                if (groups[r][c] == groupToFill)
                {
                    spaces[r][c] = Space.WATER;
                }
            }
        }
        
        
    }
    
    /**
     * Counts the totals of each line and column in spaces.
     * Cribbed shamelessly from columnCounts() and rowCounts()
     * Returns int[][].
     * generateTotals[0] = columnCount[]
     * generateTotals[1] = rowCount
     */
    private static int[][] generateTotals(Space[][] spaces)
    {
        int[] rowCount = new int[spaces.length];
        for (int r = 0; r < spaces.length; r++) {
            int count = 0;
            for (int c = 0; c < spaces[r].length; c++) {
                if (spaces[r][c] == Space.WATER) {
                    count++;
                }
            }
            rowCount[r] = count;
        }
        int[] columnCount = new int[spaces.length];
        for (int c = 0; c < spaces.length; c++) {
            int count = 0;
            for (int r = 0; r < spaces[c].length; r++) {
                if (spaces[r][c] == Space.WATER) {
                    count++;
                }
            }
            columnCount[c] = count;
        }
        return new int[][]{columnCount,rowCount};
    }
    
}
