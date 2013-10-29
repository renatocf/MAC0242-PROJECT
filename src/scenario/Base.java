package scenario;

/**
 * <b>Scenario - Base</b><br>
 * Main target to the crystals of 
 * another player.
 */
public class Base implements Scenario 
{
    public int takeDamage(int damage) 
    {
        // Bases cannot be destroyed...
        return 0;
    }
    
    public int getHP() { return 42; }
    
    public String toString() { return "(ß) Base"; }
}
