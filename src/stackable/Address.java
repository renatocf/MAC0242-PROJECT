package stackable;

public class Address implements Stackable
{
    private final int address;
    
    public Address(int address)
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
