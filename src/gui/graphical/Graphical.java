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
    Player player;
    MapFrame     mapFrame;
    EditorFrame  editorFrame;
    MiniMapFrame miniMapFrame;
    
    public Graphical(Map map, Player player)
    {
        this.player = player;   

        /* TODO: Finish editor frame */
        /* this.editorFrame    = new EditorFrame(); */
        this.mapFrame       = new MapFrame(map, player);
        this.miniMapFrame   = new MiniMapFrame(map, player);
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
        this.mapFrame.paint();
        this.miniMapFrame.paint();
    }
    
    /* Implementing interface GUI */
    public void printMiniMap() 
    { 
        /* this.miniMapFrame(); */
    }
    
    /* Implementing interface GUI */
    public void winner(int nTS, int nPlayers, int nRobots)
    {
        this.mapFrame.winner(nTS, nPlayers, nRobots);
    }
    
    /* Implementing interface GUI */
    public void looser()
    {
        this.mapFrame.looser();
    }
}
