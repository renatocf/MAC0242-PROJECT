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
    private static int id = 1;
    private static int time = 0;
    private static int nPlayers;
    
    // Global characteristics
    private static Map       map;
    private static Robot     turn;
    
    /* Robot list */
    private static RobotList armies;
    
    // Graphical User Interface (GUI)
    private static Textual GUI;
    
    public static void genesis(int np, Weather w)
        throws InvalidOperationException
    {
        // Set game configurations
        nPlayers = np;
        map      = new Map(w);
        armies   = new RobotList(nPlayers);
        
        // Create map
        Robot[][] initial = map.genesis(nPlayers);
        
        // Set up initial robots
        for(int i = 0; i < nPlayers; i++)
            for(int j = 0; j < ROBOTS_NUM_INITIAL; j++)
            {
                Debugger.say("[i:",i,"],[j:",j,"]");
                if(initial[i][j] == null)
                    Debugger.say("[World] É null");
                armies.add(initial[i][j]);
            }
        
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
        
        armies.sort(); // Organize armies accordingly to
                       // their priorities.
        while( (turn = armies.next()) != null )
        {
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
        //for(int i = 0; i < nPlayers; i++)
        //{
        //    for(int j = 0; j < ROBOTS_NUM_MAX; j++)
        //    {
        //        // No army found in the list: continue
        //        if(armies[i][j] == null) continue;
        //        else turn = armies[i][j];
        //        
        //        // Debug
        //        Debugger.print("\n[" + turn.toString() + "]");
        //        Debugger.say  ("[" + time + "ts]");
        //        
        //        try { turn.run(); }
        //        catch (Exception e) 
        //        {
        //            System.out.println
        //                ("[World]["+ turn.toString() +"] " + e);
        //        }
        //    }
        //}
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
        Robot r = map.insertArmy(name, player, id++, 
                                 i, j, pathToProg);
        armies.add(r);
    }
    
    public static void removeArmy(Robot dead)
        throws SegmentationFaultException
    {
        map.removeScenario(dead.i, dead.j);
        armies.remove(dead);
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
