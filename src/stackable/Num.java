package stackable;

public class Num implements Stackable
{
    private final Number num;
    
    public Num(Number num)
    {
        this.num = num;
    }

    public Number getNumber() { return this.num; }
    public String toString()  { return this.num.toString(); }
}
