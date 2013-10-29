package arena;

/* Default libraries */
import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;

/* Libraries */
import parameters.*;


/**
 * <b>In this class, we create and use the armies functions,
 * like insert a new robot in an arm or remove it.
 * We have the vector of robots and the function sort to
 * manipulating the movement order of robots.</b>
 * @author Karina Suemi Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Nascimento Silva
 */

public class RobotList implements Game, Iterable<Robot>
{
    private Robot[] armies;
    private HashMap <Integer,Double> speedy 
        = new HashMap <Integer,Double>();
    final private Random rand = new Random();
    
    int nextRobot  = -1;
    int emptySpace = 0;
    
    RobotList(int nPlayers) 
    {
        Debugger.say("[RobotList] Builded");
        armies = new Robot[nPlayers * ROBOTS_NUM_MAX];
    }
    
    void add(Robot r)
    {
        /* TODO: When the three initial are created,
         *       we will not need this test any more */
        if(r == null) return;
        armies[emptySpace++] = r;
        speedy.put(r.ID, 1.0 * r.speed);
    }
    
    public void remove(Robot robot)
    {
        for(int i = 0; i < emptySpace; i++) 
        {
            if(armies[i] == robot) 
            {
                Robot r = armies[i];
                armies[i] = null;
                speedy.remove(r.ID);
                break;
            }
        }
    }
    
    void sort()
    {
        for(Robot r: armies)
        {
            if(r == null) continue;
            speedy.put(r.ID, rand.nextDouble() + r.speed);
            Debugger.say("[SPEED][", r, "] ", speedy.get(r.ID));
        }
        
        // Debug (unordered array)
        Debugger.print("[INIT] ");
        for(int i = 0; i < emptySpace; i++) 
        {
            Robot r = armies[i];
            Debugger.print( (r != null) ? r : "null" );
            if(i != emptySpace-1) Debugger.print(", ");
        }
        Debugger.say();
        
        // Sorts array untill the first known empty space
        quickSort(0, emptySpace-1);
        
        // Updates first empty space position
        int emptySpace = 0;
        for(Robot r: armies) 
            if(r == null) break; 
            else emptySpace++;
        
        // Debug (sorted array)
        Debugger.print("[SORT] ");
        for(int i = 0; i < emptySpace; i++) 
        {
            Robot r = armies[i];
            Debugger.print( (r != null) ? r : "null" );
            if(i != emptySpace-1) Debugger.print(", ");
        }
        Debugger.say();
    }
    
    public Iterator<Robot> iterator()
    {
        return new RobotListIterator(emptySpace);
    }
    
    private void quickSort(int begin, int end)
    {
        if(begin < end)
        {
            int middle = divide(begin, end);
            quickSort(begin, middle-1);
            quickSort(middle +1, end);
        }
    }
    
    private int divide(int begin,int end)
    {
        int     i = begin -1;
        Robot   x = armies[end];
        
        for(int j = begin; j <= end; j++)
            if(cmpLessRobot(armies[j], x))
            {
                Robot t = armies[++i];
                armies[i] = armies[j];
                armies[j] = t;
            }
        
        return i;
    }
    
    private boolean cmpLessRobot(Robot robotA, Robot robotB)
    {
        /* Comparison function */
        double costA = speedy.get(robotA.ID);
        double costB = speedy.get(robotB.ID);
        
        if(robotA == null) return true;
        if(robotB == null) return false;
        return (costA <= costB);
    }
    
    private class RobotListIterator implements Iterator<Robot>
    {
        private int nextRobot = -1;
        final private int emptySpace;

        public RobotListIterator(int emptySpace)
        {
            this.emptySpace = emptySpace;
        }
        
        public boolean hasNext()
        {
            String pre = "[Iterator] ";
            if(nextRobot + 1 != emptySpace)
            {
                Debugger.say("\n", pre, "Has next!");
                Debugger.say(pre, "[nextRobot:", 
                             armies[nextRobot+1], "]");
                Debugger.say(pre, "[emptySpace:", 
                             emptySpace, "]");
            }
            else Debugger.say("\n", pre, "No next...");
            return nextRobot + 1 != emptySpace;
        }
        
        public Robot next()
        {
            Debugger.say("[Iterator] Getting next!");
            nextRobot++;
            return armies[nextRobot];
        }
        
        public void remove()
        {
            Robot r = armies[nextRobot];
            armies[nextRobot] = null;
            speedy.remove(r.ID);
        }
    }
}
