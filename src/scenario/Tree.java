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
