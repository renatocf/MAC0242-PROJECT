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
    private static void action(RVM rvm, Stackable arg, String type)
        throws WrongTypeException
    {
        Operation op;
        try { 
            op = new Operation(type, arg);
        }
        catch (InvalidOperationException e) {
            throw new WrongTypeException(type);
        }
        
        Num permission = World.ctrl(op);
        rvm.DATA.push(permission);
    }
    
    public static void MOVE(RVM rvm, Stackable arg)
        throws WrongTypeException
    {
        action(rvm, arg, "MOVE");
        Stackable ans = rvm.DATA.pop();
        if(ans instanceof Num) 
        {
            System.out.println("MOVE");
        }
    }
    
    public static void DRAG(RVM rvm, Stackable arg)
        throws WrongTypeException
    {
        action(rvm, arg, "DRAG");
    }
    
    public static void DROP(RVM rvm, Stackable arg)
        throws WrongTypeException
    {
        action(rvm, arg, "DROP");
    }
    
    public static void HIT(RVM rvm, Stackable arg)
        throws WrongTypeException
    {
        action(rvm, arg, "HIT");
    }
}
