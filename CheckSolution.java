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
        System.out.println("----- SOLVE -----");
        ArrayList<LinkedHashMap<Integer,Boolean>>[] puzzleSolutions = new ArrayList[p.getSize()];
        ArrayList<LinkedHashMap<Integer,Boolean>> row = new ArrayList<LinkedHashMap<Integer,Boolean>>();
        //ArrayList<LinkedHashMap<Integer,Boolean>> lastRow;
        for (Integer j = p.getSize()-1; j >= 0 ; j--) {
            //lastRow = new ArrayList<LinkedHashMap<Integer,Boolean>>(row); //Shallow copy
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
                
                // Check combinations on this row for tanks that must be filled.
                // Remove combinations in previous rows where this tank isn't filled. 
                LinkedHashMap<Integer,Boolean> alwaysFilled = new LinkedHashMap<Integer,Boolean>();
                for (int aquariumID : p.getAquariumsOnRow(j).keySet()) {
                    alwaysFilled.put(aquariumID, true);
                    for (LinkedHashMap<Integer,Boolean> solution : row) {
                        if (!solution.get(aquariumID)) {
                            alwaysFilled.put(aquariumID, false);
                            break;
                        }
                    }
                }
                alwaysFilled.values().removeIf(n -> (!n)); // Remove all tanks which arn't always filled
                for (int n = new Integer(j)+1; n < p.getSize()-1; n++) {
                    for (int m = 0; m < puzzleSolutions[n].size(); m++) {
                        for (Entry filled : alwaysFilled.entrySet()) {
                            if (puzzleSolutions[n].get(m).containsKey(filled.getKey())) {
                                if (!puzzleSolutions[n].get(m).get(filled.getKey())) {
                                    puzzleSolutions[n].remove(m);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            
            puzzleSolutions[j] = row;
        }
        
        // Debug print to console
        for (ArrayList<LinkedHashMap<Integer,Boolean>> potentialSolutions : puzzleSolutions) {
            System.out.println(potentialSolutions.toString());
        }
        
        // Fill in rows that we know already
        for (int i = 0; i < p.getSize(); i++) {
            if (puzzleSolutions[i].size() == 1) {
                for (Entry<Integer,Boolean> entry : puzzleSolutions[i].get(0).entrySet()) {
                    p.fillAquariumRow(i,entry.getKey(),entry.getValue()? Space.WATER : Space.AIR);
                }
            } 
        }
    }
    
    /**
     * TODO:
     * Added for project extension: auto-solver
     * Returns an arraylist of different combinations of aquariums that correctly add to the row sum.
     */
    private static ArrayList<LinkedHashMap<Integer,Boolean>> rowSolutions(HashMap<Integer,Integer> rowAquariums, int rowTotal) {
        double n = Math.pow(2,rowAquariums.size())-1;
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
}
