package stackable;

public class Num implements Stackable
{
    private final double num;
    
    public Num(double num)
    {
        this.num = num;
    }
    
    public boolean looksLikeInteger()
    {
      if(this.num % 1 == 0.0)
        return true;
        return false;
    }
       
    public boolean looksLikeNumber()
    {
      
      return true;
    }

    public double getNumber() { return this.num; }
    public String toString()  { return String.valueOf(this.num); }
}
