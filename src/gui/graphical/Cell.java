package gui.graphical;

// Graphical Libraries (AWT)
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

// Libraries
import arena.Terrain;

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
    // Characteristics
    final private Polygon hex = new Polygon();
    final Terrain terrain; 
    final int x, y;
    
    /**
     * Default constructor.<br>
     * @param x       Horizontal position where the cell 
     *                center will be set
     * @param y       Vertical position whee the cell 
     *                center will be set
     * @param terrain Terrain to build the cell
     */
    Cell(int x, int y, int r, Terrain terrain) 
    {
        this.x = x; this.y = y;
        this.terrain = terrain;
        
        // Create the hexagon with points
        for(int i = 0; i < 6; i++)
            hex.addPoint(
                x + (int) (r * Math.sin(i * Math.PI/3)),
                y + (int) (r * Math.cos(i * Math.PI/3))
            );
    }

    /**
     * Add the texture to the cell, using an image
     * inside a rectangle with dimensions 32x32.
     * @param g Graphic object with all context 
     *          needed to render the image
     */
    void draw(Graphics g) 
    { 
        /* TODO: Take out hardcoded numbers */
        Graphics2D g2d = (Graphics2D) g;
        
        // Get appearence
        String app = terrain.getAppearence().name();
        BufferedImage appearence = Images.valueOf(app).img();
        
        // Get rectangle to size qhe appearence
        Rectangle rec = new Rectangle(0,0,32,32);
        
        // Paint the background
        g2d.setPaint (new TexturePaint(appearence, rec));
        g2d.fill     (hex);
    }   

    /**
     * Translates the cell among the X and Y axis.
     * @param dx Variation in the X axis
     * @param dy Variation in the Y axis
     */
    void trans(int dx, int dy) 
    {
        hex.translate(dx, dy);
    }
}
