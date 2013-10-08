package arena;

import java.util.Vector;

import exception.*;
import robot.*;
import stackable.*;
import scenario.Scenario;

public class Robot implements Scenario
{
    // ID
    final protected String name;

    // Hardware
    protected Stackable[] slots;
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
    
    public Robot(String baptism, Vector<Command> PROG)
    {
        this.name = baptism;
        
        this.slots = new Stackable[1];
        this.positronic = new RVM (PROG);
        
        this.HP         = 12;
        this.power      = 12;
        
        this.damageMelee = 1;
        this.damageRange = 0;
        this.maxRange    = 0;
        this.forceShield = 0;
        
        this.maxHP       = 12;
        this.maxPower    = 24;
        this.costMove    = 3;
        this.sight       = 1;
        this.costTime    = 5;
    }
    
    public void identify()
    {
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
    
}
