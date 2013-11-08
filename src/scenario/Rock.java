package scenario;

// Libraries
import players.Player;

/**
 * <b>Scenario - Rock</b><br>
 * Just an obstacle (that may be destroyed).
 */
public class Rock implements Scenario 
{
    private int HP = 10;
    public int takeDamage(int damage)
    {
        return HP -= damage;
    }
    
    public int getHP() { return HP; }
    
    public String toString() { return "(O) Rock"; }
    
    public Player getTeam() { return Player.Nature; }
    
    public String name() { return "ROCK"; }
}
