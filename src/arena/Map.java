package arena;

// Default libraries
import java.util.Vector;

// Libraries
import robot.*;
import stackable.*;
import exception.*;
import parameters.*;
import parser.Parser;
import stackable.item.*;

public class Map implements Game
{
    // Map Matrix
    Terrain[][] map = new Terrain[MAP_SIZE][MAP_SIZE];
    Weather w;
    
    public Map(Weather w)
    {
        this.w = w;
    }
    
    public Robot[][] genesis(int nPlayers)
        throws InvalidOperationException
    {
        for(int i = 0; i < MAP_SIZE; i++)
        {
            for(int j = 0; j < MAP_SIZE; j++)
            {
                if(j % 5 == 0 && i % 5 == 0)
                { this.map[i][j] = new Terrain(nPlayers, Appearence.GRASS, new Crystal()); }
                else
                { this.map[i][j] = new Terrain(nPlayers, Appearence.GRASS); }
            }
        }
        
        // TODO: receive PROG, generate robots
        Parser user = new Parser();
        Vector<Command> PROG = user.upload();
        
        Robot bender = new Robot("Bender", 8, 9, PROG);
        Robot c3po   = new Robot("C3PO", 9, 8, PROG);
        /* bender.identify(); */
        /* c3po.identify(); */
                
        Robot[][] initial = new Robot[nPlayers][ROBOTS_NUM_INITIAL];
        
        initial[0][0] = bender; 
        initial[1][0] = c3po;
        
        this.map[8][9].setScenario(bender);
        this.map[9][8].setScenario(c3po);
        
        return initial;
    }
}
