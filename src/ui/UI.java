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
package ui;

// Libraries
import players.Player;
import parameters.Game;

/**
 * <b>UI</b><br>
 * Methods that a UI must provide to 
 * work with the game characteristics.
 * @see parameters.Game
 */
public interface UI
{
    /**
     * Shows Map.<br>
     * Creates a Map with more details of each element
     * of the scenarios and items in the map. Each 
     * hexagon represents one title. 
     */
    public void paint();
    
    /**
     * Paint 1 frame of the game.<br>
     * Depending on the speedy attribute,
     * from interface Game, exhibits a frame
     * in the program's standart output.
     */
    public void printMap();
    
    /** 
     * Shows Mini Map.<br>
     * Creates a Mini Map with dimensions MAP_SIZE
     * x MAP_SIZE, with a one-character representation
     * of each scenario/item in the map.
     */
    public void printMiniMap();
    
    /** 
     * Send a message to this GUI.<br>
     * Prints the message in the appropriate 
     * area, with this string.
     * @param strings Variable size list of objects,
     *                which will have their 'toString()'
     *                method used for being printed.
     */
    public void printText(Object ... strings);
    
    /** 
     * Send a message to this GUI (with \n).<br>
     * Prints the message in the appropriate 
     * area, with this string.
     * @param strings Variable size list of objects,
     *                which will have their 'toString()'
     *                method used for being printed.
     */
    public void sayText(Object ... strings);
    
    /** 
     * Finishes the game.
     * @param nTS      Number of time steps since 
     *                 the beggining of the game
     * @param nPlayers Number of players
     * @param nRobots  Number of robots created by 
     *                 all players along the game
     */
    public void winner(int nTS, int nPlayers, int nRobots);
    
    /** 
     * Remove the looser and exhibit 
     * the looser message for him.
     */
    public void looser();
}
