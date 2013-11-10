package gui.graphical;

// Graphical Libraries (AWT)
import java.awt.Dimension;
import java.awt.GridBagLayout;

// Graphical Libraries (Swing)
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

// Libraries
import gui.*;
import arena.Map;
import parameters.*;

// Import links
import static parameters.Game.*;

/**
 * <b>GUI - Graphical Mode</b><br>
 * Makes an implementation of the interface
 * GUI for exhibiting the game, using Java's
 * default graphic libraries (AWT and Swing).
 * 
 * @author Karina Suemi
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
public class Graphical extends JFrame implements GUI
{
    /* Auxiliar variables for keeping interface GUI */
    private boolean firstTime = true;
    private Panel screen;
    private Map map;
    
    /** 
     * Default constructor.<br>
     * @param map Object of the class map
     *            from package arena.
     */
    public Graphical(Map map)
    {
        this.map = map;
        
        /* TODO: Take out hardcoded strings */
        this.setTitle("Robot's Battle");
        this.setSize(725, 655);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.screen = new Panel(25, 
            (int)(25.2*MAP_SIZE*Math.sqrt(3)), 
            (int)(25.5*3*MAP_SIZE/2), map);
        
        JScrollPane scrollPane = new JScrollPane(
            screen,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        this.add(scrollPane);
    }
    
    /* Implementing interface GUI */
    public void paint()
    {
        try { Thread.sleep(SPEED); } 
        catch (Exception e) { }
        
        this.printMap();
    }
    
    /* Implementing interface GUI */
    public void printMap()
    {
        if(this.firstTime)
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() { setVisible(true); }
            });
            this.firstTime = false;
        }
        this.screen.repaint();
    }
    
    /* Implementing interface GUI */
    public void printMiniMap() { }
    
    /* Implementing interface GUI */
    public boolean gameOver()
    {
        return this.screen.gameOver();
    }
}
