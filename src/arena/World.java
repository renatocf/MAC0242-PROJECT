package arena;

import stackable.*;
import robot.*;
import operation.*;
import exception.*;

public class World
{
    Num permissao;
    Direction dir;
    public World(boolean param)
    {
        if(param)
          { this.permissao = new Num(1); }
        else
          { this.permissao = new Num(0); }
    }
    
    public void setDirection(Direction d)
    {
        this.dir = d;
    }
    
    public Num get()
    {
        return this.permissao;
    }
    
    static public Num ctrl(Operation op)
    {
        Num answer = new Num(1); 
        return answer;
    }
}
