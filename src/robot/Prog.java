package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * Assembly functions - class Prog.
 * Provides the funcions for controlling
 * the program: no operation (NOP) and 
 * end of program (END).
 * 
 * @author Renato Cordeiro Ferreira
 * @see Function
 * @see RMV
 */
final public class Prog
{
    /**
     * Assembly funcion NOP. 
     * Do nothing for one step.
     */
    static void NOP(RVM rvm) {}
    
    /**
     * Assembly funcion END. 
     * Sets the counter to indicate
     * the END of the program.
     */
    static void END(RVM rvm) { rvm.PC = -2; }
    // Sets the counter to -2, to be able to
    // increment in each iteration of a for loop.
}
