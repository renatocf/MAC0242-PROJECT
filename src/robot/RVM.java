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
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;

// Libraries
import robot.*;
import exception.*;
import stackable.*;
import parameters.*;

// Import links
import static parameters.Game.*;

/**
 * <b>
 * Main package class with the 
 * constructor of the robot and 
 * its data.
 * </b>
 * @author Renato Cordeiro Ferreira
 */
public class RVM
{
    Vector  <Command>   PROG;
    Stack   <Integer>   CTRL = new Stack <Integer>   ();
    Stack   <Stackable> DATA = new Stack <Stackable> ();
    HashMap <String, Integer>  LABEL = new HashMap <String, Integer>    ();
    HashMap <String, Stackable> VARS = new HashMap <String, Stackable>  ();
    HashMap <Integer, Stackable> RAM = new HashMap <Integer, Stackable> ();
    Stackable[] CACHE = new Stackable[] { Nil.get(), Nil.get() };
    int PC = 0;

    boolean syscall = false;
    State activity = State.ACTIVE;

    /**
     * Class constructor specifying a 'program' (vector of
     * objects of the class Command) to the RVM.
     * 
     * @param PROG    Vector of objects of the class Command
     * @see   Command 
     */
    public RVM(Vector <Command> PROG) 
    { 
        upload(PROG);
    }
    
    /**
     * Setter method created to upload a new 'program' (vector
     * of object of the class Command) in the RVM.
     * 
     * @param PROG    Vector of objects of the class Command
     * @see   Command
     */
    public void upload(Vector <Command> PROG) 
    { 
        this.PROG = PROG; upload_labels();
    }
    
    /**
     * Execute 1 assembly instruction.
     * 
     * @throws SegmentationFaultException
     * @throws UndefinedFunctionException
     * @throws InvalidOperationException
     * @throws NotInitializedException
     * @throws StackUnderflowException
     * @throws NoLabelFoundException
     * @throws OutOfBoundsException
     * @throws WrongTypeException
     */ 
    public void exec() 
        throws SegmentationFaultException, 
               UndefinedFunctionException,
               InvalidOperationException, 
               NotInitializedException,
               StackUnderflowException,
               NoLabelFoundException,
               OutOfBoundsException,
               WrongTypeException
    {
        Command   com      = this.PROG.elementAt(this.PC);
        String    function = com.getFunction  ();
        Stackable arg      = com.getAttribute (); 
        
        // Call function
        if(function != null)
        {
            try { Ctrl.ctrl(this, function, arg);}
            catch (Exception e) 
            { 
                System.err.println("[RVM] " + e);
                System.err.println("At Line " + this.PC + ": " 
                                   + this.PROG.elementAt(this.PC));
                e.printStackTrace();
            }
        }
    }

    /**
     * Function responsible for executing the 'program', 
     * step by step, untill a syscall operation.
     * 
     * @throws SegmentationFaultException
     * @throws UndefinedFunctionException
     * @throws InvalidOperationException
     * @throws NotInitializedException
     * @throws StackUnderflowException
     * @throws NoLabelFoundException
     * @throws OutOfBoundsException
     * @throws WrongTypeException
     */ 
    public void run() 
        throws SegmentationFaultException, 
               UndefinedFunctionException,
               InvalidOperationException, 
               NotInitializedException,
               StackUnderflowException,
               NoLabelFoundException,
               OutOfBoundsException,
               WrongTypeException
    {
        switch(activity)
        {
            case ACTIVE:
                // Case 1: Active
                // Execute ASM_MAX_RUN assembly lines
                // OR a syscall.
                
                this.syscall = false; int c = 0; 
                while(!this.syscall && c < ASM_MAX_RUN) 
                { 
                    if(this.PROG.elementAt(this.PC) == null) this.PC = 0;
                    Debugger.printf("[PC:%3d]", this.PC); 
                    exec(); this.PC++; c++; 
                }
                break;
            
            case SLEEP: 
                // Case 1: Sleep
                // Execute one single assembly line 
                // (usually, a single syscall to wait
                // for its answer).
                //
                // If PC is 0, there it would be nowhere
                // to go. Therefore, let's get out.
                if(this.PC != 0)
                {
                    this.PC--; exec(); this.PC++; 
                }
                
                // Debug: stack
                Debugger.say  ("[STACK]");
                Debugger.print("    ");
                for(Stackable stk: this.DATA)
                    Debugger.print(stk, ", ");
                Debugger.say("[TOP]");
                
                // Debug: cache
                Debugger.say  ("[CACHE]");
                Debugger.print("    [ ");
                for(int i = 0; i < this.CACHE.length-1; i++)
                    Debugger.print(this.CACHE[i], " | ");
                Debugger.print(this.CACHE[this.CACHE.length-1]);
                Debugger.say(" ]");
                
                break;
        }
    }
    
    /** 
     * Receive data from the network 
     * and put it on the CACHE.<br>
     * The info in the cache will be 
     * overloaded accordingly to the 
     * size of the info passed, up to
     * the size avaiable on the cache.
     * @see Net
     * @see arena.Action
     */
    public void download(Stackable[] info)
    {
        for(int i = 0; i < this.CACHE.length && i < info.length; i++)
            this.CACHE[i] = info[i];
    }
    
    /** 
     * Set the robot's state to ACTIVE.
     * @param rvm Virtual Machine
     * @see State
     */
    public static void wake(RVM rvm)  { rvm.activity = State.ACTIVE; }
    
    /** 
     * Set the robot's state to SLEEP.
     * @param rvm Virtual Machine
     * @see State
     */
    public static void sleep(RVM rvm) { rvm.activity = State.SLEEP; }
    
    /**
     * Function responsible for uploading the labels of PROG,
     * doint it if and only if the program is new.
     */ 
    private void upload_labels()
    {
        this.LABEL.clear();
        for(int i = 0 ;; i++)
        {
            Command c = this.PROG.elementAt(i);
            if(c == null) break;
            
            // Upload labels to HashMap.
            if(c.getLabel() != null) this.LABEL.put(c.getLabel(), i);
        }
    }
}
