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
 * <b>Assembly functions - class Var</b><br>
 * Provides the funcions for allocating,
 * freeing, setting and getting local 
 * variables.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Var
{
    // No instances of this class allowed
    private Var() {} 
    
    /**
     * Assembly funcion ALOC. <br>
     * Allocates a variable in the local
     * stack. If it is already defined, 
     * throws an exception to warn about
     * the wrong memory usage.
     *
     * @param  rvm  Virtual Machine
     * @param  name Variable name 
     *              (to be defined)
     *                  
     * @throws SegmentationFaultException
     * @throws WrongTypeException
     */
    static void ALOC(RVM rvm, Stackable name)
        throws SegmentationFaultException,
               WrongTypeException
    {
        if(name instanceof Variable)
        {
            Variable v = (Variable) name;
            String n = v.getVariable();
            if(!rvm.VARS.containsKey(n))
                rvm.VARS.put(n, null);
            else throw new SegmentationFaultException();
        }
        else throw new WrongTypeException("a name to a variable");
    }
    
    /**
     * Assembly funcion FREE. <br>
     * Frees a variable, returning its    
     * memory to the virtual machine. If   
     * the name is not defined in the 
     * stack, throws an exception to warn
     * about the wrong memory usage.
     *
     * @param  rvm  Virtual Machine
     * @param  name Variable name 
     *              (to be freed)
     *                  
     * @throws SegmentationFaultException
     * @throws WrongTypeException
     */
    static void FREE(RVM rvm, Stackable name) 
        throws SegmentationFaultException,
               WrongTypeException
    {
        if(name instanceof Variable)
        {
            Variable v = (Variable) name;
            String n = v.getVariable();
            if(rvm.VARS.containsKey(n))
                rvm.VARS.remove(n);
            else throw new SegmentationFaultException();
        }
        else throw new WrongTypeException("a name to a variable");
    }
    
    /**
     * Assembly funcion SET. <br>
     * Sets a local variable with the value
     * in the top of the main stack. If the
     * variable was not allocated, throws 
     * an exception to warn about the wrong 
     * memory usage.
     *
     * @param  rvm  Virtual Machine
     * @param  name Variable name 
     *              (to be setted)
     *                  
     * @throws SegmentationFaultException
     * @throws WrongTypeException
     */
    static void SET(RVM rvm, Stackable name)
        throws SegmentationFaultException,
               WrongTypeException
    {
        if(name instanceof Variable)
        {
            Variable v = (Variable) name;
            String n = v.getVariable();
            if(rvm.VARS.containsKey(n))
                rvm.VARS.put(n, rvm.DATA.pop());
            else throw new SegmentationFaultException();
        }
        else throw new WrongTypeException("a name to a variable");
    }
    
    /**
     * Assembly funcion GET. <br>
     * Gets the value stored in the 
     * memory (if avaiable) and puts it in 
     * the top of the main stack.
     *
     * @param  rvm  Virtual Machine
     * @param  name Variable name (to have 
     *              its value returned)
     *                  
     * @throws NotInitializedException
     * @throws SegmentationFaultException
     * @throws WrongTypeException
     */
    static void GET(RVM rvm, Stackable name) 
        throws NotInitializedException,
               SegmentationFaultException,
               WrongTypeException
    {
        if(name instanceof Variable)
        {
            Variable v = (Variable) name;
            String n = v.getVariable();
            if(rvm.VARS.containsKey(n))
            {
                Stackable top = rvm.VARS.get(n);
                if(top != null) 
                {
                    rvm.DATA.push(top);
                    Debug.printStack(rvm);
                }
                else throw new NotInitializedException(n);
            }
            else throw new SegmentationFaultException();
        }
        else throw new WrongTypeException("a name to a variable");
    }
}
