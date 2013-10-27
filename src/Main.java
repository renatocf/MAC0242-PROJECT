// Default Libraries
import java.util.Vector;

// Libraries
import arena.*;
import exception.*;
import stackable.*;
import parameters.*;
import robot.Command;

// External Libraries (.jar)
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

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
        
        // Generate map
        //World.genesis(2, Weather.CONTINENTAL );
         World.genesis(2, Weather.ARTICAL     ); 
        /* World.genesis(2, Weather.DESERTIC    ); */
        //World.genesis(2, Weather.TROPICAL    );
        
        try{
            World.insertArmy(1, "R2D2", 8, 9, args[0]);
            World.insertArmy(2, "Bender"  , 9, 8, args[1]);
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
        LongOpt[] longopts = 
        {
            new LongOpt("help"   , LongOpt.NO_ARGUMENT, null, 'h'),
            new LongOpt("debug"  , LongOpt.NO_ARGUMENT, null, 'd'),
            new LongOpt("verbose", LongOpt.NO_ARGUMENT, null, 'v'),
        };
        //
        Getopt g = new Getopt("testprog", args, "hdv", longopts);
        
        for (int i = g.getOptind(); i < args.length ; i++)
            System.out.println("Non option argv element: " 
                    + args[i] + "\n");

        for(String arg: args)
        {
            if(arg.equals("-v")) Debugger.info = true;
            if(arg.equals("-d")) Debugger.info = true;
        }
    }
}
