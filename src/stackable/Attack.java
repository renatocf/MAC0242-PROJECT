package stackable;

// Libraries
import exception.*;

/**
 * <b>Stackable - Attack</b><br>
 * Store one of the valid types of attacks 
 * supported by the game, encapsulating 
 * them in a stackable.
 *
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 */
public class Attack implements Stackable
{
    private final String attackType;
    
    /** 
     * Default constructor.<br>
     * If the attack is not supported, 
     * throws an exception.
     * @param attack String with the attack
     *               type (must be a supported
     *               type of attack).
     * @throws InvalidOperationException
     */
    public Attack(String attack) 
        throws InvalidOperationException
    {
        switch(attack)
        {
            case "MELEE" : break;
            case "RANGED": break;
            default: 
                throw new InvalidOperationException(attack);
        }
        attackType = attack;
    }
    
    /** 
     * Getter for the string with the 
     * attack type.
     * @return String with the attack type.
     */
    public String getAttack()
    {
        return this.attackType;
    }
    
    public String toString()
    {
        return getAttack();
    }
}
