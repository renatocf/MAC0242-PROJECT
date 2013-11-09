package gui;

// Libraries
import parameters.Game;

/**
 * <b>GUI</b><br>
 * Methods that a GUI must provide to 
 * work with the game characteristics.
 * @see parameters.Game
 */
public interface GUI
{
    /**
     * Shows Map.<br>
     * Creates a Map with more details of each element
     * of the scenarios and items in the map. Each 
     * hexagon represents one title. 
     */
    public void paint();
    
    /**
     * Paint 1 frame of the game.<br>
     * Depending on the speedy attribute,
     * from interface Game, exhibits a frame
     * in the program's standart output.
     */
    public void printMap();
    
    /** 
     * Shows Mini Map.<br>
     * Creates a Mini Map with dimensions MAP_SIZE
     * x MAP_SIZE, with a one-character representation
     * of each scenario/item in the map.
     */
    public void printMiniMap();
    
    /** 
     * Finishes the game.
     * @return Result of ending the game
     */
    public boolean gameOver();
}
