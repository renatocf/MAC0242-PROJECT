package exception;

/**
 * Exception used inside RVM launched when going out of bounds 
 * inside the stack - Example: requiring 2 arguments when there
 * is only one in the stack).
 * @author Renato Cordeiro Ferreira
 * @see    robot.RVM
 */
public final class SegmentationFaultException extends Exception 
{
    /** 
     * Default constructor.
     */
    public SegmentationFaultException ()
    { super("Segmentation Fault! "); }
}
