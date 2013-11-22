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
            int RADIUS = 5;
            int VSPLIT = 20;
                
            this.miniMap = new Panel(
                map, player, RADIUS, 0, 
                (int)(RADIUS * MAP_SIZE * Math.sqrt(3)), 
                (int)(RADIUS * 3 * MAP_SIZE/2) + 2*VSPLIT
            );
            this.setPreferredSize(new Dimension(
                (int)(RADIUS * MAP_SIZE * Math.sqrt(3)),
                (int)(RADIUS * 3 * MAP_SIZE/2) + 2*VSPLIT
            ));
            this.miniMap.hide(true); // No scenarios/items
        
        //* MINI MAP FRAME INFO **************************************//
            /* this.setPreferredSize(new Dimension( */
                /* (int)(RADIUS * MAP_SIZE * Math.sqrt(3)), // Height */
                /* (int)(RADIUS * 3 * MAP_SIZE/2)           // Width */
            /* )); */
            this.pack();
        
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
