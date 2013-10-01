package exception;

/**
 * Exception used inside RVM to declare when there is no
 * of the required label defined in the code.
 * @author Karina Awoki
 * @author Renato Cordeiro
 * @author Vinicius Silva
 * @see    Function
 */
public final class WrongTypeException extends Exception 
{
    /** 
     * Default constructor.
     */
    public WrongTypeException (String type) 
    { super("Wrong Type! Required " + type + "\n"); }
}
