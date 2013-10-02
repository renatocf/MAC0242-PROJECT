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
     * Assembly funcion EQ. 
     * Makes the correspondent funcion 
     * comparison between the two elements
     * int the top of the main stack.
     */
    void EQ() throws WrongTypeException,
                     StackUnderflowException
    {
        Num yes = new Num(1);
        Num no = new Num(0);
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            if(num1.getNumber() == num2.getNumber())
            {
               DATA.push(yes);
            }
            else
            {
               DATA.push(no); 
            }
        }
    }
    
    void GT() throws StackUnderflowException,
                     WrongTypeException
    {
        Num yes = new Num(1);
        Num no = new Num(0);
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            if(num1.getNumber() < num2.getNumber())
            {
                DATA.push(yes);
            }
            else
            {
                DATA.push(no);
            }
        }
    }
    
    
    void GE() throws StackUnderflowException,
                     WrongTypeException
    {
        Num yes = new Num(1);
        Num no = new Num(0);
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            if(num1.getNumber() <= num2.getNumber())
            {
                DATA.push(yes);
            }
            else
            {
                DATA.push(no);
            }
        }
    }
    
    
    void LT() throws StackUnderflowException,
                     WrongTypeException
    {
        Num yes = new Num(1);
        Num no = new Num(0);
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            if(num1.getNumber() > num2.getNumber())
            {
                DATA.push(yes);
            }
            else
            {
                DATA.push(no);
            }
        }
    }
    
    
    void LE() throws StackUnderflowException,
                     WrongTypeException
    {
        Num yes = new Num(1);
        Num no = new Num(0);
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            if(num1.getNumber() >= num2.getNumber())
            {
                DATA.push(yes);
            }
            else
            {
                DATA.push(no);
            }
        }
    }
    
    void NE() throws WrongTypeException,
                     StackUnderflowException
    {
        Num yes = new Num(1);
        Num no = new Num(0);
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            if(num1.getNumber() != num2.getNumber())
            {
               DATA.push(yes);
            }
            else
            {
               DATA.push(no); 
            }
        }
    }
}
