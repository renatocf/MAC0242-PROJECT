package stackable;

/**
 * Exception used inside Stack for indicating Stack Underflow.
 * @author Renato Cordeiro Ferreira
 * @see    Stack
 */
public final class StackUnderflowException extends Exception 
{
    /** 
     * Default constructor.
     */
    public StackUnderflowException () 
    { super("Stack Underflow\n"); }
}
