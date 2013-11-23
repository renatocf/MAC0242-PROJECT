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
package stackable.item;

// Libraries
import gui.Printable;
import stackable.Stackable;

/** 
 * <b>Item</b><br>
 * Simple interface to mark an element
 * as being an item, extending the stackable
 * interface. 
 * <p>
 * Thus, every item can be stored both in the 
 * robot virtual machine (RVM) main stack (as 
 * a 'software concept') and inside the robot
 * itself (as a 'real' catchable item).
 */
public interface Item extends Stackable, Printable { }
