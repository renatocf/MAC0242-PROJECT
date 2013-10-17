package stackable;

import exception.*;

public class Attack implements Stackable
{
    private final String attackType;
    
    public Attack(String s) throws InvalidOperationException
    {
        switch(s)
        {
            case "MELEE" : break;
            case "RANGED": break;
            
            default: throw new InvalidOperationException(s);
        }
        attackType = s;
    }
    
    public String getAttack()
    {
        return this.attackType;
    }
    
    public String toString()
    {
        return getAttack();
    }
}
