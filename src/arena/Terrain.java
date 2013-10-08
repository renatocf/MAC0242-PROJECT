package arena;

import stackable.Stackable;
import scenario.*;
import stackable.item.*;


public class Terrain implements Stackable
{    
    final Type t;
    final Appearence a;
    boolean[] fog = new boolean[2];
    
    Scenario s;
    
    Item item;

    public Terrain(Appearence a)
    {
        this.a = a;
        switch (a)
        {
            case DIRT   : this.t = Type.NORMAL; break;
            case GRASS  : this.t = Type.NORMAL; break;
            case TUNDRA : this.t = Type.NORMAL; break;
            
            case ROCKY  : this.t = Type.ROUGH;  break;
            case ICE    : this.t = Type.ROUGH;  break;
            case JUNGLE : this.t = Type.ROUGH;  break;
            case WATER  : this.t = Type.ROUGH;  break;
            case SAND   : this.t = Type.ROUGH;  break;
            default     : this.t = Type.NORMAL; break;
        }
        
        fog[0] = fog[1] = true; 
    }
    
    public Terrain(Appearence a, Scenario s)
    {
        this(a);
        this.s = s;    
    }
    
    public Terrain(Appearence a, Item item)
    {
        this(a);
        this.item = item;    
    }
    
    public Terrain(Appearence a, Scenario s, Item item)
    {
        this(a, item);
        this.s = s;    
    }
    
    public boolean setScenario(Scenario s)
    {
        if(this.s != null)
        { 
            this.s = s;
            return true;
        }
        return false;
    }
    
    public Scenario removeScenario()
    {
        Scenario sRet = this.s; 
        this.s = null;
        return sRet;
    }
    
    
    public String toString()
    {
        return t.toString() + " " + a.toString() + " " + s.toString() + " " +  item.toString();
    }
   
    

}


