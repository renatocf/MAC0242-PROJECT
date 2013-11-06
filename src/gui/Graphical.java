package gui;

// Graphical Libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// Libraries
import parameters.*;
import arena.Map;

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
public class Graphical extends Frame implements GUI,Game
{
    /* Auxiliar variables for keeping interface GUI */
    private boolean firstTime = true;
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
        setTitle("Robot's Battle");
		setSize(600, 600);
		
        /* TODO: Fix listener (not working) */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		add(new Panel(30, 600, 600));
    }
    
    /**
     * Paint 1 frame of the game.<br>
     * Exhibits a new frame in the main
     * window created in the game.
     */
    public void paint()
    {
        /* TODO: Set up printing time */
        if(firstTime)
        {
            printMap();
            firstTime = false;
        }
    }
    
    /**
     * Shows Map.<br>
     * Creates a Map with more details of each element
     * of the scenarios and items in the map.
     */
    public void printMap()
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { setVisible(true); }
        });
    }
    
    /** 
     * Shows Mini Map.<br>
     * Creates a Mini Map with dimensions MAP_SIZE
     * x MAP_SIZE, with a one-character representation
     * of each scenario/item in the map.
     */
    public void printMiniMap() { }
}
