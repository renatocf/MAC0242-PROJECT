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
package parameters;

// Default libraries
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;

/**
 * <b>Debugger</b><br>
 * Provide methods for printing messages 
 * accordingly to a flag.
 *
 * @author Renato Cordeiro Ferreira
 */
public class Debugger
{
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                              DEBUGGER METHODS
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
    /**
     * Initialize the debugger.
     */
    public static void init()
    {
        debugger = new Debugger("log");
        info = true;
    }
    
    /**
     * Close the debugger.<br>
     * Makes no action if the debugger was not initilized.
     */
    public static void close()
    {
        if(!info) return;
        debugger.log.close();
    }
    
    /**
     * Shows info if the debugger is working or not.<br>
     * Makes no action if the debugger was not initilized.
     * 
     * @return True if debugging, false otherwise
     */
    public static boolean debugging()
    {
        return info;
    }
    
    /** 
     * Print without a terminal newline.<br>
     * Makes no action if the debugger was not initilized.
     * 
     * @param strings Variable size list of objects,
     *                which will have their 'toString()'
     *                method used for being printed.
     */
    public static void print(Object ... strings)
    {
        if(!info) return;
        for(Object s: strings) 
            debugger.log.print((s != null) ? s.toString() : "null");
    }

    /** 
     * Print with a terminal newline.<br>
     * Makes no action if the debugger was not initilized.
     * 
     * @param strings Variable size list of objects,
     *                which will have their 'toString()'
     *                method used for being printed.
     */
    public static void say(Object ... strings)
    {
        if(!info) return;
        debugger.print(strings);
        debugger.log.println();
    }
    
    /** 
     * Print formatted.<br>
     * Makes no action if the debugger was not initilized.
     * 
     * @param format String with sequences of formats to
     *               be printed.
     * @param args   Variable size list of objects,
     *               which will have their 'toString()'
     *               method used for being printed.
     */
    public static void printf(String format, Object ... args)
    {
        if(!info) return;
        debugger.log.printf(format, args);
    }
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                                  VARIABLES
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
    /** 
     * Variable to indicate if the Debugger should 
     * print it's info.
     */
    private static boolean info = false;
    
    /**
     * Singleton with a debugger object 
     */
    private static Debugger debugger;
    
    /**
     * PrintWriter with the log file
     */
    private static PrintWriter log;
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                                CONSTRUCTOR
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
    /**
     * Constructor just to create the new log file.
     * @param fileName Log file name
     */
    private Debugger(String fileName)
    {
        try
        {   
            // Create a new log file
            FileOutputStream fos = new FileOutputStream(fileName, false);
            BufferedOutputStream bf = new BufferedOutputStream(fos);
            log = new PrintWriter(bf, true); 
            
            // Set stderr to go to the log file
            System.setErr(new PrintStream(bf));
        }
        catch(IOException e)
        {
            System.out.println(
                "[DEBUGGER] Error to generate log file\n" + e
            );
            e.printStackTrace();
        }
    }
}
