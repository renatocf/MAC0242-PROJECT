package robot;

// Default libraries
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.reflect.Method;

// Libraries
import robot.*;
import exception.*;
import stackable.*;

/**
 * Main class with the constructor of the robot and its data.
 * @author Renato Cordeiro Ferreira
 */
public class RVM 
{
    Vector  <Command>   PROG;
    Stack   DATA = new Stack();
    Vector  <Stackable> RAM  = new Vector <Stackable>();
    Vector  <Integer>   CTRL = new Vector <Integer>  ();
    HashMap <String, Integer> LABEL = new HashMap <String, Integer>();
    int PC = 0;
    
    Function asm = new Function(DATA);
    
    /**
     * Class constructor specifying a 'program' (vector of
     * objects of the class Command) to the RVM.
     * 
     * @param PROG    Vector of objects of the class Command
     * @see   Command 
     */
    public RVM(Vector <Command> PROG) 
    { 
        this.PROG = PROG; 
    }
    
    /**
     * Setter method created to upload a new 'program' (vector
     * of object of the class Command) in the RVM.
     * 
     * @param PROG    Vector of objects of the class Command
     * @see   Command
     */
    public void upload(Vector <Command> PROG) { this.PROG = PROG; }
    
    /**
     * Function responsable for executing the 'program', step by 
     * step, using the parameters f the object.
     * 
     * @throws SegmentationFaultException
     * @throws InvalidOperationException
     * @throws NoLabelFoundException
     */ 
    public void ctrl() 
        throws SegmentationFaultException, 
               InvalidOperationException, 
               StackUnderflowException,
               NoLabelFoundException
    {
        int stack = 0;
        
        //##############################################################
        //##                    CARREGA LABELS                        ##
        //##############################################################
        Command c = this.PROG.elementAt(0);
        for(int i = 0; c != null; i++)
        {
            // Carrega labels e transforma linhas 
            // só de labels em linhas com a string "0"
            if(c.getLabel() != null) 
                this.LABEL.put(c.getLabel(), i);
            c = this.PROG.elementAt(i+1);
        }
        
        //##############################################################
        //##                    EXECUTA CÓDIGOS                       ##
        //##############################################################
        
        // Carrega primeiro comando e funções
        Command com      = this.PROG.elementAt(0);
        String  function = com.getCommand();
        
        this.PC = 0; // Zera contador
        while(!function.equals("END"))
        {
            Stackable arg = com.getAttribute(); // Carrega argumento
            int line = ++this.PC;
            
            // Call function
            if(!function.equals("0")) { asm.call(function, arg); }
            
            switch(stack) // Exceptions
            {
                case -1: throw new SegmentationFaultException(line);
                case -2: throw new InvalidOperationException(line);
                case -3: throw new NoLabelFoundException(line);
                default: 
                    com      = this.PROG.elementAt(this.PC);
                    function = com.getCommand();
            }
        }//while
    }
    
    
    //##############################################################
    //##                       ASSEMBLY                           ##
    //##############################################################
    
    void ADD() { System.out.println("Adicionou"); }
}
