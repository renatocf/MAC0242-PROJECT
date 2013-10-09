package robot;

// Libraries
import stackable.*;
import stackable.item.*;
import arena.Terrain;
import exception.*;

/**
 * Assembly functions - class ITEM.
 * Provides the funcions for checking
 * items in a terrain.
 * 
 * @author Renato Cordeiro Ferreira
 * @author Vinícius Silva
 * @see Function
 * @see RMV
 */
final public class Item
{
    /**
     * Assembly funcion ITEM. 
     * Takes out the top of the main stack,
     * checks if it's a terrain. If it has
     * an item, pushes it. In the other case,
         * pushes 0 in the top of the stack.
     */
    static void ITEM(RVM rvm)
        throws StackUnderflowException,
               WrongTypeException
    {
        Stackable stk;
        
        try { stk = rvm.DATA.pop(); }
        catch (Exception e) {
            throw new StackUnderflowException();
        }
        
        if(!(stk instanceof Terrain))
            throw new WrongTypeException("Terrain");
        
        // Get terrain's item
        Terrain t = (Terrain) stk;
        stackable.item.Item item = t.getItem();
        
        // Return Num(0) if no item is avaiable.
        if(item == null) rvm.DATA.push(new Num(0));
        else             rvm.DATA.push(item);
    }
}
