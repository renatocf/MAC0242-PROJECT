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
    private WeakHashMap<Robot,JRobot> robots = new WeakHashMap<Robot,JRobot>();
    
    
    // Paint auxiliary
    private int gamePhase;
    Player p;
    int nTS;
    int nPlayers;
    int nRobots;
    // 0 -> active game
    // 1 -> looser
    // 2 -> winner


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
        
        this.gamePhase = 0;
        
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
    
    private void looser(Graphics g)
    {
    
	    for (int i = 0; i < MAP_SIZE; i++) 
        	for (int j = 0; j < MAP_SIZE; j++)
            	cell[i][j].draw(g); 
                          
        // Painting strip
        g.setColor(Color.WHITE);
        g.fillRect(65, getHeight()/2 - 20, getWidth() - 130, 100);
        
        // Painting label
        g.setColor(Color.RED);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString(this.p + ", YOU LOOSE!", getWidth()/2 - 300, getHeight()/2 + 35);

		paintEdge(g);

        repaint();
    }
    
    private void winner(Graphics g)
    {
    	// Painting the map
    	for (int i = 0; i < MAP_SIZE; i++) 
            for (int j = 0; j < MAP_SIZE; j++)
                cell[i][j].draw(g); 
    	
    	// Painting the strip
        g.setColor(Color.WHITE);
        g.fillRect(65, getHeight()/2 - 60, getWidth() - 130, 190);
        
        //Painting the label
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString(this.p + ", YOU WIN!", getWidth()/2 - 280, getHeight()/2-15);
		
		
        //g.setColor(Color.GREEN);
        g.setFont(new Font("Arial Black", Font.BOLD, 30));
        g.drawString("Number of Times:   " + this.nTS     , getWidth()/2 - 280, getHeight()/2 + 35);
        g.drawString("Number of Players: " + this.nPlayers, getWidth()/2 - 280, getHeight()/2 + 66);
        g.drawString("Number of Robots:: " + this.nRobots , getWidth()/2 - 280, getHeight()/2 + 97);
       	
       	paintEdge(g);
       	
        repaint();
    }
    
    private void paintEdge(Graphics g)
    {
    	        // Painting the border
        Graphics2D g2d = (Graphics2D) g;
        Image img = Toolkit.getDefaultToolkit().getImage("data/img/scenario/tech/red/Robot.png");
        	g2d.drawImage(img, 28, getHeight()/2 - 65, null);
        	g2d.drawImage(img, 28, getHeight()/2 + 5 , null);
        	g2d.drawImage(img, 28, getHeight()/2 + 75, null);
        
        	g2d.drawImage(img, getWidth() - 60, getHeight()/2 - 65, null);
        	g2d.drawImage(img, getWidth() - 60, getHeight()/2 + 5 , null);
        	g2d.drawImage(img, getWidth() - 60, getHeight()/2 + 75, null);
        	g2d.drawImage(img, getWidth() - 60, getHeight()/2 + 145, null);
        	
       	
       	Image imga = Toolkit.getDefaultToolkit().getImage("data/img/scenario/tech/black/Robot.png");
       		
       		g2d.drawImage(imga, 28, getHeight()/2 - 30, null);
        	g2d.drawImage(imga, 28, getHeight()/2 + 40, null);
        	g2d.drawImage(imga, 28, getHeight()/2 + 110, null);
       		
       		g2d.drawImage(imga, getWidth() - 60, getHeight()/2 - 100, null);
       		g2d.drawImage(imga, getWidth() - 60, getHeight()/2 - 30, null);
        	g2d.drawImage(imga, getWidth() - 60, getHeight()/2 + 40, null);
        	g2d.drawImage(imga, getWidth() - 60, getHeight()/2 + 110, null);
       	
       	for(int i = 28; i < getWidth() - 28; i = i + 35)
       	{
			g2d.drawImage(imga, i, getHeight()/2 - 100, null);
   			g2d.drawImage(img , i, getHeight()/2 + 145, null);	
       	}
    }
    
    public void setGamePhase(int nGF, Player p, int nTS, int nPlayers, int nRobots)
    {
        this.gamePhase = nGF;
        this.p = p;

        this.nTS = nTS;
        this.nPlayers = nPlayers;
        this.nRobots = nRobots;
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
        Graphics2D g2d = (Graphics2D) g;
        
        
            
        if(this.gamePhase == 1) 
        { 
        	looser(g); 
        	for(java.util.Map.Entry<Robot, JRobot> entry : robots.entrySet())
            	entry.getValue().remove(); 
        }
        else if(this.gamePhase == 2) {winner(g); }
        
        switch(this.gamePhase)
        {//---->
            case 0:
            
 
            // First, draw all the background
            for (int i = 0; i < MAP_SIZE; i++) 
                for (int j = 0; j < MAP_SIZE; j++)
                    cell[i][j].draw(g); 

            // After, draw items and scenarios
            for (int i = 0; i < MAP_SIZE; i++) 
                for (int j = 0; j < MAP_SIZE; j++) 
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

                    // Print scenarios
                    Scenario s = hex.terrain.getScenario();
                    if(s != null)
                    {
                        Images scen = Images.valueOf(s.name(), s.getTeam());
                        g2d.drawImage(
                                scen.img(), x-scen.dx(), y-scen.dy(), null
                        );

                        if(s instanceof Robot)
                        {
                            Robot r = (Robot) s;
                            if(!this.robots.containsKey(r)) 
                                this.robots.put(r, new JRobot(r));

                            JRobot jr = robots.get(r);
                            jr.update(x-scen.dx(), y-scen.dy());
                            jr.add();
                        }
                    }
            
                }
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
            this.power.setMaximum       
      (this.maxPower );
            this.power.setPreferredSize       (this.size     );
            this.hp.setForeground             (Color.GREEN   );
            
        }
        
        /**
         * Update infos exhibited by a r
obot.
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
            this.updateColorScheme(this.
hp, this.maxHP);
            
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
