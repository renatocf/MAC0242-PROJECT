package arena;

// Libraries
import robot.*;
import stackable.*;
import operation.*;
import exception.*;

public class World
{
    Num permission;
    Direction dir;
    
    public World(boolean param)
    {
        this.permission = (param) ? new Num(1) : new Num(0);
    }

    public void setDirection(Direction d)
    {
        this.dir = d;
    }

    public Num get()
    {
        return this.permission;
    }

    static public Num ctrl(Operation op)
    {
        switch(op.getAction())
        {
            case "MOVE" : MOVE(); break;
            case "DRAG" : DRAG(); break;    
            case "DROP" : DROP(); break;
            case "HIT"  : HIT (); break;        
        }
        
        Num answer = new Num(1); 
        return answer;
    }
    
    private static void MOVE () {  }
    private static void DRAG () {  }
    private static void DROP () {  }
    private static void HIT  () {  }
}
