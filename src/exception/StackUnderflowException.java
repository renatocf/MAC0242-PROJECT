package exception;

/**
 * Exception used inside Stack for indicating Stack Underflow.
 * @author Renato Cordeiro Ferreira
 * @see    robot.Stk
 */
public final class StackUnderflowException extends Exception 
{
    /** 
     * Default constructor.
     */
    public StackUnderflowException () 
    { super("Stack Underflow\n"); }
}
