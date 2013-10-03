package robot.function;

// Default libraries
import java.util.Vector;

//Libraries
import stackable.*;
import exception.*;

public class Function
{
    // Constructor arguments
    private int PC = 0;
    private Stack DATA;
    private Vector <Stackable> RAM;
    
    // Functions objects
    private IO    io;
    private Mem   mem;
    private Stk   stk;
    private Arit  arit;
    private Tests tests;
    
    /** 
     * Class constructor (without PC)
     * @param Virtual Machine's Main stack
     * @param Virtual Machine's internal memory
     */
    public Function(Stack stack, Vector<Stackable> ram)
    {
        this.DATA = stack;
        this.RAM  = ram;
        
        this.io    = new IO    (DATA);
        this.mem   = new Mem   (DATA, RAM);
        this.stk   = new Stk   (DATA);
        this.arit  = new Arit  (DATA);
        this.tests = new Tests (DATA);
    }
    
    /** 
     * Class constructor (with PC)
     * @param Virtual Machine's Main stack
     * @param Virtual Machine's internal memory
     * @param Virtual Machine's program counter
     */
    public Function(Stack stack, Vector<Stackable> ram, int pc)
    {
        this(stack, ram);
        this.PC = pc;
    }
    
    /** 
     * Selector for the function to be called
     * @param String with the name of the function
     * @param Argument of the assembly method
     */
    public void call(String met, Stackable arg) 
        throws StackUnderflowException, 
               OutOfBoundsException,
               WrongTypeException
    {
        // IO functions
        if(met.equals("PRN" )) { io.PRN ();    }
        
        // Stack functions
        else if(met.equals("POP" )) { stk.POP  ();    }
        else if(met.equals("PUSH")) { stk.PUSH (arg); }
        else if(met.equals("DUP" )) { stk.DUP  ();    }
        
        // Arithmetic functions
        else if(met.equals("ADD" )) { arit.ADD ();    }
        else if(met.equals("SUB" )) { arit.SUB ();    }
        else if(met.equals("MUL" )) { arit.MUL ();    }
        else if(met.equals("DIV" )) { arit.DIV ();    }
        else if(met.equals("MOD" )) { arit.MOD ();    }
        
        // Memory functions
        else if(met.equals("STO" )) { mem.STO  (arg); }
        else if(met.equals("RCL" )) { mem.RCL  (arg); }
        
        // Tests functions
        else if(met.equals("EQ"  )) { tests.EQ ();    }
        else if(met.equals("GT"  )) { tests.GT ();    }
        else if(met.equals("GE"  )) { tests.GE ();    }
        else if(met.equals("LT"  )) { tests.LT ();    }
        else if(met.equals("LE"  )) { tests.LE ();    }
        else if(met.equals("NE"  )) { tests.NE ();    }
    }
}
