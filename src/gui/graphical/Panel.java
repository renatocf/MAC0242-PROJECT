package gui.graphical;

// Default Libraries
import java.io.IOException;
import java.util.WeakHashMap;

// Graphical Libraries (AWT)
import java.awt.*;
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
    private Map map;
    private Insets insets;
    private WeakHashMap<Robot,JRobot> robots 
        = new WeakHashMap<Robot,JRobot>();
    
    // Paint auxiliary
    private Phase  phase;
    private Player player;
    private int    nTS;
    private int    nPlayers;
    private int    nRobots;
    
    /**
     * Create a Panel with dimensions width x height,
     * containing MAP_SIZE² hexagons (built from a map).
     * @see Cell
     * @see Graphical
     *
     * @param R      radius
     * @param width  Desired width of the screen
     * @param height Desired height of the screen
     * @param y0     Desired vertical shift
     * @param map    Map over which the panel will
     *               create the GUI hexagons
     */
    Panel(int R, int width, int height, int y0, Map map) 
    {
        this.map = map;
           
        // Preferences
        this.insets = this.getInsets();
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(width, height));
        
        this.phase = Phase.ACTIVE;
        
        // Put images in the screen
        int Dx = (int) ( 2*R * Math.sin(Math.PI/3) ); 
        int Dy = 3 * R/2 +1;
       
        int Δ  = 0;
        for (int i = 0; i < MAP_SIZE; i++)
            for (int j = 0; j < MAP_SIZE; j++) 
            {
                cell[i][j] = new Cell(
                    Δ + R + i*Dx, R + j*Dy + y0, R, map.map[j][i]
                ); 
                Δ = (Δ == 0) ? Dx/2 : 0;
            }
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
        
        // First, draw all the background
        this.paintMap(g);
        
        // Remove all GUI elements, mainly the robots power/HP bars
        for(java.util.Map.Entry<Robot, JRobot> entry : robots.entrySet())
            entry.getValue().remove(); 
        
        switch(this.phase)
        {
            case LOOSER: looser(g);    break;
            case WINNER: winner(g);    break;
            case ACTIVE: paintLife(g); break;
        }
    }
    
    /**
     * Load info about the game phase.<br>
     * @param phase    Game phase
     * @param player   Player (looser or winner)
     * @param nTS      Number of time steps since 
     *                 the beggining of the game
     * @param nPlayers Number of players
     * @param nRobots  Number of robots created by 
     *                 all players along the game
     */
    void setGamePhase(
        Phase phase, Player player, int nTS, int nPlayers, int nRobots)
    {
        this.nTS      = nTS;
        this.phase    = phase;
        this.player   = player;
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
                item(g2d, i, j); // Items (crystals, stones, ...)
                scen(g2d, i, j); // Scenarios (robots, trees, rocks, ...)
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
        Cell hex = cell[j][i];
        int x = hex.x, y = hex.y;
        
        Scenario s = hex.terrain.getScenario();
        if(s != null)
        {
            Images scen = Images.valueOf(s.name(), s.getTeam());
            
            if(s instanceof Robot)
            {
                Robot r = (Robot) s;
                int[] phase = r.getPhase();
                if(!this.robots.containsKey(r)) 
                    this.robots.put(r, new JRobot(r));
                
                //Get the right sprite and corrects the robot
                g2d.drawImage(
                scen.img().getSubimage(phase[0], phase[1], 32, 32), 
                x-scen.dx(), y-scen.dy(), null
                );
                
                r.setPhase(32, phase[1]);
                                
                JRobot jr = robots.get(r);
                jr.update(x-scen.dx(), y-scen.dy());
                jr.add();
            }
            else
            {
                g2d.drawImage(
                scen.img(), x-scen.dx(), y-scen.dy(), null);
            }
        }
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
        Cell hex = cell[j][i];
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
        
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial Black", Font.BOLD, 30));
        g.drawString("Number of Steps: "   + this.nTS,      hW-280, hH+35);
        g.drawString("Number of Players: " + this.nPlayers, hW-280, hH+66);
        g.drawString("Number of Robots: "  + this.nRobots,  hW-280, hH+97);
        
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
        
        // Draw red robot
        Image red = Images.RED_ROBOT.img();
        
        g2d.drawImage(red,   28,     hH - 65,  null);
        g2d.drawImage(red,   28,     hH + 5 ,  null);
        g2d.drawImage(red,   28,     hH + 75,  null);
        
        g2d.drawImage(red,   W - 60, hH - 65,  null);
        g2d.drawImage(red,   W - 60, hH + 5 ,  null);
        g2d.drawImage(red,   W - 60, hH + 75,  null);
        g2d.drawImage(red,   W - 60, hH + 145, null);
            
        // Draw black robot
        Image black = Images.BLACK_ROBOT.img();
            
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
    
    /**
     * <b>JRobot - Robot with more than images</b><br>
     * Print a robot exhibiting a status bar and other
     * useful info for the player.
     */
    private class JRobot 
    {
        private Robot robot;
        private Dimension size = new Dimension(30,5); // Bar size
        
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
