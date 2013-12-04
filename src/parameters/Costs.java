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
package parameters;

/**
 * <b>Costs</b><br>
 * Define levels of costs, in power,
 * for different actions.
 */
public final class Costs
{
    /** 
     * <b>Energy: Charge</b>
     */
    public static int ENERGY_CHARGE = 2;

    /** 
     * <b>Energy: None</b>
     */
    public static int ENERGY_NONE   = 0;

    /** 
     * <b>Energy: Low</b>
     */
    public static int ENERGY_LOW    = 1;
    
    /** 
     * <b>Energy: Medium</b>
     */
    public static int ENERGY_MEDIUM = 2;
    
    /** 
     * <b>Energy: High</b>
     */
    public static int ENERGY_HIGH   = 4;    
}
