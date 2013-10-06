package robot;
import stackable.*;

public class Command
{
    final private String    function;
    final private Stackable attribute;
    final private String    label;

    public Command(String func, Stackable atr, String lbl)
    {
        this.function  = func;
        this.attribute = atr;
        this.label     = lbl;
    }

    public String toString()
    {
        return this.label + ": " + this.function + " " + this.attribute;
    }

    public String    getFunction  () { return this.function;  }
    public Stackable getAttribute () { return this.attribute; }
    public String    getLabel     () { return this.label;     }
}
