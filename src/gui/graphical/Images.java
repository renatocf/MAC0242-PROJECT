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

interface ROOT
{
    final String ROOT = "/img/";
}    

/**
 * Set up the paths for all images used in the
 * graphical user interface, allowing to load 
 * the buffered image from the enum value.
 * @see Graphical
 * 
 * @author Renato Cordeiro Ferreira
 */
enum Images implements ROOT
{   
    ROCK    ( "Rock.png"    ),
    
    // Images
    CRYSTAL ( "Crystal.png" ),
    STONE   ( "Stone.png"   ),
    
    /* TODO: complete appearences */
    DIRT    ( "Dirt.png"    ),
    GRASS   ( "Grass.png"   ),
    SAND    ( "Sand.png"    ),
    TUNDRA  ( "Snow.png"    ),
    ICE     ( "Ice.png"     );
    
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