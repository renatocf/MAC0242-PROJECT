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
    final private static String ColorPC      = YELLOW;
    final private static String ColorJump    = RED;
    final private static String ColorStack   = GREEN;
    final private static String ColorCache   = RED;
    final private static String ColorLabel   = BLUE;
    final private static String ColorCommand = YELLOW;
    
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
            Debugger.print(formatStackable(stk));
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
     * Auxiliar function for debugging the jumps.<br>
     * @param rvm Virtual Machine
     */
    final static void printJump(RVM rvm)
    {
        if(!Debugger.debugging()) return;
        
        Debugger.say(ColorJump, "        [GOTO] ", PURPLE, rvm.PC+1, RESTORE);
    }
    
    /**
     * Auxiliar function for debugging comparisons.<br>
     * @param rvm Virtual Machine
     * @param yes Comparison answer (true/false)
     */
    final static void printCmp(RVM rvm, boolean yes)
    {
        if(!Debugger.debugging()) return;
        
        Debugger.say(ColorJump, "        [CMP] ", PURPLE, yes ? "TRUE" : "FALSE", RESTORE);
        printStack(rvm);
    }
    
    /**
     * Auxiliar function for debugging the program counter (PC).<br>
     * @param PC Program Counter
     */
    final static void printPC(int PC)
    {
        if(!Debugger.debugging()) return;
        
        Debugger.print(ColorPC);
        Debugger.printf("%4d ", PC); 
        Debugger.print(RESTORE);
    }
    
    /**
     * Auxiliar function for printing 
     * the label of a given line.
     * @param label Label
     */ 
    final static void printLabel(String label)
    {
        if(!Debugger.debugging()) return;
        
        if(label == null || label.equals("")) return;
        Debugger.say(ColorLabel, label, RESTORE, ":");
    }
    
    /**
     * Auxiliar function for debugging 
     * the command being executed.<br>
     * @param rvm     Virtual Machine
     * @param command Name of the command
     *                to be executed
     * @param stk     Command arguments
     *                (if any)
     */
    final static void printCommand(RVM rvm, String command, Stackable stk)
    {
        if(!Debugger.debugging()) return;
        
        // Debug
        if(rvm.activity == State.SLEEP)
        {
            Debugger.print(ColorCommand);
            Debugger.print("    [", command, "]");
            Debugger.print(RESTORE);
        }
        else 
        { 
            Debugger.print  (ColorCommand, "    ");
            Debugger.printf ("%-4s   ", command);
            Debugger.print  (RESTORE, " ");
            if(stk != null) Debugger.print (formatStackable(stk));
        }
        Debugger.say();
    }
    
    /**
     * Auxiliar function for printing a stackable with the
     * right colors related to it.
     * @param stk Stackable to be printed as argument to the command
     */
    private static String formatStackable(Stackable stk)
    {
        if(stk instanceof Direction) return PURPLE + stk + RESTORE;
        else if(stk instanceof Num)  return PURPLE + stk + RESTORE;
        else if(stk instanceof Text) return PURPLE + '"' + stk + '"' + RESTORE;
        else                         return stk.toString();
    }
}
