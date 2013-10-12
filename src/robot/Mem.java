package robot;

// Default libraries
import java.util.Vector;

// Libraries
import stackable.*;
import exception.*;

/**
 * <b>Assembly functions - class Mem</b><br>
 * Provides the funcions for using 
 * the auxiliar memory in the virtual 
 * machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Mem
{
    // No instances of this class allowed
    private Mem() {} 
    
    /**
     * Assembly funcion STO. <br>
     * Takes out the top of the main stack
     * and puts it in a specific position 
     * of the secondary memory.
     *
     * @param  rvm      Virtual Machine
     * @param  position Stackable position 
     *                  (must be numeric)
     *                  
     * @throws SegmentationFaultException
     * @throws StackUnderflowException
     */
    static void STO(RVM rvm, Stackable position)
        throws SegmentationFaultException,
               StackUnderflowException
    {
        int pos;
        if(position instanceof Num)
        {
            Num num = (Num) position;
            pos = (int) num.getNumber();
        }
        else throw new StackUnderflowException();
        rvm.RAM.put(pos, rvm.DATA.pop());
    }
    
    /**
     * Assembly funcion RCL. <br>
     * Takes out the data in the secondary
     * memory (if avaiable) and puts it in 
     * the top of the main stack.
     *
     * @param  rvm      Virtual Machine
     * @param  position Stackable position 
     *                  (must be numeric)
     *                  
     * @throws SegmentationFaultException
     * @throws StackUnderflowException
     */
    static void RCL(RVM rvm, Stackable position) 
        throws SegmentationFaultException,
               StackUnderflowException
    {
        int pos;
        if(position instanceof Num)
        {
            Num num = (Num) position;
            pos = (int) num.getNumber();
        }
        else throw new StackUnderflowException();
        rvm.DATA.push(rvm.RAM.remove(pos));
    }
}
