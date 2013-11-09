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
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

// Libraries
import arena.Map;
import parameters.*;

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
public class Panel extends JPanel 
{
    // Map made with cells
    private Cell[][] cell = new Cell[MAP_SIZE][MAP_SIZE];
    
    // Local variables
    private Map map;
    private int width;
    private int height;
    
    // Game status
    private boolean activeGame = true;
    
    /**
     * Create a Panel with dimensions width x height,
     * containing MAP_SIZE² hexagons (built from a map).
     * @see Cell
     * @see Graphical
     *
     * @param R      radius
     * @param width  Desired width of the screen
     * @param height Desired height of the screen
     * @param map    Map over which the panel will
     *               create the GUI hexagons
     */
    Panel(int R, int width, int height, Map map) 
    {
        this.map = map;
        
        // Dimensions
        this.width = width;
    	this.height = height;
        
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
                    Δ + R + i*Dx, R + j*Dy, R, map.map[j][i]
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
	    this.activeGame = false;
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
        
        if(!this.activeGame)
        {
	        Image img = Images.GAME_OVER.img();
	        g2d.drawImage(img, (width/2)-250, (height/2)-62, null);
            return;
        }
        
        for (int i = 0; i < MAP_SIZE; i++) 
            for (int j = 0; j < MAP_SIZE; j++)
            {
                Cell hex = cell[i][j]; hex.draw(g); 
                
                // Print items
                if(hex.terrain.getItem() != null)
                {
                    Images item = Images.valueOf(
                        hex.terrain.getItem().name()
                    );
                    g2d.drawImage(
                        item.img(), hex.x-13, hex.y-13, null
                    );
                }
                
                // Print scenarios
                if(hex.terrain.getScenario() != null)
                {
                    Images scen = Images.valueOf(
                        hex.terrain.getScenario().name()
                    );
                    g2d.drawImage(
                        scen.img(), hex.x-13, hex.y-13, null
                    );
                }
            }
    }
}
