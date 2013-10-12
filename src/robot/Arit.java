package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * <b>Assembly functions - class Arit</b><br>
 * Provides the funcions for arithmetic
 * operations (sum, subtraction, multiplication,
 * division and modulus) in the data inside the 
 * stack of the virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Arit
{
    // No instances of this class allowed
    private Arit() {} 
    
    /** 
     * Interface for arithmetic operations,
     * Dummy interface with the aim of being a 
     * model for all the arithmetic operations.
     */
    private interface Operation 
    {
        double op(double a, double b);
    }
    
    /**
     * General-model funcion calculate. <br>
     * Takes out the two arguments in the top 
     * of the main stack and pushes the answer
     * of the operation made over them.
     * 
     * @param rvm Virtual Machine
     * @param op  Anonymous inner class of type operation
     */
    private static final void calculate(RVM rvm, Operation op) 
        throws WrongTypeException,
               StackUnderflowException
    {
        Stackable arg1 = rvm.DATA.pop();
        Stackable arg2 = rvm.DATA.pop();
        
        if(arg1 instanceof Num && arg2 instanceof Num)
        {
            Num a = (Num) arg1, b = (Num) arg2;
            double ans = op.op(b.getNumber(), a.getNumber());
            rvm.DATA.push(new Num(ans));
        }
        else { throw new WrongTypeException("Num"); }
    }

    /**
     * Assembly function ADD.
     * @param rvm Virtual Machine
     */
    static void ADD(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a+b; } }
        );
    }
    
    /**
     * Assembly function SUB.
     * @param rvm Virtual Machine
     */
    static void SUB(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a-b; } }
        );
    }
    
    /**
     * Assembly function MUL.
     * @param rvm Virtual Machine
     */
    static void MUL(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a*b; } }
        );
    }
    
    /**
     * Assembly function DIV.
     * @param rvm Virtual Machine
     */
    static void DIV(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a/b; } }
        );
    }
    
    /**
     * Assembly function MOD.
     * @param rvm Virtual Machine
     */
    static void MOD(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a%b; } }
        );
    }
}
