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
package robot;

public enum Returns
{   
    TRUE           (1),
    SUCCEDED       (1),
                      
    FALSE          (0),
    NOT_SUCCEDED   (0),
    NO_TARGET      (0),
    NO_ENERGY      (-1), // =  0;
    END_OF_MAP     (-2), // =  0;
    BLOCKED        (-3), // =  0;
    FULL_SLOTS     (-3), // =  0;
    EMPTY_SLOTS    (-3), // =  0;
    OUT_OF_RANGE   (-3), // =  0;
    FRIENDLY_FIRE  (-4), // =  0;
    INVALID_ACTION (-5); // =  0;
        
    // Return value
    private int value;
    
    // No instances of this class allowed
    private Returns(int value) { this.value = value; }
        
    /**
     * Method for getting the internal
     * value of the field.
     */
    public int getValue() { return this.value; }
}
