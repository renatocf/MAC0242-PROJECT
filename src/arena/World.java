package arena;

// Libraries
import gui.*;
import robot.*;
import operation.*;
import exception.*;
import stackable.*;
import parameters.*;

/**
 * World - general game configuration.
 * Manages the time, players and scenario
 * of the game.
 *
 * @author Karina Suemi
 * @author Renato Cordeiro Ferreira
 * @author Vinícius Silva
 * @see Action
 * @see gui.Textual
 */
public class World implements Game
{
    // Global settings
    private static int time = 0;
    private static int nPlayers;
    
    // Global characteristics
    private static Map       map;
    private static Robot     turn;
    private static Robot[][] armies;
    
    // Graphical User Interface (GUI)
    private static Textual GUI;
    
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
        
        // Initializes GUI
        GUI = new Textual(map);
        if(Verbosity.v) GUI.printMiniMap();
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
                    System.out.println("[World]["+ turn.toString() +"] " + e);
                }
            }
        }
        if(!(Verbosity.v)) GUI.paint();
    }
    
    static public Stackable[] ctrl(Operation op)
       throws InvalidOperationException
    {
        return Action.ctrl(map, turn, op);
    }
}
