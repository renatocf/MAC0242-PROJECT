package arena;

// Default libraries
import java.util.Random;

// Graphical Libraries (Internal)
import gui.*;
import gui.textual.*;
import gui.graphical.*;

// Libraries
import robot.*;
import random.*;
import players.*;
import scenario.*;
import operation.*;
import exception.*;
import stackable.*;
import parameters.*;

// Import links
import static parameters.Game.*;

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
 * @see gui
 */
public class World
{
    // Global settings
    private static int id = 1;
    private static int time = 0;
    private static int nPlayers;
    
    // Global characteristics
    private static Map    map;
    private static Robot  turn;
    private static Base[] bases;
    
    /* Robot list */
    private static RobotList armies;
    private static Player[] players;
    
    // Graphical User Interface (GUI)
    private static GUI GUI;
    
    // No instances of this class allowed
    private World() {}
    
    /**
     * Builds a new arena with n players and
     * a given weather.
     * @param np Number of players
     * @param w  Weather
     */
    public static Player[] genesis(int np, Weather w, Interfaces gui, Random rand)
        throws InvalidOperationException
    {
        // Set game configurations
        nPlayers = np;
        map      = new Map(w);
        armies   = new RobotList(nPlayers);
        players  = new Player[nPlayers];
        
        // Create map
        bases = map.genesis(players, rand);
        
        // Create new players
        for(int i = 0; i < 2; i++)
            players[i] = new Player(bases[i]);
            
        // Initializes GUI
        switch(gui)
        {
            case TEXTUAL   : GUI = new Textual   (map); break;
            case GRAPHICAL : GUI = new Graphical (map); break;
            default:         GUI = new Graphical (map);
        }
        
        if(Debugger.info) GUI.printMiniMap();
        return players;
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
        String pre = "[WORLD] ========================";
        Debugger.say(pre + time + "ts\n");
        
        Debugger.say("[POST] Receiveing requests");
        Debugger.say("--------------------------------");
        for(Robot r: armies)
        {
            if(r == null) continue;
            
            // Partially recharges the robot
            r.recharges();
            
            // Do nothing if the robot is still waiting
            // for this turn to execute some action.
            if(!r.ON) continue;
            
            // Otherwise, keep the robot waiting for an
            // answer (this or in other turns).
            yourTurn(r); turn.OFF(0);
        }
        
        Debugger.say("[SORT] Sorting");
        Debugger.say("--------------------------------");
        armies.sort(); // Organize armies accordingly to
                       // their priorities.
        
        Debugger.say("[POST] Sending answers");
        Debugger.say("--------------------------------");
        for(Robot r: armies)
        {
            if(r == null) continue;
            // Send the answer and set the robot to 
            // be able to run a new set of programs
            yourTurn(r); 
            if(r.wait == 0) turn.ON(); else r.wait--;
        }
        
        // Game Over
        int numActivePlayers = 0;
        Player p = null;
        
        for(int i = 0; i < players.length; i++)
        {
            if(players[i] == null) continue;
                                                
            if( players[i].getBase().getCrystals() >= MAX_CRYSTALS )
            {
                GUI.looser(players[i]);
                players[i] = null;
            }
            else 
            { 
                numActivePlayers++;
                p = players[i];
            }
        }
        
        // TODO: Add counter of number of robots created
        if(numActivePlayers == 1)
        {
            GUI.winner(p, time, nPlayers, 6);
            
            try { Thread.sleep(5000); }
            catch (InterruptedException e) {}
            
            System.exit(0);
        }           
        
        if(!(Debugger.info)) GUI.paint();
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
            e.printStackTrace();
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
     * @param pathToProg Robot assembly program
     */
    public static void 
    insertArmy(Player player, String name, String pathToProg)
        throws SegmentationFaultException
    {
        Robot r = map.insertArmy(
            name, player, id++, pathToProg
        );
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
        String pre = "         [DESTROY] ";
        int team = dead.getTeam().getID();
        
        Debugger.say(pre, "Player lost 1 robot");
        players[team-1].removeArmy(dead);
        
        Debugger.say(pre, "Taking out of map");
        map.removeScenario(dead.i, dead.j);
        
        Debugger.say(pre, "Removing from the list");
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
    
    /**
     * Auxiliar function for setting 
     * the robot of the turn.
     * @param r Robot
     */
    private static void yourTurn(Robot r)
    {
        // Set global turn
        turn = r;
        
        // Debug
        Debugger.print("[", turn, "]");
        Debugger.say  ("[", time, "ts]");
        
        Debugger.say  ("    [", "energy before: ", r.power, "]");
        
        try { turn.run(); }
        catch (Exception e) 
        {
            System.err.println
                ("[World]["+ turn.toString() +"] " + e);
        }
        Debugger.say  ("    [", "energy after : ", r.power, "]");
        Debugger.say();
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
     * @deprecated
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
}
