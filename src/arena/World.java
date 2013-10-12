package arena;

// Default libraries
import java.io.IOException;
import java.lang.Thread;

// Libraries
import gui.*;
import robot.*;
import scenario.*;
import operation.*;
import exception.*;
import stackable.*;
import parameters.*;
import stackable.item.*;

public class World implements Game
{
    private static int nPlayers;
    private static Robot[][] armies;
    
    private static int time = 0;
    private static Textual GUI;
    
    private static Map map;
    private static Robot turn;
    
    public static void genesis(int np, Weather w)
        throws InvalidOperationException
    {
        // Set game configurations
        nPlayers = np;
        map      = new Map(w);
        armies   = new Robot[nPlayers][ROBOTS_NUM_MAX];
        
        // Create map
        Robot[][] initial = map.genesis(nPlayers);
        
        // Set up initial robots
        for(int i = 0; i < nPlayers; i++)
            for(int j = 0; j < ROBOTS_NUM_INITIAL; j++)
                armies[i][j] = initial[i][j];
        
        GUI = new Textual(map);
        GUI.printMiniMap();
    }
    
    public static void timeStep()
    {
        time++; // On each time step, increments time
        
        if(Verbosity.v)
        {
            String pre = "\n[WORLD] ====================== ";
            Verbosity.debug(pre + time + "ts");
        }

        for(int i = 0; i < nPlayers; i++)
        {
            for(int j = 0; armies[i][j] != null; j++)
            {
                turn = armies[i][j];
                
                if(Verbosity.v) 
                {
                    System.out.print("\n[" + turn.toString() + "]");
                    System.out.println("[" + time + "ts]");
                }
                
                try { turn.run(); }
                catch (Exception e) 
                {
                    System.out.println("["+ turn.toString() +"] " + e);
                }
            }
        }
        if(!(Verbosity.v)) GUI.paint();
    }
    
    static public Stackable[] ctrl(Operation op)
       throws InvalidOperationException
    {
        Stackable[] stackable = null;
        boolean can = false;
        switch(op.getAction())
        {
            case "MOVE" : can = Action.MOVE (map, turn, op); break;
            case "DRAG" : can = Action.DRAG (map, turn, op); break;    
            case "DROP" : can = Action.DROP (map, turn, op); break;
            case "HIT"  : can = Action.HIT  (map, turn, op); break;
            
            case "LOOK" : stackable = Action.LOOK (map, turn, op); break;
            case "SEE"  : stackable = Action.SEE  (map, turn, op); break;
        }
        
        if(stackable == null) 
        {
            stackable = new Stackable[1]; 
            stackable[0] = new Num( (can)? 1 : 0 );
        }
        return stackable;
    }
}

// :¨:
// =%=
// / \
