package robot;
import stackable.*;

/**
 * <b>Commands</b><br>
 * Generates a triple [ function, argument, label ] from
 * strings for being used to process a robot's program. 
 *
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinícius Silva
 * @see Ctrl
 */
public class Command
{
    final private String    function;
    final private Stackable attribute;
    final private String    label;

    /**
     * Default constructor. <br>
     * Stores a triple with assembly function, attribute
     * stackable and label. If one of them does not exist,
     * a null reference must be passed.
     * <p> 
     * Once created, the command cannot be changed.
     *
     * @param func String with an assembly function 
     *             (or null, if none avaiable)
     * @param attr String with the function's attribute
     *             (or null, if none avaiable or required)
     * @param lbl  String with a specified label
     *             (or null, if none avaiable)
     */
    public Command(String func, Stackable attr, String lbl)
    {
        this.function  = func;
        this.attribute = attr;
        this.label     = lbl;
    }

    /** @return String with the function name. */
    public String    getFunction  () { return this.function;  }
    
    /** @return String with the attribute of the line. */
    public Stackable getAttribute () { return this.attribute; }
    
    /** @return String with a label */
    public String    getLabel     () { return this.label;     }

    public String toString()
    {
        return this.label + ": " + this.function + " " + this.attribute;
    }
}
