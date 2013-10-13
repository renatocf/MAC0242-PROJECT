package random;

// Default libraries
import java.lang.Math;
import java.util.Random;

class Jungle implements Theme
{
    private Random rand = new Random(42);
    
    public char[][] generateMatrix(int side)
    {    
        char[][] map        = new char[side][side];
        int[][]  river      = generateRiver(side);
        boolean deepWater   = false;
        boolean water       = false;
        
        for(int i = 0; i < side; i++)
        {
            for(int j = 0; j < side; j++)   
            {
                if (j == river[i][0])   deepWater = true;
                if (j-1 == river[i][1]) deepWater = false;
                if (j == river[i][2])   water     = true;
                if (j-1 == river[i][3]) water     = false;
                              
                     if (deepWater) map[i][j] = '~';
                else if (water)     map[i][j] = '≈';
                else                map[i][j] = ' ';
            }
        }
        
        for(int i = river[side/3-1][0]; i < river[side/3-1][1]; i++)
        {
            int r = (int) (rand.nextFloat()*3);
            
            if(r == 2) map[side/3][i] = map[side/3 + 1][i] = '~';
            if(r == 1) map[side/3][i] = '~';
            
            r = (int) (rand.nextFloat()*3);
            
            if(r == 2) map[side - side/3 - 1][i] = map[side - side/3 - 2][i] = '~';
            if(r == 1) map[side - side/3 - 1][i] = '~';
        }
        
        map = putTrees(map);
        map = putRocks(map);
        map = putCrystals(map);
        map = putStones(map);
        map = putBases(map);
        return map;
    }
    
    
    int[][] generateRiver(int side)
    {
        int leftMargin  = (int) (side/12 + rand.nextFloat()*(side/12) + 0.5); 
        int rightMargin = (int) (side/12 + rand.nextFloat()*(side/12) + 0.5);
        int[][] river   = new int[side][4];
        
        for(int i = 0; i < side/3; i++)
        {
            leftMargin  = updateNarrowMargin(leftMargin, side/3 - 1 - i, side);   
            rightMargin = updateNarrowMargin(rightMargin, side/3 - 1 - i, side);
            river[i][0] = side/2 - leftMargin;
            river[i][1] = side/2 + rightMargin;
            river[i][2] = side;
            river[i][3] = side;
        }
        
        for(int i = side/3; i < (side - side/3); i++)
        {
            leftMargin  = updateFlatMargin(leftMargin, (side - side/3) - 1 - i, side);   
            rightMargin = updateFlatMargin(rightMargin, (side - side/3) - 1 - i, side);
            river[i][0] = side;
            river[i][1] = side;
            river[i][2] = side/2 - leftMargin;
            river[i][3] = side/2 + rightMargin;
        }
        
        for(int i = (side - side/3); i < side; i++)
        {
            leftMargin  = updateNarrowMargin(leftMargin, i - (side - side/3), side);   
            rightMargin = updateNarrowMargin(rightMargin, i - (side - side/3), side);
            river[i][0] = side/2 - leftMargin;
            river[i][1] = side/2 + rightMargin;
            river[i][2] = side;
            river[i][3] = side;
        }
        
        
        return river;
    }   
    
    private char[][] putTrees(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
                if(map[i][j] == ' ' && this.rand.nextFloat() < 0.2)
                    map[i][j] = '\u2663';
        return map;
    }
    
    private char[][] putRocks(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
                if(map[i][j] == ' ' && this.rand.nextFloat() < 0.001)
                    map[i][j] = 'O';
        return map;
    }
    
    private char[][] putCrystals(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
            {
                if(map[i][j] == ' ' && this.rand.nextFloat() < 1.0/(map.length*1.25))
                    map[i][j] = '\u2662';
                if(map[i][j] == '\u2663' && this.rand.nextFloat() < 1.0/(map.length*1.5))
                    map[i][j] = 'Y';
                if(map[i][j] == 'O' && this.rand.nextFloat() < 1.0/(map.length*1.5))
                    map[i][j] = '@';
                if(map[i][j] == '≈' && this.rand.nextFloat() < 1.0/(map.length*1.5))
                    map[i][j] = 'õ';
            }                    
        return map;
    }

    private char[][] putStones(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
                if(map[i][j] == ' ' && this.rand.nextFloat() < 0.01)
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
    
    private int updateNarrowMargin(int margin, int remain, int side)
    {   
        
        double probMinus = (remain - side/6 + margin);
        double probPlus  = (side/3 - remain);
        
        if(probMinus < 0) probMinus = 0;
                
        double total     = probPlus + probMinus;
        double rand      = this.rand.nextFloat();
        probPlus /= total;
        
        if(rand < probPlus)     margin+=1;
        else                    margin-=1;
        if(margin < side/12)    margin+=2;
        if(margin > side/6 
        && probMinus > 1   )    margin-=2;
        if(margin > side/6 
        && probMinus <= 1  )    margin-=1;
        return margin;
    }
    
       private int updateFlatMargin(int margin, int remain, int side)
    {   
        double probMinus = (margin - remain + side/6 + 2);
        double probPlus  = (remain - margin + side/6)*4;
               
        if(probMinus < 0) probMinus = 0;
        if(probPlus < 0) probPlus = 0;
        
        double total     = probPlus + probMinus;
        double rand      = this.rand.nextFloat();
        probPlus /= total;
        if(rand < probPlus)     margin+=1;
        else                    margin-=1;
        if(margin < side/6)     margin+=2;
        if(margin > side/3 
        && probMinus > 1   )    margin-=2;
        if(margin > side/3 
        && probMinus <= 1  )    margin-=1;
        return margin;
    }
    
}