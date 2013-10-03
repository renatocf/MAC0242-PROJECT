package robot.function;

// Default libraries
import java.util.Vector;

// Libraries
import stackable.*;
import exception.*;

/**
 * Assembly functions - class Mem.
 * Provides the funcions for using 
 * the auxiliar memory in the virtual 
 * machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Function
 * @see RMV
 */
public class Mem
{
    final private Stack DATA;
    final private Vector <Stackable> RAM;

    /**
     * Class constructor. 
     * Receives a handle to the main stack.
     * 
     * @param Stack Data
     * @param Vector Stackable RAM;
     */
    Mem(Stack DATA, Vector <Stackable> RAM)
    {
        this.DATA = DATA; this.RAM = RAM;
    }
    
    /**
     * Assembly funcion STO.
     * Takes out the top of the main stack
     * and puts it in a specific position 
     * of the secondary memory.
     * @param Stackable position (but 
     *        must be numeric)
     * @throws OutOfBoundsException
     * @throws StackUnderflowException
     */
    void STO(Stackable position)
        throws OutOfBoundsException,
               StackUnderflowException
    {
        int pos;
        if(position.looksLikeNumber())
        {
            Num num = (Num) position;
            pos = (int) num.getNumber();
        }
        else throw new StackUnderflowException();
        RAM.set(pos, DATA.pop());
    }
    
    /**
     * Assembly funcion RCL.
     * Takes out the data in the secondary
     * memory (if avaiable) and puts it in 
     * the top of the main stack.
     * @param Stackable position (but 
     *        must be numeric)
     * @throws OutOfBoundsException
     * @throws StackUnderflowException
     */
    void RCL(Stackable position) 
        throws OutOfBoundsException,
               StackUnderflowException
    {
        int pos;
        if(position.looksLikeNumber()) 
        {
            Num num = (Num) position;
            pos = (int) num.getNumber();
        }
        else throw new StackUnderflowException();
        DATA.push(RAM.get(pos));
    }
}
