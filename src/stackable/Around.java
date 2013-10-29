package stackable;

// Libraries
import scenario.*;
import exception.*;
import arena.Robot;
import arena.Terrain;
import stackable.item.*;

/**
 * <b>Stackable - Around</b><br>
 * This class provides a set of terrains
 * with the neighborhoods of a given 
 * terrain.
 * @see robot.Syst.CHECK
 *
 * @author Karina Awoki
 * @author Vinicius Silva
 */
public class Around implements Stackable
{
    public String [][] matrix;
    
    /** 
     * Default constructor.
     * @param seeing Terrain with the robot
     *               that is seeing its 
     *               neighborhood.
     */
    public Around(Terrain[] seeing)
    {
        matrix = new String[2][seeing.length];
        for(int i = 0; i< seeing.length; i++)
        {
            if (seeing[i] == null) matrix[0][i] = "";
            else
            {
                Scenario s = seeing[i].getScenario();
                if(s == null) matrix[0][i] = " ";
                else          matrix[0][i] = s.getClass().getName();
            
                stackable.item.Item it = seeing[i].getItem(); 
                if(it == null) matrix[1][i] = " ";
                else          matrix[1][i] = it.getClass().getName();
            }
        }
    }
    
    public String toString() { return "( Around )"; }
}
    
// Some methods in case they are needed
// in the future.

//public String[] indexToPosition(int index)
//{
//    String ret[] = new String[2];
//    switch (index)
//    {
//        case   0: break;   
//        case   1: ret[0] = "->E" ; break;
//        case   2: ret[0] = "->NE"; break;
//        case   3: ret[0] = "->NW"; break;
//        case   4: ret[0] = "->W" ; break;
//        case   5: ret[0] = "->SW"; break;
//        case   6: ret[0] = "->SE"; break;
//        case   7: ret[0] = "->E" ; ret[1] = "->E" ; break;
//        case   8: ret[0] = "->E" ; ret[1] = "->NE"; break;
//        case   9: ret[0] = "->NE"; ret[1] = "->NE"; break;
//        case  10: ret[0] = "->NE"; ret[1] = "->NW"; break;
//        case  11: ret[0] = "->NW"; ret[1] = "->NW"; break;
//        case  12: ret[0] = "->NW"; ret[1] = "->W" ; break;
//        case  13: ret[0] = "->W" ; ret[1] = "->W" ; break;
//        case  14: ret[0] = "->W" ; ret[1] = "->SW"; break;
//        case  15: ret[0] = "->SW"; ret[1] = "->SW"; break;
//        case  16: ret[0] = "->SW"; ret[1] = "->SE"; break; 
//        case  17: ret[0] = "->SE"; ret[1] = "->SE"; break;
//        case  18: ret[0] = "->SE"; ret[1] = "->E" ; break;                          
//        default : ret = null;
//    }
//    return ret;
//}

//public void print()
//{
//    String pre = "    [AROUND] ";
//    System.out.print(pre + "Scenario: ");
//    for(String s: matrix[0])
//    {
//        System.out.print(s + " " );
//    }   
//    
//    System.out.print("\n" + pre + "Item: ");
//    for(String s: matrix[1])
//    {
//        System.out.print(s + " " );
//    } 
//    System.out.println();
//}
