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
public class Num implements Stackable
{
    private final double num;
    
    /** 
     * Default constructor.
     * @param num Integer or double to be
     *            packed inside this container
     *            class.
     */
    public Num(double num)
    {
        this.num = num;
    }
    
    /** 
     * Getter for the string packed inside the
     * class.
     * @return Double with the number.
     */
    public double getNumber()
    { 
        return this.num;
    }
    
    public String toString() 
    {
        // Is an integer
        if ((num == Math.floor(num)) && !Double.isInfinite(num))
            return String.valueOf( (int) this.num );
        
        // Is a floating point number
        return String.valueOf(num);
    }
}
