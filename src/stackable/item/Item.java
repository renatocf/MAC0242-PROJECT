package stackable.item;

// Libraries
import stackable.Stackable;

/** 
 * <b>Item</b><br>
 * Simple interface to mark an element
 * as being an item, extending the stackable
 * interface. 
 * <p>
 * Thus, every item can be stored both in the 
 * robot virtual machine (RVM) main stack (as 
 * a 'software concept') and inside the robot
 * itself (as a 'real' catchable item).
 */
public interface Item extends Stackable { }
