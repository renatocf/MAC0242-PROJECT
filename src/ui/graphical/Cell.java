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

// Graphical Libraries (AWT)
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

// Graphical Libraries (Swing)
import javax.swing.JLabel;
import javax.swing.ImageIcon;

// Libraries
import arena.Robot;
import arena.Terrain;
import players.Player;
import scenario.Scenario;
import stackable.item.Item;

/** 
 * <b>Graphical - Cell</b><br>
 * Encapsulates the creation of the hexagonal
 * terrains of the map.
 * @see Panel
 *
 * @author Karina Suemi
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
class Cell
{ 
    // Visual representation of terrain attributes
    JLabel hit  = null;
    JLabel invs = null;
    JLabel scen = null;
    JLabel item = null;
    
    // Characteristics
    private final Polygon hex = new Polygon();
    private final Player  player;
    
    final Terrain terrain; 
    final int x, y;
    
    // Painting
    private TexturePaint bg;
    
    /**
     * Default constructor.<br>
     * @param player  Player who is visualizing the
     *                cell (for his specific view)
     * @param x       Horizontal position where the cell 
     *                center will be set
     * @param y       Vertical position whee the cell 
     *                center will be set
     * @param r       Hexagon radius
     * @param terrain Terrain to build the cell
     */
    Cell(Player player, int x, int y, int r, Terrain terrain) 
    {
        this.player = player;
        this.x = x; this.y = y;
        this.terrain = terrain;
        
        // Create the hexagon with points
        for(int i = 0; i < 6; i++)
            hex.addPoint(
                x + (int) (r * Math.sin(i * Math.PI/3)),
                y + (int) (r * Math.cos(i * Math.PI/3))
            );
        
        // Get appearence
        String app = terrain.getAppearence().name();
        BufferedImage appearence = Images.valueOf(app).img();
        
        // Get rectangle with the size the appearence
        /* TODO: Take out hardcoded numbers */
        Rectangle rec = new Rectangle(0,0,32,32);
        
        // Create texture
        this.bg = new TexturePaint(appearence, rec);
        
        // Initialize fog/item/scen
        this.insertHit();
        this.insertInv();
    }
    
    /**
     * Add the texture to the cell, using an image
     * inside a rectangle with dimensions 32x32.
     * @param g Graphic object with all context 
     *          needed to render the image
     */
    void draw(Graphics g) 
    { 
        Graphics2D g2d = (Graphics2D) g;
        
        // Paint the background
        boolean fogWar = terrain.getFogWar(this.player);
        if(fogWar) return;
        
        g2d.setPaint (this.bg);
        g2d.fill     (this.hex);
    }   
    
    /**
     * Insert a scenario if there is one
     * in the terrain of the cell.
     */
    void insertScenario()
    {
        Scenario s = this.terrain.getScenario();
        if(s == null || s instanceof Robot) return;
        
        Images scenario = Images.valueOf(s.name(), s.getTeam());
        
        int x0 = x-scenario.dx();
        int y0 = y-scenario.dy();
        int w  = scenario.getWidth();
        int h  = scenario.getHeight();
        
        this.scen = new JLabel(scenario.ico());
        this.scen.setBounds (x0, y0, w, h);
    }
        
    /**
     * Insert an item if there is one
     * in the terrain of the cell.
     */
    void insertItem()
    {
        Item itm = this.terrain.getItem();
        if(itm == null) return;
        
        Images i = Images.valueOf(itm.name());
        
        int x0 = x-i.dx();
        int y0 = y-i.dy();
        int w  = i.getWidth();
        int h  = i.getHeight();
        
        this.item = new JLabel(i.ico());
        this.item.setBounds (x0, y0, w, h);
    }
    
    /**
     * Insert the Fog War.
     */
    private void insertHit()
    {
        int x0 = x-Images.HIT.dx();
        int y0 = y-Images.HIT.dy();
        int w  = Images.HIT.getWidth();
        int h  = Images.HIT.getHeight();
        
        this.hit = new JLabel(Images.HIT.ico());
        this.hit.setBounds (x0, y0, w, h);
    }
    
    /**
     * Insert the Fog War.
     */
    private void insertInv()
    {
        int x0 = x-Images.INVISIBLE.dx();
        int y0 = y-Images.INVISIBLE.dy();
        int w  = Images.INVISIBLE.getWidth();
        int h  = Images.INVISIBLE.getHeight();
        
        this.invs = new JLabel(Images.INVISIBLE.ico());
        this.invs.setBounds (x0, y0, w, h);
    }
}
