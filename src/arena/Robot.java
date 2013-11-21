package arena;

// Default libraries
import java.util.Vector;

// Libraries
import robot.*;
import players.*;
import exception.*;
import stackable.*;
import gui.Printable;
import scenario.Scenario;
import stackable.item.Item;

// Import links
import static parameters.Costs.*;

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
public class Robot implements Scenario, Printable
{
    // ID
    final protected String name;
    final protected Player team;
    final protected int    ID;

    // Position
    protected int     i;        // Line
    protected int     j;        // Column
    protected int     leftFoot; 
    protected int[]   phase;    //Phase of animation
    protected Terrain terrain;  
    
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
    final protected int sight;
    final protected int costTime;
    
    // Costs
    
    final protected int costMove;
    final protected int costAttack;
    final protected int costLook;
    final protected int costSee;
    final protected int costAsk;
    final protected int costDrag;
    final protected int costDrop;
    
    // Robot ON/OFF
    protected int     wait = 0;
    protected boolean ON   = true;
    
    // Robot state
    protected boolean damageTaken = false;
    
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
    public Robot(String baptism, Player team, int ID, 
                 int i, int j, Terrain terrain, Vector<Command> PROG)
    {
        // ID
        this.name = baptism;
        this.team = team;
        this.ID   = ID;
        
        // Position
        this.i        = i;
        this.j        = j;
        this.terrain  = terrain;
        this.phase    = new int[] {32, 0};
        this.leftFoot = 0;       
        
        // Hardware
        this.slots = new Item[1];
        this.positronic = new RVM (PROG);
        
        // Energy
        this.HP         = 12;
        this.power      = 16;
        
        // Combat
        this.damageMelee = 3;
        this.damageRange = 0;
        this.maxRange    = 0;
        this.forceShield = 0;
        
        // Capacities
        this.speed       = 10;
        this.maxHP       = 12;
        this.maxPower    = 32;
        this.sight       = 1;
        this.costTime    = 5;
        
        // Costs
        this.costMove    = ENERGY_MEDIUM;
        this.costAttack  = ENERGY_MEDIUM;
        this.costLook    = ENERGY_LOW;
        this.costSee     = ENERGY_HIGH;
        this.costAsk     = ENERGY_LOW;
        this.costDrag    = ENERGY_MEDIUM;
        this.costDrop    = ENERGY_LOW;
    }
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                                    INFO
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
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
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                              POSITRONIC BRAIN
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
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
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                                 ACTIVITY
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
    /**
     * Activate the robot.
     * @see robot.RVM
     */
    public void ON()
    {
        ON = true;
        RVM.wake(positronic);
    }
    
    /**
     * Desactivate the robot.
     * @see robot.RVM
     */
    public void OFF(int wait)
    {
        ON = false;
        this.wait = wait;
        RVM.sleep(positronic);
    }
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                              CONTROL OF POWER 
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
    /**
     * Recharges the power 
     * (limited to maxPower).
     */
    public void recharges()
    {
        this.power += ENERGY_CHARGE;
        if(this.power > this.maxPower)
            this.power = this.maxPower;
    }
    
    /**
     * Spend the amount of power required by 
     * a certain action, if there is enought.
     * 
     * @param  action String with the action
     *                to be done
     * @return Boolean saying if the power 
     *         was successfully spent 
     */
    public boolean spendPower(String action)
    {
        int cost = 0;
        
        switch(action)
        {
            case "MOVE"       : cost = move();            break;
            case "DRAG"       : cost = this.costDrag;     break;    
            case "DROP"       : cost = this.costDrop;     break;
            case "HIT"        : cost = this.costAttack;   break;
            case "LOOK"       : cost = this.costLook;     break;
            case "SEE"        : cost = this.costSee;      break;
            case "ASK"        : cost = this.costAsk;      break;
        }
        
        if(cost > this.power) return false;
        this.power -= cost;
        return true;
    }
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                            GETTERS AND SETTERS
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
    /**
     * Getter for the robot's ownew ID.
     * @return Player number
     */
    public Player getTeam () { return this.team; }
    
    /**
     * Setter for the robot's phase.
     * @param x the X coordinate of the upper-left 
     *          corner of the robot image 
     * @param y the Y coordinate of the upper-left 
     *          corner of the robot image 
     */
    public void setPhase(int x, int y)
    {
        phase[0] = x;
        phase[1] = y;
    }
    
    /**
     * Setter for the robot's phase.
     * @param d the direction of the movement 
     */
    public void setPhase(Direction d)
    {
        String mov = d.toString();
        switch (mov)
        {
            case "->SE" :
            case "->SW" :
                phase[0] = leftFoot*64;
                phase[1] = 0; 
                break;
            case "->NW" :
            case "->NE" : 
                phase[0] = leftFoot*64;
                phase[1] = 96; 
                break;
            case "->E"  : 
                phase[0] = leftFoot*64;
                phase[1] = 64; 
                break;
            case "->W"  :
                phase[0] = leftFoot*64;
                phase[1] = 32; 
                break;
        }
        leftFoot += 1;
        leftFoot %= 2;
    }
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                                INTERFACES 
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
    // Interface scenario
    public int takeDamage(int damage)
    {
        this.HP -= damage - this.forceShield;
        damageTaken = true;
        return this.HP;
    }
    
    // Interface scenario
    public boolean sufferedDamage()
    {
        if(damageTaken)
        {
            damageTaken = false;
            return true;
        }
        return false;
    }
    
    // Interface scenario
    public int getHP       () { return this.HP;       }
    public int getMaxHP    () { return this.maxHP;    }
    
    // Interface scenario
    public int getPower    () { return this.power;    }
    public int getMaxPower () { return this.maxPower; }
    
    public int[] getPhase  () { return this.phase;    }
    
    // Interface printable 
    public String name() { return "ROBOT";}
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                              AUXILIAR METHODS
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
        
    /**
     * Getter for the vertical position.
     * @return Vertical position (Y-axis)
     */
    public int getPosY() { return this.i; }
    
    /**
     * Getter for the horizontal position.
     * @return Horizontal position (X-axis)
     */
    public int getPosX() { return this.j; }
    
    /**
     * Getter for the robot's sight.
     * @return Sight
     */
    public int getSight() { return this.sight; }
    
    /**
     * Take out an item from inside the robot.
     * @param position Position of the item
     *                 up to be removed inside
     *                 its cargo
     */
    protected Item removeSlots(int position)
    {
    	Item item = this.slots[position];
    	slots[position] = null;
    	return item;
    }
    
    /**
     * Cost for movements
     * @return Cost for movements depending
     *         on the terrain where the Robot is
     */
    protected int move()
    {
        switch (this.terrain.type)
        {
            case ROUGH : return this.costMove*2;
            default    : return this.costMove;
        }
    }
}
