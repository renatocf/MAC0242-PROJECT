// Default Libraries
import java.util.Vector;

// Libraries
import arena.*;
import exception.*;
import stackable.*;
import parameters.*;
import robot.Command;

class Main 
{
    final static String USAGE = 
        "USAGE: java -jar dist/MAC0242.jar <prog1> <prog2> [-v]";
    
    public static void main(String[] args)
        throws InvalidOperationException
    {
        if(args.length < 2)
        {
            System.err.println(USAGE);
            return;
        }
        
        getopt(args); // Get options
        String input = args[0];
        
        World.genesis(2, Weather.ARTICAL);
        try{
            World.insertArmy(1, "Bender", 8, 9, args[0]);
            World.insertArmy(2, "C3PO"  , 9, 8, args[1]);
        }
        catch(SegmentationFaultException e)
        {
            System.err.println("Invalid position!");
        }
        
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
