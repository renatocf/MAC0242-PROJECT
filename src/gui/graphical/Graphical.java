package gui.graphical;

// Graphical Libraries (AWT)
import java.awt.*;

// Graphical Libraries (Swing)
import javax.swing.*;


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
        this.setLocationRelativeTo(null);
        this.setSize(725,887);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.screen = new Panel(25, 
            (int)(25.2*MAP_SIZE*Math.sqrt(3)), 
            (int)(25.5*3*MAP_SIZE/2), 32, map);
            
        screen.setSize(725, 687);
            
        
        JScrollPane scrollPane = new JScrollPane(
            screen,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        //this.add(scrollPane);
        
        JTextArea log = new JTextArea(5, 72);
        log.setFont(new Font("Comics Sans", Font.BOLD, 12));
        log.setText("Hello World!");
        log.setSize(725,200);
        
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, scrollPane , log);
        split.setDividerLocation(687);
        
        this.add(split);
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
