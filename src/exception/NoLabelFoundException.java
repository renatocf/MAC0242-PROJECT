package exception;

/**
 * Exception used inside RVM to declare when there is no
 * of the required label defined in the code.
 * @author Renato Cordeiro Ferreira
 * @see    RVM
 */
public final class NoLabelFoundException extends Exception 
{
    /** 
     * Default constructor.
     */
    public NoLabelFoundException (int line) 
    { super("No Label Found! Line " + line + "\n"); }
}
