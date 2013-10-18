package scenario;

public class Water implements Scenario 
{
    public int takeDamage(int damage) 
    {
        // Water cannot be destroyed...
        return 0;
    }
    
    public int getHP() { return 42; }
    
    public String toString() { return "(≈) Water"; }
}
