package arena;

// Default libraries
import java.util.Vector;

// Libraries
import robot.*;
import exception.*;
import stackable.*;
import stackable.item.Item;
import scenario.Scenario;

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
    Item[] slots;
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
    final protected int maxHP;
    final protected int maxPower;
    final protected int costMove;
    final protected int sight;
    final protected int costTime;
    
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
        this.maxHP       = 12;
        this.maxPower    = 24;
        this.costMove    = 3;
        this.sight       = 1;
        this.costTime    = 5;
    }
    
    public Item removeSlots(int position)
    {
    //*
    	Item item = this.slots[position];
    	slots[position] = null;
    	return item;
    }
    
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
    
    public String toString()
    {
        return this.name;
    }
    
    public void run()
            throws SegmentationFaultException, 
               UndefinedFunctionException,
               InvalidOperationException, 
               StackUnderflowException,
               NoLabelFoundException,
               OutOfBoundsException,
               WrongTypeException
    {
        this.positronic.run();
    }
    
    public void upload(Vector<Command> PROG)
    {
        this.positronic.upload(PROG);
    }
    
    public int takeDamage(int damage)
    {
        this.HP -= damage - this.forceShield;
        return this.HP;
    }
    
    public int getHP() { return HP; }
}
