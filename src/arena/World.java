package arena;

// Default libraries
import java.io.IOException;
import java.lang.Thread;

// Libraries
import robot.*;
import scenario.*;
import operation.*;
import exception.*;
import stackable.*;
import parameters.*;
import stackable.item.*;

public class World implements Game, Colors
{
    private static Map map;
    private static int nPlayers;
    private static Robot[][] armies;
    
    private static Robot turn;
    private static int time = 0;
    
    public static void genesis(int np, Weather w)
        throws InvalidOperationException
    {
        // Set game configurations
        nPlayers = np;
        map      = new Map(w);
        armies   = new Robot[nPlayers][ROBOTS_NUM_MAX];
        
        // Create map
        Robot[][] initial = map.genesis(nPlayers);
        
        // Set up initial robots
        for(int i = 0; i < nPlayers; i++)
            for(int j = 0; j < ROBOTS_NUM_INITIAL; j++)
                armies[i][j] = initial[i][j];
    }
    
    public static void timeStep()
    {
        time++; // On each time step, increments time
        
        if(Verbosity.v)
        {
            String pre = "\n[WORLD] ====================== ";
            Verbosity.debug(pre + time + "ts");
        }

        for(int i = 0; i < nPlayers; i++)
        {
            for(int j = 0; armies[i][j] != null; j++)
            {
                turn = armies[i][j];
                
                if(Verbosity.v) 
                {
                    System.out.print("\n[" + turn.toString() + "]");
                    System.out.println("[" + time + "ts]");
                }
                
                try { turn.run(); }
                catch (Exception e) 
                {
                    System.out.println("["+ turn.toString() +"] " + e);
                }
            }
        }
        if(!(Verbosity.v)) paint();
    }
    
    static public Stackable[] ctrl(Operation op)
       throws InvalidOperationException
    {
        Stackable[] stackable = null;
        boolean can = false;
        switch(op.getAction())
        {
            case "MOVE" : can = MOVE(op); break;
            case "DRAG" : can = DRAG(op); break;    
            case "DROP" : can = DROP(op); break;
            case "HIT"  : can = HIT (op); break;
            
            case "LOOK" : stackable = LOOK(op); break;
            case "SEE"  : stackable = SEE (op); break;
        }
        
        if(stackable == null) 
        {
            stackable = new Stackable[1]; 
            stackable[0] = new Num( (can)? 1 : 0 );
        }
        return stackable;
    }
    
    private static boolean MOVE (Operation op) 
    {
        // Extract direction info from operation
        Direction d = (Direction) op.getArgument();
        int[] update = d.get(turn.i);
        
        int newI = turn.i + update[0];
        int newJ = turn.j + update[1];
        
        if(newI >= MAP_SIZE 
        || newJ >= MAP_SIZE  
        || newI < 0  
        || newJ < 0  
        || map.map[newI][newJ].scenario != null) return false;
        
        
        // Takes out from original position
        Robot robot = (Robot) map.map[turn.i][turn.j].removeScenario();
        
        // Update robot attributes
        turn.i = newI; 
        turn.j = newJ;
        
        // Goes to the new position in the map
        map.map[turn.i][turn.j].setScenario(robot);
        return true;
    }
    
    private static boolean DRAG (Operation op) 
    { 
         // Extract direction info from operation
        Direction d = (Direction) op.getArgument();
        int[] update = d.get(turn.i);
        
        int lookI = turn.i + update[0];
        int lookJ = turn.j + update[1];
        
     
        int cont = 0;
        
        if(lookI >= MAP_SIZE 
        || lookJ >= MAP_SIZE  
        || lookI < 0  
        || lookJ < 0  
        || map.map[lookI][lookJ].item == null) return false;

        for(int i = 0; i < turn.slots.length && turn.slots[i] != null; i++) cont++;
        if(cont >= turn.slots.length) return false;
            
        String pre = "    [DRAG]";
        if(Verbosity.v) { Verbosity.debug(pre + map.map[lookI][lookJ].toString()); }
        turn.slots[cont] = map.map[lookI][lookJ].removeItem();
        //if(map.map[lookI][lookJ].item == null);
        if(Verbosity.v) { Verbosity.debug(pre + map.map[lookI][lookJ].toString()); }
        
        return true;
    }
    
    private static boolean DROP (Operation op) 
    {  
    //*	
        Direction d = (Direction) op.getArgument();
        int[] update = d.get(turn.i);
        
        int lookI = turn.i + update[0];
        int lookJ = turn.j + update[1];
        
        int cont = 0;
        
        if(lookI >= MAP_SIZE 
        || lookJ >= MAP_SIZE  
        || lookI < 0  
        || lookJ < 0  
        || map.map[lookI][lookJ].item != null) return false;
        
        // Takes out from original position
        Robot robot = (Robot) map.map[turn.i][turn.j].scenario;
        
        for(int i = 0; i < turn.slots.length && robot.slots[i] != null; i++) cont++;
        if(cont == 0) return false;
            
        String pre = "    [DROP]";
        if(Verbosity.v) { Verbosity.debug(pre + map.map[lookI][lookJ].toString()); }
        //robot.slots[cont] = map.map[lookI][lookJ].removeItem(); // Add item
        
        map.map[lookI][lookJ].item = robot.removeSlots(cont - 1);
        
        if(Verbosity.v) { Verbosity.debug(pre + map.map[lookI][lookJ].toString()); }
        
        return true;
    }
    
    private static boolean HIT  (Operation op) 
    {  
        return true;
    }
    
    private static Stackable[] LOOK (Operation op) 
    { 
         // Extract direction info from operation
        Direction d = (Direction) op.getArgument();
        int[] update = d.get(turn.i);
        
        int lookI = turn.i + update[0];
        int lookJ = turn.j + update[1];
        
        if(lookI > MAP_SIZE) lookI %= MAP_SIZE;
        else if(lookI < 0) lookI += MAP_SIZE;
        
        if(lookJ > MAP_SIZE) lookJ %= MAP_SIZE;
        else if(lookJ < 0) lookJ += MAP_SIZE;
        
        if(Verbosity.v)
        {
            String pre = "    [LOOK] ";
            Verbosity.debug(pre + "dir: " + d.toString());
            Verbosity.debug(pre + "pos: I: " + lookI);
            Verbosity.debug(pre + "pos: J: " + lookJ);
        }
        
        if(lookI >= MAP_SIZE 
        || lookJ >= MAP_SIZE  
        || lookI < 0  
        || lookJ < 0) return null;
        
        if(Verbosity.v)
        {
            String pre = "    [LOOK] ";
            String t = map.map[lookI][lookJ].toString();
            Verbosity.debug(pre + "ter: " + t);
        }
        
        Stackable[] st = new Stackable[1];
        st[0] = map.map[lookI][lookJ];
        
        // Takes out from original position
        return st;
    }
    
    public static Stackable[] SEE(Operation op)
        throws InvalidOperationException
    {
        Direction d;
        
        Stackable[] st = new Stackable[1];
        
        int nTerrain; 
        if(turn.sight == 1) nTerrain = 7;
        else nTerrain = 19;
        
        Terrain[] ter = new Terrain[nTerrain];
        
        int lookI;
        int lookJ;
        
        for(int i = 0; i < nTerrain; i++)
        {
            d = new Direction(0, i);
            
            int[] update = d.get(turn.i);
            lookI = turn.i + update[0];
            lookJ = turn.j + update[1];
            
            if(lookI >= MAP_SIZE 
            || lookJ >= MAP_SIZE  
            || lookI < 0  
            || lookJ < 0)         ter[i] = null;
            
            else 
            {  
                if(i < 7)
                    ter[i] = map.map[lookI][lookJ];
                else
                {
                    d = new Direction(1, i);
                    update =  d.get(lookI);
                    lookI  += update[0];
                    lookJ  += update[1];
                    
                    if(lookI >= MAP_SIZE 
                    || lookJ >= MAP_SIZE  
                    || lookI < 0  
                    || lookJ < 0)         ter[i] = null;
                    
                    else  ter[i] = map.map[lookI][lookJ];
                    
                }
            }
        }
        Around a = new Around(ter);
        st[0] = (Stackable) a;
        return st;
    }
    
    public static void paint()
    {
        try { 
            Thread.sleep(SPEED);
        } catch (Exception e) {
        }
        // Clear screen
        System.out.print(CLEAR);
        print();
    }
    
    public static void printMiniMap()
    {
        map.print();
        try { 
            Thread.sleep(3000);
        } catch (Exception e) {
        }
    }

    private static String hexTop(int type, String midColor)
    {
        if(type == 1)
            return RESTORE + "/" + midColor +  " "  + RESTORE + "\\"; /*  / \\ */
        else
            return RESTORE + "/" + midColor + "   " + RESTORE + "\\"; /* /   \\ */
    }

    private static String hexBot(int type, String midColor)
    {
        if(type == 1)
            return RESTORE + "\\" + midColor + "   " + RESTORE + "/"; /* \   // */
        else
            return RESTORE + "\\" + midColor +  " "  + RESTORE + "/"; /*  \ / */
    }

    private static String colorof(Appearence ap)
    {
        if(ap == null) return RESTORE;
        switch (ap)
        {
            case DIRT   : return ON_BLACK; 
            case GRASS  : return ON_IGREEN;
            case TUNDRA : return ON_WHITE; 
            
            case ROCKY  : return ON_IBLACK;
            case ICE    : return ON_CYAN;  
            case JUNGLE : return ON_IGREEN;
            case WATER  : return ON_BLUE;  
            case SAND   : return ON_YELLOW;
            default     : return RESTORE;  
        }
    }

    private static Appearence terrainLook(int i, int j)
    {
        // For arrays out of bounds, we'll have null.
        // In colorof function, its color will be RESTORE
        if(i < 0 || j < 0 || i >= MAP_SIZE || j >= MAP_SIZE) return null;
        return map.map[i][j].appearence;
    }
    
    public static void print()
    {
        // Print scenario top
        for(int j = 0; j < MAP_SIZE-1; j++)
            System.out.print("   .  ");
        System.out.println("   .");
        
        System.out.print("  " + hexTop( 1, colorof(terrainLook(0,0)) ));
        for(int j = 1; j < MAP_SIZE; j++)
            System.out.print("   " + hexTop( 1, colorof(terrainLook(0,j)) ));
        System.out.println(RESTORE);
        
        System.out.print(" " + hexTop(2, colorof(terrainLook(0,0)) ));
        for(int j = 1; j < MAP_SIZE; j++)
            System.out.print(" " + hexTop(2, colorof(terrainLook(0,j)) ));
        System.out.println(RESTORE);
        
        // Print ordinary lines
        for(int i = 0; i < MAP_SIZE; i++)
        {
            /* System.out.print(ON_GREEN); */
            boolean odd = (i % 2 == 1) ? true : false;
            
            // Print itens and scenario
            System.out.print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item1     = " ", item2     = " ";
                String scenario1 = " ", scenario2 = " ";
                
                if      (map.map[i][j].scenario == null)          { scenario1 = " "; }
                else if (map.map[i][j].scenario instanceof Base ) { scenario1 = ON_RED   + "ß"; scenario2 = ON_RED   + "ß"; }
                else if (map.map[i][j].scenario instanceof Rock ) { scenario1 = ON_BLACK + "⌈"; scenario2 = ON_BLACK + "⌉"; }
                else if (map.map[i][j].scenario instanceof Tree ) { scenario1 = ON_GREEN + "☘"; scenario2 = ON_GREEN + "☘"; }
                else if (map.map[i][j].scenario instanceof Water) { scenario1 = ON_BLUE  + "≈"; scenario2 = ON_BLUE  + "≈"; }
                else if (map.map[i][j].scenario instanceof Robot) { scenario1 = ON_BLACK + "("; scenario2 = ON_BLACK + ")"; }
                
                if      (map.map[i][j].item == null)            { item1 = " "; item2 = " "; }
                else if (map.map[i][j].item instanceof Crystal) { item1 = ON_YELLOW + "/"; item2 = ON_YELLOW + "\\"; }
                else if (map.map[i][j].item instanceof Stone)   { item1 = ON_BLACK  + "."; item2 = ON_BLACK  + "."; }
                
                item1     += colorof(terrainLook(i,j));
                item2     += colorof(terrainLook(i,j));
                scenario1 += colorof(terrainLook(i,j));
                scenario2 += colorof(terrainLook(i,j));
                
                System.out.print(RESTORE + "|" + colorof(terrainLook(i,j)) 
                                + scenario1 + scenario2 + " " + item1 + item2);
            }
            System.out.println(RESTORE + "|");
            
            // Center of hexagons
            System.out.print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item1     = " ", item2     = " ";
                String scenario1 = " ", scenario2 = " ";
                
                if      (map.map[i][j].scenario == null)          { scenario1 = " "; }
                else if (map.map[i][j].scenario instanceof Base ) { scenario1 = ON_RED   + "ß"; scenario2 = ON_RED   + "ß"; }
                else if (map.map[i][j].scenario instanceof Rock ) { scenario1 = ON_BLACK + "⌊"; scenario2 = ON_BLACK + "⌋"; }
                else if (map.map[i][j].scenario instanceof Tree ) { scenario1 = ON_GREEN + "☘"; scenario2 = ON_GREEN + "☘"; }
                else if (map.map[i][j].scenario instanceof Water) { scenario1 = ON_BLUE  + "≈"; scenario2 = ON_BLUE  + "≈"; }
                else if (map.map[i][j].scenario instanceof Robot) { scenario1 = ON_BLACK + "/"; scenario2 = ON_BLACK + "\\"; }
                
                if      (map.map[i][j].item == null)            { item1 = " "; item2 = " "; }
                else if (map.map[i][j].item instanceof Crystal) { item1 = ON_YELLOW + "\\"; item2 = ON_YELLOW + "/"; }
                else if (map.map[i][j].item instanceof Stone)   { item1 = ON_BLACK  + "¨";  item2 = ON_BLACK  + "¨"; }
                
                item1     += colorof(terrainLook(i,j));
                item2     += colorof(terrainLook(i,j));
                scenario1 += colorof(terrainLook(i,j)); 
                scenario2 += colorof(terrainLook(i,j)); 
                
                System.out.print(RESTORE + "|" + colorof(terrainLook(i,j)) 
                                + scenario1 + scenario2 + " " + item1 + item2);
            }
            System.out.println(RESTORE + "|");
            
            if(i == MAP_SIZE-1) break;

            // First line of hexagons
            System.out.print( (odd) 
                    ? "  "  + hexTop(1, colorof( terrainLook(i,  0) )) 
                    : " "   + hexBot(1, colorof( terrainLook(i+1,0) )) 
            );
            for(int j = 1; j < MAP_SIZE; j++)
                System.out.print( (odd) 
                    ? colorof( terrainLook(i,  j-1) ) + "   " + hexTop(1, colorof( terrainLook(i+1,j) )) 
                    : colorof( terrainLook(i+1,j-1) ) + " "   + hexBot(1, colorof( terrainLook(i,  j) ))
            );
            System.out.println( (odd) 
                    ? colorof( terrainLook(i,MAP_SIZE-1) ) + "   " + RESTORE + "/" 
                    : colorof( terrainLook(i+1,MAP_SIZE-1) ) + " "   + RESTORE + "\\"
            );
            
            // Second line of hexagons
            System.out.print( (odd) 
                    ? " "   + hexTop(2, colorof( terrainLook(i,0) ))
                    : "  "  + hexBot(2, colorof( terrainLook(i,0) ))
            );
            for(int j = 1; j < MAP_SIZE; j++)
                System.out.print( (odd) 
                    ? colorof( terrainLook(i,  j-1) ) + " "   + hexTop(2, colorof( terrainLook(i+1,j) ))
                    : colorof( terrainLook(i+1,j-1) ) + "   " + hexBot(2, colorof( terrainLook(i,  j) ))
            );
            System.out.println( (odd) 
                    ? colorof( terrainLook(i,MAP_SIZE-1) ) + " "   + RESTORE + "/" 
                    : colorof( terrainLook(i,MAP_SIZE-1) ) + "   " + RESTORE + "\\"
            ); 
        }
            
        // Print scenario bottom
        System.out.print(" " + hexBot(1, colorof(terrainLook(MAP_SIZE-1,0)) ));
        for(int j = 1; j < MAP_SIZE; j++)
            System.out.print(" " + hexBot(1, colorof(terrainLook(MAP_SIZE-1,j)) ));
        System.out.println(RESTORE);
        
        System.out.print("  " + hexBot(2, colorof(terrainLook(MAP_SIZE-1,0)) ));
        for(int j = 1; j < MAP_SIZE; j++)
            System.out.print("   " + hexBot(2, colorof(terrainLook(MAP_SIZE-1,j)) ));
        System.out.println(RESTORE);
        
        for(int j = 0; j < MAP_SIZE-1; j++)
            System.out.print("   '  ");
        System.out.println("   '");
    }
}

// :¨:
// =%=
// / \
