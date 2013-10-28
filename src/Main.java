// Default Libraries
import java.io.*;
import java.util.Vector;
import java.util.Arrays;

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
    final private static String USAGE = 
        "USAGE: java -jar dist/MAC0242.jar <prog1> <prog2> [-v]";

    // Options
    static private boolean help;
    static private boolean usage;
    static private Weather weather = Weather.TROPICAL;
    
    public static void main(String[] argv)
        throws InvalidOperationException
    {
        String[] args = getopt(argv); // Get options
        
        // Help and Usage
        if(help)            { help();                    return; }
        if(args.length < 2) { System.err.println(USAGE); return; }
        
        // Generate map
        World.genesis(2, weather); 
        
        // Menu
        // TODO: automate inserction of programs
        try{
            World.insertArmy(1, "R2D2", 8, 9, args[0]);
            World.insertArmy(2, "Bender"  , 9, 8, args[1]);
        }
        catch(SegmentationFaultException e)
        {
            System.err.println("Invalid position!");
        }
        
        // Game main loop
        for(int t = 0; t < 370; t++)
            World.timeStep();
    }
    
    private static String[] getopt(String[] argv)
    {
        LongOpt[] longopts = 
        {
            // Help and Debug
            new LongOpt("help"   , LongOpt.NO_ARGUMENT, null, 'h'),
            new LongOpt("debug"  , LongOpt.NO_ARGUMENT, null, 'd'),
            new LongOpt("verbose", LongOpt.NO_ARGUMENT, null, 'v'),

            // Weather options
            new LongOpt("artical"     , LongOpt.NO_ARGUMENT, null, 1),
            new LongOpt("desertic"    , LongOpt.NO_ARGUMENT, null, 2),
            new LongOpt("tropical"    , LongOpt.NO_ARGUMENT, null, 3),
            new LongOpt("continental" , LongOpt.NO_ARGUMENT, null, 4),
        };
        //
        Getopt g = new Getopt("MAC0242-Project", argv, "hdv", longopts);
        
        int c;
        while ((c = g.getopt()) != -1)
        {
            switch(c)
            {
                case 'h': // --help
                    help = true;
                    break;
                //
                case 'd': // --debug
                case 'v': // --verbose
                    Debugger.info = true;
                    break;
                //
                case 1: weather = Weather.ARTICAL;
                case 2: weather = Weather.DESERTIC;
                case 3: weather = Weather.TROPICAL;
                case 4: weather = Weather.CONTINENTAL;
                //
                case '?': // getopt() will print the error
                    break;
                //
                default:
                    System.out.println("getopt() returned " + c);
            }
        }
        
        // Return array without the options
        return Arrays.copyOfRange(argv, g.getOptind(), argv.length);
    }
    
    private static void help()
    {
        // Generate and run process
        try {
            Process p = Runtime.getRuntime().exec(
                new String[] {"sh", "-c", "man ./doc/robots.6 < /dev/tty > /dev/tty" });
            
            try{ p.waitFor(); }
            catch(InterruptedException e) {
                System.err.println("Execution interrupted!");
            }
        }
        catch(IOException e)
        {
            System.err.print("[MAIN] Impossible to print man output ");
        }
    }
}
