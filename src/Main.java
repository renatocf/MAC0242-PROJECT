// Default Libraries
import java.util.Vector;
import java.util.HashMap;

// Libraries
import arena.*;
import parser.*;
import exception.*;
import stackable.*;
import parameters.*;
import robot.Command;

class Main
{
    public static void main(String[] args)
        throws InvalidOperationException
    {
        getopt(args); // Get options
        
        Parser user = new Parser();
        Vector<Command> PROG = user.upload();
        
        //Robot Walle = new Robot("Wall-e", PROG);
        //try {
        //    Walle.identify();
        //    Walle.run();
        //}
        //catch(Exception e)
        //{
        //    System.out.println("Shit: " + e);
        //}
        World.genesis(2, Weather.CONTINENTAL);
        
        for(int t = 0; t < 370; t++)
            World.timeStep();
        
        /* World.print(); */
        
        /* Direction d = new Direction("NE"); */
        /* System.out.println(d); */
        /* int[] qd = d.get(41); */
        /* System.out.println(qd[0] + " " + qd[1]); */
    }

    private static void getopt(String[] args)
    {
        for(String arg: args)
        {
            if(arg.equals("-v")) Verbosity.v = true;
        }
    }
}
