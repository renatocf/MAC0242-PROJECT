package arena;

import stackable.*;
import exception.*;
import parameters.*;
import operation.Operation;

public class Action implements Game
{      
    static Stackable[] ctrl (Map map, Robot turn, Operation op)
       throws InvalidOperationException
    {
        Stackable[] stackable = null;
        boolean can = false;
        switch(op.getAction())
        {
            case "MOVE" : can = MOVE (map, turn, op); break;
            case "DRAG" : can = DRAG (map, turn, op); break;    
            case "DROP" : can = DROP (map, turn, op); break;
            case "HIT"  : can = HIT  (map, turn, op); break;
            
            case "LOOK" : stackable = LOOK (map, turn, op); break;
            case "SEE"  : stackable = SEE  (map, turn, op); break;
        }
        
        if(stackable == null) 
        {
            stackable = new Stackable[1]; 
            stackable[0] = new Num( (can) ? 1 : 0 );
        }
        return stackable;
    }
    
    static boolean MOVE (Map map, Robot turn, Operation op) 
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
    
    static boolean DRAG (Map map, Robot turn, Operation op)
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
    
    static boolean DROP (Map map, Robot turn, Operation op)
    {  
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
        
        map.map[lookI][lookJ].item = robot.removeSlots(cont - 1);
        
        if(Verbosity.v) { Verbosity.debug(pre + map.map[lookI][lookJ].toString()); }
        
        return true;
    }
    
    static boolean HIT  (Map map, Robot turn, Operation op)
    {  
        return true;
    }
    
    static Stackable[] LOOK (Map map, Robot turn, Operation op)
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
    
    static Stackable[] SEE (Map map, Robot turn, Operation op)
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
}
