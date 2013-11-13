package gui.graphical;

// Libraries
import gui.*;
import arena.Map;
import parameters.*;

// Import links
import static parameters.Game.*;

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
    public boolean gameOver()
    {
        return this.mapFrame.gameOver();
    }
    
    /* Implementing interface GUI */
    public boolean whoWins(int i)
    {
 		return this.mapFrame.whoWins(i);   
    }
    
    

}




