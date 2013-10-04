package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * Assembly functions - class Arit.
 * Provides the funcions for arithmetic
 * operations (sum, subtractio, multiplication,
 * division and modulus) in the data inside the 
 * stack of the virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Function
 * @see RMV
 */
final public class Arit
{
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
     * General-model funcion calculate.
     * Takes out the two arguments in the top 
     * of the main stack and pushes the answer
     * of the operation made over them.
     * @param Anonymous inner class of type operation
     */
    static final private void calculate(RVM rvm, Operation op) 
        throws WrongTypeException,
               StackUnderflowException
    {
        Stackable arg1 = rvm.DATA.pop();
        Stackable arg2 = rvm.DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num a = (Num) arg1, b = (Num) arg2;
            double ans = op.op(b.getNumber(), a.getNumber());
            rvm.DATA.push(new Num(ans));
        }
        else { throw new WrongTypeException("Num"); }
    }

    static void ADD(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a+b; } }
        );
    }
    
    static void SUB(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a-b; } }
        );
    }
    
    static void MUL(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a*b; } }
        );
    }
    
    static void DIV(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a/b; } }
        );
    }
    
    static void MOD(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        calculate(rvm, new Operation() { 
            public double op(double a, double b) { return a%b; } }
        );
    }
}
