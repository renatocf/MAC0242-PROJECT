package robot;

public interface Returns
{   
    public final static int TRUE            =  1;
    public final static int SUCCEDED        =  1;

    public final static int FALSE           =  0;
    public final static int NOT_SUCCEDED    =  0;
    public final static int NO_TARGET       =  0;
    public final static int NO_ENERGY       =  0; //= -1;
    public final static int END_OF_MAP      =  0; //= -2;
    public final static int BLOCKED         =  0; //= -3;
    public final static int FULL_SLOTS      =  0; //= -3;
    public final static int EMPTY_SLOTS     =  0; //= -3;
    public final static int OUT_OF_RANGE    =  0; //= -3;
    public final static int FRIENDLY_FIRE   =  0; //= -4;
    public final static int INVALID_ACTION  =  0; //= -5;
}
