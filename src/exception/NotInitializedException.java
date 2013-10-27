package exception;

/**
 * Exception used inside RVM to warn when the variable
 * has not been previously initialized.
 * @author Karina Awoki
 * @author Renato Cordeiro
 * @author Vinicius Silva
 * @see    robot.Var
 */
public final class NotInitializedException extends Exception 
{
    /** 
     * Default constructor.
     */
    public NotInitializedException(String var) 
    { super("Variable " + var + " not initialized.\n"); }
}
