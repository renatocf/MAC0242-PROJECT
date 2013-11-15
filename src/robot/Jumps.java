package robot;

// Default libraries
import java.util.Vector;
import java.util.HashMap;

// Libraries
import stackable.*;
import exception.*;
import parameters.*;

/**
 * <b>Assembly functions - class Jumps</b><br>
 * Provides the funcions for conditional
 * (JMP) and unconditional (JIF/JIT) jumps
 * in the Program Counter (PC) of a giver
 * Virtual Machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Jumps
{
    // No instances of this class allowed
    private Jumps() {} 
    
    /** 
     * Interface for compartison operations,
     * Dummy interface with the aim of being a 
     * model for all the comparison operations.
     */
    private interface Verify
    {
        boolean verify();
    }
    
    /**
     * General-model funcion JCMP. <br>
     * Takes out the top of the main stack 
     * updates the counter accordingly to
     * the value on it. 
     * @param  rvm Virtual Machine
     * @param  arg Stackable argument
     * @param  v   Anonymous inner class of type Cmp
     *
     * @throws OutOfBoundsException
     */
    private static final void JCMP(RVM rvm, Stackable arg, Verify v)
        throws OutOfBoundsException
    {
        if(!v.verify()) return;
        
        if(arg instanceof Addr)
        {
            Addr address = (Addr) arg; 
            int index = (int) address.getAddress();
            
            try {
                if(rvm.PROG.elementAt(index) != null) 
                    rvm.PC = index-1;
                    // Sets the counter to the index -1, 
                    // to be able to increment in each 
                    // iteration of a for loop.
            }
            catch (Exception e) {
                throw new OutOfBoundsException();
            }
        } 
        else if(arg instanceof Num)
        {
            Num relative = (Num) arg; 
            int index = (int) relative.getNumber();
            
            try {
                if(rvm.PROG.elementAt(rvm.PC + index) != null) 
                    rvm.PC += (index-1);
                    // Sets the counter to the index -1, 
                    // to be able to increment in each 
                    // iteration of a for loop.
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
                rvm.PC = rvm.LABEL.get(label)-1;
                // Sets the counter to the index -1, 
                // to be able to increment in each 
                // iteration of a for loop.
            else
                throw new OutOfBoundsException();
        }
        
        // Debug
        Debugger.say("    [GOTO] ", rvm.PC+1);
    }
    
    /**
     * Assembly funcion JMP. <br>
     * Unconditional Jump
     *
     * @param  rvm Virtual Machine
     * @param  arg Stackable argument
     *
     * @throws OutOfBoundsException
     * @throws WrongTypeException
     */
    public static final void JMP(RVM rvm, Stackable arg) 
        throws OutOfBoundsException, WrongTypeException
    {
        // Always jump
        JCMP(rvm, arg, new Verify() {
            public boolean verify() { return true; } }
        );
    }
    
    /**
     * Assembly funcion JIT. <br>
     * Jump if stack top is a number that verifyes true.
     *
     * @param  rvm Virtual Machine
     * @param  arg Stackable argument
     *
     * @throws OutOfBoundsException
     * @throws WrongTypeException
     */
    public static final void JIT(RVM rvm, Stackable arg)
        throws OutOfBoundsException, WrongTypeException
    {
        Stackable top = rvm.DATA.pop();
        if(!(top instanceof Num)) throw new WrongTypeException("Num");
        
        Num num = (Num) top;
        final int bool = (int) num.getNumber();
        
        // Jump if top is a non-zero number
        JCMP(rvm, arg, new Verify() {
            public boolean verify()
            { return (bool != 0) ? true : false; } }
        );
    }
    
    /**
     * Assembly funcion JIF. <br>
     * Jump if stack top is a number that verifyes false.
     *
     * @param  rvm Virtual Machine
     * @param  arg Stackable argument
     *
     * @throws OutOfBoundsException
     * @throws WrongTypeException
     */
    public static final void JIF(RVM rvm, Stackable arg)
        throws OutOfBoundsException, WrongTypeException
    {
        Stackable top = rvm.DATA.pop();
        if(!(top instanceof Num)) throw new WrongTypeException("Num");
        
        Num num = (Num) top;
        final int bool = (int) num.getNumber();
        
        // Jump if top is zero
        JCMP(rvm, arg, new Verify() {
            public boolean verify()
            { return (bool == 0) ? true : false; } }
        );
    }
}
