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
            int x0 = 20;
            int y0 = 20;
            int MAP_WIDTH  = 2*x0 + (int)(RADIUS * MAP_SIZE * Math.sqrt(3) * 0.875);
            int MAP_HEIGHT = 2*y0 + (int)(RADIUS * 3 * MAP_SIZE/2 * 1.05);
                
            this.miniMap = new Panel(
                map, player, RADIUS, 0, 0, MAP_WIDTH, MAP_HEIGHT 
            );
            this.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
            this.miniMap.hide(true); // No scenarios/items
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
