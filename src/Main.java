// Libraries
import robot.*;
import exception.*;
import stackable.*;
import java.util.Vector;
import java.util.HashMap;

class Main
{
    public static void main(String[] args) /* throws StackUnderflowException */
    {
        //HashMap <String, Integer> map = new HashMap <String, Integer>();
        //map.put("RESPOSTA", 42);
        //map.put("PRIMO", 73);
        //
        //System.out.println("val: " + map.get("RESPOSTA"));
        
        Vector<Command> PROG = new Vector<Command>();
        Text msg = new Text("Eeeeva");
        
        PROG.add(new Command("PUSH", msg,  null)); 
        PROG.add(new Command("PRN",  null, null));
        PROG.add(new Command("END",  null, null));
        PROG.add(null);
        
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
