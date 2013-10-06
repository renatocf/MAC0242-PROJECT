package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * Assembly functions - class Func.
 * Provides the funcions for controlling
 * the creation of functions: CALL and
 * RET (special types of jumps).
 * 
 * @author Renato Cordeiro Ferreira
 * @see Function
 * @see RMV
 */
final public class Func
{
    /**
     * Assembly funcion CALL.
     * Execute an inconditional jump to 
     * a label or position, storing the 
     * position of return in the CTRL
     * stack for doing a callback with RET.
     */
    static void CALL(RVM rvm, Stackable arg) 
        throws WrongTypeException, OutOfBoundsException
    {
        // Saves PC in the CTRL stack
        rvm.CTRL.push(rvm.PC);
        
        // Do inconditional Jump to label/position
        if(arg instanceof Num)
        {
            Num num = (Num) arg; 
            int index = (int) num.getNumber();
            
            try {
                if(rvm.PROG.elementAt(index) != null)
                    rvm.PC = index;
            }
            catch (Exception e) {
                throw new OutOfBoundsException();
            }
        } 
        else if(arg instanceof Text) 
        {
            Text text = (Text) arg;
            String label = (String) text.getText();
            
            if(rvm.LABEL.containsKey(label))
                rvm.PC = rvm.LABEL.get(label);
            else
                throw new OutOfBoundsException();
        }
        else throw new WrongTypeException("String or Number");
    }
    
    /**
     * Assembly funcion RET.
     * Pops the top most saved return postion
     * to be able to go back to the function 
     * caller.
     */
    static void RET(RVM rvm)
        throws UndefinedFunctionException
    { 
        try { rvm.PC = rvm.CTRL.pop(); }
        catch (Exception e) { 
            throw new UndefinedFunctionException(rvm.PC);
        }
    }
    // No need to increment PC. In a for 
    // loop, it will be done automatically.
}
