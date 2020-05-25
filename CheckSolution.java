import java.util.ArrayList;
import java.lang.Math;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
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
    
    public static void solve(Aquarium p) {
        //System.out.println("----- SOLVE -----");
        ArrayList<LinkedHashMap<Integer,Boolean>>[] puzzleSolutions = new ArrayList[p.getSize()];
        ArrayList<LinkedHashMap<Integer,Boolean>> row = new ArrayList<LinkedHashMap<Integer,Boolean>>();
        
        for (Integer j = p.getSize()-1; j >= 0 ; j--) {
            row = rowSolutions(p.getAquariumsOnRow(j), p.getRowTotals()[j]);
            
            //Only rows after bottom row
            if (j != p.getSize()-1) {
                // Check combinations on this row with the combinations on the previous row
                // If a tank is filled on this row and empty on the previous, remove that combination.
                for (int n = 0; n < row.size(); n++) {
                    if (checkRow(row.get(n),puzzleSolutions[j+1])) {
                        //System.out.println("Removing "+j+" "+row.get(n).toString());
                        row.remove(n);
                    } else {
                        //System.out.println("Keeping  "+j+" "+row.get(n).toString());
                    }
                }
                
                // Remove combinations in previous rows which do not allow for any possible configuration on this one.  
                for (int n = new Integer(j)+1; n < p.getSize()-1; n++) { // For lines below this one
                    // A shallow copy of the line's possible solutions must be made as items are removed from the real one. 
                    ArrayList<LinkedHashMap<Integer,Boolean>> tempLineSolutions = (ArrayList<LinkedHashMap<Integer,Boolean>>) puzzleSolutions[n].clone();
                    for (int m = 0; m < tempLineSolutions.size(); m++) { // For potential solutions in previous line
                        
                        Boolean anyPossible = false;
                        for (LinkedHashMap<Integer,Boolean> possibleSolution : row) { // For potential solutions in current line
                            
                            Boolean possible = true;
                            for (Entry<Integer,Boolean> tankID : possibleSolution.entrySet()) {
                                if (tempLineSolutions.get(m).containsKey(tankID.getKey())) {
                                    if (tankID.getValue() && !tempLineSolutions.get(m).get(tankID.getKey())) {
                                        possible = false;
                                        break;
                                    }
                                }
                            }
                            if (possible) { anyPossible = true; }
                        }
                        if (!anyPossible) {
                            puzzleSolutions[n].remove(tempLineSolutions.get(m));
                        }
                    }
                }
            }

            puzzleSolutions[j] = row;
        }       
        
        LinkedHashMap<Integer,Integer[]> unsolved = new LinkedHashMap<>();
        // Fill in rows that we know already
        for (int i = 0; i < p.getSize(); i++) {
            if (puzzleSolutions[i].size() == 1) {
                for (Entry<Integer,Boolean> entry : puzzleSolutions[i].get(0).entrySet()) {
                    p.fillAquariumRow(i,entry.getKey(),entry.getValue()? Space.WATER : Space.AIR);
                }
            } else {
                unsolved.put(i,new Integer[]{puzzleSolutions[i].size()-1,0});
            }
        }
        
        // If there are unsolved rows, brute force.
        
        if (unsolved.size() != 0) {
            String solveText = "";
            while (solveText != "\u2713\u2713\u2713") {
                for (Entry<Integer,Integer[]> solutionData : unsolved.entrySet()) {
                    int i = solutionData.getKey();
                    for (Entry<Integer,Boolean> entry : puzzleSolutions[i].get(solutionData.getValue()[1]).entrySet()) {
                        p.fillAquariumRow(i,entry.getKey(),entry.getValue()? Space.WATER : Space.AIR);
                    }
                }
                solveText = CheckSolution.isSolution(p);
                unsolved = incrementSolutions(unsolved);
            }
        }
    }
    
    /**
     * TODO:
     * Added for project extension: auto-solver
     * Returns an arraylist of different combinations of aquariums that correctly add to the row sum.
     */
    private static ArrayList<LinkedHashMap<Integer,Boolean>> rowSolutions(HashMap<Integer,Integer> rowAquariums, int rowTotal) {
        double n = Math.pow(2,rowAquariums.size());
        ArrayList<LinkedHashMap<Integer,Boolean>> rowCombinations = new ArrayList<LinkedHashMap<Integer,Boolean>>();
        LinkedHashMap<Integer,Boolean> rowCombination = new LinkedHashMap<Integer,Boolean>();
        for (int k : rowAquariums.keySet()) { //Initialise aquariums as false
            rowCombination.put(k,false);
        }
        
        for (int i = 0; i < n; i++) {
            Integer sum = 0;
            for (Entry<Integer,Boolean> aquarium : rowCombination.entrySet()) {
                if (aquarium.getValue()) {
                    sum += rowAquariums.get(aquarium.getKey());
                }
                if (sum > rowTotal) {
                    break;
                }
            }
            if (sum.equals(rowTotal)) {
                rowCombinations.add(deepCopy(rowCombination));
            }
            rowCombination = incrementAquariums(rowCombination);
        }
        
        return rowCombinations;
    }
    
    /**
     * TODO:
     * Added for project extension: auto-solver
     * Increments given aquarium combination by one.
     * Operates in a binary counting fashion
     */
    private static LinkedHashMap<Integer,Boolean> incrementAquariums(LinkedHashMap<Integer,Boolean> rowAquariums) {
        Integer i = 0;
        for (int key : rowAquariums.keySet()) {
            if (!rowAquariums.get(key)) {
                rowAquariums.put(key,true);
                i = key;
                break;
            }
        }
        
        for (int key : rowAquariums.keySet()) {
            if (i.equals(key)) {
                break;
            }
            rowAquariums.put(key,false);
        }
        return rowAquariums;
    }
    
    private static LinkedHashMap<Integer,Integer[]> incrementSolutions(LinkedHashMap<Integer,Integer[]> solutionMap) {
        Integer i = 0;
        for (int key : solutionMap.keySet()) {
            Integer[] rowData = solutionMap.get(key);
            if (rowData[0] > rowData[1]) {
                rowData[1] += 1;
                    
                i = key;
                break;
            }
        }
        
        for (int key : solutionMap.keySet()) {
            if (i.equals(key)) {
                break;
            }
            Integer[] rowData = solutionMap.get(key);
            rowData[1] = 0;
        }
        return solutionMap;
    }
    
    private static LinkedHashMap<Integer,Boolean> deepCopy(LinkedHashMap<Integer,Boolean> original) {
        // Create a deepcopy so the original can be used for further processing
        LinkedHashMap<Integer,Boolean> copy = new LinkedHashMap<Integer,Boolean>();
        for (Entry<Integer,Boolean> entry : original.entrySet()) {
            copy.put(new Integer(entry.getKey()),new Boolean(entry.getValue()));
        }
        return copy;
    }
    
    private static Boolean checkRow(LinkedHashMap<Integer,Boolean> n, ArrayList<LinkedHashMap<Integer,Boolean>> lastRow) {
        Boolean remove = true;
        if (lastRow.size() == 0) { 
            System.out.println("Previous row empty");
            return false; 
        }
        
        for (LinkedHashMap<Integer,Boolean> solution : lastRow) {
            Boolean valid = true;
            for (Entry<Integer,Boolean> entry : n.entrySet()) {
                if (solution.containsKey(entry.getKey())) {
                    if (!solution.get(entry.getKey()) && entry.getValue()){
                        valid = false;
                    }
                }
            }
            if (valid && remove) {
                remove = false;
            }
        }
        
        return remove;
    }
    
    /**
     * Returns a new aquarium object with a randomly generated
     * puzzle of size size.
     */
    public static Aquarium newPuzzle(int size)
    {
        int[][] groups = generateGroups(size);
        Space[][] spaces = new Space[size][size];
        fillAllGroups(groups, spaces);
        int[][] totals = generateTotals(spaces);
        Aquarium p = new Aquarium(groups, totals);

        return p;
    }
    
    /** 
     * Returns an int[][] of new group shapes of size size
     */
    private static int[][] generateGroups(int size)
    {
        int[][] groups = new int[size][size];
        double rnd = 0.0;
        int currentMax = 0;
        for (int r = 0; r < size; r++)
        {
            for (int c = 0; c < size; c++)
            {
                
                boolean aboveExists = r != 0;
                boolean leftExists = c != 0;
                rnd = Math.random()*10;
                if (r == size-1 && c == size-1) // if last cell
                { // ensure its always the highest value
                    if (groups[r][c-1] == currentMax)
                    {
                        groups[r][c] = groups[r][c-1];
                        continue;
                    }
                } 
                else if (aboveExists && leftExists) // 3 options
                {
                    if (rnd < 4)
                    {
                        groups[r][c] = groups[r][c-1];
                        continue;
                    }
                    else if (rnd > 6)
                    {
                        groups[r][c] = groups[r-1][c];
                        continue;
                    }
                    
                }
                else if (aboveExists) // 2 options
                {
                    if (rnd < 5)
                    {
                        groups[r][c] = groups[r-1][c];
                        continue;
                    }
                }
                else if (leftExists) // 2 options
                {
                    if (rnd < 5)
                    {
                        groups[r][c] = groups[r][c-1];
                        continue;
                    }
                }
                currentMax++;
                groups[r][c] = currentMax;
            }
        }
    return groups;    
    }
    
    /**
     * Iterates through groups and calls fillGroup() for each
     */
    private static void fillAllGroups(int[][] groups, Space[][] spaces)
    {
        int maxGroup = groups[groups.length-1][groups.length-1];
        for (int groupNum = 1; groupNum <= maxGroup; groupNum++)
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
        
        java.util.Random rnd = new java.util.Random();
        
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
