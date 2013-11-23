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
 * <b>Scenario - Water</b><br>
 * Take care with the water: it cannot be
 * destroyed, but can stuck robots.
 */
@Deprecated
public class Water implements Scenario 
{
    public int takeDamage(int damage) 
    {
        // Water cannot be destroyed...
        return 0;
    }
    
    public int getHP() { return 42; }
    
    public String toString() { return "(≈) Water"; }
    
    public boolean sufferedDamage() { return false; }
    
    public Player getTeam() { return Player.Nature; }
    
    public String name() { return "WATER"; }
}
