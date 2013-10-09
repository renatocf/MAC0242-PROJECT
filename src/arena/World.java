package arena;

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
    }
    
    static public Num ctrl(Operation op)
    {
        switch(op.getAction())
        {
            case "MOVE" : MOVE(op); break;
            case "DRAG" : DRAG(op); break;    
            case "DROP" : DROP(op); break;
            case "HIT"  : HIT (op); break;        
        }
        
        Num answer = new Num(1);
        return answer;
    }
    
    private static void MOVE (Operation op) 
    {
        // Extract direction info from operation
        Direction d = (Direction) op.getArgument();
        int[] update = d.get(turn.i);
        
        // Takes out from original position
        Robot robot = (Robot) map.map[turn.i][turn.j].removeScenario();
        
        System.out.println("\n[" + turn.toString() + "]");
        System.out.println("=========================");
        
        // Update robot attributes
        System.out.println("I: " + turn.i + "J: " + turn.j);
        turn.i += update[0]; 
        turn.j += update[1];
        System.out.println("I: " + turn.i + "J: " + turn.j);
        
        // Goes to the new position in the map
        map.map[turn.i][turn.j].setScenario(robot);
        
        System.out.println("Move to " + d.toString());
    }
    
    private static void DRAG (Operation op) {  }
    private static void DROP (Operation op) {  }
    private static void HIT  (Operation op) {  }
    
    public static void print()
    {
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print("  /\\ ");
        System.out.println(" ");
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print(" /  \\");
        System.out.println(" ");
        
        for(int i = 0; i < MAP_SIZE; i++)
        {
            boolean odd = (i % 2 == 1) ? true : false;
            
            System.out.print( (odd) ? "  " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item     = " ";
                String scenario = " ";
                
                if      (map.map[i][j].scenario == null)          scenario = " ";
                else if (map.map[i][j].scenario instanceof Base ) scenario = "ß";
                else if (map.map[i][j].scenario instanceof Rock ) scenario = "ø";
                else if (map.map[i][j].scenario instanceof Tree ) scenario = "ł";
                else if (map.map[i][j].scenario instanceof Water) scenario = "_";
                else if (map.map[i][j].scenario instanceof Robot) scenario = "R";
                
                if      (map.map[i][j].item == null)            item = " ";
                else if (map.map[i][j].item instanceof Crystal) item = "*";
                else if (map.map[i][j].item instanceof Stone)   item = ".";
                
                System.out.print("| " + scenario + item + " ");
            }
            System.out.println("|");
            
            if(i == MAP_SIZE-1) break;
            for(int j = 0; j < MAP_SIZE; j++)
                System.out.print( (odd) ? "  /\\ " : " \\  /");
            System.out.println( (odd) ? " /" : "\\");
            
            for(int j = 0; j < MAP_SIZE; j++)
                System.out.print( (odd) ? " /  \\" : "  \\/ ");
            System.out.println( (odd) ? "/" : " \\"); 
        }
            
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print(" \\  /");
        System.out.println(" ");
        for(int j = 0; j < MAP_SIZE; j++)
            System.out.print("  \\/ ");
        System.out.println(" ");
    }
}
