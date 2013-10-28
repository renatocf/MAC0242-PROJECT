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
    
    public Direction(int move, int dir) throws InvalidOperationException
    {
        
        if (move == 0)
        { 
            switch (dir)
            {
              case 0: set( 0,  0,  0,  0 ); this.cardPoint = "";     break; // ->
              case 1: set( 0,  1,  0,  1 ); this.cardPoint = "E";    break; // ->E
              case 2: set(-1,  0, -1,  1 ); this.cardPoint = "NE";   break; // ->NE
              case 3: set(-1, -1, -1,  0 ); this.cardPoint = "NW";   break; // ->NW
              case 4: set( 0, -1,  0, -1 ); this.cardPoint = "W";    break; // ->W
              case 5: set( 1, -1,  1,  0 ); this.cardPoint = "SW";   break; // ->SW
              case 6: set( 1,  0,  1,  1 ); this.cardPoint = "SE";   break; // ->SE
                  
              case 7:  set( 0,  1,  0,  1 );  this.cardPoint = "E";  break; // ->E
              case 8:  set(-1,  0, -1,  1 );  this.cardPoint = "NE"; break; // ->NE
              case 9:  set(-1,  0, -1,  1 );  this.cardPoint = "NE"; break; // ->NE
              case 10: set(-1, -1, -1,  0 );  this.cardPoint = "NW"; break; // ->NW
              case 11: set(-1, -1, -1,  0 );  this.cardPoint = "NW"; break; // ->NW
              case 12: set( 0, -1,  0, -1 );  this.cardPoint = "W";  break; // ->W
              case 13: set( 0, -1,  0, -1 );  this.cardPoint = "W";  break; // ->W
              case 14: set( 1, -1,  1,  0 );  this.cardPoint = "SW"; break; // ->SW
              case 15: set( 1, -1,  1,  0 );  this.cardPoint = "SW"; break; // ->SW
              case 16: set( 1,  0,  1,  1 );  this.cardPoint = "SE"; break; // ->SE
              case 17: set( 1,  0,  1,  1 );  this.cardPoint = "SE"; break; // ->SE
              case 18: set( 0,  1,  0,  1 );  this.cardPoint = "E";  break; // ->E
   
              default: throw new InvalidOperationException("" + dir);
            }
        }
        else if(move == 1)
        { 
            switch (dir)
            {
              case 0: set( 0,  0,  0,  0 ); break; // ->
              case 1: set( 0,  0,  0,  0 ); break; // ->
              case 2: set( 0,  0,  0,  0 ); break; // ->
              case 3: set( 0,  0,  0,  0 ); break; // ->
              case 4: set( 0,  0,  0,  0 ); break; // ->
              case 5: set( 0,  0,  0,  0 ); break; // ->
              case 6: set( 0,  0,  0,  0 ); break; // ->
                 
              case 7:  set( 0,  1,  0,  1 );  break; // ->E
              case 8:  set( 0,  1,  0,  1 );  break; // ->E
              case 9:  set(-1,  0, -1,  1 );  break; // ->NE
              case 10: set(-1,  0, -1,  1 );  break; // ->NE
              case 11: set(-1, -1, -1,  0 );  break; // ->NW
              case 12: set(-1, -1, -1,  0 );  break; // ->NW
              case 13: set( 0, -1,  0, -1 );  break; // ->W
              case 14: set( 0, -1,  0, -1 );  break; // ->W
              case 15: set( 1, -1,  1,  0 );  break; // ->SW
              case 16: set( 1, -1,  1,  0 );  break; // ->SW
              case 17: set( 1,  0,  1,  1 );  break; // ->SE
              case 18: set( 1,  0,  1,  1 );  break; // ->SE
   
              default: throw new InvalidOperationException("" + dir);
            }
        }
    }
    
    public String toString()
    {
        return "->" + this.cardPoint;
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

