package arena;

// Default libraries
import java.io.IOException;
import java.lang.Thread;

// Libraries
import gui.*;
import robot.*;
import scenario.*;
import operation.*;
import exception.*;
import stackable.*;
import parameters.*;
import stackable.item.*;

public class World implements Game
{
    private static int nPlayers;
    private static Robot[][] armies;
    
    private static int time = 0;
    private static Textual GUI;
    
    private static Map map;
    private static Robot turn;
    
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
        
        GUI = new Textual(map);
        GUI.printMiniMap();
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
        if(!(Verbosity.v)) GUI.paint();
    }
    
    static public Stackable[] ctrl(Operation op)
       throws InvalidOperationException
    {
        Stackable[] stackable = null;
        boolean can = false;
        switch(op.getAction())
        {
            case "MOVE" : can = Action.MOVE (map, turn, op); break;
            case "DRAG" : can = Action.DRAG (map, turn, op); break;    
            case "DROP" : can = Action.DROP (map, turn, op); break;
            case "HIT"  : can = Action.HIT  (map, turn, op); break;
            
            case "LOOK" : stackable = Action.LOOK (map, turn, op); break;
            case "SEE"  : stackable = Action.SEE  (map, turn, op); break;
        }
        
        if(stackable == null) 
        {
            stackable = new Stackable[1]; 
            stackable[0] = new Num( (can)? 1 : 0 );
        }
        return stackable;
    }
    
//    private static boolean MOVE (Operation op) 
//    {
//        // Extract direction info from operation
//        Direction d = (Direction) op.getArgument();
//        int[] update = d.get(turn.i);
//        
//        int newI = turn.i + update[0];
//        int newJ = turn.j + update[1];
//        
//        if(newI >= MAP_SIZE 
//        || newJ >= MAP_SIZE  
//        || newI < 0  
//        || newJ < 0  
//        || map.map[newI][newJ].scenario != null) return false;
//        
//        
//        // Takes out from original position
//        Robot robot = (Robot) map.map[turn.i][turn.j].removeScenario();
//        
//        // Update robot attributes
//        turn.i = newI; 
//        turn.j = newJ;
//        
//        // Goes to the new position in the map
//        map.map[turn.i][turn.j].setScenario(robot);
//        return true;
//    }
//    
//    private static boolean DRAG (Operation op) 
//    { 
//         // Extract direction info from operation
//        Direction d = (Direction) op.getArgument();
//        int[] update = d.get(turn.i);
//        
//        int lookI = turn.i + update[0];
//        int lookJ = turn.j + update[1];
//        
//     
//        int cont = 0;
//        
//        if(lookI >= MAP_SIZE 
//        || lookJ >= MAP_SIZE  
//        || lookI < 0  
//        || lookJ < 0  
//        || map.map[lookI][lookJ].item == null) return false;
//
//        for(int i = 0; i < turn.slots.length && turn.slots[i] != null; i++) cont++;
//        if(cont >= turn.slots.length) return false;
//            
//        String pre = "    [DRAG]";
//        if(Verbosity.v) { Verbosity.debug(pre + map.map[lookI][lookJ].toString()); }
//        turn.slots[cont] = map.map[lookI][lookJ].removeItem();
//        //if(map.map[lookI][lookJ].item == null);
//        if(Verbosity.v) { Verbosity.debug(pre + map.map[lookI][lookJ].toString()); }
//        
//        return true;
//    }
//    
//    private static boolean DROP (Operation op) 
//    {  
//    //*	
//        Direction d = (Direction) op.getArgument();
//        int[] update = d.get(turn.i);
//        
//        int lookI = turn.i + update[0];
//        int lookJ = turn.j + update[1];
//        
//        int cont = 0;
//        
//        if(lookI >= MAP_SIZE 
//        || lookJ >= MAP_SIZE  
//        || lookI < 0  
//        || lookJ < 0  
//        || map.map[lookI][lookJ].item != null) return false;
//        
//        // Takes out from original position
//        Robot robot = (Robot) map.map[turn.i][turn.j].scenario;
//        
//        for(int i = 0; i < turn.slots.length && robot.slots[i] != null; i++) cont++;
//        if(cont == 0) return false;
//            
//        String pre = "    [DROP]";
//        if(Verbosity.v) { Verbosity.debug(pre + map.map[lookI][lookJ].toString()); }
//        //robot.slots[cont] = map.map[lookI][lookJ].removeItem(); // Add item
//        
//        map.map[lookI][lookJ].item = robot.removeSlots(cont - 1);
//        
//        if(Verbosity.v) { Verbosity.debug(pre + map.map[lookI][lookJ].toString()); }
//        
//        return true;
//    }
//    
//    private static boolean HIT  (Operation op) 
//    {  
//        return true;
//    }
//    
//    private static Stackable[] LOOK (Operation op) 
//    { 
//         // Extract direction info from operation
//        Direction d = (Direction) op.getArgument();
//        int[] update = d.get(turn.i);
//        
//        int lookI = turn.i + update[0];
//        int lookJ = turn.j + update[1];
//        
//        if(lookI > MAP_SIZE) lookI %= MAP_SIZE;
//        else if(lookI < 0) lookI += MAP_SIZE;
//        
//        if(lookJ > MAP_SIZE) lookJ %= MAP_SIZE;
//        else if(lookJ < 0) lookJ += MAP_SIZE;
//        
//        if(Verbosity.v)
//        {
//            String pre = "    [LOOK] ";
//            Verbosity.debug(pre + "dir: " + d.toString());
//            Verbosity.debug(pre + "pos: I: " + lookI);
//            Verbosity.debug(pre + "pos: J: " + lookJ);
//        }
//        
//        if(lookI >= MAP_SIZE 
//        || lookJ >= MAP_SIZE  
//        || lookI < 0  
//        || lookJ < 0) return null;
//        
//        if(Verbosity.v)
//        {
//            String pre = "    [LOOK] ";
//            String t = map.map[lookI][lookJ].toString();
//            Verbosity.debug(pre + "ter: " + t);
//        }
//        
//        Stackable[] st = new Stackable[1];
//        st[0] = map.map[lookI][lookJ];
//        
//        // Takes out from original position
//        return st;
//    }
//    
//    public static Stackable[] SEE(Operation op)
//        throws InvalidOperationException
//    {
//        Direction d;
//        
//        Stackable[] st = new Stackable[1];
//        
//        int nTerrain; 
//        if(turn.sight == 1) nTerrain = 7;
//        else nTerrain = 19;
//        
//        Terrain[] ter = new Terrain[nTerrain];
//        
//        int lookI;
//        int lookJ;
//        
//        for(int i = 0; i < nTerrain; i++)
//        {
//            d = new Direction(0, i);
//            
//            int[] update = d.get(turn.i);
//            lookI = turn.i + update[0];
//            lookJ = turn.j + update[1];
//            
//            if(lookI >= MAP_SIZE 
//            || lookJ >= MAP_SIZE  
//            || lookI < 0  
//            || lookJ < 0)         ter[i] = null;
//            
//            else 
//            {  
//                if(i < 7)
//                    ter[i] = map.map[lookI][lookJ];
//                else
//                {
//                    d = new Direction(1, i);
//                    update =  d.get(lookI);
//                    lookI  += update[0];
//                    lookJ  += update[1];
//                    
//                    if(lookI >= MAP_SIZE 
//                    || lookJ >= MAP_SIZE  
//                    || lookI < 0  
//                    || lookJ < 0)         ter[i] = null;
//                    
//                    else  ter[i] = map.map[lookI][lookJ];
//                    
//                }
//            }
//        }
//        Around a = new Around(ter);
//        st[0] = (Stackable) a;
//        return st;
//    }
}

// :¨:
// =%=
// / \
