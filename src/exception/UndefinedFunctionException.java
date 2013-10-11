package exception;

/**
 * Exception used inside RVM to declare when there is no
 * of the required label defined in the code.
 * @author Renato Cordeiro Ferreira
 * @see    RVM
 */
public final class UndefinedFunctionException extends Exception 
{
    /** 
     * Default constructor.
     */
    public UndefinedFunctionException (int pos) 
    { super("Undefined function in position " + pos + ".\n"); }
    
    public UndefinedFunctionException (String function) 
    { super("Undefined function: " + function + ".\n"); }
}
