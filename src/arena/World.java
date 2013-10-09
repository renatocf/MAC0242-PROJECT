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
import stackable.item.*;

public class World implements Parameters
{
    private static Map map;
    private static int nPlayers;
    private static Robot[][] armies;
    
    private static Robot turn;
    private static int time = 0;
    
    public static void genesis(int np, Weather w)
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
        for(int i = 0; i < nPlayers; i++)
        {
            for(int j = 0; armies[i][j] != null; j++)
            {
                turn = armies[i][j];
                try { turn.run(); }
                catch (Exception e) 
                {
                    System.out.println("["+ turn.toString() +"] " + e);
                }
            }
            
        }
        
        try { 
            Thread.sleep(500);
        } catch (Exception e) {
        }
        System.out.print("\n\n\n\n");
        print();
    }
    
    static public Num ctrl(Operation op)
    {
        boolean can = false;
        switch(op.getAction())
        {
            case "MOVE" : can = MOVE(op); break;
            case "DRAG" : can = DRAG(op); break;    
            case "DROP" : can = DROP(op); break;
            case "HIT"  : can = HIT (op); break;        
        }
        Num answer = new Num( (can)? 1 : 0 ) ;
        return answer;
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
        
        
        // Takes out from original position
        Robot robot = (Robot) map.map[turn.i][turn.j].scenario;
        
        for(int i = 0; robot.slots[i] != null; i++) cont++;
        if(cont == robot.slots.length) return false;
            
        robot.slots[cont] = map.map[lookI][lookJ].removeItem();
        return true;
    }
    
    private static boolean DROP (Operation op) 
    {  
        return true;
    }
    
    private static boolean HIT  (Operation op) 
    {  
        return true;
    }
    
    public static void print()
    {
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print("   .  ");
        System.out.println(" ");
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print("  / \\ ");
        System.out.println(" ");
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print(" /   \\");
        System.out.println(" ");
        
        for(int i = 0; i < MAP_SIZE; i++)
        {
            boolean odd = (i % 2 == 1) ? true : false;
            
            System.out.print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item     = " ";
                String scenario = " ";
                
                if      (map.map[i][j].scenario == null)          scenario = " ";
                else if (map.map[i][j].scenario instanceof Base ) scenario = "ß";
                else if (map.map[i][j].scenario instanceof Rock ) scenario = "ø";
                else if (map.map[i][j].scenario instanceof Tree ) scenario = "☘";
                else if (map.map[i][j].scenario instanceof Water) scenario = "≈";
                else if (map.map[i][j].scenario instanceof Robot) scenario = "R"; 
                
                if      (map.map[i][j].item == null)            item = " ";
                else if (map.map[i][j].item instanceof Crystal) item = "◇";
                else if (map.map[i][j].item instanceof Stone)   item = ".";
                
                System.out.print("| " + scenario + " " + item + " ");
            }
            System.out.println("|");
            
            System.out.print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
                System.out.print("|     ");
            System.out.println("|");
            
            if(i == MAP_SIZE-1) break;
            for(int j = 0; j < MAP_SIZE; j++)
                System.out.print( (odd) ? "  / \\ " : " \\   /");
            System.out.println( (odd) ? "  /" : " \\");
            
            for(int j = 0; j < MAP_SIZE; j++)
                System.out.print( (odd) ? " /   \\" : "  \\ / ");
            System.out.println( (odd) ? " /" : "  \\"); 
        }
            
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print(" \\   /");
        System.out.println(" ");
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print("  \\ / ");
        System.out.println(" ");
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print("   '  ");
        System.out.println(" ");
    }
}

// :¨:
// =%=
// / \
