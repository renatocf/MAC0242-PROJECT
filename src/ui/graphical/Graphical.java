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
package ui.graphical;

// Libraries
import ui.*;
import arena.Map;
import parameters.*;
import players.Player;

// Import links
import static parameters.Game.*;

/**
 * <b>UI - Graphical Mode</b><br>
 * Makes an implementation of the interface
 * UI for exhibiting the game, using Java's
 * default graphic libraries (AWT and Swing).
 * 
 * @author Karina Suemi
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
public class Graphical implements UI
{
    // GUI owner
    Player player;
    
    // Main GUI components
    MapFrame     mapFrame;
    EditorFrame  editorFrame;
    MiniMapFrame miniMapFrame;
    
    /**
     * Default constructor.<br>
     * @param map    Map of the arena
     * @param player Owner of the User Interface
     */
    public Graphical(Map map, Player player)
    {
        this.player = player;   

        /* TODO: Finish editor frame */
        this.editorFrame    = new EditorFrame  (this);
        this.miniMapFrame   = new MiniMapFrame (this, map, player);
        this.mapFrame       = new MapFrame     (this, map, player);
    }
    
    /* Implementing interface GUI */
    public void printText(Object ... strings)
    { 
        for(Object s: strings) 
            System.out.println((s != null) ? s.toString() : "null");
    }
    
    /* Implementing interface GUI */
    public void sayText(Object ... strings)
    {
        System.out.print   (strings);
        System.out.println ();
    }
    
    /* Implementing interface GUI */
    public void paint()
    {
        try { Thread.sleep(SPEED); } 
        catch (Exception e) { }
        
        this.printMap();
    }
    
    /* Implementing interface GUI */
    public void printMap()
    {
        this.mapFrame.paint();
        this.miniMapFrame.paint();
    }
    
    /* Implementing interface GUI */
    public void printMiniMap() 
    { 
        /* this.miniMapFrame(); */
    }
    
    /* Implementing interface GUI */
    public void winner(int nTS, int nPlayers, int nRobots)
    {
        this.mapFrame.winner(nTS, nPlayers, nRobots);
    }
    
    /* Implementing interface GUI */
    public void looser()
    {
        this.mapFrame.looser();
    }
}
