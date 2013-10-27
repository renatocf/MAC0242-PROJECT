package robot;

// Libraries
import stackable.*;
import exception.*;
import parameters.*;
import stackable.item.*;

/**
 * <b>Assembly functions - class Tests</b><br>
 * Provides the funcions for making 
 * boolean comparisons between elements
 * inside the stack of the virtual Machine
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Tests
{
    // No instances of this class allowed
    private Tests() {} 
    
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
     * Assembly funcion EQ. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack.
     * <p>
     * If numerical, checks if their value are
     * the same. In the other case, Checks if
     * they have the same type.
     * 
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
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
        else if(arg1 instanceof Direction && arg2 instanceof Direction)
        {
            // Downcasts to direction if the type is correct
            Direction d1 = (Direction) arg1, d2 = (Direction) arg2;
            
            // Gets the direction
            String s1 = d1.toString(), s2 = d2.toString();
            
            // Push true or false accordingly to the comparison
            if( s1.equals(s2)) 
                rvm.DATA.push(yes);
            else rvm.DATA.push(no);
            
            if(Verbosity.v)
            {
                String pre = "    [EQ] "; 
                if(s1.equals(s2))
                {
                    Verbosity.debug(pre + "YES");
                    Verbosity.debug(pre + "stack: " + s1); 
                    Verbosity.debug(pre + "stack: " + s2);
                }
                else
                {
                    Verbosity.debug(pre + "NO");
                    Verbosity.debug(pre + "stack: " + s1); 
                    Verbosity.debug(pre + "stack: " + s2);
                }
            }
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
    
    /**
     * Assembly funcion NEQ. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack.
     * <p>
     * If numerical, checks if their value are
     * different. In the other case, Checks if
     * they have different types.
     * 
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
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
            boolean res = num1.getNumber() != num2.getNumber();
            
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
     * General-model funcion compare. <br>
     * Makes the correspondent funcion 
     * comparison between the two elements
     * int the top of the main stack.
     * 
     * @param  rvm Virtual Machine
     * @param  cmp Anonymous inner class that
     *             implements Cmp interface
     * 
     * @throws WrongTypeException
     * @throws StackUnderflowException
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
    
    /**
     * Assembly funcion GT. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack to 
     * see if the top is greater than the 
     * second.
     *
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void GT(RVM rvm) 
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a > b; } }
        );
    }
    
    /**
     * Assembly funcion GE. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack to 
     * see if the top is greater or equal 
     * than the second.
     *
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void GE(RVM rvm) 
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a >= b; } }
        );
    }
    
    /**
     * Assembly funcion LT. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack to 
     * see if the top is lower than the second.
     *
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void LT(RVM rvm)
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a < b; } }
        );
    }
    
    /**
     * Assembly funcion LE. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack to 
     * see if the top is lower or equal than 
     * the second.
     *
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void LE(RVM rvm)
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a <= b; } }
        );
    }
}
