package robot;

// Libraries
import arena.*;
import operation.*;
import stackable.*;
import exception.*;

/**
 * Assembly functions - class Syst.
 * Implements the commands MOVE, DRAG,
 * DROP and HIT and get from the World
 * if it has permission for executing 
 * the correspondent action.
 *
 * @author Karina Suemi 
 * @author Renato Cordeiro
 * @author Vinícius Silva 
 * @see Function 
 * @see World
 */
final public class Syst
{
    private static void action(RVM rvm, String type) // mandando Stackable!
        throws WrongTypeException
    {
        Stackable dir = rvm.DATA.pop();
        Operation op;
        
        try { 
            op = new Operation(type, dir); 
        }
        catch (InvalidOperationException e) {
            throw new WrongTypeException(type);
        }
        
        Num permission = World.ctrl(op);
        rvm.DATA.push(permission);
    }
    
    public static void MOVE(RVM rvm)
        throws WrongTypeException
    {
        action(rvm, "MOVE");
    }
    
    public static void DRAG(RVM rvm)
        throws WrongTypeException
    {
        action(rvm, "DRAG");
    }
    
    public static void DROP(RVM rvm)
        throws WrongTypeException
    {
        action(rvm, "DROP");
    }
    
    public static void HIT(RVM rvm)
        throws WrongTypeException
    {
        action(rvm, "HIT");
    }
}
