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
 * <b>Assembly functions - class Prog</b><br>
 * Provides the funcions for controlling
 * the program: no operation (NOP) and 
 * end of program (END).
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Prog
{
    // No instances of this class allowed
    private Prog() {} 
    
    /**
     * Assembly funcion NOP. <br>
     * Do nothing for one step.
     * (Equivalent to a syscall)
     */
    static void NOP(RVM rvm) { Syst.SKIP(rvm); }
    
    /**
     * Assembly funcion END. <br>
     * Sets the counter to indicate
     * the END of the program.
     * the execution for 1 step.
     */
    static void END(RVM rvm) { rvm.PC = -1; Syst.SKIP(rvm); }
    // Sets the counter to -1, to be able to
    // increment in each iteration of a for loop.
}
