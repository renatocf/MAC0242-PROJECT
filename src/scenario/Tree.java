package scenario;

// Libraries
import players.Player;

/**
 * <b>Scenario - Tree</b><br>
 * Just an obstacle (that may be destroyed).
 */
public class Tree implements Scenario 
{
    private int HP = 7; // Vinícius: I think that the trees should be stronger than the robot
    public int takeDamage(int damage)
    {
        return HP -= damage;
    }
    
    public int getHP() { return this.HP; }
    
    public String toString() { return "(♣) Tree"; }
    
    public Player getTeam() { return Player.Nature; }
}
