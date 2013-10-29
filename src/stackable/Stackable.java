package stackable;

/** 
 * <b>Stackable</b><br>
 * Simple interface to mark an element
 * as being stackable (and thus being 
 * able to be put inside a robot main
 * stack).
 */
public interface Stackable 
{
    /** 
     * General function to print, in a more legible
     * format, the stackable. It is used mainly
     * with debug purposes.
     * @return String representing the stackable.
     */
    public String toString();
}
