package scenario;

public class Tree implements Scenario 
{
    private int HP = 7; // Vinícius: I think that the trees should be stronger than the robot
    public int takeDamage(int damage)
    {
        return HP -= damage;
    }
    
    public int getHP() { return this.HP; }
    
    public String toString() { return "(♣) Tree"; }
}
