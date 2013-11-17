package parameters;

// Default libraries
import java.util.Random;

// Libraries
import random.Weather;

/** 
 * <b>Game</b><br>
 * General class with parameters used to
 * configure the general behaviors of the game.
 */
public final class Game
{
    /** 
     * <b>Game: maximum of crystals</b><br>
     * Maximum number of crystals to have
     * a base destroyed.
     */
    public static int MAX_CRYSTALS = 1;
    
    // /////////////////////////////////////////////////////////////////
    // ----------------------------------------------------------------
    //                            ANIMATION                              
    // ----------------------------------------------------------------
    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
     /** 
     * <b>Animation: rand</b><br>
     * Random number generator for the map genesis.
     */
    public static Random RAND = new Random();
    
    /** 
     * <b>Animation: speed</b><br>
     * Number of miliseconds before painting
     * a new screen.
     */
    public static int SPEED = 300;
    
    // /////////////////////////////////////////////////////////////////
    // ----------------------------------------------------------------
    //                               MAP                                 
    // ----------------------------------------------------------------
    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
    /**
     * <b>Map: size</b><br>
     * Dimensions for the map. In a common
     * screen, 16x16 is a good size.
     */
    public static int MAP_SIZE = 16;
    
    // /////////////////////////////////////////////////////////////////
    // ----------------------------------------------------------------
    //                           SCREEN
    // ----------------------------------------------------------------
    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * <b>Screen: Width</b><br>
     * inicial width of the main window.
     */
    public static int SCREEN_WIDTH  = 720;

    /**
     * <b>Screen: Height</b><br>
     * inicial height of the main window.
     */
    public static int SCREEN_HEIGHT = 720;

    /**
     * <b>Map: weather</b><br>
     * Weather for the map. 
     * By default, tropical.
     */
    public static Weather WEATHER = Weather.TROPICAL;
    
    // /////////////////////////////////////////////////////////////////
    // ----------------------------------------------------------------
    //                             ROBOTS                               
    // ----------------------------------------------------------------
    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
    /** <b>Robots: max number</b><br>
     * Maximum number of robots per player
     * accepted by the arena 
     */
    public static int ROBOTS_NUM_MAX = 100;
    /**
     * <b>Robots: initial number</b><br>
     * Initial number od robots per player
     * created when the game starts.
     */
    public static int ROBOTS_NUM_INITIAL = 3;
    
    // /////////////////////////////////////////////////////////////////
    // ----------------------------------------------------------------
    //                            ASSEMBLY                               
    // ----------------------------------------------------------------
    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * <b>Assembly: maximum run</b><br>
     * Maximum of assembly lines to be run 
     * by a program that makes no syscall.
     */
    public static int ASM_MAX_RUN = 300;
}
