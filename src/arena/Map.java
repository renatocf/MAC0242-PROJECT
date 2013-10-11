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
import random.*;

public class Map implements Game
{
    // Map Matrix
    Terrain[][] map = new Terrain[MAP_SIZE][MAP_SIZE];
    char[][] miniMap = new char[MAP_SIZE][MAP_SIZE];
    Weather w;
    
    public Map(Weather w)
    {
        this.w = w;
    }
    
    public Robot[][] genesis(int nPlayers)
        throws InvalidOperationException
    {
        Winter w = new Winter();
        miniMap = w.generateMatrix(MAP_SIZE); 
        map = w.generateMap(miniMap ,nPlayers, MAP_SIZE);              
        
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
    
    public void print()
    {
        for(int i = 0; i < MAP_SIZE; i++)
        {
            for(int j = 0; j < MAP_SIZE; j++)
            {
                System.out.print(miniMap[i][j]);
            }
            System.out.println();
        }
    }   
}
