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
 * <b>Stackable - Addr</b><br>
 * Packs a number in an imutable 
 * way to be used as an address 
 * for jumps and function calls. 
 * Its structure is very similar 
 * to Num's.
 * 
 * @author Renato Cordeiro Ferreira
 */
public class Addr implements Stackable
{
    private final int address;
    
    /** 
     * Default constructor.
     * @param address Integer with the 
     *                value to be packed
     *                inside this container
     *                class.
     */
    public Addr(int address)
    {
        this.address = address;
    }
    
    /** 
     * Getter for the integer packed inside the
     * class.
     * @return String with the text.
     */
    public int getAddress()
    { 
        return this.address;
    }
    
    public String toString() 
    {
        return String.valueOf(address);
    }
}
