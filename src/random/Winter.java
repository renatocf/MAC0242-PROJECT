package random;

// Default libraries
import java.lang.Math;
import java.util.Random;

// Libraries
import scenario.*;
import arena.Terrain;
import stackable.item.*;
import arena.Appearence;

public class Winter
{
    private Random rand = new Random();
    
    public Terrain[][] generateMap(char[][] matrix, int nPlayer, int side)
    {
        Terrain[][] map = new Terrain[side][side];
        for(int i = 0; i < side; i++)
            for(int j = 0; j < side; j++)
            {
                switch (matrix[i][j])
                {
                    case '.'     : map[i][j] = new Terrain(nPlayer, Appearence.TUNDRA);                            break;
                    case '#'     : map[i][j] = new Terrain(nPlayer, Appearence.ICE);                               break;
                    case '\u2662': map[i][j] = new Terrain(nPlayer, Appearence.TUNDRA, new Crystal());             break;
                    case '\u2663': map[i][j] = new Terrain(nPlayer, Appearence.TUNDRA, new Tree());                break;
                    case 'O'     : map[i][j] = new Terrain(nPlayer, Appearence.TUNDRA, new Rock());                break;
                    case '*'     : map[i][j] = new Terrain(nPlayer, Appearence.TUNDRA, new Stone());               break;
                    case 'B'     : map[i][j] = new Terrain(nPlayer, Appearence.TUNDRA, new Base());                break;    
                    case 'Y'     : map[i][j] = new Terrain(nPlayer, Appearence.TUNDRA, new Tree(), new Crystal()); break;
                    case '@'     : map[i][j] = new Terrain(nPlayer, Appearence.TUNDRA, new Rock(), new Crystal()); break;
                    default      : map[i][j] = new Terrain(nPlayer, Appearence.TUNDRA);                            break;
                }
            }
        return map;
    }
    
    private int[][] generateLakeBorder(int side)
    {
        int[][] points = new int[2][1000];
        int t = 0;
        double i = 0;
        int rad = (int) (this.rand.nextFloat() * (side/6.0) + side/6.0 + 0.5);
        int mod = 0;
        points[0][t] = coordX(rad, 0);
        points[1][t] = coordY(rad, 0);
        for(int j = side*100; j > 0; j--)
        {
            int x, y;
            i += Math.PI / (side*50);
            x = coordX(rad + mod, i);
            y = coordY(rad + mod, i);;
            if(x != points[0][t] || y != points[1][t])
            { 
                t++;
                mod = updateMod(mod, j, rad, side);
                points[0][t] = coordX(rad + mod, i);
                points[1][t] = coordY(rad + mod, i);
            }
        }
        int[][] coord = new int[2][t];
        for(int j = 0; j < t; j++)
        {
            coord[0][j] = points[0][j];
            coord[1][j] = points[1][j];
        } 
        return coord;
    }   
    
    public char[][] generateMatrix(int side)
    {    
        char[][] map = new char[side][side];
        int devMax = (int) ((side/10.0 + 1)*(side/10.0 + 1));
        boolean open = true;
        
        
        while(open)
        {
            open = false;
            int[][] points = generateLakeBorder(side);
            int prevMax = 0, prevMin = 0;
            boolean started = false, finished = false;
            
            for(int i = 0; i < side; i++)
                for(int j = 0; j < side; j++)
                    map[i][j] = '.';
            
            for(int i = 0; i < points[0].length; i++)
                map[points[0][i]+side/2][points[1][i]+side/2] = '#';
            
            for(int i = 0; i < side; i++)
            {
                int max = 0, min = 0;
                
                for(int j = 0; j < side; j++)
                {
                    if(map[i][j] == '#')
                    {
                        started = true;
                        if(finished) open = true;
                        if(min == 0) min = j;
                        max = j;
                    }
                }
                
                if(max != 0 && max == min && started) finished = true;
                if (max == 0 && started) finished = true;
                if (prevMax == 0) prevMax = max;
                if (prevMin == 0) prevMin = min;
                if ((prevMin - min)*(prevMin - min) > devMax && min != 0) open = true;
                if ((prevMax - max)*(prevMax - max) > devMax && max != 0) open = true;
                prevMin = min;
                prevMax = max;
                
                for(int j = min+1; j<max; j++)
                    map[i][j] = '#';
            }
        }
        map = putTrees(map);
        map = putRocks(map);
        map = putCrystals(map);
        map = putStones(map);
        map = putBases(map);
        return map;
    }
    
    private char[][] putTrees(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
                if(map[i][j] == '.' && this.rand.nextFloat() < 0.02  )
                    map[i][j] = '\u2663';
        return map;
    }
    
    private char[][] putRocks(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
                if(map[i][j] == '.' && this.rand.nextFloat() < 0.005)
                    map[i][j] = 'O';
        return map;
    }
    
    private char[][] putCrystals(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
            {
                if(map[i][j] == '.' && this.rand.nextFloat() < 1.0/(map.length*1.25))
                    map[i][j] = '\u2662';
                if(map[i][j] == '\u2663' && this.rand.nextFloat() < 1.0/(map.length*1.5))
                    map[i][j] = 'Y';
                if(map[i][j] == 'O' && this.rand.nextFloat() < 1.0/(map.length*1.5))
                    map[i][j] = '@';
            }                    
        return map;
    }

    private char[][] putStones(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
                if(map[i][j] == '.' && this.rand.nextFloat() < 0.005)
                    map[i][j] = '*';
        return map;
    }
    
    private char[][] putBases(char[][] map)
    {
        int x = (int) (map.length/10 * this.rand.nextFloat());
        int y = (int) (map.length/10 * this.rand.nextFloat());
        map[x][y] = 'B';
        x = (int) (map.length/10 * this.rand.nextFloat());
        y = (int) (map.length/10 * this.rand.nextFloat());
        x = map.length - x - 1;
        y = map.length - y - 1;
        map[x][y] = 'B';
        return map;
    }
    
    private static int coordX(int rad, double ang)
    {
        return (int) (rad*Math.cos(ang) + 0.5);
    }
    
    private static int coordY(int rad, double ang)
    {
        return (int) (rad*Math.sin(ang) + 0.5);
    }

    private int updateMod(int mod, int remain, int rad, int side)
    {
        double probPlus  = (double) (remain - mod) / remain;
        double probMinus = (double) (remain + mod) / remain;
        double total     = probPlus + probMinus;
        double rand      = this.rand.nextFloat();
        probPlus /= total;
        if(rand < probPlus)  mod+=1;
        else                 mod-=1;
        if(rad+mod < side/6) mod+=2;
        if(rad+mod > side/3) mod-=2;
        return mod;
    }
}
