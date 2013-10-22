package stackable;

public class Addr implements Stackable
{
    private final int address;
    
    public Addr(int address)
    {
        this.address = address;
    }
    
    public int getAddress()
    { 
        return this.address;
    }
    
    public String toString() 
    {
        return String.valueOf(address);
    }
}
