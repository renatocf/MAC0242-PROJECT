package robot;

// Libraries
import  java.util.Vector;
import  java.util.HashMap;

// Internal libraries
import  stackable.*;

/**
 * Main class with the constructor of the robot and its data.
 * @author Renato Cordeiro Ferreira
 */
public class RVM 
{
    Vector  <Command>   PROG;
    Vector  <Stackable> DATA = new Vector <Stackable>();
    Vector  <Stackable> RAM  = new Vector <Stackable>();
    Vector  <Integer>   CTRL = new Vector <Integer>  ();
    HashMap <String, Integer> LABEL = new HashMap <String, Integer>();
    int PC = 0;
    
    /**
     * Class constructor specifying a 'program' (vector of
     * objects of the class Command) to the RVM.
     * 
     * @param PROG    Vector of objects of the class Command
     * @see   Command 
     */
    public RVM(Vector <Command> PROG) { this.PROG = PROG; }
    
    /**
     * Setter method created to upload a new 'program' (vector
     * of object of the class Command) in the RVM.
     * 
     * @param PROG    Vector of objects of the class Command
     * @see   Command
     */
    public void upload(Vector <Command> PROG) { this.PROG = PROG; }
}
