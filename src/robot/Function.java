package robot;

import java.util.Vector;
import stackable.*;
import exception.*;

public class Function
{
    private int PC;
    private Stack DATA;
    private Vector <Stackable> RAM;
    
    Function(Stack stack, Vector <Stackable> ram)
    {
        this.PC = pc;
        this.DATA = stack;
        this.RAM  = ram;
    }
    
    void call(String met, Stackable arg) 
        throws StackUnderflowException, 
               OutOfBoundsException,
               WrongTypeException
    {
        // IO functions
        if(met.equals("PRN" )) { PRN ();    }
        
        // Stack functions
        else if(met.equals("POP" )) { POP ();    }
        else if(met.equals("PUSH")) { PUSH(arg); }
        else if(met.equals("DUP" )) { DUP ();    }
        
        // Arithmetic functions
        else if(met.equals("ADD" )) { ADD ();    }
        else if(met.equals("SUB" )) { SUB ();    }
        else if(met.equals("MUL" )) { MUL ();    }
        else if(met.equals("DIV" )) { DIV ();    }
        else if(met.equals("MOD" )) { MOD ();    }
        
        // Memory functions
        else if(met.equals("STO" )) { STO ();    }
        else if(met.equals("RCL" )) { RCL ();    }
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
    
    void DUP() throws StackUnderflowException
    {
      Stackable st = DATA.pop(); 
      DATA.push(st);
      DATA.push(st);
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
    
    void STO(int position)
        throws OutOfBoundsException,
               StackUnderflowException
    {
        RAM.set(position, DATA.pop());
    }
    
    void RCL(int position) 
        throws OutOfBoundsException,
               StackUnderflowException
    {
        DATA.push(RAM.get(position));
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
            Num num1 = new Num(arg1);
            Num num2 = new Num(arg2);
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
            Num num1 = new Num(arg1);
            Num num2 = new Num(arg2);
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
            Num num1 = new Num(arg1);
            Num num2 = new Num(arg2);
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
            Num num1 = new Num(arg1);
            Num num2 = new Num(arg2);
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
            Num num1 = new Num(arg1);
            Num num2 = new Num(arg2);
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
            Num num1 = new Num(arg1);
            Num num2 = new Num(arg2);
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
