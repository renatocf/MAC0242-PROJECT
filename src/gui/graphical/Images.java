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
    /* Appearences */
    DEEP    ( "Deep.png"    ),
    DIRT    ( "Dirt.png"    ),
    GRASS   ( "Grass.png"   ),
    ICE     ( "Ice.png"     ),
    SAND    ( "Sand.png"    ),
    TUNDRA  ( "Snow.png"    ),
    WATER   ( "Water.png"   ),
    
    // Items
    CRYSTAL ( "Crystal.png" ),
    STONE   ( "Stone.png"   ),
    
    // Scenarios
    ROCK    ( "Rock.png"    ),
    BASE    ( "Base.png"    ),
    TREE    ( "Tree.png"    ),
    ROBOT   ( "Robot_1.png" );
    
    /* Auxiliar private variables */
    private BufferedImage img;
    
    /** 
     * Default Constructor.<br>
     * @param value Path for the image
     */
    private Images(String path) { this.img = load(ROOT + path); }
    
    /**
     * Getter for the internal image.<br>
     * @return Buffered image for the element
     */
    BufferedImage img() { return this.img; }
    
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
            System.err.println("Image not found!");
            e.printStackTrace();
        }
        return null;
    }
}
