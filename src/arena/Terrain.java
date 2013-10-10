package arena;

import stackable.Stackable;
import scenario.*;
import stackable.item.*;

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
    
    public Terrain(int nPlayers, Appearence appearence)
    {
        this.fog        = new boolean[nPlayers];
        this.appearence = appearence;
        
        switch (appearence)
        {
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
    
    public Terrain(int nPlayers, Appearence a, Scenario s)
    {
        this(nPlayers, a);
        this.scenario = s;
    }
    
    public Terrain(int nPlayers, Appearence a, Item item)
    {
        this(nPlayers, a);
        this.item = item;
    }
    
    public Terrain(int nPlayers, Appearence a, Scenario s, Item i)
    {
        this(nPlayers, a, i);
        this.scenario = s;
    }
    
    protected boolean setScenario(Scenario scenario)
    {
        if(this.scenario == null)
        { 
            this.scenario = scenario;
            return true;
        }
        return false;
    }
    
    protected Scenario removeScenario()
    {
        Scenario sRet = this.scenario; 
        this.scenario = null;
        return sRet;
    }
    
    public Scenario getScenario()
    {
        return this.scenario;
    }
    
    protected Item removeItem()
    {
        Item iRet = this.item; 
        this.item = null;
        return iRet;
    }
    
    public Item getItem()
    {
        return this.item;
    }
    
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
}
