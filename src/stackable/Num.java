package stackable;

public class Num implements Stackable
{
    private final double num;
    
    public Num(double num)
    {
        this.num = num;
    }

    public double getNumber() { return this.num; }
    public String toString()  { return String.valueOf(this.num); }
}
