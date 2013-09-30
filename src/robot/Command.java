package robot;
import stackable.*;

public class Command
{
    final private String    command;
    final private Stackable atribute;
    final private String    label;

    public Command(String cmd, Stackable atr, String lbl)
    {
        this.command = cmd;
        this.atribute = atr;
        this.label = lbl;
    }

    public String toString()
    {
        return this.label + ": " + this.command + " " + this.atribute;
    }

    public String    getCommand  () { return this.command;  }
    public Stackable getAtribute () { return this.atribute; }
    public String    getLabel    () { return this.label;    }
}
