package robot.function;

import java.util.Vector;
import stackable.*;
import exception.*;

public class Function
{
    private int PC = 0;
    private Stack DATA;
    private Vector <Stackable> RAM;
    
    private IO    io    = new IO    (DATA);
    private Stk   stk   = new Stk   (DATA);
    private Arit  arit  = new Arit  (DATA);
    private Tests tests = new Tests (DATA);
    
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
        if(met.equals("PRN" )) { io.PRN ();    }
        
        // Stack functions
        else if(met.equals("POP" )) { stk.POP ();    }
        else if(met.equals("PUSH")) { stk.PUSH(arg); }
        else if(met.equals("DUP" )) { stk.DUP ();    }
        
        // Arithmetic functions
        else if(met.equals("ADD" )) { arit.ADD ();    }
        else if(met.equals("SUB" )) { arit.SUB ();    }
        else if(met.equals("MUL" )) { arit.MUL ();    }
        else if(met.equals("DIV" )) { arit.DIV ();    }
        else if(met.equals("MOD" )) { arit.MOD ();    }
        
        // Memory functions
        else if(met.equals("STO" )) { STO (arg); }
        else if(met.equals("RCL" )) { RCL (arg); }
              // ???? Temos que passar o endereço como int, mas ele é do tipo Stackable
        
        // Tests functions
        else if(met.equals("EQ" )) { tests.EQ(); }
        else if(met.equals("GT" )) { tests.GT(); }
        else if(met.equals("GE" )) { tests.GE(); }
        else if(met.equals("LT" )) { tests.LT(); }
        else if(met.equals("LE" )) { tests.LE(); }
        else if(met.equals("NE" )) { tests.NE(); }
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
}
