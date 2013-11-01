package arena;

// Libraries
import gui.*;
import robot.*;
import random.*;
import scenario.*;
import operation.*;
import exception.*;
import stackable.*;
import parameters.*;

/**
 * <b>World - general game configuration.</b><br>
 * Manages the time, players and the
 * arena of the game.
 *
 * @author Karina Suemi
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 * 
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
    private static Map   map;
    private static Robot turn;
    
    /* Robot list */
    private static RobotList armies;
    
    // Graphical User Interface (GUI)
    private static Textual GUI;
    
    /**
     * Builds a new arena with n players and
     * a given weather.
     * @param np Number of players
     * @param w  Weather
     */
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
    
    /**
     * Runs one game time step. On each
     * turn, sort the robots accordingly
     * to their priorities, solving conflicts
     * randomically. Then, executes their
     * actions and attend their requests.
     */
    public static void timeStep()
    {
        time++; // On each time step, increments time
        
        // Debug
        String pre = "\n[WORLD] ====================== ";
        Debugger.say(pre + time + "ts");
        
        for(Robot r: armies)
        {
            // Set global turn
            turn = r;
            
            // Debug
            Debugger.print("\n[" + turn.toString() + "]");
            Debugger.say  ("[" + time + "ts]");
            
            try { turn.run(); turn.OFF(); }
            catch (Exception e) 
            {
                System.out.println
                    ("[World]["+ turn.toString() +"] " + e);
            }
        }
        
        armies.sort(); // Organize armies accordingly to
                       // their priorities.
        
        for(Robot r: armies)
        {
            // Set global turn
            turn = r;
            
            // Debug
            Debugger.print("\n[" + turn.toString() + "]");
            Debugger.say  ("[" + time + "ts]");
            
            try { turn.run(); turn.ON(); }
            catch (Exception e) 
            {
                System.out.println
                    ("[World]["+ turn.toString() +"] " + e);
            }
        }
        if(!(Debugger.info)) GUI.paint();
    }
    
    /**
     * Receive the robot's syscalls.
     * Uses an object with type 'operation'
     * to execute some action, which could
     * be an attack (HIT), an iteraction 
     * with the environment (DRAG/DROP),
     * a displacement (MOVE) or even to get
     * visual info (SEE/LOOK).
     * 
     * @param  op Operation
     * @return Stackable with the answer
     *         (or Num 0 if the system 
     *         refused the operation).
     */
    public static Stackable[] ctrl(Operation op)
       throws InvalidOperationException
    {
        return Action.ctrl(map, turn, op);
    }
    
    /**
     * Receive a robot's syscalls.
     * Uses an object with type 'operation'
     * to store a robot action, which could
     * be an attack (HIT), an iteraction 
     * with the environment (DRAG/DROP),
     * a displacement (MOVE) or even to get
     * visual info (SEE/LOOK).
     * 
     * @param  op Operation
     */
    public static void POST(Operation op)
    {
        try { armies.setOperation(turn, op); 
        }
        catch (NotInitializedException e) {
            System.err.println(e);
        }
    }
    
    /**
     * Answer's to a robot's syscalls.
     * Uses an object with type 'operation'
     * to execute some action, returning 
     * to the user.
     * 
     * @return Stackable with the answer
     *         (or Num 0 if the system 
     *         refused the operation).
     */
    public static Stackable[] GET()
       throws InvalidOperationException
    {
        try {
            Operation op = armies.getOperation(turn);
            return Action.ctrl(map, turn, op);
        }
        catch (NotInitializedException e) {
            System.err.println(e);
        }
        return null;
    }
    
    /**
     * Create a new robot in the map.
     * Make a new robot to the player 'player',
     * with name 'name', putting it in the 
     * position (i,j) of the map, programmed 
     * with the assembly program defined by 
     * the file in 'pathToProg'.
     * 
     * @param player     Robot owner
     * @param name       Name of the new robot
     * @param i          Vertical position
     * @param j          Horizontal position
     * @param pathToProg Robot assembly program
     */
    public static void insertArmy
        (int player, String name, int i, int j, String pathToProg)
        throws SegmentationFaultException
    {
        Robot r = map.insertArmy(name, player, id++, 
                                 i, j, pathToProg);
        armies.add(r);
    }
    
    /**
     * Remove a given robot from the map.
     * Take out the robot from the map and
     * remove it from the Robot List. 
     * 
     * @param dead Robot to be removed.
     */
    public static void removeArmy(Robot dead)
        throws SegmentationFaultException
    {
        map.removeScenario(dead.i, dead.j);
        armies.remove(dead);
    }
    
    /**
     * Remove a given scenario from the map.
     * Take out a scenario from the position
     * (i,j) of the map.
     * 
     * @param i Vertical position
     * @param j Horizontal position
     */
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
            System.err.println(
               "[World] Destroying in invalid " +
               "position (" + i + "," + j + ")");
        }
    }
}
