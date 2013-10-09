package scenario;

public class Rock implements Scenario 
{
    private int HP = 10;
    public int takeDamage(int damage)
    {
        return HP -= damage;
    }
}
