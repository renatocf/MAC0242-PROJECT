package random;

// Default library
import java.util.ArrayList;
import java.util.Random;

// Libraries
import scenario.*;
import players.Base;
import arena.Terrain;
import stackable.item.*;
import arena.Appearence;

// Static symbols
import static arena.Appearence.*;

/**
 * <b>Random Map Generator</b><br>
 * Generate an hexagonal matrix with 
 * an specified theme, with dimensions
 * side x side and a 'Weather' style.
 * @see arena.World
 * @see gui
 *
 * @author Vinicius Silva
 */
public class RandomMap
{
    Weather         style;
    int             nPlayer;
    int             side;
    char[][]        matrix;
    ArrayList<Base> bases;
        
    /**
     * Default constructor.<br>
     * Construct a RandomMap object, accordingly with the 
     * theme chosen and the size of the map.
     * 
     * @param style   Weather of the map
     * @param nPlayer Number of players
     * @param side    Size of the side of the 
     *                matrix
     * @see Weather
     */
    public RandomMap(Weather style, int nPlayer, int side, Random rand)
    {
        this.style = style;
        this.nPlayer = nPlayer;
        this.side = side;
        
        // Set up a list of bases
        bases = new ArrayList<Base>();
        
        Theme t = null;
        switch(style)
        {
            case ARTICAL     : t = new Winter(rand);    break;
            case TROPICAL    : t = new Jungle(rand);    break;
            case DESERTIC    : t = new Desert(rand);    break;
            case CONTINENTAL : t = new CalmField(rand); break;
            default          : t = new CalmField(rand); break;
        }
        this.matrix = t.generateMatrix(this.side);        
    }
    
    /**
     * Creates a random terrain matrix accordingly 
     * to the theme, items and scenarios.
     * @return Random terrain matrix
     */
    public Terrain[][] generateMap()
    {
        Terrain[][] map = new Terrain[this.side][this.side];
        
        Appearence DEF;
        switch(style)
        {      
            case ARTICAL     : DEF = TUNDRA ; break;
            case TROPICAL    : DEF = GRASS  ; break;
            case DESERTIC    : DEF = DIRT   ; break; 
            case CONTINENTAL : DEF = GRASS  ; break;
            default          : DEF = GRASS  ;
        }
        
        for(int i = 0; i < this.side; i++)
            for(int j = 0; j < this.side; j++)
            {
                Appearence app = null; 
                Scenario   sce = null;
                Item       itm = null; 
                
                switch (this.matrix[i][j])
                {
                    // No items or scenario
                    case ' ': app = GRASS ; break;
                    case '.': app = TUNDRA; break;
                    case ':': app = DIRT  ; break;
                    case '#': app = ICE   ; break;
                    case 'S': app = SAND  ; break;         
                    case '~': app = DEEP  ; break;
                    case '≈': app = WATER ; break;
                    
                    // Scenario only
                    case 'B': app = DEF   ; sce = new Base (i,j);
                                            bases.add((Base)sce); break;    
                    case 'O': app = DEF   ; sce = new Rock    (); break;
                    case '♣': app = DEF   ; sce = new Tree    (); break;
                    
                    // Item only
                    case '♢': app = DEF   ; itm = new Crystal (); break;
                    case '$': app = SAND  ; itm = new Crystal (); break;
                    case 'õ': app = WATER ; itm = new Crystal (); break;
                    case '*': app = DEF   ; itm = new Stone   (); break;
                    case '§': app = SAND  ; itm = new Stone   (); break;
                    
                    // Scenario and item
                    case 'Y': app = DEF   ; itm = new Crystal (); 
                                            sce = new Tree    (); break;
                    case '@': app = DEF   ; itm = new Crystal (); 
                                            sce = new Rock    (); break;
                    case '©': app = SAND  ; itm = new Crystal (); 
                                            sce = new Rock    (); break;
                    
                    // Default case
                    default : app = GRASS ;                       break;
                }
                
                // Create terrain with all attributes
                map[i][j] = new Terrain(this.nPlayer, app, sce, itm);
            }
        
        return map;
    }
    
    /**
     * Stores the bases created for this map.
     * @return Matrix of characters representing
     *         the entire map.
     */
    public Base[] getBases()
    {
        return bases.toArray(new Base[0]);
    }
    
    /**
     * This method returns a matrix of characters,
     * with debug purposes.
     * @return Matrix of characters representing
     *         the entire map.
     */
    public char[][] getMatrix()
    {   
        return this.matrix;
    }
}
