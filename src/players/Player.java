package players;

// Default libraries
import java.util.ArrayList;

// Libraries
import arena.Robot;

/**
 * Create a general player, used to
 * identify the Team of all the scenarios
 * avaiable in the map.
 * @see arena.Robot
 * @see scenario.Scenario
 *
 * @author Renato Cordeiro Ferreira
 */
public class Player
{
    /**
     * Default player: Mother Nature
     */
    public static Player Nature = new Player(null);
    
    // Counter with the amount of
    // players created (to ID's)
    private static int total = -1;
    
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
        if(base != null) base.own(this);
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
     * Returns the player's base.
     * @return Player's base
     */
    public Base getBase()
    {
        return this.base;
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
            case 0:  return "";
            case 1:  return ON_BLACK;
            case 2:  return ON_RED;
            case 3:  return ON_GREEN;
            case 4:  return ON_YELLOW;
            default: return "";
        }
    }
}
