package parameters;

public class Verbosity
{
    public static boolean v = false;
    public static void debug(String s)
    {
        System.out.println(s);
    }
    
    public static void debug()
    {
        System.out.println();
    }
}
