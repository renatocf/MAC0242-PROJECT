package stackable;

public final class StackUnderflowException extends Exception 
{
    public StackUnderflowException () 
    { super("Stack Underflow\n"); }
}
