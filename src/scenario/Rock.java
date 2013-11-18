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
    protected boolean damageTaken = false;
    
    public int takeDamage(int damage)
    {   
        damageTaken = true;
        return HP -= damage;
    }
    
    public int getHP() { return HP; }
    
    public String toString() { return "(O) Rock"; }
    
    public boolean sufferedDamage()
    {
        if(damageTaken)
        {
            damageTaken = false;
            return true;
        }
        return false;
    }
    
    public Player getTeam() { return Player.Nature; }
    
    public String name() { return "ROCK"; }
}
