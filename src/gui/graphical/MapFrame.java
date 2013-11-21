package gui.graphical;

// Default Libraries
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.IOException;

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

/**
 * <b>Graphical - MapFrame Mode</b><br>
 * Creates a panel with the main workflow
 * for the game, as the complete arena and
 * a log-message with robot info.
 * 
 * @author Karina Suemi
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
class MapFrame extends JFrame
{
    // View data model
    private Map map;
    private Player player;
    
    // Internal structures
    protected Panel screen;
    private JTextArea log;
    
    /** 
     * Default constructor.<br>
     * @param map    Object of the class map
     *               from package arena.
     * @param player Player who is visualizing the
     *               map (whith his specific view)
     */
    MapFrame(Map map, Player player)
    {
        this.map    = map;
        this.player = player;
        
        //* MAP FRAME INFO *******************************************//
            /* TODO: Take out hardcoded strings */
            this.setSize                  (SCREEN_WIDTH,SCREEN_HEIGHT);
            this.setTitle                 ("Robot's Battle");
            this.validate                 ();
            this.setLocationRelativeTo    (null);
            this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
                
        //* ARENA SCREEN *********************************************//
            this.screen = new Panel(
                map, player, 25, 32, 
                (int)(25.2*MAP_SIZE*Math.sqrt(3)), 
                (int)(25.5*3*MAP_SIZE/2) + 64
            );
                
            this.screen.setSize      (SCREEN_WIDTH, SCREEN_HEIGHT*9/10);
            this.screen.setFocusable (true);
                
            JScrollPane scrollPane = new JScrollPane(
                this.screen,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            );
        
        //* LOG BOX **************************************************//
            this.log = new JTextArea(5, 72);
            this.log.setFont      (new Font("Serif", Font.BOLD, 12));
            this.log.setSize      (SCREEN_WIDTH, SCREEN_HEIGHT/10);
            this.log.setFocusable (true);
            
            JScrollPane scrollLog = new JScrollPane(
                log,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            );
            
            // Redirect all outputs to this log box
            this.redirectSystemStreams();
        
        //* ARENA + LOG **********************************************//
            JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT, false, scrollPane, scrollLog
            );
            
            split.setDividerLocation (0.9);
            split.setResizeWeight    (0.9);
        
        //* VISIBILITY ***********************************************//
            this.add(split);
            
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() { setVisible(true); }
            });
    }
    
    /**
     * Auxiliar method to repaint frame.
     */
    void paintMap()
    {
        this.screen.setGamePhase(Phase.ACTIVE, -1, -1, -1);
        this.screen.repaint();
    }
    
    /**
     * Auxiliar function for painting in the arena
     * info about the end of the game.
     * @param nTS      Number of time steps since 
     *                 the beggining of the game
     * @param nPlayers Number of players
     * @param nRobots  Number of robots created by 
     *                 all players along the game
     */
    void winner(int nTS, int nPlayers, int nRobots)
    {
        this.screen.setGamePhase(Phase.WINNER, nTS, nPlayers, nRobots);
        this.screen.repaint();
    }
    
    /** 
     * Auxilar function for painting in the arena
     * info about a player that lost the game.
     */
    void looser()
    {
        /* '-1' for all info not used (players/time steps/robots) */
        this.screen.setGamePhase(Phase.LOOSER, -1, -1, -1);
        this.screen.repaint();
    }
    
    /**
     * Redefines the system output to go entirely
     * to the GUI's output stream.
     */
    private void redirectSystemStreams() 
    {
        // Implementation of interface OutputStrem
        OutputStream out = new OutputStream() 
        {
            @Override
            public void write(int b) throws IOException 
            {
                updateTextArea(String.valueOf((char) b));
            }
            
            @Override
            public void write(byte[] b, int off, int len) 
                throws IOException 
            {
                updateTextArea(new String(b, off, len));
            }
            
            @Override
            public void write(byte[] b) throws IOException 
            {
                write(b, 0, b.length);
            }
        };
        
        // Substitute system streams for Graphical's one
        System.setOut(new PrintStream(out, true));
    }
    
    /**
     * Append text to log message area.
     * @param text String to be appended in the
     *             log window
     */
    private void updateTextArea(final String text) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                log.append(text);
                log.setCaretPosition(log.getText().length() - 1);
            }
        });
    }
}
