package robot.function;

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
public class Tests
{
    final private Stack DATA;

    /**
     * Class constructor. 
     * Receives a handle to the main stack.
     * 
     * @param Stack Data
     */
    Tests(Stack DATA)
    {
        this.DATA = DATA;
    }
    
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
    void compare(Cmp cmp) throws WrongTypeException,
                          StackUnderflowException
    {
        // Default answers
        Num no = new Num(0), yes = new Num(1);
           
        // Stores the arguments to be tested
        Stackable arg1 = DATA.pop(), arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            // Downcasts to number if the type is correct
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            
            // Result of the comparison
            boolean res = cmp.cmp(num1.getNumber(), num2.getNumber());
            
            // Push true or false accordingly to the comparison
            /* TODO: res ? DATA.push(yes) : DATA.push(no); */
            if(res) { DATA.push(yes); } else { DATA.push(no); }
        }
    }
    
    void EQ() throws WrongTypeException,
                     StackUnderflowException
    {
        compare(new Cmp() {
            public boolean cmp(double a, double b) { return a == b; } }
        );
    }
    
    void GT() throws StackUnderflowException,
                     WrongTypeException
    {
        compare(new Cmp() {
            public boolean cmp(double a, double b) { return a > b; } }
        );
    }
    
    void GE() throws StackUnderflowException,
                     WrongTypeException
    {
        compare(new Cmp() {
            public boolean cmp(double a, double b) { return a >= b; } }
        );
    }
    
    void LT() throws StackUnderflowException,
                     WrongTypeException
    {
        compare(new Cmp() {
            public boolean cmp(double a, double b) { return a < b; } }
        );
    }
    
    void LE() throws StackUnderflowException,
                     WrongTypeException
    {
        compare(new Cmp() {
            public boolean cmp(double a, double b) { return a <= b; } }
        );
    }
    
    void NE() throws WrongTypeException,
                     StackUnderflowException
    {
        compare(new Cmp() {
            public boolean cmp(double a, double b) { return a != b; } }
        );
    }
}
