package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * Assembly functions - class IO.
 * Provides the funcions for checking
 * the data inside the stack of the 
 * virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Function
 * @see RMV
 */
final public class IO
{
    /**
     * Assembly funcion PRN. 
     * Takes out the top of the main stack
     * ant prints it in the stdout.
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
