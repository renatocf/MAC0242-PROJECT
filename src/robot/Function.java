package robot;

// Default libraries
import java.util.Vector;
import java.util.HashMap;

//Libraries
import robot.*;
import stackable.*;
import exception.*;

final public class Function
{
    /** 
     * Selector for the function to be called
     * @param String with the name of the function
     * @param Argument of the assembly method
     */
    static public void call(RVM rvm, String met, Stackable arg) 
        throws SegmentationFaultException,
               InvalidOperationException,
               StackUnderflowException, 
               OutOfBoundsException,
               WrongTypeException
    {
        switch(met)
        {
            // IO functions
            case "PRN" : IO.PRN    (rvm);      break;
            
            // Stack functions
            case "POP" : Stk.POP   (rvm);      break;
            case "PUSH": Stk.PUSH  (rvm, arg); break;
            case "DUP" : Stk.DUP   (rvm);      break;
            
            // Arithmetic functions
            case "ADD" : Arit.ADD  (rvm);      break;
            case "SUB" : Arit.SUB  (rvm);      break;
            case "MUL" : Arit.MUL  (rvm);      break;
            case "DIV" : Arit.DIV  (rvm);      break;
            case "MOD" : Arit.MOD  (rvm);      break;
            
            // Memory functions
            case "STO" : Mem.STO   (rvm, arg); break;
            case "RCL" : Mem.RCL   (rvm, arg); break;
            
            // Tests functions
            case "EQ"  : Tests.EQ  (rvm);      break;
            case "GT"  : Tests.GT  (rvm);      break;
            case "GE"  : Tests.GE  (rvm);      break;
            case "LT"  : Tests.LT  (rvm);      break;
            case "LE"  : Tests.LE  (rvm);      break;
            case "NE"  : Tests.NE  (rvm);      break;
            
            // Jumps functions
            case "JMP" : Jumps.JMP (rvm, arg); break;
            case "JIT" : Jumps.JIT (rvm, arg); break;
            case "JIF" : Jumps.JIF (rvm, arg); break;

            // Program workflow
            case "NOP" : Prog.NOP  (rvm);      break;
            case "END" : Prog.NOP  (rvm);      break;
            
            // Base case
            default: throw new InvalidOperationException(met);
        }
    }
}
