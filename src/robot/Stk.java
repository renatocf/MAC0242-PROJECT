package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * <b>Assembly functions - class Stk</b><br>
 * Provides the funcions for manipulating 
 * the main stack of the virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Stk
{
    // No instances of this class allowed
    private Stk() {} 
    
    /**
     * Assembly funcion PUSH. <br>
     * Puts an element in the top of the main stack.
     * 
     * @param  rvm Virtual Machine
     * @param  st  Stackable element.
     * @throws StackUnderflowException
     * @see    Stackable
     */
    static void PUSH(RVM rvm, Stackable st)
    {
        rvm.DATA.push(st);
    }
    
    /**
     * Assembly funcion POP. <br>
     * Takes out an element of the top of the main stack.
     * 
     * @param  rvm Virtual Machine
     * @throws StackUnderflowException
     * @see    Stackable
     */
    static Stackable POP(RVM rvm) throws StackUnderflowException
    {
        return rvm.DATA.pop();
    }
    
    /**
     * Assembly funcion DUP. <br>
     * Duplicates the top of the main stack.
     * 
     * @param  rvm Virtual Machine
     * @throws StackUnderflowException
     * @see    Stackable
     */
    static void DUP(RVM rvm) throws StackUnderflowException
    {
        Stackable st = rvm.DATA.pop(); 
        rvm.DATA.push(st);
        rvm.DATA.push(st);
    }
}
