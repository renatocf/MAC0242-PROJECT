package gui.graphical;

// Libraries
import gui.*;
import arena.Map;
import parameters.*;
import players.Player;

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
public class Graphical implements GUI
{
    MapFrame mapFrame;
    EditorFrame editorFrame;
    
    public Graphical(Map map)
    {
        this.editorFrame    = new EditorFrame();
        this.mapFrame       = new MapFrame(map);
    }
    
    public void printText()
    {
        System.out.println(this.editorFrame.getText());
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
        this.mapFrame.paintMap();
    }
    
    /* Implementing interface GUI */
    public void printMiniMap() { }
    
    /* Implementing interface GUI */
    public void winner(Player p, int nTS, int nPlayers, int nRobots)
    {
        mapFrame.winner(p, nTS, nPlayers, nRobots);
    }
    
    /* Implementing interface GUI */
    public void looser(Player p)
    {
        mapFrame.looser(p);
    }
}




