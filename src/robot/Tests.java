package robot;

// Libraries
import stackable.*;
import exception.*;

/**
 * Assembly functions - class Tests.
 * Provides the funcions for making 
 * boolean comparisons between elements
 * inside the stack of the virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Function
 * @see RMV
 */
final public class Tests
{
    /** 
     * Interface for compartison operations,
     * Dummy interface with the aim of being a 
     * model for all the comparison operations.
     */
    private interface Cmp
    {
        boolean cmp(double a, double b);
    }
    
    /**
     * General-model funcion compare.
     * Makes the correspondent funcion 
     * comparison between the two elements
     * int the top of the main stack.
     */
    static void compare(RVM rvm, Cmp cmp) 
        throws WrongTypeException, StackUnderflowException
    {
        // Default answers
        Num no = new Num(0), yes = new Num(1);
           
        // Stores the arguments to be tested
        Stackable arg1 = rvm.DATA.pop(), arg2 = rvm.DATA.pop();
        
        if(arg1 instanceof Num && arg2 instanceof Num)
        {
            // Downcasts to number if the type is correct
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            
            // Result of the comparison
            boolean res = cmp.cmp(num1.getNumber(), num2.getNumber());
            
            // Push true or false accordingly to the comparison
            /* TODO: res ? DATA.push(yes) : DATA.push(no); */
            if(res) { rvm.DATA.push(yes); } else { rvm.DATA.push(no); }
        }
    }
    
    static void EQ(RVM rvm) throws WrongTypeException,
                     StackUnderflowException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a == b; } }
        );
    }
    
    static void GT(RVM rvm) 
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a > b; } }
        );
    }
    
    static void GE(RVM rvm) 
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a >= b; } }
        );
    }
    
    static void LT(RVM rvm)
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a < b; } }
        );
    }
    
    static void LE(RVM rvm)
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a <= b; } }
        );
    }
    
    static void NE(RVM rvm)
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a != b; } }
        );
    }
}
