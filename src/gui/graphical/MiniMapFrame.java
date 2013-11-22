package gui.graphical;

// Graphical Libraries (AWT)
import java.awt.*;

// Graphical Libraries (Swing)
import javax.swing.*;

// Libraries
import arena.Map;
import parameters.*;
import players.Player;

// Import links
import static parameters.Game.*;

public class MiniMapFrame extends JFrame
{
    // View data model
    private Map map;
    private Player player;
    
    // Internal structures
    protected Panel miniMap;
    
    MiniMapFrame(Map map, Player player)
    {
        // Setting MiniMapFrame attributes
        this.map    = map;
        this.player = player;
        
        //* ARENA SCREEN *********************************************//
            int RADIUS   = 5;
            int IMG_SIZE = 0;
                
            this.miniMap = new Panel(
                map, player, RADIUS, IMG_SIZE, 
                (int)(RADIUS * MAP_SIZE * Math.sqrt(3)), 
                (int)(RADIUS * 3 * MAP_SIZE/2) + 2*IMG_SIZE
            );
            this.miniMap.hide(true); // No scenarios/items
        
        //* MINI MAP FRAME INFO **************************************//
            this.setSize(
                (int)(RADIUS * MAP_SIZE * Math.sqrt(3)), // Height
                (int)(RADIUS * 3 * MAP_SIZE/2)           // Width
            );
        
        //* VISIBILITY ***********************************************//
            this.add(this.miniMap);
            
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() { setVisible(true); }
            });
    }
    
    /**
     * Auxiliar method to repaint frame.
     */
    void paint() { this.miniMap.repaint(); }
}
