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
import arena.*;
import remote.*;
import stackable.*;
import exception.*;

/**
 * <b>Assembly functions - class Syst</b><br>
 * Implements the commands MOVE, DRAG,
 * DROP, HIT, LOOK and get from the World
 * if it has permission for executing 
 * the correspondent action.
 *
 * @author Karina Suemi 
 * @author Renato Cordeiro
 * @author Vinícius Silva 
 * @see Ctrl
 * @see arena.World
 */
final public class Syst
{
    // No instances of this class allowed
    private Syst() {} 
    
    /**
     * General-model funcion action.
     * Takes out the top of the main stack,
     * generates an operand object and does
     * a system call. Pushes all the system
     * answers in the Virtual Machine main 
     * stack. 
     * <p>
     * If the command needs no argument, 
     * does not pop the stack.
     * 
     * @param rvm  Virtual Machine.
     * @param type Command type.
     *
     * @throws WrongTypeException
     * @throws InvalidOperationException 
     */
    private static void action(RVM rvm, String type, int nPops)
        throws WrongTypeException,
               InvalidOperationException 
    {
        if(rvm.activity == State.ACTIVE)
        {
            Stackable[] args = new Stackable[nPops];
            for(int i = 0; i < nPops; i++) 
                args[i] = rvm.DATA.pop();
            Operation op;
            
            try { 
                op = new Operation(rvm, type, args);
            }
            catch (InvalidOperationException e) {
                throw new WrongTypeException(type);
            }
            
            // Request operation to the server
            World.POST(op);
        }
        else if(rvm.activity == State.SLEEP)
        {
            // Get server answer
            Stackable[] stk = World.GET();
            for(int i = 0; i < stk.length; i++) rvm.DATA.push(stk[i]);
        }
        
        // Counts as a syscall
        rvm.syscall = true;
    }
    
    /**
     * Assembly funcion MOVE. <br>
     * Makes a syscall, requesting for a 
     * movement in the arena.
     * 
     * @param  rvm Virtual Machine.
     * @throws WrongTypeException
     * @throws InvalidOperationException 
     */
    static void MOVE(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "MOVE", 1);
    }
    
    /**
     * Assembly funcion DRAG. <br>
     * Makes a syscall, requesting for  
     * taking an item from the arena.
     * 
     * @param  rvm Virtual Machine.
     * @throws WrongTypeException
     * @throws InvalidOperationException 
     */
    static void DRAG(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "DRAG", 1);
    }
    
    /**
     * Assembly funcion DROP. <br>
     * Makes a syscall, requesting for  
     * taking leaving an item in the 
     * arena.
     * 
     * @param  rvm Virtual Machine.
     * @throws WrongTypeException
     * @throws InvalidOperationException 
     */
    static void DROP(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "DROP", 1);
    }
    
    /**
     * Assembly funcion HIT. <br>
     * Makes a syscall, requesting to
     * attack a robot or a scenario
     * in the arena.
     * 
     * @param  rvm Virtual Machine.
     * @throws WrongTypeException
     * @throws InvalidOperationException 
     */
    static void HIT(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        if(rvm.activity == State.ACTIVE)
        {
            Stackable atk = rvm.DATA.pop();
            Stackable stk = rvm.DATA.pop();
            
            if(stk instanceof Num)
            {
                /* Get number of directions */
                int ndirs = (int) ((Num)stk).getNumber();
                
                rvm.DATA.push(stk);
                rvm.DATA.push(atk);
                
                action(rvm, "HIT", ndirs + 2);
            }
            else // coordinate
            {
                rvm.DATA.push(stk);
                rvm.DATA.push(atk);
                
                action(rvm, "HIT", 2);
            }
        }
        // No pushes will be done in the return 
        else action(rvm, "HIT", 0);
    }
    
    /**
     * Assembly funcion LOOK. <br>
     * Makes a syscall, requesting to
     * get a terrain for being analysed.
     * 
     * @param  rvm Virtual Machine.
     * @throws WrongTypeException
     * @throws InvalidOperationException 
     * @see    Check
     */
    static void LOOK(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "LOOK", 1);
    }
    
    /**
     * Assembly funcion SEE. <br>
     * Makes a syscall, requesting to
     * get a neighborhood for being 
     * analysed.
     * 
     * @param  rvm Virtual Machine.
     * @throws WrongTypeException
     * @throws InvalidOperationException 
     * @see    Check
     */
    static void SEE(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "SEE", 0);
    }
    
    /**
     * Assembly funcion ASK. <br>
     * Makes a syscall, requesting to
     * get info about the robot from 
     * the arena's control.
     * 
     * @param  rvm Virtual Machine.
     * @throws WrongTypeException
     * @throws InvalidOperationException 
     */
    static void ASK(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "ASK", 1);
    }
    
    /**
     * Assembly funcion SEND. <br>
     * Makes a syscall to repass to the 
     * player's robot network the info
     * inside the robot's cache memory.
     * 
     * @param  rvm Virtual Machine.
     * @throws WrongTypeException
     * @throws InvalidOperationException 
     */
    static void SEND(RVM rvm)
        throws WrongTypeException,
               InvalidOperationException
    {
        action(rvm, "SEND", 2);
    }
    
    /**
     * Assembly funcion SKIP. <br>
     * Makes a syscall with no operations.
     * 
     * @param  rvm Virtual Machine.
     */
    static void SKIP(RVM rvm)
    {
        try { action(rvm, "SKIP", 0);
        
        } catch(WrongTypeException e) {
            System.err.println("[SYST][SKIP] " + 
                "This exception should never happen"
            );
        } catch(InvalidOperationException e) {
            System.err.println("[SYST][SKIP] " + 
                "This exception should never happen"
            );
        } // try
    }
}
