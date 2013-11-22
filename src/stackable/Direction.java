package stackable;

// Libraries
import exception.*;

/**
 * <b>Stackable - Direction</b><br>
 * Stores the numerical representation 
 * of a direction, as used by the map 
 * to update positions.
 *
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 */
public class Direction implements Stackable
{
    private int movement[][] = new int[2][2];
    private String cardPoint;
    
    /** 
     * Default constructor.<br>
     * If the direction is invalid, 
     * throws an exception.
     * 
     * @param dir String with the direction,
     *            as in a compass rose (E, NE,
     *            NW, W, SW, SE and black, for
     *            the center).
     *            
     * @throws InvalidOperationException
     */
    public Direction(String dir) 
        throws InvalidOperationException
    {
        this.cardPoint = dir;
        switch (dir)
        {
          case "E" : set( 0,  1,  0,  1); break; 
          case "NE": set(-1,  0, -1,  1); break; 
          case "NW": set(-1, -1, -1,  0); break;
          case "W" : set( 0, -1,  0, -1); break; 
          case "SW": set( 1, -1,  1,  0); break; 
          case "SE": set( 1,  0,  1,  1); break; 
          case ""  : set( 0,  0,  0,  0); break;
          default: throw new InvalidOperationException(dir);
        }
    }
    
    /** 
     * Secondary constructor.<br>
     * Stores the direction to be moved
     * when updating a movement, accordingly
     * to if the line is odd or even in the 
     * arena. 
     * <p>
     * If the direction is invalid, throws
     * an exception.
     * 
     * @param move Indicative if the line is
     *             odd or even when should be
     *             updated.
     * @param dir  String with the direction,
     *             as in a compass rose (E, NE,
     *             NW, W, SW, SE and black, for
     *             the center).
     *            
     * @throws InvalidOperationException
     */
    public Direction(int move, int dir) 
        throws InvalidOperationException
    {
        if(move == 0)
        { 
            switch (dir)
            {
              case  0: set( 0,  0,  0,  0 ); this.cardPoint = ""  ; break; // ->
              case  1: set( 0,  1,  0,  1 ); this.cardPoint = "E" ; break; // ->E
              case  2: set(-1,  0, -1,  1 ); this.cardPoint = "NE"; break; // ->NE
              case  3: set(-1, -1, -1,  0 ); this.cardPoint = "NW"; break; // ->NW
              case  4: set( 0, -1,  0, -1 ); this.cardPoint = "W" ; break; // ->W
              case  5: set( 1, -1,  1,  0 ); this.cardPoint = "SW"; break; // ->SW
              case  6: set( 1,  0,  1,  1 ); this.cardPoint = "SE"; break; // ->SE
                  
              case  7: set( 0,  1,  0,  1 ); this.cardPoint = "E" ; break; // ->E
              case  8: set(-1,  0, -1,  1 ); this.cardPoint = "NE"; break; // ->NE
              case  9: set(-1,  0, -1,  1 ); this.cardPoint = "NE"; break; // ->NE
              case 10: set(-1, -1, -1,  0 ); this.cardPoint = "NW"; break; // ->NW
              case 11: set(-1, -1, -1,  0 ); this.cardPoint = "NW"; break; // ->NW
              case 12: set( 0, -1,  0, -1 ); this.cardPoint = "W" ; break; // ->W
              case 13: set( 0, -1,  0, -1 ); this.cardPoint = "W" ; break; // ->W
              case 14: set( 1, -1,  1,  0 ); this.cardPoint = "SW"; break; // ->SW
              case 15: set( 1, -1,  1,  0 ); this.cardPoint = "SW"; break; // ->SW
              case 16: set( 1,  0,  1,  1 ); this.cardPoint = "SE"; break; // ->SE
              case 17: set( 1,  0,  1,  1 ); this.cardPoint = "SE"; break; // ->SE
              case 18: set( 0,  1,  0,  1 ); this.cardPoint = "E" ; break; // ->E
   
              default: throw new InvalidOperationException("" + dir);
            }
        }
        else if(move == 1)
        { 
            switch (dir)
            {
              case  0: set( 0,  0,  0,  0 ); break; // ->
              case  1: set( 0,  0,  0,  0 ); break; // ->
              case  2: set( 0,  0,  0,  0 ); break; // ->
              case  3: set( 0,  0,  0,  0 ); break; // ->
              case  4: set( 0,  0,  0,  0 ); break; // ->
              case  5: set( 0,  0,  0,  0 ); break; // ->
              case  6: set( 0,  0,  0,  0 ); break; // ->
                 
              case  7: set( 0,  1,  0,  1 ); break; // ->E
              case  8: set( 0,  1,  0,  1 ); break; // ->E
              case  9: set(-1,  0, -1,  1 ); break; // ->NE
              case 10: set(-1,  0, -1,  1 ); break; // ->NE
              case 11: set(-1, -1, -1,  0 ); break; // ->NW
              case 12: set(-1, -1, -1,  0 ); break; // ->NW
              case 13: set( 0, -1,  0, -1 ); break; // ->W
              case 14: set( 0, -1,  0, -1 ); break; // ->W
              case 15: set( 1, -1,  1,  0 ); break; // ->SW
              case 16: set( 1, -1,  1,  0 ); break; // ->SW
              case 17: set( 1,  0,  1,  1 ); break; // ->SE
              case 18: set( 1,  0,  1,  1 ); break; // ->SE
   
              default: throw new InvalidOperationException("" + dir);
            }
        }
    }
    
    public String toString()
    {
        return "->" + this.cardPoint;
    }
    
    /** 
     * Getter for the integer vector
     * with the direction-update type.
     *
     * @param  row Map's row in which an
     *             element is above. The 
     *             return value changes 
     *             accordingly to if the 
     *             row is odd or even.
     * @return Integer vector with the 
     *         directions to be updated.
     */
    public int[] get(int row)
    {
        row = row % 2;
        return this.movement[row];
    }
    
    /**
     * Setter for the cardinal point.
     * @param  dir Integer representing one of the directions
     *             (0 for its own position and starting from E
     *             counterclockwise)
     * @return String of the cardinal point
     */
    public String set(int dir) 
        throws InvalidOperationException
    {
        switch(dir)
        {
            case 1: set( 0,  1,  0,  1); this.cardPoint = "E" ; break; 
            case 2: set(-1,  0, -1,  1); this.cardPoint = "NE"; break; 
            case 3: set(-1, -1, -1,  0); this.cardPoint = "NW"; break;
            case 4: set( 0, -1,  0, -1); this.cardPoint = "W" ; break; 
            case 5: set( 1, -1,  1,  0); this.cardPoint = "SW"; break; 
            case 6: set( 1,  0,  1,  1); this.cardPoint = "SE"; break; 
            case 0: set( 0,  0,  0,  0); this.cardPoint = ""  ; break;
            default: throw new InvalidOperationException("" + dir);
        }
        return this.cardPoint;
    }
    
    /**
     * Auxiliar function to shortcut atribution
     * inside the secondary structure.
     * @param even_x Horizontal position in even row
     * @param even_y Vertical position in even row
     * @param odd_x  Horizontal position in odd row 
     * @param odd_y  Vertical position in odd row
     */
    private void set(int even_x, int even_y, int odd_x, int odd_y)
    {
        this.movement[0][0] = even_x;
        this.movement[0][1] = even_y;
        this.movement[1][0] = odd_x;
        this.movement[1][1] = odd_y;
    }
}
