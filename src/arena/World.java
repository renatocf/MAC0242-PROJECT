package arena;

// Libraries
import robot.*;
import stackable.*;
import operation.*;
import exception.*;

public class World
{
    static Map map;
    static Robot[][] armies = new Robot[2][100];
    
    public static void genesis(Weather w)
    {
         map = new Map(w);
         map.genesis();
         armies[0][0] = (Robot) map.map[42][13].s;
         armies[1][0] = (Robot) map.map[42][12].s;
    }
    
    static public Num ctrl(Operation op)
    {
        switch(op.getAction())
        {
            case "MOVE" : MOVE(op, armies[0][0]); break;
            case "DRAG" : DRAG(); break;    
            case "DROP" : DROP(); break;
            case "HIT"  : HIT (); break;        
        }
        
        Num answer = new Num(1); 
        return answer;
    }
    
    private static void MOVE (Operation op, Robot robot) 
    {
         Direction dir = (Direction) op.getArgument()
         int movement[] = dir.get()
    }
    private static void DRAG () {  }
    private static void DROP () {  }
    private static void HIT  () {  }
}
