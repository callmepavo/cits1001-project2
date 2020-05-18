import java.util.ArrayList;
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

    /**
     * Constructor for objects of class Aquarium. 
     * Creates, initialises, and populates all of the fields.
     */
    public Aquarium(String filename)
    {
        // TODO 3 - complete
        FileIO file = new FileIO(filename);
        ArrayList<String> lines = file.getLines();
        columnTotals = parseLine(lines.get(0));
        rowTotals = parseLine(lines.get(1));
        size = rowTotals.length;
        
        aquariums = new  int[size][size];
        for (int i = 0; i < size; i++)
        {
            aquariums[i] = parseLine(lines.get(i+3));
        }
        
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
    
    
    
    
    // FOR AUTOSOLVE
    public int getCurrentRowWaterAmount(int r)
    {
        int currentRowAmount = 0;
        for (int c = 0; c < size; c++)
        {
            if (spaces[r][c] == Space.WATER)
            {
                currentRowAmount++;
            }
        }
        return currentRowAmount;
    }
    
    public int getCurrentRowAirAmount(int r)
    {
        int currentRowAmount = 0;
        for (int c = 0; c < size; c++)
        {
            if (spaces[r][c] == Space.AIR)
            {
                currentRowAmount++;
            }
        }
        return currentRowAmount;
    }
    
    public int getCurrentColumnnAmount(int c)
    {
        int currentColumnAmount = 0;
        for (int r = 0; r < size; r++)
        {
            if (spaces[r][c] == Space.WATER)
            {
                currentColumnAmount++;
            }
        }
        return currentColumnAmount;
    }
    
    /** Returns the most extreme rows and columns a group exists in
     * [0] = top row
     * [1] = bottom row
     * [2] = leftmost column
     * [3] = rightmost column
    */
    public int[] getAquariumEdges(int group)
    {
        int[] edges = new int[4];
        edges[0] = size-1;
        edges[1] = 0;
        edges[2] = size-1;
        edges[3] = 0;
        for (int r = 0; r < size; r++)
        {
            for (int c = 0; c < size; c++)
            {
                if (aquariums[r][c] == group)
                {
                    if (r < edges[0]) {edges[0] = r;}
                    if (r > edges[1]) {edges[1] = r;}
                    if (c < edges[2]) {edges[2] = c;}
                    if (c > edges[3]) {edges[3] = c;}
                }
            }
        }
        return edges;
    }

    /**
     * Gets aquarium group's width on row r
     */
    public int getAquariumRowWidth(int group, int r)
    {
        int width = 0;
        for (int c = 0; c < size; c++)
        {
            if (aquariums[r][c] == group) {width++;}
        }
        return width;
    }
    
    /**
     * Gets aquarium group's width on column c
     */
    public int getAquariumColumnHeight(int group, int c)
    {
        int height = 0;
        for (int r = 0; r < size; r++)
        {
            if (aquariums[r][c] == group) {height++;}
        }
        return height;
    }
    
    public int findAquariumRowLegality(int group, int rowWithinGroup)
    {
        // Find row of first group appearance
        int firstAppearance = 0;
        outerLoop: for (int r = 0; r < size; r++)
        {
            for (int c = 0; c < size; c++)
            {
                if (aquariums[r][c] == group)
                {
                    firstAppearance = r;
                    break outerLoop;
                }
            }
        }
        
        int row = firstAppearance + rowWithinGroup;
        // iff groupRow + existing water is more than this line can handle
        if (  getAquariumRowWidth(group, row)
            + getCurrentRowWaterAmount(row)
            
            > rowTotals[row]  ) 
        {
            return -1;//illegal
        }
        else if (  size
                 - getCurrentRowWaterAmount(row)
                 - getCurrentRowAirAmount(row)
                 - getAquariumRowWidth(group, row)  
                 < rowTotals[row] - getCurrentRowWaterAmount(row)  ) 
        {   // iff row is impossible without this groupRow
            System.out.println("group" +group);
            System.out.println("rowWithinGroup"+rowWithinGroup);
            System.out.println("firstappearance "+firstAppearance);
            System.out.println("row"+ rowWithinGroup);
            System.out.println(size +"-"+ getCurrentRowWaterAmount(row)+"-"+getCurrentRowAirAmount(row)+"-"+getAquariumRowWidth(group, row)  +" < "+rowTotals[row]+"-"+ getCurrentRowWaterAmount(row));
            return 1;//required
        }
        
        return 0;//legal
    }

    public int findAquariumColumnLegality(int group, int columnWithinGroup)
    {return 0;}
    
    /**
     * Increases water level in a given group by one
     * returns true if sucessful, false if not
     */
    public void setAquariumWaterLevel(int group, int level)
    {
        
        for (int row = getAquariumEdges(group)[1]; row >= level+getAquariumEdges(group)[0]; row--)
        {
            System.out.println(row);
            for (int c = 0; c < size; c++)
            {
                if (aquariums[row][c] == group)
                {
                    spaces[row][c] = Space.WATER;
                }
            }
        }
    }
    
    public void setAquariumAirLevel(int group, int level)
    {
        
        for (int row = getAquariumEdges(group)[0]; row <= level; row++)
        {
            System.out.println(row);
            for (int c = 0; c < size; c++)
            {
                if (aquariums[row][c] == group)
                {
                    spaces[row][c] = Space.AIR;
                }
            }
        }
    }
    
    /*
    /*
     * Added for project extension: auto-solver
     * Returns the vertical height of the specified aquarium. 
     * /
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
    
    /*
     * Added for project extension: auto-solver
     * Fills the specified aquarium f number of squares deep. 
     * /
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
    
    public ArrayList<Integer> getAquariumIDs() {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int[] row: aquariums) {
            for (int id: row) {
                if (!ids.contains(id)){
                    ids.add(id);
                }
            }
        }
        return ids;
    }*/
    
    
}
