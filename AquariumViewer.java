
/**
 * AquariumViewer represents an interface for playing a game of Aquarium.
 *
 * @author Lyndon While
 * @version 2020
 */
import java.awt.*;
import java.awt.event.*; 
import javax.swing.SwingUtilities;

public class AquariumViewer implements MouseListener
{
    private final int BOXSIZE = 40;          // the size of each square
    private final int OFFSET  = BOXSIZE * 2; // the gap around the board
    private       int WINDOWSIZE;            // set this in the constructor 
    
    private Aquarium puzzle; // the internal representation of the puzzle
    private int        size; // the puzzle is size x size
    private SimpleCanvas sc; // the display window

    /**
     * Main constructor for objects of class AquariumViewer.
     * Sets all fields, and displays the initial puzzle.
     */
    public AquariumViewer(Aquarium puzzle)
    {
        // TODO 8
        this.puzzle = puzzle;
        size = puzzle.getSize();
        
        WINDOWSIZE = (OFFSET*2) + (BOXSIZE*size);
        sc = new SimpleCanvas("Aquarium Puzzle - Zach & Oliver", WINDOWSIZE, WINDOWSIZE, Color.white);
        
    }
    
    /**
     * Selects from among the provided files in folder Examples. 
     * xyz selects axy_z.txt. 
     */
    public AquariumViewer(int n)
    {
        this(new Aquarium("Examples/a" + n / 10 + "_" + n % 10 + ".txt"));
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public AquariumViewer()
    {
        this(61);
    }
    
    /**
     * Returns the current state of the puzzle.
     */
    public Aquarium getPuzzle()
    {
        // TODO 7a - complete
        return puzzle;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        // TODO 7b - complete
        return size;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        // TODO 7c - complete
        return sc;
    }
    
    /**
     * Displays the initial puzzle; see the LMS page for the format.
     */
    private void displayPuzzle()
    {
        // TODO 13
    }
    
    /**
     * Displays the grid in the middle of the window.
     */
    public void displayGrid()
    {
        // TODO 9 - complete
        for (int s = 0; s < size+1; s++) {
            sc.drawLine(OFFSET+(BOXSIZE*s), OFFSET, OFFSET+(BOXSIZE*s), OFFSET+(size*BOXSIZE), Color.black);
            sc.drawLine(OFFSET, OFFSET+(BOXSIZE*s), OFFSET+(size*BOXSIZE), OFFSET+(BOXSIZE*s), Color.black);
        }
    }
    
    /**
     * Displays the numbers around the grid.
     */
    public void displayNumbers()
    {
        // TODO 10 - complete
        sc.setFont(new Font("Arial",1,20));
        int[] rowTotals = puzzle.getRowTotals();
        for (int r = 0; r < rowTotals.length; r++){
            sc.drawString(String.valueOf(rowTotals[r]), OFFSET-(BOXSIZE/2)-4, OFFSET+(BOXSIZE*r)+(BOXSIZE/2)+8, Color.black);
        }
        
        int[] columnTotals = puzzle.getColumnTotals();
        for (int c = 0; c < columnTotals.length; c++){
            sc.drawString(String.valueOf(columnTotals[c]), OFFSET+(BOXSIZE*c)+(BOXSIZE/2)-4, OFFSET-(BOXSIZE/2)+8, Color.black);
        }
    }
    
    /**
     * Displays the aquariums.
     */
    public void displayAquariums()
    {
        // TODO 11
    }
    
    /**
     * Displays the buttons below the grid.
     */
    public void displayButtons()
    {
        // TODO 12 - complete
        sc.setFont(new Font("Arial",1,20));
        sc.drawRectangle(0, WINDOWSIZE-BOXSIZE, WINDOWSIZE/2, WINDOWSIZE, Color.green);
        sc.drawString("CHECK", BOXSIZE+30, WINDOWSIZE-(BOXSIZE/2)+8, Color.black);
        
        sc.drawRectangle(WINDOWSIZE/2, WINDOWSIZE-BOXSIZE, WINDOWSIZE, WINDOWSIZE, Color.red);
        sc.drawString("CLEAR", BOXSIZE+(WINDOWSIZE/2)+30, WINDOWSIZE-(BOXSIZE/2)+8, Color.black);
    }
    
    /**
     * Updates the display of Square r,c. 
     * Sets the display of this square to whatever is in the squares array. 
     */
    public void updateSquare(int r, int c)
    {
        // TODO 14
    }
    
    /**
     * Responds to a mouse click. 
     * If it's on the board, make the appropriate move and update the screen display. 
     * If it's on SOLVED?,   check the solution and display the result. 
     * If it's on CLEAR,     clear the puzzle and update the screen display. 
     */
    public void mousePressed(MouseEvent e) 
    {
        // TODO 15
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
