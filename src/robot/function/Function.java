package robot.function;

// Default libraries
import java.util.Vector;
import java.util.HashMap;

//Libraries
import robot.*;
import stackable.*;
import exception.*;

public class Function
{
    // Constructor arguments
    private Integer PC = 0;
    private Stack DATA;
    private Vector<Command> PROG;
    private Vector <Stackable> RAM;
    private HashMap<String, Integer> LABEL;
    
    // Functions objects
    private IO    io;
    private Mem   mem;
    private Stk   stk;
    private Arit  arit;
    private Jumps jumps;
    private Tests tests;
    
    /** 
     * Class constructor (without PC)
     * @param Virtual Machine's Main stack
     * @param Virtual Machine's internal memory
     */
    //public Function(Stack stack, Vector<Stackable> ram)
    //{
    //    
    //}
    
    /** 
     * Class constructor (with PC)
     * @param Virtual Machine's Main stack
     * @param Virtual Machine's internal memory
     * @param Virtual Machine's program counter
     */
    public Function(Stack stack, Vector<Stackable> ram, 
                    Vector<Command> prog, Integer pc, 
                    HashMap<String, Integer> label)
    {
        this.DATA  = stack;
        this.RAM   = ram;
        this.PROG  = prog; 
        this.PC    = pc; 
        this.LABEL = label;
        
        this.io    = new IO    (DATA);
        this.mem   = new Mem   (DATA, RAM);
        this.stk   = new Stk   (DATA);
        this.arit  = new Arit  (DATA);
        this.tests = new Tests (DATA);
        this.jumps = new Jumps (PROG, DATA, PC, LABEL);
        
        if(this.PROG == null) { System.out.println("Este this está null antes");}
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
        else if(met.equals("POP" )) { stk.POP   ();    }
        else if(met.equals("PUSH")) { stk.PUSH  (arg); }
        else if(met.equals("DUP" )) { stk.DUP   ();    }
        
        // Arithmetic functions
        else if(met.equals("ADD" )) { arit.ADD  ();    }
        else if(met.equals("SUB" )) { arit.SUB  ();    }
        else if(met.equals("MUL" )) { arit.MUL  ();    }
        else if(met.equals("DIV" )) { arit.DIV  ();    }
        else if(met.equals("MOD" )) { arit.MOD  ();    }
        
        // Memory functions
        else if(met.equals("STO" )) { mem.STO   (arg); }
        else if(met.equals("RCL" )) { mem.RCL   (arg); }
        
        // Tests functions
        else if(met.equals("EQ"  )) { tests.EQ  ();    }
        else if(met.equals("GT"  )) { tests.GT  ();    }
        else if(met.equals("GE"  )) { tests.GE  ();    }
        else if(met.equals("LT"  )) { tests.LT  ();    }
        else if(met.equals("LE"  )) { tests.LE  ();    }
        else if(met.equals("NE"  )) { tests.NE  ();    }
        
        // Jumps functions
        else if(met.equals("JMP" )) { jumps.JMP (arg); }
        else if(met.equals("JIT" )) { jumps.JIT (arg); }
        else if(met.equals("JIF" )) { jumps.JIF (arg); }
    }
}
