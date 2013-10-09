package scenario;

public class Tree implements Scenario 
{
    private int HP = 7;
    public int takeDamage(int damage)
    {
        return HP -= damage;
    }
}
