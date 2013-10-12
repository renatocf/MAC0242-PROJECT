package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * <b>Assembly functions - class IO</b><br>
 * Provides the funcions for checking
 * the data inside the stack of the 
 * virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class IO
{
    // No instances of this class allowed
    private IO() {} 
    
    /**
     * Assembly funcion PRN. <br>
     * Takes out the top of the main stack
     * ant prints it in the stdout.
     *
     * @param rvm Virtual Machine
     */
    static void PRN(RVM rvm)
    {
        try
        {
            System.out.println(rvm.DATA.pop());
        }
        catch (Exception e)
        {
            System.out.println("Empty stack!");
        }
    }
}
