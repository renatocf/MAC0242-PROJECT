package arena;

// Default libraries
import java.io.*;
import java.util.Vector;
import java.lang.reflect.*;

// Libraries
import robot.*;
import random.*;
import stackable.*;
import exception.*;
import parameters.*;
import stackable.item.*;
import scenario.Scenario;

/**
 * <b>Map</b><br>
 * Control an hexagonal matrix of
 * terraints in which are placed 
 * all scenarios, items and robots. 
 * Make changes on it accordingly
 * to requests made by the world 
 * and robots.
 * @see Action
 * @see World
 * 
 * @author Karina Suemi Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Nascimento Silva
 */
public class Map implements Game
{
    // Map Matrixes
    public Terrain[][] map     = new Terrain[MAP_SIZE][MAP_SIZE];
    public char[][]    miniMap = new char[MAP_SIZE][MAP_SIZE];

    // Weather
    final private Weather w;
    
    /**
     * Default constructor.
     * @param w Weather of the map
     */
    public Map(Weather w)
    {
        this.w = w;
    }
    
    /**
     * Create map to 'n' players.
     * @param  nPlayers Number of players
     * @return Matrix of robots, with 'n' 
     *         players of height, and 
     *         ROBOTS_NUM_INITIAL of lenght
     */
    public Robot[][] genesis(int nPlayers)
        throws InvalidOperationException
    {
        RandomMap arena = new RandomMap (w, nPlayers, MAP_SIZE);
        miniMap = arena.getMatrix   (); 
        map     = arena.generateMap ();              
        
        //// TODO: receive PROG, generate robots
        //Parser user = new Parser();
        //Vector<Command> PROG = user.upload();
        //
        //Robot bender = new Robot("Bender", 8, 9, PROG);
        //Robot c3po   = new Robot("C3PO"  , 9, 8, PROG);
        //
        Robot[][] initial = new Robot[nPlayers][ROBOTS_NUM_INITIAL];
        
        return initial;
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
    public Robot insertArmy(String name, int player, int ID, 
                            int i, int j, String pathToProg)
        throws SegmentationFaultException
    {
        if(i < 0 || j < 0 || i >= MAP_SIZE || j >= MAP_SIZE) 
            throw new SegmentationFaultException();
        
        // Creates a new string
        String prog = processInput(pathToProg);
        
        try { 
            Class<?> Parser = Class.forName("parser." + prog); 
            Method   upload = Parser.getMethod("upload");
            
            // Suppress warning of converting from Object class
            // to Vector<Command> (unhappily, it cannot be solved
            // due to the characteristics of generic types).
            @SuppressWarnings("unchecked")
            Vector<Command> PROG = (Vector<Command>) upload.invoke(null);
            
            Robot r = new Robot(name, player, ID, i, j, PROG);
            this.map[i][j].setScenario(r);
            return r;
        }
        catch(ClassNotFoundException e) 
        {
            System.err.println(
                "Class not found! Program \"" + 
                prog + "\" does not exist!"
            );
        }
        catch(NoSuchMethodException     e) {}
        catch(IllegalAccessException    e) {}
        catch(InvocationTargetException e) {}
        return null;
    }
    
    /**
     * Remove a given robot from the map.
     * Take out the robot from the map.
     * 
     * @param i Vertical position
     * @param j Horizontal position
     */
    public Scenario removeScenario(int i, int j)
    {
        return map[i][j].removeScenario();
    }
    
    /** 
     * Changes path to make the program name.<br>
     * Given a strinfg makes tha path a valid name
     * for the program to be loaded.
     */
    private static String processInput(String input)
    {
        // Process input
        input = input.replaceFirst("^.*/"  , ""); // Takes out dir path
        input = input.replaceFirst("\\..*$", ""); // Takes out excension
        
        // Uppercase first
        char   first = Character.toUpperCase(input.charAt(0));
        String other = input.substring(1).toLowerCase();
        String prog  = first + other;

        // Debug message
        if(!prog.equals(input))
        {
            Debugger.say(input + " is not a valid name!");
            Debugger.say("Searching for " + prog);
            Debugger.say();
        }
        return prog;
    }
}
