package exception;

public final class OutOfBoundsException extends Exception 
{
    /** 
     * Default constructor.
     */
    public OutOfBoundsException (int line)
    { super("Out of Bounds!\n"); }
}
