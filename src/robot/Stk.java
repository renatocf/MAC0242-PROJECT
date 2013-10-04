package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * Assembly functions - class Stk.
 * Provides the funcions for manipulating 
 * the main stack of the virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Function
 * @see RMV
 */
final public class Stk
{
    /**
     * Assembly funcion PUSH. 
     * Puts an element in the top of the main stack.
     * @param Stackable element.
     * @see Stackable
     */
    static void PUSH(RVM rvm, Stackable st)
    {
        rvm.DATA.push(st);
    }
    
    /**
     * Assembly funcion POP. 
     * Takes out an element of the top of the main stack.
     * @see Stackable
     */
    static Stackable POP(RVM rvm) throws StackUnderflowException
    {
        return rvm.DATA.pop();
    }
    
    /**
     * Assembly funcion DUP. 
     * Duplicates the top of the main stack.
     * @see Stackable
     */
    static void DUP(RVM rvm) throws StackUnderflowException
    {
        Stackable st = rvm.DATA.pop(); 
        rvm.DATA.push(st);
        rvm.DATA.push(st);
    }
}
