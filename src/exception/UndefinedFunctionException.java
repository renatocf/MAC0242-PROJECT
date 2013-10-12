package exception;

/**
 * Exception used inside RVM to declare when there is no
 * of the required label defined in the code.
 * @author Renato Cordeiro Ferreira
 * @see    robot.RVM
 */
public final class UndefinedFunctionException extends Exception 
{
    /** 
     * Constructor with error message indicating the 
     * position, within the code, of the error.
     */
    public UndefinedFunctionException (int pos) 
    { super("Undefined function in position " + pos + ".\n"); }
    
    /** 
     * Constructor eith error message indicating which
     * function was unknown.
     */
    public UndefinedFunctionException (String function) 
    { super("Undefined function: " + function + ".\n"); }
}
