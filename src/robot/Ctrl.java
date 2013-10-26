package robot;

// Default libraries
import java.util.Vector;
import java.util.HashMap;

//Libraries
import robot.*;
import stackable.*;
import exception.*;
import parameters.*;

/**
 * <b>Ctrl</b><br>
 * Given a assembly function name and its
 * argument (if required), executes it for
 * the Virtual Machine specified.
 *
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinícius Silva
 * @see Ctrl
 */
final public class Ctrl
{
    // No instances of this class allowed
    private Ctrl() {} 
    
    /** 
     * Selector for the function to be called
     * @param rvm Virtual Machine
     * @param met String with the name of the function
     * @param arg Argument of the assembly method
     * 
     * @throws SegmentationFaultException
     * @throws UndefinedFunctionException
     * @throws InvalidOperationException
     * @throws StackUnderflowException
     * @throws NoLabelFoundException
     * @throws OutOfBoundsException
     * @throws WrongTypeException
     */
    public static void ctrl(RVM rvm, String met, Stackable arg) 
        throws SegmentationFaultException,
               UndefinedFunctionException,
               InvalidOperationException,
               StackUnderflowException, 
               OutOfBoundsException,
               WrongTypeException
    {
        // Debug
        Debugger.say("[CTRL] ", met);
        if(arg != null) Debugger.say(" ", arg.toString());
        System.out.println();
        if(met.equals("END")) Debugger.say("===========");
        
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
            case "END" : Prog.END  (rvm);      break;

            // Functions
            case "CALL": Func.CALL (rvm, arg); break;
            case "RET" : Func.RET  (rvm);      break;
            
            // System calls
            case "MOVE": Syst.MOVE (rvm);      break;
            case "DRAG": Syst.DRAG (rvm);      break;
            case "DROP": Syst.DROP (rvm);      break;
            case "HIT" : Syst.HIT  (rvm);      break;
            case "LOOK": Syst.LOOK (rvm);      break;
            case "SEE" : Syst.SEE  (rvm);      break;
            case "ASK" : Syst.ASK  (rvm);      break;

            // Item veirification
            case "ITEM": Analysis.ITEM (rvm);  break;
            case "SEEK": Analysis.SEEK (rvm);  break;
            
            // Base case
            default: throw new InvalidOperationException(met);
        }
    }
}
