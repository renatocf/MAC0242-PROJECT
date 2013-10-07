package operation;

// Libraries
import arena.*;
import exception.*;
import stackable.*;

/**
 * Class Operation.
 * Validates the type and arguments of
 * a given operation. Also, is an interface
 * betwen the arena and a RVM.
 * 
 * @author Karina Suemi 
 * @author Renato Cordeiro
 * @author Vinícius Silva 
 * @see RVM 
 * @see World
 */

public class Operation
{
    final private   Stackable arg;
    final private   String action;
    private boolean wrongType;
    
    /**
     * Class constructor.
     * @param Action to be executed
     * @param Required arguments
     * 
     * @throws WrongTypeException
     * @throws InvalidOperationException
     */
     
    //11public Operation(String action, Stackable arg)
    public Operation(String action, Stackable arg)
        throws InvalidOperationException,
               WrongTypeException
    {
        this.action = action;
        this.arg = arg;
        
        switch (this.action)
        {
            case "MOVE" : wrongType = checkArg(); break;
            case "DRAG" : wrongType = checkArg(); break;    
            case "DROP" : wrongType = checkArg(); break;
            case "HIT"  : wrongType = checkArg(); break;        
            default     : throw new InvalidOperationException(this.action);
        }
        if(wrongType)
        {
            throw new WrongTypeException("argument to operation " + 
                this.action + ", but argument was " + arg.toString());
        }
    }
    
    // Getters
    public String    getAction   () { return this.action; }
    public Stackable getArgument () { return this.arg;    }
    
    // Verify arguments
    private boolean checkArg() { return this.arg instanceof Direction; }
}  
