package robot.function;

import java.util.Vector;
import stackable.*;
import exception.*;

public class Function
{
    private int PC = 0;
    private Stack DATA;
    private Vector <Stackable> RAM;
    
    private Stk stk = new Stk(DATA);
    
    Function(Stack stack, Vector <Stackable> ram)
    {
        this.DATA = stack;
        this.RAM  = ram;
    }
    
    Function(Stack stack, Vector <Stackable> ram, int pc)
    {
        this(stack, ram); this.PC = pc;
    }
    
    void call(String met, Stackable arg) 
        throws StackUnderflowException, 
               OutOfBoundsException,
               WrongTypeException
    {
        // IO functions
        if(met.equals("PRN" )) { PRN ();    }
        
        // Stack functions
        else if(met.equals("POP" )) { stk.POP ();    }
        else if(met.equals("PUSH")) { stk.PUSH(arg); }
        else if(met.equals("DUP" )) { stk.DUP ();    }
        
        // Arithmetic functions
        else if(met.equals("ADD" )) { ADD ();    }
        else if(met.equals("SUB" )) { SUB ();    }
        else if(met.equals("MUL" )) { MUL ();    }
        else if(met.equals("DIV" )) { DIV ();    }
        else if(met.equals("MOD" )) { MOD ();    }
        
        // Memory functions
        else if(met.equals("STO" )) { STO (arg); }
        else if(met.equals("RCL" )) { RCL (arg); }
              // ???? Temos que passar o endereço como int, mas ele é do tipo Stackable
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
    //##                  ARITHMETIC FUNCTIONS                   ###
    //##############################################################
    void ADD() throws WrongTypeException,
                      StackUnderflowException
    {
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num a = (Num) arg1, b = (Num) arg2;
            Num sum = new Num(b.getNumber() + a.getNumber());
            DATA.push(sum);
        }
        else { throw new WrongTypeException("Num"); }
    }
    
    void SUB() throws WrongTypeException,
                      StackUnderflowException
    {
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num a = (Num) arg1, b = (Num) arg2;
            Num sum = new Num(b.getNumber() - a.getNumber());
            DATA.push(sum);
        }
        else { throw new WrongTypeException("Num"); }
    }
    
    void MUL() throws WrongTypeException,
                      StackUnderflowException
    {
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num a = (Num) arg1, b = (Num) arg2;
            Num sum = new Num(b.getNumber() * a.getNumber());
            DATA.push(sum);
        }
        else { throw new WrongTypeException("Num"); }
    }
    
    void DIV() throws WrongTypeException,
                      StackUnderflowException
    {
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num a = (Num) arg1, b = (Num) arg2;
            Num sum = new Num(b.getNumber() / a.getNumber());
            DATA.push(sum);
        }
        else { throw new WrongTypeException("Num"); }
    }
    
    
    void MOD() throws WrongTypeException,
                      StackUnderflowException
    {
        
        Stackable arg1 = DATA.pop();
        Stackable arg2 = DATA.pop();
        
        if(arg1.looksLikeNumber() && arg2.looksLikeNumber())
        {
            Num a = (Num) arg1, b = (Num) arg2;
            Num sum = new Num(b.getNumber() % a.getNumber());
            DATA.push(sum);
        }
        else { throw new WrongTypeException("Num"); }
    }
    
    //##############################################################
    //##                      MEMORY FUNCTIONS                   ###
    //##############################################################
    
    void STO(Stackable position)
        throws OutOfBoundsException,
               StackUnderflowException
    {
        int pos;
        if(position.looksLikeNumber())
        {
            Num num = (Num) position;
            pos = (int) num.getNumber();
        }
        else throw new StackUnderflowException();
        RAM.set(pos, DATA.pop());
    }
    
    void RCL(Stackable position) 
        throws OutOfBoundsException,
               StackUnderflowException
    {
        int pos;
        if(position.looksLikeNumber()) 
        {
            Num num = (Num) position;
            pos = (int) num.getNumber();
        }
        else throw new StackUnderflowException();
        DATA.push(RAM.get(pos));
    }
    
    
    
    //##############################################################
    //##                      TESTS FUNCTIONS                    ###
    //##############################################################
    
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
