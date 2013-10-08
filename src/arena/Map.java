package arena;

import stackable.*;
import stackable.item.*;
import parser.Parser;
import java.util.Vector;
import robot.*;


public class Map
{
    Terrain[][] map = new Terrain[50][50];
    Weather w;
    
    
    public Map(Weather w)
    {
        this.w = w;
    }
    
    public void genesis()
    {
        for(int i = 0; i<50; i++)
        {
            for(int j = 0; j < 50; j++)
            {
                if(j%5 == 0 && i%5 == 0)
                { this.map[i][j] = new Terrain(Appearence.GRASS, new Crystal()); }
                else
                { this.map[i][j] = new Terrain(Appearence.GRASS); }
            }
        }
        
        Parser user = new Parser();
        Vector<Command> PROG = user.upload();
        
        Robot bender = new Robot("Bender", PROG);
        Robot c3po = new Robot("C3PO", PROG);
                
        this.map[42][13].setScenario(bender);
        this.map[42][12].setScenario(c3po);
    }
}
