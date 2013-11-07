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
 * <b>Graphical Element - Cell</b><br>
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
    final private Polygon hex = new Polygon();
    /* final private Graphics2D GImg; */
    
    private Terrain terrain; private int x, y;
    private BufferedImage appearence;
    
    /**
     * Default constructor.<br>
     * @param x   Horizontal position where the cell 
     *            center will be set
     * @param y   Vertical position whee the cell 
     *            center will be set
     * @param img Texture for the cell
     */
    Cell(int x, int y, int r, Terrain terrain) 
    {
        this.x = x; this.y = y;
        this.terrain = terrain;
        this.appearence = Cell.appearence(terrain);
        
        for(int i = 0; i < 6; i++)
            hex.addPoint(
                x + (int) (r * Math.sin(i * Math.PI/3)),
                y + (int) (r * Math.cos(i * Math.PI/3))
            );
        
        /* GImg = img.createGraphics(); */
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
        
        Rectangle rec = new Rectangle(0,0,32,32);
        g2d.setPaint (new TexturePaint(appearence, rec));
        g2d.fill     (hex);
        
        if(terrain.getItem() != null)
        {
            Images test = Images.valueOf(terrain.getItem().name());
            g2d.drawImage(test.img(), x-11, y-13, null);
        }
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
        
    private static BufferedImage appearence(Terrain t)
    {
        String app = t.getAppearence().name();
        return Images.valueOf(app).img();
    }
}
