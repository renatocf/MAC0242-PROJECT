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
import gui.*;
import arena.Map;
import parameters.*;


// Import links
import static parameters.Game.*;

/**
 * <b>GUI - MapFrame Mode</b><br>
 * Makes an implementation of the interface
 * GUI for exhibiting the game, using Java's
 * default graphic libraries (AWT and Swing).
 * 
 * @author Karina Suemi
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
class MapFrame extends JFrame
{
    /* Auxiliar variables for keeping interface GUI */
    private boolean firstTime = true;
    private Panel screen;
    private Map map;
    private JTextArea log;
    
    /** 
     * Default constructor.<br>
     * @param map Object of the class map
     *            from package arena.
     */
    MapFrame(Map map)
    {
        this.map = map;  
        
        /* TODO: Take out hardcoded strings */
        this.setTitle("Robot's Battle");
        this.setLocationRelativeTo(null);
        this.setSize(725,787);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.screen = new Panel(25, 
            (int)(25.2*MAP_SIZE*Math.sqrt(3)), 
            (int)(25.5*3*MAP_SIZE/2), 32, map);
            
        screen.setSize(725, 687);
        
        screen.setFocusable(true);
            
        
        JScrollPane scrollPane = new JScrollPane(
            screen,
            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        
        log = new JTextArea(5, 72);
        log.setFont(new Font("Comics Sans", Font.BOLD, 12));
        log.setText("Hello World!");
        log.setSize(725,200);
        log.setFocusable(true);
        
        JScrollPane scrollLog = new JScrollPane(
            log,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        
        
        redirectSystemStreams();
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, scrollPane , scrollLog);
        split.setDividerLocation(0.8);
        split.setResizeWeight(0.8);
        
        this.add(split);
        
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() { setVisible(true); }
            });
    }
    
    private void redirectSystemStreams() 
    {
        OutputStream out = new OutputStream() 
        {
            @Override
            public void write(int b) throws IOException 
            {
                updateTextArea(String.valueOf((char) b));
            }
            
            @Override
            public void write(byte[] b, int off, int len) throws IOException 
            {
                updateTextArea(new String(b, off, len));
            }
            
            @Override
            public void write(byte[] b) throws IOException 
            {
                write(b, 0, b.length);
            }
        };
        
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
    
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
    
    void paintMap()
    {
        this.screen.repaint();
    }
    
    boolean gameOver()
    {
        return this.screen.gameOver();
    }
    
     boolean whoWins(int i)
    {
 		return this.screen.theWinner(i);   
    }

    
}
