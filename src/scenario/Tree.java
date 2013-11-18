package scenario;

// Libraries
import players.Player;

/**
 * <b>Scenario - Tree</b><br>
 * Just an obstacle (that may be destroyed).
 */
public class Tree implements Scenario 
{
    private int HP = 7;
    protected boolean damageTaken = false;

    public int takeDamage(int damage)
    {
        damageTaken = true;
        return HP -= damage;
    }
    
    public int getHP() { return this.HP; }
    
    public String toString() { return "(♣) Tree"; }
    
    public Player getTeam() { return Player.Nature; }
    
    // Interface scenario
    public boolean sufferedDamage()
    {
        if(damageTaken)
        {
            damageTaken = false;
            return true;
        }
        return false;
    }
    
    public String name() { return "TREE"; }
}
