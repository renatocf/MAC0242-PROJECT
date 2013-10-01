package robot;
import stackable.*;
import exception.*;

public class Function
{
    private Stack DATA;
    
    Function(Stack stack)
    {
        this.DATA = stack;
    }
    
    void call(String met, Stackable arg) 
        throws StackUnderflowException
    {
        // IO functions
        if(met.equals("PRN" )) { PRN ();    }
        
        // Stack functions
        else if(met.equals("POP" )) { POP ();    }
        else if(met.equals("PUSH")) { PUSH(arg); }
        
        // Arithmetic functions
        else if(met.equals("ADD" )) { ADD ();    }
    }
    
    //##############################################################
    //##                      IO FUNCTIONS                       ###
    //##############################################################
    void PRN()
    {
        try
        {
            System.out.println(DATA.pop());
        }
        catch (Exception e)
        {
            System.out.println("Empty stack!");
        }
    }

    //##############################################################
    //##                     STACK FUNCTIONS                     ###
    //##############################################################
    void PUSH(Stackable st)
    {
        DATA.push(st);
    }
    
    Stackable POP() throws StackUnderflowException
    {
        return DATA.pop();
    }
    
    //##############################################################
    //##                  ARITHMETIC FUNCTIONS                   ###
    //##############################################################
    void ADD()
    {
    //    
    //    Num arg1 = DATA.pop();
    //    Num arg2 = DATA.pop();
    //    
    //    if(Typeof.num(arg1) && Typeof.num(arg2))
    //    {
    //        arg1.getNumber();
    //        Num sum = new Num(arg1.getNumber() + arg2.getNumber());
    //        DATA.push(sum);
    //    }
    //    else { throw new WrongTypeException("Num"); }
    }
}

class Typeof
{
    static boolean num(Object obj)
    {
        return !(obj instanceof Num);
    }
}
