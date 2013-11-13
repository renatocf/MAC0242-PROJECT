package gui.graphical;

// Default Libraries
import java.io.IOException;
import java.lang.reflect.Field;

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
import players.Player;

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
    
    // Game status
    //The phases os this data are "active", "over" and "winner".
    private String gamePhase = "active";
    
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
        //this.setMargin(new Insets(5,5,5,5));
         //this.setBorder(BorderFactory.createEmptyBorder(100,100,100,100)); 
        // this.setBorder(BorderFactory.createLineBorder(Color.white,100,100,100,100)); 
           
        // Preferences
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(width, height));
        
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
     * Finishes the game.
     * @return Result of ending the game
     */
    boolean gameOver()
	{
	    this.gamePhase = "over";
        this.repaint();
        return true;
	}
	
	boolean theWinner(int i)
	{
		this.gamePhase = "winner";
		this.repaint();
		return true;
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
                if(hex.terrain.getScenario() != null)
                {
                    Images scen = Images.valueOf(
                        hex.terrain.getScenario().name(),
                        hex.terrain.getScenario().getTeam()
                    );
                    g2d.drawImage(
                        scen.img(), x-scen.dx(), y-scen.dy(), null
                    );
                }
            }
    }
}
