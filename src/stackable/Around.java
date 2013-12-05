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
import scenario.*;
import exception.*;
import arena.Robot;
import arena.Terrain;
import stackable.item.*;

/**
 * <b>Stackable - Around</b><br>
 * Provides the neighborhoods (set
 * of terreins) for a given terrain.
 * @see robot.Check#SEEK
 *
 * @author Karina Awoki
 * @author Vinicius Silva
 */
public class Around implements Stackable
{
    public String [][] matrix;
           Coordinate[] coords;
    
    /** 
     * Default constructor.
     * @param seeing Terrain with the robot
     *               that is seeing its 
     *               neighborhood.
     */
    public Around(Terrain[] seeing, Coordinate[] coords)
    {
        matrix = new String[2] [seeing.length];
        this.coords = coords;
        for(int i = 0; i< seeing.length; i++)
        {
            if (seeing[i] == null) matrix[0][i] = "";
            else
            {
                Scenario s = seeing[i].getScenario();
                if(s == null) matrix[0][i] = " ";
                else
                {
                    matrix[0][i] = s.getClass().getName().replaceFirst("^.*\\.", "");
                }
                          
                //Adjusting the String for simplicity
                if(matrix[0][i].equals("Robot") && i != 0)
                {
                    Robot r = (Robot) s;
                    matrix[0][i] += r.getTeam().getID();
                }
                
                stackable.item.Item it = seeing[i].getItem(); 
                if(it == null) matrix[1][i] = " ";
                else           matrix[1][i] = it.getClass().getName().replaceFirst("^.*\\.", "");
            }
        }
    }
    
    public Coordinate getCoordinate(int i)
    {
        return coords[i];
    }
    
    public String toString() { return "( Around )"; }
}
