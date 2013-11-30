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
 * <b>Assembly functions - class Net</b><br>
 * Provides functions for controlling the
 * accumulator responsible for network 
 * communications between robots.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Net
{
    // No instances of this class allowed
    private Net() {} 
    
    /**
     * Assembly funcion READ. <br>
     * Get some info from the CACHE memory
     * and stores put it in the top of 
     * the main stack.
     * @param  rvm Virtual Machine
     */
    static void READ(RVM rvm) 
    {
        // TODO: Get a better system for sending no info
        for(int i = 0; i < rvm.CACHE.length; i++)
            if(rvm.CACHE[i] != null) rvm.DATA.push(rvm.CACHE[i]);
            else rvm.DATA.push(Nil.get());
    }
    
    /**
     * Assembly funcion WRT. <br>
     * Stores two stackables from the top 
     * of the main stack and puts it on the
     * cache memory for network communications.
     * @param  rvm Virtual Machine
     * @throws StackUndeflowException
     */
    static void WRT(RVM rvm) 
        throws StackUnderflowException
    {
        rvm.CACHE[0] = rvm.DATA.pop(); 
        rvm.CACHE[1] = rvm.DATA.pop(); 
    }
}
