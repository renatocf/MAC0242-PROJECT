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
    public static void main(String[] args) /* throws StackUnderflowException */
    {
        //HashMap <String, Integer> map = new HashMap <String, Integer>();
        //map.put("RESPOSTA", 42);
        //map.put("PRIMO", 73);
        //
        //System.out.println("val: " + map.get("RESPOSTA"));
        
        /* Parser user = new Parser(); */
        /* Vector<Command> PROG = user.upload(); */
        
        Vector<Command> PROG = new Vector<Command>();
        Text msg = new Text("Eeeeva");
        Num treze = new Num(1);
        Num dez = new Num(10);
        
        //PROG.add(new Command("PUSH", treze,  null));
        //PROG.add(new Command("PUSH", dez,  null));
        //PROG.add(new Command("MOD", null,  null));
        //PROG.add(new Command("DUP", null,  null));
        //PROG.add(new Command("PUSH", treze,  null));
        //PROG.add(new Command("MUL", null,  null));
        //PROG.add(new Command("ADD", null,  null));
        //PROG.add(new Command("DUP", null,  null));
        //PROG.add(new Command("PRN", null,  null));
        //
        //PROG.add(new Command("PUSH", msg,  null));
        //PROG.add(new Command("PRN", null,  null));
        //
        //PROG.add(new Command("PUSH", dez,  null));
        //PROG.add(new Command("DIV", null,  null));
        //PROG.add(new Command("PRN",  null, null));
        PROG.add(new Command("JMP",  treze, null));
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
