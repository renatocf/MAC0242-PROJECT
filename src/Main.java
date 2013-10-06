// Default Libraries
import java.util.Vector;
import java.util.HashMap;

// Libraries
import robot.*;
import exception.*;
import stackable.*;
import parser.*;

class Main
{
    public static void main(String[] args)
    {
        Parser user = new Parser();
        Vector<Command> PROG = user.upload();
        
        RVM Walle = new RVM(PROG);
        try {
            Walle.ctrl();
        }
        catch(Exception e)
        {
            System.out.println("Shit: " + e);
        }
    }
}
