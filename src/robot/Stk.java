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
import parameters.Debugger;

/**
 * <b>Assembly functions - class Stk</b><br>
 * Provides the funcions for manipulating 
 * the main stack of the virtual machine.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Stk
{
    // No instances of this class allowed
    private Stk() {} 
    
    /**
     * Assembly funcion PUSH. <br>
     * Puts an element in the top of the main stack.
     * 
     * @param  rvm Virtual Machine
     * @param  st  Stackable element.
     * @throws StackUnderflowException
     * @see    Stackable
     */
    static void PUSH(RVM rvm, Stackable st)
    {
        rvm.DATA.push(st);
        Debug.printStack(rvm);
    }
    
    /**
     * Assembly funcion POP. <br>
     * Takes out an element of the top of the main stack.
     * 
     * @param  rvm Virtual Machine
     * @throws StackUnderflowException
     * @see    Stackable
     */
    static Stackable POP(RVM rvm) throws StackUnderflowException
    {
        Stackable pop = rvm.DATA.pop();
        Debug.printStack(rvm);
        return pop;
    }
    
    /**
     * Assembly funcion DUP. <br>
     * Duplicates the top of the main stack.
     * 
     * @param  rvm Virtual Machine
     * @throws StackUnderflowException
     * @see    Stackable
     */
    static void DUP(RVM rvm) throws StackUnderflowException
    {
        Stackable st = rvm.DATA.pop(); 
        rvm.DATA.push(st);
        rvm.DATA.push(st);
        Debug.printStack(rvm);
    }
    
    /**
     * Assembly funcion SWAP. <br>
     * Swaps the top of the main stack.
     * 
     * @param  rvm Virtual Machine
     * @throws StackUnderflowException
     * @see    Stackable
     */
    static void SWAP(RVM rvm) throws StackUnderflowException
    {
        Stackable top = rvm.DATA.pop(); 
        Stackable sl  = rvm.DATA.pop(); 
        
        rvm.DATA.push(top);
        rvm.DATA.push(sl);
        Debug.printStack(rvm);
    }
}
