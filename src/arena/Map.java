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
    public char[][]    miniMap;
    public Terrain[][] map;
    
    // Weather
    final private Weather w;
    
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
     * @param  nPlayers Number of players
     * @param  rand     Random object to 
     *                  generate everything
     *                  with the same seed
     * @return List with nPlayer bases to 
     *         be given to the players
     */
    Base[] genesis(int nPlayers, Random rand)
        throws InvalidOperationException
    {
        RandomMap arena = new RandomMap     (w, nPlayers, MAP_SIZE, rand);
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
     * @param  player     Robot owner
     * @param  name       Name of the new robot
     * @param  pathToProg Robot assembly program
     * @return Inserted robot
     */
    Robot insertArmy(
        String name, Player player, int ID, String pathToProg)
        throws SegmentationFaultException
    {
        // Find a place to put the new robot
        int[] coords = populate(player);
        int i = coords[0], j = coords[1];
        
        if(i < 0 || j < 0 || i >= MAP_SIZE || j >= MAP_SIZE) 
            throw new SegmentationFaultException();
        
        // Initilixe the new robot and place it in the map
        Robot r = new Robot(name, player, ID, i, j, this.map[i][j], pathToProg);
        this.map[i][j].setScenario(r);
        return r;
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
}
