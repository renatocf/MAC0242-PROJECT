package random;

// Default libraries
import java.lang.Math;
import java.util.Random;

/**
 * <b>Random Map Theme - Desertic</b><br>
 * Generate a matrix with a scenario
 * of theme 'Desertic'.
 *
 * @author Vinicius Silva
 */
class Desert extends Theme
{
    private Random rand;
    
    Desert(Random rand)
    {
        this.rand = rand;
    }    
    /**
     * Generate random char matrix of the
     * Desert theme for RandomMap.
     * @param  side Size of the side of the 
     *              matrix
     * @return Random char matrix with the
     *         class theme
     */
    public char[][] generateMatrix(int side)
    {    
        char[][] map = generateSands(side); 
        map = putRocks(map);
        map = putCrystals(map);
        map = putStones(map);
        map = putBases(map);
        return map;
    }
    
    /**
     * Put rocks in a char matrix map with
     * 10% of probability.
     * @param  map Map matrix
     * @return Map matrix with rocks
     */
    char[][] putRocks(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
            {
                if(map[i][j] == ':' && this.rand.nextFloat() < 0.1)
                    map[i][j] = 'O';
                
                if(map[i][j] == 'S' && this.rand.nextFloat() < 0.1)
                    map[i][j] = '©';
            }

        return map;
    }
    
    /**
     * Put crystals in a char matrix map with
     * probability accordingly to the map size
     * (bigger map, lesser crystals probability).
     * @param  map Map matrix
     * @return Map matrix with crystals
     */
    char[][] putCrystals(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
            {
                if(map[i][j] == ':' && this.rand.nextFloat() < 1.0/(map.length*1.25))
                    map[i][j] = '\u2662';
                if(map[i][j] == 'S' && this.rand.nextFloat() < 1.0/(map.length*1.25))
                    map[i][j] = '$';
                if(map[i][j] == 'O' && this.rand.nextFloat() < 1.0/(map.length*1.5))
                    map[i][j] = '@';
            }                    
        return map;
    }
    
    /**
     * Put stones in a char matrix map with
     * 10% of probability if the terrain is
     * a dirt and 5% of probability if its a
     * sand.
     * @param  map Map matrix
     * @return Map matrix with stones
     */
    char[][] putStones(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
            {
                if(map[i][j] == ' ' && this.rand.nextFloat() < 0.1)
                    map[i][j] = '*';
                if(map[i][j] == 'S' && this.rand.nextFloat() < 0.05)
                    map[i][j] = '§';
            }   
        return map;
    }
    
    /**
     * Put two bases in a char matrix map 
     * in the opposity corners.
     * @param  map Map matrix
     * @return Map matrix with bases
     */
    char[][] putBases(char[][] map)
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
    
    /**
     * Put no trees in this map
     * (It's a desert!)
     * @param  map Map matrix
     * @return Map matrix with bases
     */
    char[][] putTrees(char[][] map)
    {
        // Put no trees
        return map;
    }
    
    /**
     * Create a char matrix representing a
     * dirty terrain with sand spots.
     * @param  side Size of the side 
     *              of the matrix
     * @return Map matrix with sands
     */
    private char[][] generateSands(int side)
    {
        char[][] spots = new char[side][side];
        int nSpots = (int) (balancedRand() * side*3);
        
        for(int i = 0; i < side; i++)
            for(int j = 0; j < side; j++)
                spots[i][j] = ':';
        
        for(int i = 0; i < nSpots; i++)
        {
            int spotI           = (int) (balancedRand() * side * 2 - side/2);
            int spotJ           = (int) (balancedRand() * side * 2 - side/2);
            int tamStart        = (int) (balancedRand() * side/10);
            int tamEnd          = (int) (balancedRand() * side/10);
            int tamMax          = (int) (balancedRand() * side/5);
            int tam             = tamStart;
            boolean shrinking   = false;
            while (tam > 0 && (!shrinking || tam > tamEnd))
            {
                for(int j = spotJ - tam/2; j < tam+spotJ - tam/2; j++)
                {
                    if(spotI < side 
                    && j < side 
                    && spotI >= 0
                    && j >= 0)
                    {
                        spots[spotI][j] = 'S';
                    }
                }
                
                if      (!shrinking && rand.nextFloat() < 0.8)  tam += 2;
                else if (!shrinking)                            tam -= 2;
                else if (rand.nextFloat() < 0.8)                tam -= 2;
                else                                            tam += 2;
                
                if(!shrinking && tam >= tamMax) shrinking = true;
                
                spotI++;
            }
        }
        return spots;   
    }
   
    /**
     * Returns a double rand number in [0,1]
     * with less variance.
     * @param  map Map matrix
     * @return Map matrix with bases
     */
    private double balancedRand()
    {
        double a = rand.nextFloat();
        double b = rand.nextFloat();
        double c = rand.nextFloat();

        return (a+b+c)/3;
    }
}
