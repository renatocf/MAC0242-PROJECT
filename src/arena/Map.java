package arena;

// Default libraries
import java.io.*;
import java.util.Vector;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.*;
import java.util.Random;

// Libraries
import robot.*;
import random.*;
import players.*;
import stackable.*;
import exception.*;
import parameters.*;
import stackable.item.*;
import scenario.Scenario;

// Import links
import static parameters.Game.*;

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
public class Map
{
    // Map Matrixes
    public char[][]        miniMap;
    public Terrain[][]     map;

    // Weather
    final private Weather w;
    
    private Player[] bases;
    
    /**
     * Default constructor.
     * @param w Weather of the map
     */
    Map(Weather w)
    {
        this.w = w;
    }
    
    /**
     * Create map to 'n' players.
     * @param  players List of players
     * @return List with nPlayer bases to 
     *         be given to the players
     */
    Base[] genesis(Player[] players, Random rand)
        throws InvalidOperationException
    {
        this.bases = players;
        RandomMap arena = new RandomMap     (w, players.length, MAP_SIZE, rand);
        miniMap         = arena.getMatrix   (); 
        map             = arena.generateMap ();              
        return arena.getBases();
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
    Robot insertArmy(String name, Player player, int ID, 
                String pathToProg)
        throws SegmentationFaultException
    {
        int[] coords = populate(player);
        int i = coords[0], j = coords[1];
        
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
            
            Robot r = new Robot(name, player, ID, i, j, map[i][j],  PROG);
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
     * This method returns the coordinates of an
     * empty space around a given player's base.
     *
     * @param   p Player
     * @return  An integer array with the x 
     *          and the y coordinates close 
     *          to the base
     */  
    public int[] populate(Player p)
    {
        int x = p.getBase().getPosX(p), 
            y = p.getBase().getPosX(p);
        
        Debugger.say(x,", ",y);   
        for(int r = 1; r < 3; r++)
            for(int i = x-r; i <= x+r; i++)
                for(int j =y-r; j <= y+r; j++)
                    if(i >= 0       && j >= 0    
                    && i < MAP_SIZE && j < MAP_SIZE
                    && map[i][j].scenario == null)
                    {   
                        Debugger.say(i,", ",j);
                        return new int[]{i, j};
                    }
        return null;
    }
    
    /**
     * Remove a given robot from the map.
     * Take out the robot from the map.
     * 
     * @param i Vertical position
     * @param j Horizontal position
     */
    Scenario removeScenario(int i, int j)
    {
        return map[i][j].removeScenario();
    }
    
    /** 
     * Changes path to make the program name.<br>
     * Given a strinfg makes tha path a valid name
     * for the program to be loaded.
     */
    static String processInput(String input)
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
    
    public Player getBases(int address)
    {
        return this.bases[address];
    }
    
    public int getNumberOfBases()
    {
        return this.bases.length;
    }
}
