/**********************************************************************/
/* Copyright 2013 KRV                                                 */
/*                                                                    */
/* Licensed under the Apache License, Version 2.0 (the "License");    */
/* you may not use this file except in compliance with the License.   */
/* You may obtain a copy of the License at                            */
/*                                                                    */
/*  http://www.apache.org/licenses/LICENSE-2.0                        */
/*                                                                    */
/* Unless required by applicable law or agreed to in writing,         */
/* software distributed under the License is distributed on an        */
/* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,       */
/* either express or implied.                                         */
/* See the License for the specific language governing permissions    */
/* and limitations under the License.                                 */
/**********************************************************************/
package arena;

// Default libraries
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

// Graphical Libraries (Internal)
import ui.*;
import ui.textual.*;
import ui.graphical.*;

// Libraries
import robot.*;
import random.*;
import remote.*;
import players.*;
import scenario.*;
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
 * @see ui
 */
final public class World
{
    // Global settings
    private static int id = 1;
    private static int time = 0;
    private static int nActivePlayers;
    
    // Players and AI
    private static int nAI;
    private static int nPlayers;
    
    // Global characteristics
    private static Map    map;
    private static Robot  turn;
    private static Base[] bases;
    
    /* Robot list */
    private static RobotList armies;
    private static Player[] players;
    
    private static ConcurrentHashMap<NewRobotOp,Integer> waitList;
    
    // No instances of this class allowed
    private World() {}
    
    /**
     * Builds a new arena with n players and
     * a given weather.
     * @param np Number of players
     * @param w  Weather
     */
    public static Player[] 
    genesis(int np, int nAI, Weather w, Interfaces gui, Random rand)
        throws InvalidOperationException
    {
        // Set game configurations
        World.nAI = nAI;
        World.nActivePlayers = World.nPlayers = np;
        
        // Creating elements for the game
        map      = new Map(w);
        armies   = new RobotList(nPlayers);
        players  = new Player[nPlayers];
        waitList = new ConcurrentHashMap<NewRobotOp,Integer>();
        
        // Create map
        bases = map.genesis(nPlayers, rand);
        
        // Create new players
        for(int i = 0; i < nPlayers; i++)
        {
            UI UI = null;
            
            // Set up player with its base and UI
            players[i] = new Player(bases[i]);
            
            // Initializes UI for non-AI players
            if(i < nAI) continue;
            
            Player p = players[i];
            switch(gui)
            {
                case NONE      : UI = null;                   break;
                case TEXTUAL   : UI = new Textual   (map, p); break;
                case GRAPHICAL : UI = new Graphical (map, p); break;
                default:         UI = new Graphical (map, p);
            }
            p.setUI(UI);
        }
        
        if(Debugger.debugging()) 
            for(Player p: players) 
                if(p.UI != null) p.UI.printMiniMap();
        
        return players;
    }
    
    /**
     * Runs one game time step. On each
     * turn, sort the robots accordingly
     * to their priorities, solving conflicts
     * randomically. Then, executes their
     * actions and attend their requests.
     * @return Boolean indicating if the 
     *         game continues
     */
    public static boolean timeStep()
    {
        time++; // On each time step, increments time
        
        // Try to add a waiting robot
        for(NewRobotOp op: waitList.keySet())
        {
            int wait = waitList.get(op);
            if(wait == 0)
            {
                insertArmy(op.player, op.name, op.path);
                waitList.remove(op);
            }
            else 
            {
                waitList.put(op, wait-1);
                if(wait%3 == 0 && !op.name.startsWith("Enemy"))
                    System.out.println("[" + op.name + "]" + "I will be ready in " + wait/3);
            }
        }
        
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
        
        // Paint one frame
        for(Player p: players)
            if(p.UI != null) p.UI.paint();
        
        // Stops only when the game is over
        return gameOver();
    }
    
    /**
     * Send a message to a player.
     * Given the player's user interface, send
     * a message for him (whith or without info).
     */
    public static void chat(Player player, String msg)
    {
        if(player.UI != null) player.UI.printText(msg);
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
        try { armies.setOperation(turn, op); }
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
     * @param op New robot system remote operation
     */
    public static void
    insertArmy(NewRobotOp op)
    {
        int minWait=0;
        for(NewRobotOp o : waitList.keySet())
        {
            if(op.player == o.player)
                if(minWait < waitList.get(o))
                    minWait = waitList.get(o);
        }
        waitList.put(op, Robot.buildTime + minWait);
    }
    
    /**
     * Create a new robot in the map.
     * Make a new robot to the player 'player',
     * with name 'name', putting it in the 
     * position (i,j) of the map, programmed 
     * with the assembly program defined by 
     * the file in 'pathToProg'.
     * 
     * @param  player     Robot owner
     * @param  name       Name of the new robot
     * @param  pathToProg Robot's assembly program
     */
    public static void
    insertArmy(Player player, String name, String pathToProg)
    {
        try {
            
            Robot r = map.insertArmy(
                name, player, id++, pathToProg
            );
            player.armies.add(r);
            armies.add(r);
            
        } catch(SegmentationFaultException e) {
            System.err.println("Invalid position");
            e.printStackTrace();
        }
    }
    
    /**
     * Remove a given robot from the map.
     * Take out the robot from the map and
     * remove it from the Robot List. 
     * 
     * @param  dead Robot to be removed.
     */
    public static void removeArmy(Robot dead)
    {
        // Send message to the user   
        chat(dead.getTeam(), dead.toString() + " has been defeated!"); 
        
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
        if(s instanceof Robot) removeArmy((Robot) s);
        else map.removeScenario(i,j);
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
     * Defines the end of the game.
     * @return Boolean pointing if the game is over
     */
    private static boolean gameOver()
    {
        for(int i = 0; i < players.length; i++)
        {
            if(players[i] == null) continue;
                                                
            /* More than 5 crystals: player looses */
            if( players[i].getBase().getCrystals() >= MAX_CRYSTALS )
            {
                if(players[i].UI != null)
                {
                    players[i].UI.looser();
                    try { Thread.sleep(5000); }
                    catch (InterruptedException e) {}
                }
                
                // Take out player from the game
                players[i] = null;
                nActivePlayers--;
            }
            
            /* One player: this is the winner */
            if(nActivePlayers == 1)
            {
                Player winner = null;
                for(Player p: players) if(p != null) winner = p;
                
                /* Sets the winner */
                if(winner.UI != null)
                    winner.UI.winner(
                        time, nPlayers, armies.getPopulation()
                    );
                
                Debugger.say("=============================");
                Debugger.say("========[ GAME OVER ]========");
                Debugger.say("=============================");
                Debugger.say("[TIME] "  , time, "ts"        );
                Debugger.say("[WINNER] ", winner.toString() );
                Debugger.close();
                
                try { Thread.sleep(5000); }
                catch (InterruptedException e) {}
                
                return false;
            }
        }
        return true;
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
