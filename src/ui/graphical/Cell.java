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

// Libraries
import arena.Terrain;
import players.Player;

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
    // Fog War
    private final TexturePaint fog = new TexturePaint(
        Images.FOG_WAR.img(), new Rectangle(0,0,32,32)
    );
    
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
        
        g2d.setPaint (fogWar ? fog : this.bg);
        g2d.fill     (this.hex);
    }   
}
