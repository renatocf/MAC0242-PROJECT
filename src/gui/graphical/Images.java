package gui.graphical;

// Default Libraries
import java.util.HashMap;
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
import arena.Appearence;
import stackable.item.Item;

/**
 * <b>Graphical - ROOT</b><br>
 * Defines a path relative to the jar 
 * to find the images.
 * @see Images
 *
 * @author Renato Cordeiro Ferreira
 */
interface ROOT
{
    final String ROOT = "/img/";
}    

/**
 * <b>Graphical - Images</b><br>
 * Set up the paths for all images used in the
 * graphical user interface, allowing to load 
 * the buffered image from the enum value.
 * @see Graphical
 * 
 * @author Renato Cordeiro Ferreira
 */
enum Images implements ROOT
{   
    // Appearences
    DEEP    ( "Deep.png",      0, 0   ),
    DIRT    ( "Dirt.png",      0, 0   ),
    GRASS   ( "Grass.png",     0, 0   ),
    ICE     ( "Ice.png",       0, 0   ),
    SAND    ( "Sand.png",      0, 0   ),
    TUNDRA  ( "Snow.png",      0, 0   ),
    WATER   ( "Water.png",     0, 0   ),
    
    // Items
    CRYSTAL ( "Crystal.png",  13, 13  ),
    STONE   ( "Stone.png",    13, 13  ),
    
    // Scenarios
    ROCK    ( "Rock.png",     13, 13  ),
    BASE    ( "Base.png",     18, 50  ),
    TREE    ( "Tree.png",     25, 50  ),
    ROBOT   ( "Robot_1.png",  15, 15  ),
    
    // General
    GAME_OVER ( "GameOver.png" , 0, 0 );
        
    /* Auxiliar private variables */
    private BufferedImage img;
    private int dx = 0, dy = 0;
    
    /** 
     * Default Constructor.<br>
     * @param value Path for the image
     */
    private Images(String path, int dx, int dy) 
    { 
        this.img = load(ROOT + path); 
        this.dx = dx; this.dy = dy;
    }
    
    /**
     * Getter for the internal image.<br>
     * @return Buffered image for the element
     */
    BufferedImage img() { return this.img; }
    
    /**
     * Getter for the horizontal variation
     * when drawing the image.<br>
     * @return Horizontal variation for drawing
     */
    int dx() { return this.dx; }
    
    /**
     * Getter for the vertical variation
     * when drawing the image.<br>
     * @return Vertical variation for drawing
     */
    int dy() { return this.dy; }
    
    /** 
     * Auxiliar method for loaging an image to be 
     * put inside the graphical interface.
     * @param  path String with the path relative to
     *              the classpath (must be backslashed
     *              separated).
     * @return Buffered image
     */
    private static BufferedImage load(String path)
    {
        try { return ImageIO.read(Images.class.getResource(path)); 
        
        } catch (IOException e) {
            System.err.println("Error while reading!");
            e.printStackTrace();
        
        } catch (IllegalArgumentException e) {
            String pre = "[GRAPHICAL][IMAGE]";
            System.err.println(pre + "Image" + path + " not found!");
            e.printStackTrace();
        }
        return null;
    }
}
