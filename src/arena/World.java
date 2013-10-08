package arena;

// Libraries
import robot.*;
import stackable.*;
import operation.*;
import exception.*;

public class World
{
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
