/**********************************************************************/
/* Copyright 2013 KRV                                                 */
/*                                                                    */
/* Licensed under the Apache License, Version 2.0 (the "License");    */
/* you may not use this file except in compliance with the License.   */
/* You may obtain a copy of the License at                            */
/*                                                                    */
/*  http://www.apache.org/licenses/LICENSE-2.0                        */
/*                                                                    */
/* Unless required by applicable law or agreed to in writing,         */
/* software distributed under the License is distributed on an        */
/* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,       */
/* either express or implied.                                         */
/* See the License for the specific language governing permissions    */
/* and limitations under the License.                                 */
/**********************************************************************/
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
