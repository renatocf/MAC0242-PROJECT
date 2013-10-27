package arena;

// Libraries
import gui.*;
import robot.*;
import scenario.*;
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
        if(Debugger.info) GUI.printMiniMap();
    }
    
    public static void timeStep()
    {
        time++; // On each time step, increments time
        
        // Debug
        String pre = "\n[WORLD] ====================== ";
        Debugger.say(pre + time + "ts");

        for(int i = 0; i < nPlayers; i++)
        {
            for(int j = 0; j < ROBOTS_NUM_MAX; j++)
            {
                // No army found in the list: continue
                if(armies[i][j] == null) continue;
                else turn = armies[i][j];
                
                // Debug
                Debugger.print("\n[" + turn.toString() + "]");
                Debugger.say  ("[" + time + "ts]");
                
                try { turn.run(); }
                catch (Exception e) 
                {
                    System.out.println
                        ("[World]["+ turn.toString() +"] " + e);
                }
            }
        }
        if(!(Debugger.info)) GUI.paint();
    }
    
    public static Stackable[] ctrl(Operation op)
       throws InvalidOperationException
    {
        return Action.ctrl(map, turn, op);
    }
    
    public static void insertArmy
        (int player, String name, int i, int j, String pathToProg)
        throws SegmentationFaultException
    {
        for(int id = 0; id < ROBOTS_NUM_MAX; id++)
            if(armies[player-1][id] == null)
            {
                Robot r = map.insertArmy(name, player, id, 
                                         i, j, pathToProg);
                armies[player-1][id] = r; break;
            }
    }
    
    public static void removeArmy(Robot died)
        throws SegmentationFaultException
    {
        int player  = died.team; 
        int robotID = died.ID; 
        map.removeScenario(died.i, died.j);
        armies[player-1][robotID] = null;
    }
    
    public static void destroy(int i, int j)
    {
        // Remove all scenarios, but robots.
        // This ones are removed by the ctrl.
        Scenario s = map.map[i][j].getScenario();
        try 
        { 
            if(s instanceof Robot) 
                removeArmy((Robot) s);
            else map.removeScenario(i,j);
        } 
        catch(SegmentationFaultException e) 
        {
            System.out.println(
               "[World] Destroying in invalid " +
               "position (" + i + "," + j + ")");
        }
    }
}
