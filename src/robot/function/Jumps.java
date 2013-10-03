package robot.function;

// Default libraries
import java.util.Vector;
import java.util.HashMap;

// Libraries
import robot.*;
import stackable.*;
import exception.*;

/**
 * Assembly functions - class Arit.
 * Provides the funcions for arithmetic
 * operations (sum, subtractio, multiplication,
 * division and modulus) in the data inside the 
 * stack of the virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Function
 * @see RMV
 */
public class Jumps
{
    final private Vector<Command> PROG;
    final private Stack DATA;
    private Integer PC;
    final private HashMap<String, Integer> LABEL;
    
    /**
     * Class constructor. 
     * Receives a handle to the main stack.
     * 
     * @param Stack Data
     */
    public Jumps(Vector<Command> PROG, Stack DATA, 
                 Integer PC, HashMap<String, Integer> LABEL)
    
    {
        this.PROG  = PROG;
        this.DATA  = DATA;
        this.PC    = PC;
        this.LABEL = LABEL;
    }
    
    /** 
     * Interface for arithmetic operations,
     * Dummy interface with the aim of being a 
     * model for all the arithmetic operations.
     */
    private interface Operation 
    {
        double op(double a, double b);
    }
    
    /**
     * General-model funcion calculate.
     * Takes out the two arguments in the top 
     * of the main stack and pushes the answer
     * of the operation made over them.
     * @param Anonymous inner class of type operation
     */
    final public void JMP(Stackable arg) 
        throws OutOfBoundsException,
               SegmentationFaultException
    {
        if(arg.looksLikeNumber())
        {
            Num num = (Num) arg; 
            int index = (int) num.getNumber();
            
            try {
                if(PROG.elementAt(index) != null) { this.PC = index; }
            }
            catch (Exception e) {
                throw new OutOfBoundsException();
            }
        } else {
        }
    }
    
    final public void JIT(Stackable arg) {}
    final public void JIF(Stackable arg) {}
}
