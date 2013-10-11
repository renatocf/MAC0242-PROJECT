package robot;

// Libraries
import exception.*;
import stackable.*;
import parameters.*;
import arena.Terrain;
import stackable.item.*;

/**
 * Assembly functions - class Analysis.
 * Provides the funcions for checking
 * items in a terrain.
 * 
 * @author Renato Cordeiro Ferreira
 * @author Vinícius Silva
 * @see Function
 * @see RMV
 */
final public class Analysis
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
        
        if(Verbosity.v)
        { 
            String pre = "    [ITEM] ";
            String itm = (item != null) ? item.toString() : "NONE";
            Verbosity.debug(pre + itm); 
        }
        
        // Return Num(0) if no item is avaiable.
        if(item == null) rvm.DATA.push(new Num(0));
        else             rvm.DATA.push(item);
    }
    
    
    static void SEEK(RVM rvm)
     throws StackUnderflowException,
               WrongTypeException,
               InvalidOperationException
    {
        Stackable ar;
        Stackable stk;
        
        int cont = 0;
        
        try { stk = rvm.DATA.pop(); }
        catch (Exception e) {
            throw new StackUnderflowException();
        }
        try { ar = rvm.DATA.pop(); }
        catch (Exception e) {
            throw new StackUnderflowException();
        }
        
     
        if(!(ar instanceof Around))
            throw new WrongTypeException("Around");
        
        Around a = (Around) ar;  
        String s;
        int index;
        
        
        if(stk  instanceof Text)
        {
            s = ((Text)stk).getText();
            index = 0;
        }
        else if(stk instanceof stackable.item.Item)
        {
            s = stk.getClass().getName();
            index = 1;
        }
        else throw new WrongTypeException("Around");
        
        
        
        for(int i = a.matrix[0].length - 1; i >= 0; i--)
        {
            if(a.matrix[index][i]!= null && s.equals(a.matrix[index][i]))
            {
                if(i<7)
                {
                    rvm.DATA.push(new Direction(0, i));
                    rvm.DATA.push(new Num(1));    
                }
                else
                {
                    rvm.DATA.push(new Direction(1, i));
                    rvm.DATA.push(new Direction(0, i));
                    rvm.DATA.push(new Num(2));                        
                }
                cont ++;
            }
        }
        
        
        rvm.DATA.push(new Num(cont));
        
        if(Verbosity.v)
        { 
            String pre = "    [SEEK] ";
            String arnd = (a != null)   ? "Pop the around correctly: " : "NONE";
            String stack  = (a != null) ? stk.toString()               : "NONE";
            Verbosity.debug(pre + arnd + stack);
            
        }
    }
}
