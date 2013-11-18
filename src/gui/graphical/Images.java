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
import players.Player;
import arena.Appearence;
import stackable.item.Item;

// Import links
import static parameters.Game.*;
import static gui.graphical.Type.*;

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
 * <b>Graphical - Type</b><br>
 * Defines which type of image we
 * are dealing with
 * @see Images
 *
 * @author Renato Cordeiro Ferreira
 */
enum Type
{
    APP,
    GEN,
    ITM,
    NAT,
    TEC,
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
    DEEP         ( APP, "Deep.png",      0, 0   ),
    DIRT         ( APP, "Dirt.png",      0, 0   ),
    GRASS        ( APP, "Grass.png",     0, 0   ),
    ICE          ( APP, "Ice.png",       0, 0   ),
    SAND         ( APP, "Sand.png",      0, 0   ),
    TUNDRA       ( APP, "Snow.png",      0, 0   ),
    WATER        ( APP, "Water.png",     0, 0   ),
    
    // Items
    CRYSTAL      ( ITM, "Crystal.png",  13, 13  ),
    STONE        ( ITM, "Stone.png",    13, 13  ),
    
    // Nature
    ROCK         ( NAT, "Rock.png",     13, 13  ),
    TREE         ( NAT, "Tree.png",     25, 50  ),
    
    // Technology
    BLACK_ROBOT  ( TEC, "Robot.png",    15, 15  ),
    BLACK_BASE   ( TEC, "Base.png",     18, 50  ),
    
    GREEN_ROBOT  ( TEC, "Robot.png",    15, 15  ),
    GREEN_BASE   ( TEC, "Base.png",     18, 50  ),
    
    BLUE_ROBOT   ( TEC, "Robot.png",    15, 15  ),
    BLUE_BASE    ( TEC, "Base.png",     18, 50  ),
    
    RED_ROBOT    ( TEC, "Robot.png",    15, 15  ),
    RED_BASE     ( TEC, "Base.png",     18, 50  ),
    
    // General
    GAME_OVER    ( GEN, "GameOver.png" , 0, 0 );
        
    /* Auxiliar private variables */
    private int dx = 0, dy = 0;
    private BufferedImage img;
    private Type type;
    
    /** 
     * Default Constructor.<br>
     * @param value Path for the image
     */
    private Images(Type type, String path, int dx, int dy) 
    { 
        String subdir;
        switch(type)
        {
            case NAT: // Scenario - Nature
                String w = "scenario/nature/" + WEATHER.name();
                subdir = w.toLowerCase() + "/";
                break;
            //
            case TEC: // Scenario - Technology
                String[] name = this.name().split("_");
                subdir = "scenario/tech/" + name[0].toLowerCase() + "/";
                break;
            //
            case ITM: subdir = "item/";       break;
            case APP: subdir = "appearence/"; break;
            case GEN: subdir = "util/";       break;
            //
            default: subdir = "";
        }
        
        this.img = load(ROOT + subdir + path);
        this.dx = dx; this.dy = dy; this.type = type;
    }
    
    /**
     * Getter for the internal image.<br>
     * @return Buffered image for the element
     */
    BufferedImage img() { return this.img; }
        
    /**
     * Getter for the internal image.<br>
     * @return Buffered image for the element
     */
    static Images valueOf(String s, Player p)
    {
        if(p.color() == "") return Images.valueOf(s);
        return Images.valueOf(p.color() + "_" + s);
    }
    
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
            System.err.println(pre + "Image " + path + " not found!");
            e.printStackTrace();
        }
        return null;
    }
}
