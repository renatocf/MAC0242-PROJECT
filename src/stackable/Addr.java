package stackable;

/**
 * <b>Stackable - Addr</b><br>
 * This class packs a number in an 
 * imutable way to be used as an 
 * address for jumps and function
 * calls. Its structure is very 
 * similar to Num's.
 * 
 * @author Renato Cordeiro Ferreira
 */
public class Addr implements Stackable
{
    private final int address;
    
    /** 
     * Default constructor.
     * @param address Integer with the 
     *                value to be packed
     *                inside this container
     *                class.
     */
    public Addr(int address)
    {
        this.address = address;
    }
    
    /** 
     * Getter for the integer packed inside the
     * class.
     * @return String with the text.
     */
    public int getAddress()
    { 
        return this.address;
    }
    
    public String toString() 
    {
        return String.valueOf(address);
    }
}
