package parameters;

public class Debugger
{
    public static boolean info = false;
    public static void print(Object ... strings)
    {
        if(!info) return;
        for(Object s: strings) System.out.print(s.toString());
    }

    public static void say(Object ... strings)
    {
        if(!info) return;
        print(strings);
        System.out.println();
    }
}
