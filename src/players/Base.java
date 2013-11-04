package players;

// Libraries
import arena.Robot;
import scenario.Scenario;

/**
 * <b>Scenario - Base</b><br>
 * Main target to the crystals of 
 * another player.
 */
public class Base implements Scenario 
{
    private int posX;
    private int posY;
    private int crystals = 0;
    private Player player = null;
    
    /** 
     * Default constructor.<br>
     * Set the coordinates of the 
     * base (to be accessible by 
     * the player and its robots).
     */
    public Base(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
    }
    
    /**
     * Sets who owns this base.
     * @param ownew Player who owns the base
     */ 
    public void own(Player owner)
    {
        if(this.player == null)
            this.player = owner;
    }
    
    /**
     * Getter for the base owner ID.
     * @return Player that owna the base
     */ 
    public Player getTeam() { return this.player; }

    /**
     * Getter for the base horizontal position.
     * @param  r Robot asking for info
     * @return Base horizontal position
     */
    public int getPosX(Robot r)   
    { 
        // The player will be the only to 
        // know how to get the base pos.
        if(r.getTeam() != this.player) return -1;
        return this.posX;   
    }

    /**
     * Getter for the base horizontal position.
     * @param  p Player asking for info
     * @return Base horizontal position
     */    
    public int getPosX(Player p)   
    { 
        // The player will be the only to 
        // know how to get the base pos.
        if(this.player != p) return -1;
        return this.posX;   
    }
    
    /**
     * Getter for the base vertical position.
     * @param  r Robot asking for info
     * @return Base vertical position
     */
    public int getPosY(Robot r)
    { 
        // The player will be the only to 
        // know how to get the base pos.
        if(r.getTeam() != this.player) return -1;
        return this.posY;   
    }

    /**
     * Getter for the base vertical position.
     * @param  p Player asking for info
     * @return Base vertical position
     */
    public int getPosY(Player p)
    { 
        // The player will be the only to 
        // know how to get the base pos.
        if(this.player != p) return -1;
        return this.posY;   
    }
    
    /** 
     * Throws a crystal in the base.
     * @param  r Robot attacking the base
     *           (throwing a crystal on it)
     * @return If the base is the robot's 
     *         team, then does not allow it
     *         to throw in that place.
     */
    public boolean addCrystal(Robot r)
    { 
        // Other players will be the only to 
        // get to put a crystal in the base
        if(r.getTeam() == this.player) return false;
        crystals++; return true;
    }
    
    /**
     * Number of crystals inside the base.
     * @return Number of crystals in the base
     */
    public int getCrystals()
    {
        return crystals;
    }
    
    //
    // Scenarion interface methods
    //

    public int takeDamage(int damage) 
    {
        // Bases cannot be destroyed...
        return 0;
    }
    
    public int getHP()       { return 42; }
    public String toString() { return "(ß) Base"; }
}
