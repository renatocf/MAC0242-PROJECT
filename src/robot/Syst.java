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
    private static void action(RVM rvm, String type)
        throws WrongTypeException,
               InvalidOperationException 
    {
        Stackable arg = null;
        if(!(type.equals("SEE")))
            arg = rvm.DATA.pop();
        Operation op;
        
        try { 
            op = new Operation(rvm, type, arg);
        }
        catch (InvalidOperationException e) {
            throw new WrongTypeException(type);
        }
        
        Stackable[] stk = World.ctrl(op);
        for(int i = 0; i< stk.length; i++) rvm.DATA.push(stk[i]);
        rvm.syscall = true;
    }
    
    public static void MOVE(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "MOVE");
    }
    
    public static void DRAG(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "DRAG");
    }
    
    public static void DROP(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "DROP");
    }
    
    public static void HIT(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "HIT");
    }
    
    public static void LOOK(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "LOOK");
    }
    
    public static void SEE(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "SEE");
    }
}
