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
 * <b>Stackable - Variable</b><br>
 * Class made only to make usual strings
 * and variable declarations different.
 * 
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
public class Variable implements Stackable
{
    private final String var;
    
    /** 
     * Default constructor.
     * @param var String with the name of 
     *            a variable to be packed 
     *            inside this container
     *            class.
     */
    public Variable(String var)
    {
        this.var = var;
    }
    
    /** 
     * Getter for the string with 
     * the name of the variable.
     * @return String with the variable name.
     */
    public String getVariable()  { return "[" + this.var + "]"; }
    
    public String toString() { return "[" + this.var + "]"; }
}
