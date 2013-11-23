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

// Libraries
import stackable.*;
import exception.*;

/**
 * <b>Assembly functions - class IO</b><br>
 * Provides the funcions for checking
 * the data inside the stack of the 
 * virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class IO
{
    // No instances of this class allowed
    private IO() {} 
    
    /**
     * Assembly funcion PRN. <br>
     * Takes out the top of the main stack
     * ant prints it in the stdout.
     *
     * @param rvm Virtual Machine
     */
    static void PRN(RVM rvm)
    {
        try
        {
            System.out.println(rvm.DATA.pop());
        }
        catch (Exception e)
        {
            System.out.println("Empty stack!");
        }
    }
}
