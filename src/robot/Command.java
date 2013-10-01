package robot;
import stackable.*;

public class Command
{
    final private String    command;
    final private Stackable attribute;
    final private String    label;

    public Command(String cmd, Stackable atr, String lbl)
    {
        this.command = cmd;
        this.attribute = atr;
        this.label = lbl;
    }

    public String toString()
    {
        return this.label + ": " + this.command + " " + this.attribute;
    }

    public String    getCommand   () { return this.command;  }
    public Stackable getAttribute () { return this.attribute; }
    public String    getLabel     () { return this.label;    }
}
