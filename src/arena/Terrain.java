package arena;
import stackable.Stackable;

public enum  Type
{
    NORMAL, 
    ROUGH
}

public enum Appearence
{
    DIRT,
    GRASS,
    ROCKY,
    ICE,
    WATER,
    SAND    
}

public class Terrain 
{    
    final Type t;
    final Appearence a;
    
    Scenario s;
    
    Stackable item;

    public String toString()
    {
        String ret  = t.toString() + " " + a.toString() + " " + s.toString() + " " + item.toString();
        return ret;
    }

}


