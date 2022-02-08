import java.util.ArrayList;
import java.util.HashMap;
/**
 * Aquarium represents a single problem in the game Aquarium.
 *
 * @author Lyndon While 
 * @version 2020
 * 
 * @author Zach Manson (22903345), Oliver Lynch (22989775)
 * @version 20200512
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
    
    private Space[][] solution;
    
    public Aquarium(int[][] groups, int[][] totals, Space[][] solution)
    {
        this.solution = solution;
        size = groups.length;
        System.out.println("size as reported by Aquarium constructor: "+ size);
        aquariums = groups;
        columnTotals = totals[0];
        rowTotals = totals[1];
        spaces = new Space[size][size];
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                spaces[x][y] = Space.EMPTY;
            }
        }
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
        System.out.println("Getting spaces");
        if (spaces == null) System.out.println("spaces is null");
        return spaces;
    }

    public Space[][] getSolution() throws Exception {
        if (solution == null) {
            throw new Exception("Pregen puzzles have no solution");
        }
        return solution;
    }
    
    public void setSolution() {
        spaces = solution;
    }


    /**
     * Performs a left click on Square r,c if the indices are legal, o/w does nothing. 
     * A water space becomes empty; other spaces become water. 
     */
    public void leftClick(int r, int c)
    {
        // TODO 4 - complete
        if (r < size && c < size)
        {
            if (spaces[r][c] == Space.WATER)
            {
                spaces[r][c] = Space.EMPTY;
            }
            else
            {
                spaces[r][c] = Space.WATER;
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
        if (r < size && c < size)
        {
            if (spaces[r][c] == Space.AIR)
            {
                spaces[r][c] = Space.EMPTY;
            }
            else
            {
                spaces[r][c] = Space.AIR;
            }
        }
    }
    
    /**
     * Empties all of the spaces.
     */
    public void clear()
    {
        // TODO 6 - complete
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                spaces[x][y] = Space.EMPTY;
            }
        }
    }
    
    /**
     * Added for project extension: auto-solver
     * Returns the vertical height of the specified aquarium. 
     */
    public int getAquariumSize(int a) {
        int top = -1;
        int bottom = -1;
        
        for (int i = 0; i < aquariums.length; i++) {
            for (int aquarium : aquariums[i]) {
                if (aquarium == a) {
                    if (top == -1) {top = i;}
                    bottom = i;
                }
            }
        }
        
        return bottom - top + 1;
    }
    
    /**
     * Added for project extension: auto-solver
     * Fills the specified aquarium f number of squares deep. 
     */
    public void fillAquarium(int a, int f) {
        int aquariumSize = getAquariumSize(a);
        int airLevel = aquariumSize - f;
        boolean filling = false;
        
        for (int i = 0; i < aquariums.length; i++) {
            for (int j = 0; j < aquariums[i].length; j++) {
                if (aquariums[i][j] == a) {
                    filling = true; 
                    if (airLevel > 0) {
                        spaces[i][j] = Space.AIR;
                    } else {
                        spaces[i][j] = Space.WATER;
                    }
                }
            }
            if (filling) {
                airLevel--;
            }
        }
    }
    
    public void fillAquariumRow(int r, Integer a, Space f) {
        for (int i = 0; i < size; i++) {
            if (a.equals(aquariums[r][i])) {
                spaces[r][i] = f;
            }
        }
    }
}
