package arena;

// Default libraries
import java.util.Vector;

// Libraries
import robot.*;
import exception.*;
import stackable.*;
import stackable.item.Item;
import scenario.Scenario;

/**
 * <b>Robot</b><br>
 * Function with the general 
 * characteristics of a robot, 
 * providing the weakest and most
 * general type to the player.
 * 
 * @author Karina Suemi Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Nascimento Silva
 */
public class Robot implements Scenario
{
    // ID
    final protected String name;
    final protected int    team;
    final protected int    ID;
    
    // Position
    protected int i; // Line
    protected int j; // Column
    
    // Hardware
    protected Item[] slots;
    protected RVM positronic;
    
    // Energy
    protected int HP;
    protected int power;
    
    // Combat
    protected int damageMelee;
    protected int damageRange;
    protected int maxRange;
    protected int forceShield;
    
    // Capacities
    final protected int speed;
    final protected int maxHP;
    final protected int maxPower;
    final protected int costMove;
    final protected int sight;
    final protected int costTime;
    
    // Robot ON/OFF
    private boolean connection = true;
    
    /**
     * Default constructor.
     * @param baptism Name of the robot
     * @param team    Ownew of the robot
     * @param ID      Unique identification 
     *                for the doctor
     * @param i       Vertical position
     *                in the map
     * @param j       Horizontal position 
     *                in the map
     * @param PROG    Program with the actions
     *                to be done by the robot
     */
    public Robot(String baptism, int team, int ID, 
                 int i, int j, Vector<Command> PROG)
    {
        // ID
        this.name = baptism;
        this.team = team;
        this.ID   = ID;
        
        // Position
        this.i = i;
        this.j = j;
        
        // Hardware
        this.slots = new Item[1];
        this.positronic = new RVM (PROG);
        
        // Energy
        this.HP         = 12;
        this.power      = 12;
        
        // Combat
        this.damageMelee = 1;
        this.damageRange = 0;
        this.maxRange    = 0;
        this.forceShield = 0;
        
        // Capacities
        this.speed       = 10;
        this.maxHP       = 12;
        this.maxPower    = 24;
        this.costMove    = 3;
        this.sight       = 1;
        this.costTime    = 5;
    }
    
    /**
     * Take out an item from inside the robot.
     * @param position Position of the item
     *                 up to be removed inside
     *                 its cargo
     */
    public Item removeSlots(int position)
    {
    	Item item = this.slots[position];
    	slots[position] = null;
    	return item;
    }
    
    /**
     * Prints a detailed info about the robot.<br>
     * Used mainly with debug purposes.
     */
    public void identify()
    {
        System.out.print  ("[" + this.i + "," + this.j + "] ");
        System.out.println(this.toString());
        System.out.println("-------------------------");
        System.out.println("HP          = " + this.HP         );
        System.out.println("power       = " + this.power      );
        
        System.out.println("damageMelee = " + this.damageMelee);
        System.out.println("damageRange = " + this.damageRange);
        System.out.println("maxRange    = " + this.maxRange   );
        System.out.println("forceShield = " + this.forceShield);
        
        System.out.println("maxHP       = " + this.maxHP      );
        System.out.println("maxPower    = " + this.maxPower   );
        System.out.println("costMove    = " + this.costMove   );
        System.out.println("sight       = " + this.sight      );
        System.out.println("costTime    = " + this.costTime   );
    }
    
    /** 
     * General function to print, in a more legible
     * format, the robot. It is used mainly with 
     * debug purposes.
     * @return String representing the stackable.
     */
    public String toString()
    {
        return this.name;
    }
    
    /**
     * Runs 1 cycle of processment in the 
     * robot virtual machine program.
     */
    public void run()
            throws SegmentationFaultException, 
               UndefinedFunctionException,
               InvalidOperationException, 
               NotInitializedException,
               StackUnderflowException,
               NoLabelFoundException,
               OutOfBoundsException,
               WrongTypeException
    {
        this.positronic.run();
    }
    
    /**
     * Upload a new program for the robot.
     * @param PROG Program with the actions
     *             to be done by the robot
     */
    public void upload(Vector<Command> PROG)
    {
        this.positronic.upload(PROG);
    }
    
    /**
     * Activate the robot.
     * @see robot.RVM
     */
    public void ON()
    {
        connection = true;
        RVM.wake(positronic);
    }
    
    /**
     * Desactivate the robot.
     * @see robot.RVM
     */
    public void OFF()
    {
        connection = false;
        RVM.sleep(positronic);
    }
    
    /**
     * Getter for the robot's ownew ID.
     * @return Player number
     */
    public int getTeam () { return this.team; }
    
    // Interface scenario
    public int takeDamage(int damage)
    {
        this.HP -= damage - this.forceShield;
        return this.HP;
    }
    
    // Interface scenario
    public int getHP   () { return this.HP;   }
}
