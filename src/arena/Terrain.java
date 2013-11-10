package arena;

// Libraries
import scenario.*;
import stackable.item.*;
import stackable.Stackable;

/**
 * <b>Hexagonal Terrain</b><br>
 * Virtual hexagon defined by its type
 * and appearence. It is able to hold 
 * over it one scenario and one item.
 * (May be invisible by fog war).
 *
 * @see scenario
 * @see stackable.item
 * 
 * @author Karina Suemi Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Nascimento Silva
 */
public class Terrain implements Stackable
{    
    // Unchangable characteristics
    final Type type;
    final Appearence appearence;
    
    // Temporary characteristics
    Item item;
    Scenario scenario;
    
    // War fog
    boolean[] fog;
    
    /** 
     * Default constructor<br>
     * Creates a terrain with a given appearence,
     * defining its type accordingly to it. Also,
     * keeps a bitmap with the war fog.
     * 
     * @param nPlayers   Number of players
     * @param appearence Terrain appearence
     */
    public Terrain(int nPlayers, Appearence appearence)
    {
        this.fog        = new boolean[nPlayers];
        this.appearence = appearence;
        
        switch (appearence)
        {
            case DEEP   : this.type = Type.BLOCKED; break;
            
            case DIRT   : this.type = Type.NORMAL; break;
            case GRASS  : this.type = Type.NORMAL; break;
            case TUNDRA : this.type = Type.NORMAL; break;
            
            case ROCKY  : this.type = Type.ROUGH;  break;
            case ICE    : this.type = Type.ROUGH;  break;
            case JUNGLE : this.type = Type.ROUGH;  break;
            case WATER  : this.type = Type.ROUGH;  break;
            case SAND   : this.type = Type.ROUGH;  break;
            
            default     : this.type = Type.NORMAL; break;
        }
        
        fog[0] = fog[1] = true; 
    }
    
    /** 
     * Secondary constructor<br>
     * Does the same as the default, aditionaly 
     * putting a scenario on the terrain.
     * 
     * @param nPlayers Number of players
     * @param a        Terrain appearence
     * @param s        Scenario
     */
    public Terrain(int nPlayers, Appearence a, Scenario s)
    {
        this(nPlayers, a);
        this.scenario = s;
    }
    
    /** 
     * Secondary constructor<br>
     * Does the same as the default, aditionaly 
     * putting an item on the terrain.
     * 
     * @param nPlayers Number of players
     * @param a        Terrain appearence
     * @param item     Item
     */
    public Terrain(int nPlayers, Appearence a, Item item)
    {
        this(nPlayers, a);
        this.item = item;
    }
    
    /** 
     * Secondary constructor<br>
     * Does the same as the default, aditionaly 
     * putting a scenario and an item on the 
     * terrain.
     * 
     * @param nPlayers Number of players
     * @param a        Terrain appearence
     * @param s        Scenario
     * @param i        Item
     */
    public Terrain(int nPlayers, Appearence a, Scenario s, Item i)
    {
        this(nPlayers, a, i);
        this.scenario = s;
    }
    
    /** 
     * General function to print, in a more legible
     * format, the terrain. It is used mainly with 
     * debug purposes.
     * @return String representing the terrain.
     */
    public String toString()
    {
        String sItem = (item == null) 
                       ? ("NONE") : (item.toString());
        String sType = (type == null) 
                       ? ("NONE") : (type.toString());
        String sScen = (scenario == null) 
                       ? ("NONE") : (scenario.toString());
        String sAppe = (appearence == null) 
                       ? ("NONE") : (appearence.toString());
        
        return "[type:" + sType + "] [appearence:" + sAppe + "] "
             + "[scenario:" + sScen + "] [item:" + sItem + "]";
    }
    
    /** 
     * Getter fot the terrain's appearence. 
     * @return Appearence
     */
    public Appearence getAppearence()
    {
        return this.appearence;
    }
    
    /** 
     * Getter for the terrain's item. 
     * @return Item (or null, if none)
     */
    public Item getItem()
    {
        return this.item;
    }
    
    /** 
     * Getter for the terrain's type. 
     * @return Terrain's type
     */
    public Type getType()
    {
        return this.type;
    }
    
    /** 
     * Getter for the terrain's scenario.
     * @return Scenario (or null, if none)
     */
    public Scenario getScenario()
    {
        return this.scenario;
    }
    
    /** 
     * Setter for the scenario.
     * @param  scenario   Scenario
     * @return Booelan to respond if the 
     *         action was successful
     */
    protected boolean setScenario(Scenario scenario)
    {
        if(this.scenario == null)
        { 
            this.scenario = scenario;
            return true;
        }
        return false;
    }
    
    /** 
     * Remove the terrain's scenario.
     * @return Removed scenario
     *         (or null, if none)
     */
    protected Scenario removeScenario()
    {
        Scenario sRet = this.scenario; 
        this.scenario = null;
        return sRet;
    }
    
    /** 
     * Remove the terrain's item.
     * @return Removed item
     *         (or null, if none)
     */
    protected Item removeItem()
    {
        Item iRet = this.item; 
        this.item = null;
        return iRet;
    }
}
