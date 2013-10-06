package stackable;

public class Num implements Stackable
{
    private final double num;
    
    public Num(double num)
    {
        this.num = num;
    }
    
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
