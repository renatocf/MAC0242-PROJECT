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
import parameters.Debugger;

import static parameters.Debugger.Colors.*;

/**
 * <b>RVM - Debug</b><br>
 * Auxiliar class to help debugging and logging 
 * the Robot Virtual Machine.
 * @author Renato Cordeiro Ferreira
 */
final class Debug
{
    final private static String ColorStack   = GREEN;
    final private static String ColorCache   = RED;
    final private static String ColorCommand = YELLOW;
    final private static String ColorJump    = RED;
    
    // No instances of this class allowed
    private Debug() {}
    
    /**
     * Auxiliar function for debugging 
     * the main stack.<br>
     * @param rvm Virtual Machine
     */
    final static void printStack(RVM rvm)
    {
        if(!Debugger.debugging()) return;
        
        // Print stack
        Debugger.print(ColorStack, "        [STACK] ", RESTORE);
        for(Stackable stk: rvm.DATA)
        {
            if(stk instanceof Num)       Debugger.print(PURPLE, stk, RESTORE);
            else if(stk instanceof Text) Debugger.print(PURPLE, '"', stk, '"', RESTORE);
            else                         Debugger.print(stk);
            
            Debugger.print(", ");
        }
        Debugger.say(ColorStack, "[TOP]", RESTORE);
    }
    
    /**
     * Auxiliar function for debugging 
     * the cache.<br>
     * @param rvm Virtual Machine
     */
    final static void printCache(RVM rvm)
    {
        if(!Debugger.debugging()) return;
        
        Debugger.print(ColorCache, "        [CACHE] ");
        Debugger.print("[ ");
        
        for(int i = 0; i < rvm.CACHE.length-1; i++)
            Debugger.print(rvm.CACHE[i], " | ");
        
        Debugger.print(rvm.CACHE[rvm.CACHE.length-1]);
        Debugger.say(" ]", RESTORE);
    }
    
    /**
     * Auxiliar function for debugging 
     * the command being executed.<br>
     * @param rvm Virtual Machine
     */
    final static void printCommand(String command, Stackable arg)
    {
        if(!Debugger.debugging()) return;
        
        // Debug
        Debugger.print(" ", ColorCommand, command, RESTORE);
        if(arg != null) Debugger.print(" ", arg);
        Debugger.say();
        if(command.equals("END")) Debugger.say("===========");
    }
    
    /**
     * Auxiliar function for debugging 
     * the jumps.<br>
     * @param rvm Virtual Machine
     */
    final static void printJump(RVM rvm)
    {
        Debugger.say(ColorJump, "        [GOTO] ", PURPLE, rvm.PC+1, RESTORE);
    }
    
    /**
     * Auxiliar function for debugging comparisons.<br>
     * @param rvm Virtual Machine
     */
    final static void printCmp(RVM rvm, boolean yes)
    {
        Debugger.say(ColorJump, "        [CMP] ", PURPLE, yes ? "TRUE" : "FALSE", RESTORE);
        printStack(rvm);
    }
}
