package stackable;

/**
 * <b>Stackable - Num</b><br>
 * This class packs numbers inside a 
 * simple container, allowing them to
 * be identified as stackables.
 * 
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
public class Num implements Stackable
{
    private final double num;
    
    /** 
     * Default constructor.
     * @param num Integer or double to be
     *            packed inside this container
     *            class.
     */
    public Num(double num)
    {
        this.num = num;
    }
    
    /** 
     * Getter for the string packed inside the
     * class.
     * @return Double with the number.
     */
    public double getNumber()
    { 
        return this.num;
    }
    
    public String toString() 
    {
        // Is an integer
        if ((num == Math.floor(num)) && !Double.isInfinite(num))
            return String.valueOf( (int) this.num );
        
        // Is a floating point number
        return String.valueOf(num);
    }
}
