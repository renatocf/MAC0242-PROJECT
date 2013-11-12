package random;

// Default libraries
import java.lang.Math;
import java.util.Random;

/**
 * <b>Random Map Theme - Calm Field</b><br>
 * Generate a matrix with a scenario
 * of theme 'Calm Field'.
 *
 * @author Vinicius Silva
 */
class CalmField extends Theme
{
    private Random rand;
    
    CalmField(Random rand)
    {
        this.rand = rand;
    }    
    /**
     * Generate random char matrix of the
     * CalmField theme for RandomMap.
     * @param  side Size of the side of the 
     *              matrix
     * @return Random char matrix with the
     *         class theme
     */
    public char[][] generateMatrix(int side)
    {    
        char[][] map = new char[side][side];
        
        for(int i = 0; i<side; i++)
            for(int j = 0; j<side; j++)
                map[i][j] = ' ';
        
        map = putTrees(map);
        map = putRocks(map);
        map = putCrystals(map);
        map = putStones(map);
        map = putBases(map);
        return map;
    }
    
    /**
     * Put trees in a char matrix map with
     * 2.5% of probability.
     * @param  map Map matrix
     * @return Map matrix with trees
     */
    char[][] putTrees(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
                if(map[i][j] == ' ' && this.rand.nextFloat() < 0.025)
                    map[i][j] = '\u2663';

        return map;
    }
    
    /**
     * Put rocks in a char matrix map with
     * 2.5% of probability.
     * @param  map Map matrix
     * @return Map matrix with rocks.
     */
    char[][] putRocks(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
                if(map[i][j] == ' ' && this.rand.nextFloat() < 0.025)
                    map[i][j] = 'O';

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
                if(map[i][j] == ' ' && this.rand.nextFloat() < 1.0/(map.length*1.25))
                    map[i][j] = '\u2662';
                if(map[i][j] == '\u2663' && this.rand.nextFloat() < 1.0/(map.length*1.5))
                    map[i][j] = 'Y';
                if(map[i][j] == 'O' && this.rand.nextFloat() < 1.0/(map.length*1.5))
                    map[i][j] = '@';
            }                    

        return map;
    }

    /**
     * Put stones in a char matrix map with
     * 5% of probability.
     * @param  map Map matrix
     * @return Map matrix with stones
     */
    char[][] putStones(char[][] map)
    {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map.length; j++)
                if(map[i][j] == ' ' && this.rand.nextFloat() < 0.05)
                       map[i][j] = '*';

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
}
