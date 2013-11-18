package parameters;

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
    /** 
     * Variable to indicate if the Debugger should 
     * print it's info.
     */
    public static boolean info = false;
    
    /** 
     * Print without a terminal newline.
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
     * Print with a terminal newline.
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
     * Print formatted.
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
    
    /**
     * Close the log file.
     */
    public static void close()
    {
        debugger.log.close();
    }
    
    /**
     * Singleton with a debugger object 
     */
    private static Debugger debugger = new Debugger("log");
    
    /**
     * PrintWriter with the log file
     */
    private static PrintWriter log;
    
    /**
     * Constructor just to create the new log file.
     * @param fileName Log file name
     */
    private Debugger(String fileName)
    {
        try
        {   
            BufferedOutputStream bf = new BufferedOutputStream(new FileOutputStream(fileName, false));
            log = new PrintWriter(bf, true); 
            System.setErr(new PrintStream(bf));
        }
        catch(IOException e)
        {
            System.out.println(
                "[DEBUGGER] Error to generate log file\n" + e
            );
        }
    }
}
