package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * <b>Assembly functions - class Prog</b><br>
 * Provides the funcions for controlling
 * the program: no operation (NOP) and 
 * end of program (END).
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Prog
{
    // No instances of this class allowed
    private Prog() {} 
    
    /**
     * Assembly funcion NOP. <br>
     * Do nothing for one step.
     */
    static void NOP(RVM rvm) {}
    
    /**
     * Assembly funcion END. <br>
     * Sets the counter to indicate
     * the END of the program.
     */
    static void END(RVM rvm) { rvm.PC = -2; }
    // Sets the counter to -2, to be able to
    // increment in each iteration of a for loop.
}
