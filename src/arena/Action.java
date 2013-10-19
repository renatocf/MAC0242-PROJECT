package arena;

import scenario.*;
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
            case "ASK"  : stackable = ASK  (map, turn, op); break;
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
        Stackable[] s = op.getArgument();
        Direction d = (Direction) s[0];
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
        Stackable[] s = op.getArgument();
        Direction d = (Direction) s[0];
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
        Stackable[] s = op.getArgument();
        Direction d = (Direction) s[0];
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
        String pre = "    [HIT]";
        Stackable[] s = op.getArgument();
        
        Attack      atk  = (Attack) s[0];
        Num         num  = (Num)    s[1];
        Direction[] dirs = new Direction[(int)num.getNumber()];
        
        int damage = 0;
        int distance = (int) num.getNumber();
        
        // TODO: PROBLEMS HERE ↓ 
        // If we add more commands to HIT, we 
        // need to change this +2.
        for(int i = 0; i < distance; i++)
            dirs[i] = (Direction) s[i + 2];

        switch (atk.getAttack())
        {
            case "MELEE" : damage = turn.damageMelee; 
                           if(distance > 1)             return false; break;
            case "RANGED": damage = turn.damageRange; 
                           if(distance > turn.maxRange) return false; break;
        }
                
        if(Verbosity.v) 
        {  
            String directions = "";
            Verbosity.debug(pre + "[" + atk.getAttack() + "]");
            for(Direction d: dirs) directions += d.toString() + " ";
            Verbosity.debug(pre + " " + directions);
        } 

        int lookI = turn.i;
        int lookJ = turn.j;
        Scenario thing = null;

        for(Direction d: dirs)
        {
            int[] update = d.get(lookI);
            
            lookI += update[0];
            lookJ += update[1];
            
            if(Verbosity.v) { 
                Verbosity.debug(pre + " " + map.map[lookI][lookJ].toString()); 
            }
            
            if(lookI >= MAP_SIZE
            || lookJ >= MAP_SIZE
            || lookI < 0
            || lookJ < 0) return false;
                        
            thing = map.map[lookI][lookJ].getScenario();
            if(thing != null)
            {
                int done = thing.takeDamage(damage);
                if(Verbosity.v) 
                { 
                    Verbosity.debug(pre + "[FIGHT]");
                    Verbosity.debug("         [DAMAGE:" + damage + "]");
                    Verbosity.debug("         [REMAIN:" + done   + "]"); 
                }
                
                if(thing.getHP() <= 0) 
                {                 
                    World.destroy(lookI, lookJ);
                    if(Verbosity.v) { Verbosity.debug(pre + "[DESTROYED]"); }
                }
                break;
            }
        }   
        
        if(thing == null) 
        {
            if(Verbosity.v) { Verbosity.debug(pre + "[EMPTY]"); }
            return false;
        }
        return true;
    }
    
    static Stackable[] LOOK (Map map, Robot turn, Operation op)
    { 
         // Extract direction info from operation
        Stackable[] s = op.getArgument();
        Direction d = (Direction) s[0];
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

        static Stackable[] ASK (Map map, Robot turn, Operation op)
    {  
        Stackable[] s = op.getArgument();
        Text t = (Text) s[0];
        
        switch (t.toString())
        {
            case "position":
            case "Position": 
                Num one = new Num(1);
                Num x   = new Num(turn.i);
                Num y   = new Num(turn.j);
                s       = new Stackable[3];
                s[2]    = one; s[1] = x; s[0    ] = y;
                break;
            default: 
                Num zero = new Num(0);
                s        = new Stackable[1];
                s[0]     = zero;
                break;
        }
        
        String pre = "    [ASK]";
        if(Verbosity.v) { Verbosity.debug(pre + t);}
        
        return s;
    }
}
