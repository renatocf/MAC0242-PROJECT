package robot.function;

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
public class Arit
{
    final private Stack DATA;
    
    /**
     * Class constructor. 
     * Receives a handle to the main stack.
     * 
     * @param Stack Data
     */
    Arit(Stack DATA)
    {
        this.DATA = DATA;
    }
    
    /** 
     * Interface for arithmetic operations,
     * Dummy interface with the aim of being a 
     * model for all the arithmetic operations.
     */
    final private interface Operation 
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
    final private void calculate(Operation op) 
        throws WrongTypeException,
               StackUnderflowException
    {
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num a = (Num) arg1, b = (Num) arg2;
            double ans = op.op(b.getNumber(), a.getNumber());
            DATA.push(new Num(ans));
        }
        else { throw new WrongTypeException("Num"); }
    }

    public void ADD() throws WrongTypeException,
                      StackUnderflowException
    {
        calculate(new Operation() { 
            public double op(double a, double b) { return a+b; } }
        );
    }
    
    public void SUB() throws WrongTypeException,
                      StackUnderflowException
    {
        calculate(new Operation() { 
            public double op(double a, double b) { return a-b; } }
        );
    }
    
    public void MUL() throws WrongTypeException,
                      StackUnderflowException
    {
        calculate(new Operation() { 
            public double op(double a, double b) { return a*b; } }
        );
    }
    
    public void DIV() throws WrongTypeException,
                      StackUnderflowException
    {
        calculate(new Operation() { 
            public double op(double a, double b) { return a/b; } }
        );
    }
    
    public void MOD() throws WrongTypeException,
                      StackUnderflowException
    {
        calculate(new Operation() { 
            public double op(double a, double b) { return a/b; } }
        );
    }
}
