package arena;

/* Default libraries */
import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;

/* Libraries */
import exception.*;
import operation.*;
import parameters.*;

/**
 * Auxiliar class RobotList<br>
 * Impelments a list to be used within
 * the World to control the arena.
 *
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
public class RobotList implements Game, Iterable<Robot>
{
    // Robot's list and info
    private Robot[] armies;
    private HashMap <Integer,Double> speedy 
        = new HashMap <Integer,Double>();
    
    private HashMap <Robot,Operation> actions
        = new HashMap <Robot,Operation>();
    
    // Random number generator
    final private Random rand = new Random();
    
    // Last empty space
    int emptySpace = 0;
    
    /**
     * Default Constructor<br>
     * Create a list of robots for 'n' players,
     * with ROBOTS_NUM_MAX robots for each.
     * 
     * @param nPlayers Number of players
     */
    RobotList(int nPlayers) 
    {
        Debugger.say("[RobotList] Builded");
        armies = new Robot[nPlayers * ROBOTS_NUM_MAX];
    }
    
    /** 
     * Add a robot in the list.
     * @param robot Reference to a robot
     */
    void add(Robot robot)
    {
        /* TODO: When the three initial are created,
         *       we will not need this test any more */
        if(robot == null) return;
        armies[emptySpace++] = robot;
        speedy.put(robot.ID, 1.0 * robot.speed);
        actions.put(robot, null);
    }
    
    /**
     * Remove a robot from the list.
     * @param robot Reference to the robot 
     *              up to be removed
     */
    void remove(Robot robot)
    {
        for(int i = 0; i < emptySpace; i++) 
        {
            if(armies[i] == robot) 
            {
                Robot r = armies[i];
                armies[i] = null;
                speedy.remove(r.ID);
                actions.remove(robot);
                break;
            }
        }
    }
    
    void setOperation(Robot robot, Operation op)
        throws NotInitializedException
    {
        if(actions.containsKey(robot))
            actions.put(robot, op);
        else throw new NotInitializedException(robot.toString());
    }
    
    Operation getOperation(Robot robot)
        throws NotInitializedException
    {
        if(actions.containsKey(robot))
            return actions.get(robot);
        else throw new NotInitializedException(robot.toString());
    }
    
    /**
     * Generate a new iterator.
     * @return Iterator to the robot list
     */
    public Iterator<Robot> iterator()
    {
        return new RobotListIterator(emptySpace);
    }
    
    /**
     * Sort the robot list accordingly to 
     * the robots speed. If there are conflicts,
     * solve the randomically.
     */
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
    
    /**
     * Quicksort implementation to be
     * used over the Robot List.
     * @param begin Start of the subarray
     * @param end   End of the subarray
     */
    private void quickSort(int begin, int end)
    {
        if(begin < end)
        {
            int middle = divide(begin, end);
            quickSort(begin, middle-1);
            quickSort(middle +1, end);
        }
    }
    
    /**
     * Auxiliar funcion for quicksort 
     * @param begin Start of the subarray
     * @param end   End of the subarray
     */
    private int divide(int begin, int end)
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
    
    /**
     * Comparison function used for quicksort
     * @param robotA First robot
     * @param robotB Second robot
     */
    private boolean cmpLessRobot(Robot robotA, Robot robotB)
    {
        /* Comparison function */
        double costA = speedy.get(robotA.ID);
        double costB = speedy.get(robotB.ID);
        
        if(robotA == null) return true;
        if(robotB == null) return false;
        return (costA <= costB);
    }
    
    /** 
     * <b>Inner class for iterator</b><br>
     * Inner class implementing interface 
     * Iterator to run over the Robot List.
     */
    private class RobotListIterator implements Iterator<Robot>
    {
        private int nextRobot = -1;
        final private int emptySpace;
        
        /**
         * Default constructor<br>
         * @param emptySpace Position untill which
         *                   it is possible to find
         *                   robots
         */
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
