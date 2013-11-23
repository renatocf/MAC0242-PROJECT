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

// Default libraries
import java.util.Vector;

// Libraries
import stackable.*;
import exception.*;

/**
 * <b>Assembly functions - class Mem</b><br>
 * Provides the funcions for using 
 * the auxiliar memory in the virtual 
 * machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Mem
{
    // No instances of this class allowed
    private Mem() {} 
    
    /**
     * Assembly funcion STO. <br>
     * Takes out the top of the main stack
     * and puts it in a specific position 
     * of the secondary memory.
     *
     * @param  rvm      Virtual Machine
     * @param  position Stackable position 
     *                  (must be numeric)
     *                  
     * @throws SegmentationFaultException
     * @throws StackUnderflowException
     */
    static void STO(RVM rvm, Stackable position)
        throws SegmentationFaultException,
               StackUnderflowException
    {
        int pos;
        if(position instanceof Num)
        {
            Num num = (Num) position;
            pos = (int) num.getNumber();
        }
        else throw new StackUnderflowException();
        rvm.RAM.put(pos, rvm.DATA.pop());
    }
    
    /**
     * Assembly funcion RCL. <br>
     * Takes out the data in the secondary
     * memory (if avaiable) and puts it in 
     * the top of the main stack.
     *
     * @param  rvm      Virtual Machine
     * @param  position Stackable position 
     *                  (must be numeric)
     *                  
     * @throws SegmentationFaultException
     * @throws StackUnderflowException
     */
    static void RCL(RVM rvm, Stackable position) 
        throws SegmentationFaultException,
               StackUnderflowException
    {
        int pos;
        if(position instanceof Num)
        {
            Num num = (Num) position;
            pos = (int) num.getNumber();
        }
        else throw new StackUnderflowException();
        rvm.DATA.push(rvm.RAM.get(pos));
    }
}
