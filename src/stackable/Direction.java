package stackable;

import exception.*;



public class Direction implements Stackable
{
    int movement[][] = new int[2][2];
    String cardPoint;
    
    public Direction(String dir) throws InvalidOperationException
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
    
    public String toString()
    {
        return this.cardPoint;   
    }
    
    public int[] get(int row)
    {
        row = row%2;
        return this.movement[row];
    }
    
    private void set(int even_x, int even_y, int odd_x, int odd_y)
    {
        this.movement[0][0] = even_x;
        this.movement[0][1] = even_y;
        this.movement[1][0] = odd_x;
        this.movement[1][1] = odd_y;
    }
}


