// Default Libraries
import java.util.Vector;
import java.util.HashMap;

// Libraries
import arena.*;
import robot.Command;
import exception.*;
import stackable.*;
import parser.*;

class Main
{
    public static void main(String[] args)
        throws InvalidOperationException
    {
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
        World.print();
        for(int t = 0; t < 370; t++)
            World.timeStep();
        
        /* World.print(); */
        
        /* Direction d = new Direction("NE"); */
        /* System.out.println(d); */
        /* int[] qd = d.get(41); */
        /* System.out.println(qd[0] + " " + qd[1]); */
    }
}
