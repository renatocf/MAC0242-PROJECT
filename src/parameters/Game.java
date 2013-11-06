package parameters;

/** 
 * <b>Game</b><br>
 * General interface with parameters used to
 * configure the general behaviors of the game.
 */
public interface Game
{
    // /////////////////////////////////////////////////////////////////
    // ----------------------------------------------------------------
    //                            ANIMATION                              
    // ----------------------------------------------------------------
    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
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
     * screen, 15x15 is a good size.
     */
    public static int MAP_SIZE = 16;
    
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
