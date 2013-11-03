package players;

// Default libraries
import java.util.ArrayList;

// Libraries
import gui.Colors;
import arena.Robot;

// Static symbols
import static gui.Colors.*;

public class Player
{
    static int total = 0;
    
    private int    ID;
    private Base   base;
    private String color;
    private int[]  basePos;
    
    private // Player's armies
    ArrayList<Robot> armies = new ArrayList<Robot>();
    
    public Player(Base base)
    {
        this.ID    = ++total;
        this.base  = base;
        this.color = setColor(this.ID);
        
        // Sets the base as his
        base.own(this);
    }
    
    public int    getID() { return this.ID;    }
    public String color() { return this.color; }
    
    public void addArmy(Robot newRobot)
    {
        this.armies.add(newRobot);
    }
    
    public void removeArmy(Robot deadRobot)
    {
        this.armies.remove(deadRobot);
    }
    
    final private String setColor(int plID)
    {
        switch(plID)
        {
            case 1:  return ON_BLACK;
            case 2:  return ON_RED;
            case 3:  return ON_GREEN;
            case 4:  return ON_YELLOW;
            default: return "";
        }
    }
}
