package robot;

// Libraries
import stackable.*;
import exception.*;
import parameters.*;
import stackable.item.*;

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

    static void EQ(RVM rvm) 
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
            boolean res = num1.getNumber() == num2.getNumber();
            
            // Push true or false accordingly to the comparison
            if(res) { rvm.DATA.push(yes); } else { rvm.DATA.push(no); }
        }
        else
        {
            String class1 = arg1.getClass().getName();
            String class2 = arg2.getClass().getName();
            
            if( class1.equals(class2)) 
                rvm.DATA.push(yes);
            else rvm.DATA.push(no);
            
            if(Verbosity.v)
            {
                String pre = "    [EQ] "; 
                if( arg1.getClass().equals(arg2.getClass()) )
                {
                    Verbosity.debug(pre + "YES");
                    Verbosity.debug(pre + "stack: " + class1); 
                    Verbosity.debug(pre + "stack: " + class2);
                }
                else
                {
                    Verbosity.debug(pre + "NO");
                    Verbosity.debug(pre + "stack: " + class1); 
                    Verbosity.debug(pre + "stack: " + class2);
                }
            }
        }
    }
    
    static void NE(RVM rvm)
        throws StackUnderflowException, WrongTypeException
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
            boolean res = num1.getNumber() == num2.getNumber();
            
            // Push true or false accordingly to the comparison
            if(res) { rvm.DATA.push(yes); } else { rvm.DATA.push(no); }
        }
        else
        {
            String class1 = arg1.getClass().getName();
            String class2 = arg2.getClass().getName();
            
            if( !class1.equals(class2)) 
                rvm.DATA.push(yes);
            else rvm.DATA.push(no);
            
            if(Verbosity.v)
            {
                String pre = "    [EQ] "; 
                if( !arg1.getClass().equals(arg2.getClass()) )
                {
                    Verbosity.debug(pre + "YES");
                    Verbosity.debug(pre + "stack: " + class1); 
                    Verbosity.debug(pre + "stack: " + class2);
                }
                else
                {
                    Verbosity.debug(pre + "NO");
                    Verbosity.debug(pre + "stack: " + class1); 
                    Verbosity.debug(pre + "stack: " + class2);
                }
            }
        }
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
            if(res) { rvm.DATA.push(yes); } else { rvm.DATA.push(no); }
        }
        else throw new WrongTypeException("Num");
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
}
