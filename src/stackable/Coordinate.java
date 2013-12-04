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

/**
 * <b>Stackable - Num</b><br>
 * Packs numbers inside a simple 
 * container, allowing them to be 
 * identified as stackables.
 * 
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
public class Coordinate implements Stackable
{
    private final int coord[];
    
    /** 
     * Default constructor.
     * @param i Horizontal position (Integer)
     * @param j Vertical position (Integer)
     */
    public Coordinate(int i, int j)
    {
        this.coord = new int[] { i, j };
    }
    
    /** 
     * Secondary constructor.
     * @param i Horizontal position (Num)
     * @param j Vertical position (Num)
     */
    public Coordinate(Num i, Num j)
    {
        this.coord = new int[] { (int) i.getNumber(), (int) j.getNumber() };
    }
    
    /** 
     * Getter for the string packed 
     * inside the class.
     * @return Integer with the coordinate.
     */
    public int[] getCoordinate()
    { 
        return this.coord;
    }
    
    public int getX() { return this.coord[1]; }
    public int getY() { return this.coord[0]; }
    
    public int getI() { return this.coord[0]; }
    public int getJ() { return this.coord[1]; }
    
    public String toString() 
    {
        return "[" + this.coord[0] + "," + this.coord[1] + "]";
    }
}
