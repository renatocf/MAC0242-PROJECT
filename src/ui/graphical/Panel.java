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

// Default Libraries
import java.io.IOException;
import java.util.ArrayList;
import java.util.WeakHashMap;

// Graphical Libraries (AWT)
import java.awt.*;
import java.awt.event.*;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

// Graphical Libraries (Swing)
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

// Libraries
import arena.Map;
import parameters.*;
import arena.Robot;
import players.Player;
import scenario.Scenario;
import stackable.Direction;
import stackable.item.Item;

// Import links
import static parameters.Game.*;

/**
 * <b>Graphical - Panel</b><br>
 * Creates the main panel to exhibit 
 * the map.
 * @see Graphical
 * @see arena.Map
 * @see arena.World
 * 
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
class Panel extends JPanel
{
    // Map made with cells
    private Cell[][] cell = new Cell[MAP_SIZE][MAP_SIZE];
    
    // Local variables
    private Map    map;
    private Player player;
    
    // Paint utils
    private Insets insets;
    private int[] update  = new int[2];
    private Direction dir;
    private WeakHashMap<Robot,JRobot> robots 
        = new WeakHashMap<Robot,JRobot>();
    
    // Paint auxiliars
    private Phase phase;
    private int   nTS;
    private int   nPlayers;
    private int   nRobots;
    
    // Show or not scenarios/items
    boolean hide = false;
    
    /**
     * Create a Panel with dimensions width x height,
     * containing MAP_SIZE² hexagons (built from a map).
     * @see Cell
     * @see GraphjButton listener paralelo a outras classeical
     *
     * @param map    Map over which the panel will
     *               create the GUI hexagons
     * @param player Player who is visualizing the
     *               panel (for his specific view)
     * @param R      Hexagon radius
     * @param y0     Desired vertical shift
     * @param width  Desired width of the screen
     * @param height Desired height of the screen
     */
    Panel(Map map, Player player, int R, int x0, int y0, int width, int height)
    {
        // Store game attributes
        this.map      = map;
        this.player   = player;
        
        // Initializes auxiliar variable
        try { this.dir    = new Direction(""); }
        catch(Exception e)
        {
            System.err.println("[PANEL] This sould never happen...");
            e.printStackTrace();
        }
        
        // Preferences
        this.insets = this.getInsets ();
        this.setLayout        (null);
        this.setBackground    (Color.black);
        this.setPreferredSize (new Dimension(width, height));
        
        // Initial phase: active
        this.phase = Phase.ACTIVE;
        
        // Put images in the screen
        int Dx = (int) ( 2*R * Math.sin(Math.PI/3) ); 
        int Dy = 3 * R/2 +1;
       
        // Build a grid of cells
        int Δ  = 0;
        for (int i = 0; i < MAP_SIZE; i++)
            for (int j = 0; j < MAP_SIZE; j++) 
            {
                cell[i][j] = new Cell(
                    this.player,            // Player
                    x0 + Δ + R + i*Dx,      // Horizontal position
                    y0 + R + j*Dy,          // Vertical position
                    R,                      // Hexagon radius
                    map.map[j][i]           // Terrain in map[j,i]
                ); 
                Δ = (Δ == 0) ? Dx/2 : 0;
            }
        
        // Visibility around player's bases
        int X = this.player.getBase().getPosX(this.player);
        int Y = this.player.getBase().getPosY(this.player);
        this.setVisible(X,Y,3);
    }
    
    /**
     * Paint hexagons on the screen.<br>
     * At each step, repaint the cells
     * as they need changes.
     * @param g Graphic object with all context 
     *          needed to render the image
     */
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        // First, update player's visibility
        this.blinkVisibility();
        
        // Then, draw all the background without Fog War
        this.paintMap(g);
        
        // Remove all GUI elements, mainly the robots power/HP bars
        for(java.util.Map.Entry<Robot, JRobot> entry : robots.entrySet())
            entry.getValue().remove(); 
        
        // Finally, print accordingly the state
        switch(this.phase)
        {
            case LOOSER: if(!hide) looser(g);    break;
            case WINNER: if(!hide) winner(g);    break;
            case ACTIVE: if(!hide) paintLife(g); break;
        }
    }
    
    /**
     * Set if the scenarios and items
     * sound or not be showed.
     * @param show True if show invisible mask and 
     *             scenarios, false otherwise
     */
    void hide(boolean show)
    {
        hide = show;
    }
    
    /**
     * Load info about the game phase.<br>
     * @param phase    Game phase
     * @param nTS      Number of time steps since 
     *                 the beggining of the game
     * @param nPlayers Number of players
     * @param nRobots  Number of robots created by 
     *                 all players along the game
     */
    void setGamePhase(
        Phase phase, int nTS, int nPlayers, int nRobots)
    {
        this.nTS      = nTS;
        this.phase    = phase;
        this.nRobots  = nRobots;
        this.nPlayers = nPlayers;
    }
    
    /**
     * Auxiliar function for painting the 
     * hexagonal grid arena of the game.
     * @param g Game graphical context
     */
    private void paintMap(Graphics g)
    {
        for (int i = 0; i < MAP_SIZE; i++) 
            for (int j = 0; j < MAP_SIZE; j++)
                if(!cell[i][j].terrain.getFogWar(this.player))
                    cell[i][j].draw(g); 
    }
    
    /**
     * Auxiliar function for painting all
     * the elements over the arena.
     * @param g Game graphical context
     */
    private void paintLife(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < MAP_SIZE; i++) 
            for (int j = 0; j < MAP_SIZE; j++)
            {
                // Get fog war (and print properly)
                boolean fog = cell[j][i].terrain.getFogWar(this.player);
                
                // Print items and scenarios if there is no fog
                if(fog) continue;
                item(g2d, j, i); // Items (crystals, stones...)
                scen(g2d, j, i); // Scenarios (robots, trees, rocks...)
            }
            
        Images inv = Images.INVISIBLE;
        boolean vis;
        for (int i = 0; i < MAP_SIZE; i++) 
            for (int j = 0; j < MAP_SIZE; j++)
            {
                vis = cell[i][j].terrain.getVisibility(this.player);
                if(!vis) g2d.drawImage(
                    inv.img(), cell[i][j].x-inv.dx(), cell[i][j].y-inv.dy(), null
                );
            }
                
    }
    
    /**
     * Make all terrains invisible and, then, set the visibility
     * for this new step (around player's stuff).
     */
    private void blinkVisibility()
    {
        // Hide all the map
        for(int i = 0; i < MAP_SIZE; i++)
            for(int j = 0; j < MAP_SIZE; j++)
                cell[i][j].terrain.setInvisible(this.player);
        
        // But lejButton listener paralelo a outras classet the base's around be visible
        int X = this.player.getBase().getPosX(this.player);
        int Y = this.player.getBase().getPosY(this.player);
        this.setVisible(Y,X,7);
        
        // And the player's robots
        for(Robot r: this.player.armies)
            this.setVisible(r.getPosX(), r.getPosY(), r.getSight() * 2);
    }
    
    /**
     * Auxiliar method for making an area visible
     * (which takes out Fog War and shows all scenarios).
     * @param X Horizontal position
     * @param Y Vertical position
     * @param S Sight (radius) of the area to be set visible
     */
    private void setVisible(int X, int Y, int S)
    {
        if(X < 0 || X >= MAP_SIZE) return;
        if(Y < 0 || Y >= MAP_SIZE) return;
        
        cell[X][Y].terrain.setVisible(this.player);
        if(S == 0) return;
            
        try {
            for(int k = 1; k <= 6; k++)
            {
                dir.set(k); update = dir.get(Y);
                if(X + update[1] < 0 || X + update[1] >= MAP_SIZE) continue;
                if(Y + update[0] < 0 || Y + update[0] >= MAP_SIZE) continue;
                setVisible(X + update[1], Y + update[0], S-1);
            }
            
        } catch(Exception e) {
            System.err.println("[PANEL] (setVisible) This sould never happen...");
            e.printStackTrace();
        }
    }
    
    /**
     * Auxiliar function for painting a 
     * scenario over a terrain in the game.
     * @param g Game graphical context
     * @param i Vertical position of the scenario
     * @param j Horizontal position of the scenario
     */
    private void scen(Graphics2D g2d, int i, int j)
    {
        Cell hex = cell[i][j];
        int x = hex.x, y = hex.y;
        boolean vis = hex.terrain.getVisibility(this.player);
        
        Scenario s = hex.terrain.getScenario();
        if(s != null)
        {
            Images scen = Images.valueOf(s.name(), s.getTeam());
            
            // Special treatment for robots
            if(s instanceof Robot)
            {
                // Does not print if not visible
                if(!vis) 
                {
                //    Images inv = Images.INVISIBLE;
                  //  if(!vis) g2d.drawImage(
                    //    inv.img(), x-inv.dx(), y-inv.dy(), null
                    //);
                    return;
                }
                
                // Get the robot and its animation phase
                Robot r = (Robot) s;
                int[] phase = r.getPhase();
                
                // Add new JRobots for new Robots
                if(!this.robots.containsKey(r))
                    this.robots.put(r, new JRobot(r));
                
                // Get the right sprite and corrects the robot
                g2d.drawImage(
                    scen.img().getSubimage(phase[0], phase[1], 32, 32), 
                    x-scen.dx(), y-scen.dy(), null
                );
                
                // Update robot's position
                r.setPhase(32, phase[1]);
                
                // Add JRobot in the Panel
                JRobot jr = robots.get(r);
                jr.update(x-scen.dx(), y-scen.dy());
                jr.add();
            }
            else
            {
                // Always draw other's scenarios
                g2d.drawImage(scen.img(), x-scen.dx(), y-scen.dy(), null);
            }
            
            // When something is being hit, show it!
            if(s.sufferedDamage())
            {
                scen = Images.valueOf("HIT");
                g2d.drawImage(
                    scen.img(), x-scen.dx(), y-scen.dy(), null
                );  
            }
            
        } // s != null
            
        // If there is no visibility, masks player's view
        //Images inv = Images.INVISIBLE;
        //if(!vis) g2d.drawImage(
        //    inv.img(), x-inv.dx(), y-inv.dy(), null
        //);
    }
    
        
    /**
     * Auxiliar function for painting an 
     * item over a terrain in the game.
     * @param g Game graphical context
     * @param i Vertical position of the item
     * @param j Horizontal position of the item
     */
    private void item(Graphics2D g2d, int i, int j)
    {
        Cell hex = cell[i][j];
        int x = hex.x, y = hex.y;
        
        // Print items
        if(hex.terrain.getItem() != null)
        {
            Images item = Images.valueOf(
                hex.terrain.getItem().name()
            );
            g2d.drawImage(
                item.img(), x-item.dx(), y-item.dy(), null
            );
        }
    }
    
    /**
     * Paints message to the looser user.
     * @param g Game graphical context
     */
    private void looser(Graphics g)
    {
        // Useful variables
        int H = this.getHeight(), W = this.getWidth();
        int hH = H/2, hW = W/2; // Half Height/Width
        
        // Paint background
        this.paintMap(g);
        
        // Painting strip
        g.setColor(Color.WHITE);
        g.fillRect(65, hH-60, W-130, 190);
        
        // Painting label
        g.setColor(Color.RED);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString(this.player + ", YOU LOOSE!", hW-300, hH + 35);
        
        // Paint all the label
        paintEdge(g);
    }
    
    /**
     * Paints message to the winner user.
     * @param g Game graphical context
     */
    private void winner(Graphics g)
    {
        // Useful variables
        int H = this.getHeight(), W = this.getWidth();
        int hH = H/2, hW = W/2; // Half Height/Width
        
        // Paint background
        this.paintMap(g);
        
        // Painting the strip
        g.setColor(Color.WHITE);
        g.fillRect(65, hH-60, W-130, 190);
        
        // Painting the label
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString(this.player + ", YOU WIN!", hW-280, hH-15);
        
        // Painting game's info
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial Black", Font.BOLD, 30));
        g.drawString("Number of Steps: "   + this.nTS,      hW-280, hH+35);
        g.drawString("Number of Players: " + this.nPlayers, hW-280, hH+66);
        g.drawString("Number of Robots: "  + this.nRobots,  hW-280, hH+97);
        
        // Paint all the label
        paintEdge(g);
    }
    
    /**
     * Auxiliar method for printing a border around 
     * both winner and looser player messages.
     * @param g Game graphical context
     */
    private void paintEdge(Graphics g)
    {
        // Useful variables
        int H = this.getHeight(), W = this.getWidth();
        int hH = H/2, hW = W/2; // Half Height/Width
        
        // Painting the border
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw red robots
        Image red = Images.RED_ROBOT.img().getSubimage(0, 32, 32, 32);
        
        g2d.drawImage(red,   28,     hH - 65,  null);
        g2d.drawImage(red,   28,     hH + 5 ,  null);
        g2d.drawImage(red,   28,     hH + 75,  null);
        
        g2d.drawImage(red,   W - 60, hH - 65,  null);
        g2d.drawImage(red,   W - 60, hH + 5 ,  null);
        g2d.drawImage(red,   W - 60, hH + 75,  null);
        g2d.drawImage(red,   W - 60, hH + 145, null);
            
        // Draw black robots
        Image black = Images.BLACK_ROBOT.img().getSubimage(0, 64, 32, 32);
            
        g2d.drawImage(black, 28,     hH - 30,  null);
        g2d.drawImage(black, 28,     hH + 40,  null);
        g2d.drawImage(black, 28,     hH + 110, null);
        
        g2d.drawImage(black, W - 60, hH - 100, null);
        g2d.drawImage(black, W - 60, hH - 30,  null);
        g2d.drawImage(black, W - 60, hH + 40,  null);
        g2d.drawImage(black, W - 60, hH + 110, null);
        
        // Draw both robots
        for(int i = 28; i < (W-93); i += 35)
        {
            g2d.drawImage(black, i, hH - 100, null);
            g2d.drawImage(red  , i, hH + 145, null);  
        }
    }
    
    /**jButton listener paralelo a outras classe
     * <b>JRobot - Robot with more than images</b><br>
     * Print a robot exhibiting a status bar and other
     * useful info for the player.
     */
    private class JRobot 
    {
        private Robot robot;
        private Dimension size = new Dimension(30,5); // Bar size
        
        // Additional info for settings
        private int maxHP;
        private int maxPower;
        
        // Progress Bars
        private JProgressBar hp;
        private JProgressBar power;
        
        /**
         * Default Constructor.
         * @param robot Robot to be stored
         */
        JRobot(Robot robot)
        {
            // Stores parameters in attributes
            this.robot = robot;

            // Creates and sets HP bar
            this.maxHP = robot.getMaxHP       ();
            this.hp = new JProgressBar        ();
            this.hp.setMaximum                (this.maxHP    );
            this.hp.setPreferredSize          (this.size     );
            this.hp.setForeground             (Color.BLUE    );
            
            // Creates and sets Power bar
            this.maxPower = robot.getMaxPower ();
            this.power = new JProgressBar     ();
            this.power.setMaximum             (this.maxPower );
            this.power.setPreferredSize       (this.size     );
            this.hp.setForeground             (Color.GREEN   );
        }
        
        /**
         * Update infos exhibited by a robot.
         * @param x0 Initial horizontal position to paint 
         *           info bars
         * @param y0 Initial vertical position to paint 
         *           info bars
         */
        void update(int x0, int y0)
        {
            int hp    = this.robot.getHP    ();
            int power = this.robot.getPower ();
            
            // Update Color Scheme
            this.updateColorScheme(this.hp, this.maxHP);
            
            // Configure and paint hp bar
            this.hp.setBounds    (x0, y0-15, size.width, size.height);
            this.hp.setValue     (hp);
            
            // Configure and paint power bar
            this.power.setBounds (x0, y0-10, size.width, size.height);
            this.power.setValue  (power);
        }
        
        /**
         * Updates the color of a given bar.<br>
         * If greater than 2/3, paint in GREEN. If lower
         * than 1/3, paint RED. Otherwise, use YELLOW.
         * @param pb  JProgressBar
         * @param max Maximum value to be used as parameter
         *            to the tresholds of each color region
         */
        private void updateColorScheme(JProgressBar pb, int max)
        {
            double per = max * pb.getPercentComplete();
            Color c = Color.YELLOW;
            if(per > 2.0/3 * max) c = Color.GREEN;
            if(per < 1.0/3 * max) c = Color.RED;
            pb.setForeground(c);
        }
        
        /**
         * Add bars in the Panel.<br>
         */
        protected void add()
        {
            Panel.super.add(this.hp);
            Panel.super.add(this.power);
        }
        
        /**
         * Remove bars from the Panel.<br>
         */
        protected void remove()
        {
            Panel.super.remove(this.hp);
            Panel.super.remove(this.power);
        }
    }
}