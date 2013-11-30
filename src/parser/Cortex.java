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
package parser;

// Default libraries
import java.io.*;
import java.util.Vector;
import java.lang.reflect.*;

// Libraries
import exception.*;
import parser.auto.*;
import robot.Command;
import parameters.Debugger;

/**
 * <b>Cortex</b><br>
 * Manage all operations related to 
 * upload programs (either in assembly
 * or in Positron) to create a vector 
 * with commands readable by the 
 * Robot Virtual Machine (RVM).
 */
final public class Cortex
{
    // No instances of this class allowed
    private Cortex() {}
    
    /** 
     * Translates Positron to Assembly.<br>
     * Given a string with the path to the .pos
     * program, creates a vector of commmands 
     * from it using the assembly.
     */
    public static Vector<Command> translate(String pathToProg)
    {
        String prog = processInput(pathToProg);
        Vector<Command> PROG  = null;
        
        try {
            // ReInit parser to parse from this file
            Parser.ReInit(new FileInputStream(pathToProg));
            
        } catch(FileNotFoundException e) {
            System.err.println("File not found!");
            e.printStackTrace();
        }
        
        // TODO: Give a better exception treatment
        try {
            // Create program vector from file
            PROG = Parser.Start();
            
        } catch(ParseException e) {
            System.err.println("Parser error!");
            e.printStackTrace();
            
        } catch(TokenMgrError e) {
            System.err.println("Token Manager error!");
            e.printStackTrace();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return PROG;
    }
    
    /** 
     * Upload program made only with assembly.<br>
     * Given a string with the path to the .asm
     * program, uploads the .java program that
     * creates a vector of commands from it.
     */
    public static Vector<Command> readFromASM(String pathToProg)
    {
        // Creates a new string
        String prog = processInput(pathToProg);
        
        try { 
            Class<?> Parser = Class.forName("parser." + prog); 
            Method   upload = Parser.getMethod("upload");
            // Suppress warning of converting from Object class
            // to Vector<Command> (unhappily, it cannot be solved
            // due to the characteristics of generic types).
            @SuppressWarnings("unchecked")
            Vector<Command> PROG = (Vector<Command>) upload.invoke(null);
            return PROG;
        }
        catch(ClassNotFoundException e) 
        {
            System.err.println(
                "Class not found! Program \"" + 
                prog + "\" does not exist!"
            );
        }
        catch(NoSuchMethodException     e) {}
        catch(IllegalAccessException    e) {}
        catch(InvocationTargetException e) {}
        return null;
    }
    
    /** 
     * Changes path to make the program name.<br>
     * Given a string, makes tha path a valid name
     * for the program to be loaded.
     */
    private static String processInput(String input)
    {
        // Process input
        input = input.replaceFirst("^.*/"  , ""); // Takes out dir path
        input = input.replaceFirst("\\..*$", ""); // Takes out excension
        
        // Uppercase first
        char   first = Character.toUpperCase(input.charAt(0));
        String other = input.substring(1).toLowerCase();
        String prog  = first + other;

        // Debug message
        if(!prog.equals(input))
        {
            Debugger.say(input + " is not a valid name!");
            Debugger.say("Searching for " + prog);
            Debugger.say();
        }
        return prog;
    }
}
