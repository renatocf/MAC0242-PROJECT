package scenario;

public class Base implements Scenario 
{
    public int takeDamage(int damage) 
    {
        // Bases cannot be destroyed...
        return 0;
    }
}
