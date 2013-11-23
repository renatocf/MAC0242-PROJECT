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
 * <b>Assembly functions - class Func</b><br>
 * Provides the funcions for controlling
 * the creation of functions: CALL and
 * RET (special types of jumps).
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Func
{
    // No instances of this class allowed
    private Func() {} 
    
    /**
     * Assembly funcion CALL. <br>
     * Execute an inconditional jump to 
     * a label or address, storing the 
     * address of return in the CTRL
     * stack for doing a callback with RET.
     * 
     * @param  rvm Virtual Machine
     * @param  arg Address or label
     *
     * @throws WrongTypeException
     * @throws OutOfBoundsException
     */
    static void CALL(RVM rvm, Stackable arg) 
        throws WrongTypeException, OutOfBoundsException
    {
        // Saves PC in the CTRL stack
        rvm.CTRL.push(rvm.PC);
        
        // Do inconditional Jump to label/address
        if(arg instanceof Addr)
        {
            Addr address = (Addr) arg; 
            int index = (int) address.getAddress();
            
            try {
                if(rvm.PROG.elementAt(index) != null)
                    rvm.PC = index;
            }
            catch (Exception e) {
                throw new OutOfBoundsException();
            }
        } 
        else if(arg instanceof Text) 
        {
            Text text = (Text) arg;
            String label = (String) text.getText();
            
            if(rvm.LABEL.containsKey(label))
                rvm.PC = rvm.LABEL.get(label);
            else
                throw new OutOfBoundsException();
        }
        else throw new WrongTypeException("String or Number");
    }
    
    /**
     * Assembly funcion RET. <br>
     * Pops the top most saved return address
     * to be able to go back to the function 
     * caller.
     * 
     * @param  rvm Virtual Machine
     *
     * @throws UndefinedFunctionException 
     */
    static void RET(RVM rvm)
        throws UndefinedFunctionException
    { 
        try { rvm.PC = rvm.CTRL.pop(); }
        catch (Exception e) { 
            throw new UndefinedFunctionException(rvm.PC);
        }
    }
    // No need to increment PC. In a for 
    // loop, it will be done automatically.
}
