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
import gui.Printable;
import players.Player;

/** 
 * <b>Scenario</b><br>
 * General interface for scenarios that
 * appear in the arena.
 */
public interface Scenario extends Printable
{
    /** 
     * Amount of damage taken by a scenario.
     * If the scenario should not be destroyed,
     * this method may have no action over it HP.
     * @param damage Total of points to
     *               be subtracted from 
     *               the HP.
     * @return Scenario's remining HP.
     */
    public int takeDamage(int damage);
    
    /**
     * Check if the scenario has sufferd damage.
     * @return True if there was damage, false
     *         otherwise
     */
    public boolean sufferedDamage();
    
    /**
     * Remaining HP to the scenario 
     * be removed from the map.
     */
    public int getHP();
    
    /** 
     * Returns the team of a given scenario
     * (ordinary elements should be Nature).
     * @return Player that owns the scenario
     */
    public Player getTeam();
    
    /**
     * Prints the scenario in a more legible
     * way, with debug purposes.
     */
    public String toString();
}
