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
    private boolean rightType;
    
    /**
     * Class constructor.
     * @param Action to be executed
     * @param Required arguments
     * 
     * @throws WrongTypeException
     * @throws InvalidOperationException
     */
    public Operation(String action, Stackable arg)
        throws InvalidOperationException,
               WrongTypeException
    {
        this.action = action;
        this.arg = arg;
        
        switch (this.action)
        {
            case "MOVE" : rightType = checkArg(); break;
            case "DRAG" : rightType = checkArg(); break;    
            case "DROP" : rightType = checkArg(); break;
            case "HIT"  : rightType = checkArg(); break;        
            default     : throw new InvalidOperationException(this.action);
        }
        if(!rightType)
        {
            throw new WrongTypeException("argument to operation " + 
                this.action + ", but argument was " + arg.toString());
        }
    }
    
    // Getters
    public String    getAction   () { return this.action; }
    public Stackable getArgument () { return this.arg;    }
    
    // Verify arguments
    private boolean checkArg() {return this.arg instanceof Direction; }
}
