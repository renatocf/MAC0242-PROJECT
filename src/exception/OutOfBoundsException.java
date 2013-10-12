package exception;

/**
 * Exception used inside RVM to declare when we exceed
 * the access to a vector,
 * @author Renato Cordeiro Ferreira
 * @see    robot.RVM
 */
public final class OutOfBoundsException extends Exception 
{
    /** 
     * Default constructor.
     */
    public OutOfBoundsException ()
    { super("Out of Bounds!\n"); }
}
