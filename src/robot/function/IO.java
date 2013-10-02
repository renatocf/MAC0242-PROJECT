package robot.function;

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
public class IO
{
    final private Stack DATA;

    /**
     * Class constructor. 
     * Receives a handle to the main stack.
     * 
     * @param Stack Data
     */
    IO(Stack DATA)
    {
        this.DATA = DATA;
    }
    
    /**
     * Assembly funcion PRN. 
     * Takes out the top of the main stack
     * ant prints it in the stdout.
     */
    void PRN()
    {
        try
        {
            System.out.println(DATA.pop());
        }
        catch (Exception e)
        {
            System.out.println("Empty stack!");
        }
    }
}
