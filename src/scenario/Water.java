package scenario;

public class Water implements Scenario 
{
    public int takeDamage(int damage) 
    {
        // Bases cannot be destroyed...
        return 0;
    }
}
