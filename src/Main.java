// Default Libraries
import java.util.Vector;
import java.lang.reflect.Method;

// Libraries
import arena.*;
import parser.*;
import exception.*;
import stackable.*;
import parameters.*;
import robot.Command;

class Main
{
    final static String USAGE = 
        "USAGE: java -jar dist/MAC0242.jar <prog> [-v]";
    
    public static void main(String[] args)
        throws InvalidOperationException
    {
        if(args.length < 1) 
        {
            System.err.println(USAGE);
            return;
        }
        
        getopt(args); // Get options
        
        // Process input
        String input = args[0];
        char   first = Character.toUpperCase(input.charAt(0));
        String other = input.substring(1).toLowerCase();
        String prog  = first + other;
        if(!prog.equals(input))
        {
            System.out.println(input + "not a valid name!");
            System.out.println("Searching for " + prog);
        }
        
        try { Class Parser = Class.forName("parser." + prog); }
        catch(ClassNotFoundException e) 
        {
            System.err.println(
                "Class not found! Program \"" + 
                prog + "\" does not exist!"
            );
            return;
        }
        
        Parser user = new Parser();
        Vector<Command> PROG = user.upload();
        
        World.genesis(2, Weather.ARTICAL);
        
        for(int t = 0; t < 370; t++)
            World.timeStep();
    }

    private static void getopt(String[] args)
    {
        for(String arg: args)
        {
            if(arg.equals("-v")) Verbosity.v = true;
        }
    }
}
