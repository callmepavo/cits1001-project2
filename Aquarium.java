import java.util.ArrayList;
/**
 * Aquarium represents a single problem in the game Aquarium.
 *
 * @author Lyndon While 
 * @version 2020
 */
public class Aquarium
{
    private int   size;         // the board is size x size
    private int[] columnTotals; // the totals at the top of the columns, left to right
    private int[] rowTotals;    // the totals at the left of the rows, top to bottom 
    
    // the board divided into aquariums, numbered from 1,2,3,...
    // spaces with the same number are part of the same aquarium
    private int[][] aquariums;
    // the board divided into spaces, each empty, water, or air
    private Space[][] spaces;

    /**
     * Constructor for objects of class Aquarium. 
     * Creates, initialises, and populates all of the fields.
     */
    public Aquarium(String filename)
    {
        // TODO 3 - complete
        FileIO file = new FileIO(filename);
        ArrayList<String> lines = file.getLines();
        
        // Get first two lines from file as column totals
        columnTotals = this.parseLine(lines.get(0));
        rowTotals = this.parseLine(lines.get(1));
        
        size = columnTotals.length;
        aquariums = new int[rowTotals.length][columnTotals.length];
        spaces = new Space[rowTotals.length][columnTotals.length];
        
        for (int i = 0; i < size; i++) {
            aquariums[i] = this.parseLine(lines.get(i+3));
        }
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public Aquarium()
    {
        this("Examples/a6_1.txt");
    }

    /**
     * Returns an array containing the ints in s, 
     * each of which is separated by one space. 
     * e.g. if s = "1 299 34 5", it will return {1,299,34,5} 
     */
    public static int[] parseLine(String s)
    {
        // TODO 2 - complete
        String[] numsAsStr = s.split(" ");
        int[] numsAsInt = new int[numsAsStr.length];
        
        for (int i = 0; i < numsAsStr.length; i++)
        {
            numsAsInt[i] = Integer.parseInt(numsAsStr[i]);
        }
        
        return numsAsInt;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        // TODO 1a - complete
        return size;
    }
    
    /**
     * Returns the column totals.
     */
    public int[] getColumnTotals()
    {
        // TODO 1b - complete
        return columnTotals;
    }
    
    /**
     * Returns the row totals.
     */
    public int[] getRowTotals()
    {
        // TODO 1c - complete
        return rowTotals;
    }
    
    /**
     * Returns the board in aquariums.
     */
    public int[][] getAquariums()
    {
        // TODO 1d - complete
        return aquariums;
    }
    
    /**
     * Returns the board in spaces.
     */
    public Space[][] getSpaces()
    {
        // TODO 1e - complete
        return spaces;
    }
    
    /**
     * Performs a left click on Square r,c if the indices are legal, o/w does nothing. 
     * A water space becomes empty; other spaces become water. 
     */
    public void leftClick(int r, int c)
    {
        // TODO 4 - complete
        if (r <= rowTotals.length && r >= 0) {
            if (c <= columnTotals.length && c >= 0) {
                Space clicked = spaces[r][c];
                if (clicked == Space.WATER) {
                    spaces[r][c] = Space.EMPTY;
                } else {
                    spaces[r][c] = Space.WATER;
                }
            }
        }
    }
    
    /**
     * Performs a right click on Square r,c if the indices are legal, o/w does nothing. 
     * An air space becomes empty; other spaces become air. 
     */
    public void rightClick(int r, int c)
    {
        // TODO 5 - complete
        if (r <= rowTotals.length && r >= 0) {
            if (c <= columnTotals.length && c >= 0) {
                Space clicked = spaces[r][c];
                if (clicked == Space.AIR) {
                    spaces[r][c] = Space.EMPTY;
                } else {
                    spaces[r][c] = Space.AIR;
                }
            }
        }
    }
    
    /**
     * Empties all of the spaces.
     */
    public void clear()
    {
        // TODO 6 - complete
        spaces = new Space[rowTotals.length][columnTotals.length];
    }
}
