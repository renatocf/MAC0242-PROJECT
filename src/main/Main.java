package main;

// Default Libraries
import java.io.*;
import java.util.Vector;
import java.util.Arrays;

// Libraries
import arena.*;
import players.*;
import exception.*;
import stackable.*;
import parameters.*;
import robot.Command;

// Configs interfaces
import gui.Interfaces;
import random.Weather;

// External Libraries (.jar)
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * <b>Main class</b><br>
 * Controls all the user interactions,
 * the start and end of the program.
 */
class Main 
{
    final private static String USAGE = 
        "USAGE: java -jar dist/MAC0242.jar <prog1> <prog2> <prog3> [-v|-d]";

    // Options
    static private boolean help;
    static private boolean usage;
    static private Weather weather = Weather.TROPICAL;
    static private Interfaces GUI  = Interfaces.GRAPHICAL;
    
    /**
     * <b>Main method</b><br>
     * Gets options from command line, interacts
     * with user and starts the game.
     */
    public static void main(String[] argv)
        throws InvalidOperationException
    {
        String[] args = getopt(argv); // Get options
        
        // Help and Usage
        if(help)            { help();                    return; }
        if(args.length < 3) { System.err.println(USAGE); return; }
        
        // Generate map
        Player[] p = World.genesis(2, weather, GUI);
               
        // Menu
        // TODO: automate inserction of programs
        try{
            World.insertArmy(p[0], "Caprica Six"     , "behaviors/Carrier.asm"  );
            World.insertArmy(p[0], "Number Seventeen", "behaviors/Carrier.asm"  );
            World.insertArmy(p[0], "Megatron"        , "behaviors/Protector.asm");
            
            World.insertArmy(p[1], "Boomer"         , args[0]);
            World.insertArmy(p[1], "Number Eighteen", args[1]);
            World.insertArmy(p[1], "Optimus Prime"  , args[2]);
        }
        catch(SegmentationFaultException e)
        {
            System.err.println("Invalid position!");
        }
        
        // Game main loop
        for(int t = 0; t < 370; t++)
            World.timeStep();
    }
    
    /**
     * Encapsulates the use of the library
     * GetOpt to get the options for the program.
     * @param  argv Argument vector (with options)
     * @return Argument vector without options (args)
     */
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
            
            // Interfaces
            new LongOpt("textual", LongOpt.NO_ARGUMENT, null, 't'),
        };
        //
        Getopt g = new Getopt("MAC0242-Project", argv, "hdvt", longopts);
        
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
                case 1: weather = Weather.ARTICAL;     break;
                case 2: weather = Weather.DESERTIC;    break;
                case 3: weather = Weather.TROPICAL;    break;
                case 4: weather = Weather.CONTINENTAL; break;
                //
                case 't':
                    GUI = Interfaces.TEXTUAL;
                    break;
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
    
    /** 
     * Execute man page to show the help
     */
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
