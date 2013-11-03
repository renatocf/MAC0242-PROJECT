package players;

// Default libraries
import java.util.ArrayList;

// Libraries
import gui.Colors;
import arena.Robot;

// Static symbols
import static gui.Colors.*;

public class Player
{
    // Counter with the amount of
    // players created (to ID's)
    static int total = 0;
    
    // Internal variables
    private int    ID;
    private Base   base;
    private String color;
    private int[]  basePos;
    
    private // Player's armies
    ArrayList<Robot> armies = new ArrayList<Robot>();
    
    /**
     * Default constructor.
     * Creates the ID, color and the base
     * for a given player.
     */
    public Player(Base base)
    {
        this.ID    = ++total;
        this.base  = base;
        this.color = setColor(this.ID);
        
        // Sets the base as his
        base.own(this);
    }
    
    /** 
     * Getter for the player's ID.
     * @return Integer with player ID
     */
    public int    getID() { return this.ID;    }
    
    /** 
     * Getter for the player's color
     * @return String representing player's color
     */
    public String color() { return this.color; }
    
    /**
     * Add a new robot on player's army, 
     * keeping a list that avoid friends
     * to attack themselves.
     * @param newRobot Robot to be added
     *                 in the player's army
     */
    public void addArmy(Robot newRobot)
    {
        this.armies.add(newRobot);
    }
    
    /**
     * Takes out the robot from the 
     * player's army.
     * @param deadRobot Robot to be 
     *        removed from the list
     */
    public void removeArmy(Robot deadRobot)
    {
        this.armies.remove(deadRobot);
    }
    
    /** 
     * Auxiliar method to define a new 
     * player's color.
     * @param  plID Player's ID.
     * @return String representing 
     *         player's color
     */
    final private String setColor(int plID)
    {
        switch(plID)
        {
            case 1:  return ON_BLACK;
            case 2:  return ON_RED;
            case 3:  return ON_GREEN;
            case 4:  return ON_YELLOW;
            default: return "";
        }
    }
}
