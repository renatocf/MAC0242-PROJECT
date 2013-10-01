package exception;

/**
 * Exception used inside RVM launched when the argument type 
 * does not correspond with the command - Example: operation
 * ADD for non-numeric stackable arguments.
 * @author Renato Cordeiro Ferreira
 * @see    RVM
 */
public final class InvalidOperationException extends Exception 
{
    /** 
     * Default constructor.
     */
    public InvalidOperationException (int line)
    { super("Invalid Operation! Line " + line + "\n"); }
}