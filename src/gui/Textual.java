package gui;

// Libraries
import arena.*;
import scenario.*;
import parameters.*;
import stackable.item.*;

public class Textual 
    implements Game, Colors, GUI
{
    private Map map;

    public Textual(Map map)
    {
        this.map = map;
    }
    
    public void paint()
    {
        try { 
            Thread.sleep(SPEED);
        } catch (Exception e) {
        }
        // Clear screen
        System.out.print(CLEAR);
        printMap();
    }
    
    public void printMiniMap()
    {
        for(int i = 0; i < MAP_SIZE; i++)
        {
            for(int j = 0; j < MAP_SIZE; j++)
            {
                System.out.print(map.miniMap[i][j]);
            }
            System.out.println();
        }
    }
    
    final private String hexTop(int type, String midColor)
    {
        if(type == 1)
            return RESTORE + "/" + midColor +  " "  + RESTORE + "\\"; /*  / \\ */
        else
            return RESTORE + "/" + midColor + "   " + RESTORE + "\\"; /* /   \\ */
    }
    
    final private String hexBot(int type, String midColor)
    {
        if(type == 1)
            return RESTORE + "\\" + midColor + "   " + RESTORE + "/"; /* \   // */
        else
            return RESTORE + "\\" + midColor +  " "  + RESTORE + "/"; /*  \ / */
    }
    
    final private String colorof(int i, int j) //(Appearence ap)
    {
        Appearence ap = map.map[i][j].getAppearence();
        
        if(ap == null) return RESTORE;
        switch (ap)
        {
            case DIRT   : return ON_BLACK; 
            case GRASS  : return ON_IGREEN;
            case TUNDRA : return ON_WHITE; 
            
            case ROCKY  : return ON_IBLACK;
            case ICE    : return ON_CYAN;  
            case JUNGLE : return ON_IGREEN;
            case WATER  : return ON_BLUE;  
            case SAND   : return ON_YELLOW;
            default     : return RESTORE;  
        }
    }
    
    final private Scenario scen(int i, int j)
    {
        if(i < 0 || j < 0 || i >= MAP_SIZE || j >= MAP_SIZE) return null;
        return map.map[i][j].getScenario();
    }
    
    final private Item item(int i, int j)
    {
        if(i < 0 || j < 0 || i >= MAP_SIZE || j >= MAP_SIZE) return null;
        return map.map[i][j].getItem();
    }

    final private void print(String s)
    {
        System.out.print(s);
    }
    
    final private void println(String s)
    {
        System.out.println(s);
    }
    
    public void printMap()
    {
        // Print scenario top
        for(int j = 0; j < MAP_SIZE-1; j++)
            print("   .  ");
        println("   .");
        
        print("  " + hexTop( 1, colorof(0,0)) );
        for(int j = 1; j < MAP_SIZE; j++)
            print("   " + hexTop( 1, colorof(0,j)) );
        println(RESTORE);
       
        print(" " + hexTop(2, colorof(0,0)) );
        for(int j = 1; j < MAP_SIZE; j++)
            print(" " + hexTop(2, colorof(0,j)) );
        println(RESTORE);
        
        // Print ordinary lines
        for(int i = 0; i < MAP_SIZE; i++)
        {
            /* print(ON_GREEN); */
            boolean odd = (i % 2 == 1) ? true : false;
            
            // Print itens and scenario
            print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item1 = " ", item2 = " ";
                String scen1 = " ", scen2 = " ";
                
                if      (scen(i,j) == null)            { scen1 = " "; scen2 = " "; }
                else if (scen(i,j) instanceof Base )   { scen1 = ON_RED    + "ß"; scen2 = ON_RED    + "ß";  }
                else if (scen(i,j) instanceof Rock )   { scen1 = ON_BLACK  + "⌈"; scen2 = ON_BLACK  + "⌉";  }
                else if (scen(i,j) instanceof Tree )   { scen1 = ON_GREEN  + "☘"; scen2 = ON_GREEN  + "☘";  }
                else if (scen(i,j) instanceof Water)   { scen1 = ON_BLUE   + "≈"; scen2 = ON_BLUE   + "≈";  }
                else if (scen(i,j) instanceof Robot)   { scen1 = ON_BLACK  + "("; scen2 = ON_BLACK  + ")";  }
                
                if      (item(i,j) == null)            { item1 = " "; item2 = " "; }
                else if (item(i,j) instanceof Crystal) { item1 = ON_YELLOW + "/"; item2 = ON_YELLOW + "\\"; }
                else if (item(i,j) instanceof Stone)   { item1 = ON_BLACK  + "."; item2 = ON_BLACK  + ".";  }
                
                item1 += colorof(i,j);
                item2 += colorof(i,j);
                scen1 += colorof(i,j);
                scen2 += colorof(i,j);
                
                print(RESTORE + "|" + colorof(i,j)
                      + scen1 + scen2 + " " + item1 + item2);
            }
            println(RESTORE + "|");
            
            // Center of hexagons
            print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item1 = " ", item2 = " ";
                String scen1 = " ", scen2 = " ";
                
                if      (scen(i,j) == null)            { scen1 = " "; scen2 = " "; }
                else if (scen(i,j) instanceof Base )   { scen1 = ON_RED    + "ß"; scen2 = ON_RED    + "ß";  }
                else if (scen(i,j) instanceof Rock )   { scen1 = ON_BLACK  + "⌊"; scen2 = ON_BLACK  + "⌋";  }
                else if (scen(i,j) instanceof Tree )   { scen1 = ON_GREEN  + "☘"; scen2 = ON_GREEN  + "☘";  }
                else if (scen(i,j) instanceof Water)   { scen1 = ON_BLUE   + "≈"; scen2 = ON_BLUE   + "≈";  }
                else if (scen(i,j) instanceof Robot)   { scen1 = ON_BLACK  + "/"; scen2 = ON_BLACK  + "\\"; }
                
                if      (item(i,j) == null)            { item1 = " "; item2 = " "; }
                else if (item(i,j) instanceof Crystal) { item1 = ON_YELLOW + "\\"; item2 = ON_YELLOW + "/"; }
                else if (item(i,j) instanceof Stone)   { item1 = ON_BLACK  + "¨";  item2 = ON_BLACK  + "¨"; }
                
                item1 += colorof(i,j);
                item2 += colorof(i,j);
                scen1 += colorof(i,j); 
                scen2 += colorof(i,j); 
                
                print(RESTORE + "|" + colorof(i,j)
                      + scen1 + scen2 + " " + item1 + item2);
            }
            println(RESTORE + "|");
            
            if(i == MAP_SIZE-1) break;

            // First line of hexagons
            print( (odd) 
                    ? "  "  + hexTop(1, colorof(i,  0) ) 
                    : " "   + hexBot(1, colorof(i+1,0) )
            );
            for(int j = 1; j < MAP_SIZE; j++)
                print( (odd) 
                    ? colorof(i,  j-1) + "   " + hexTop(1, colorof(i+1,j))
                    : colorof(i+1,j-1) + " "   + hexBot(1, colorof(i,  j))
            );
            println( (odd) 
                    ? colorof(i,  MAP_SIZE-1) + "   " + RESTORE + "/" 
                    : colorof(i+1,MAP_SIZE-1) + " "   + RESTORE + "\\"
            );
            
            // Second line of hexagons
            print( (odd) 
                    ? " "   + hexTop(2, colorof(i,0))
                    : "  "  + hexBot(2, colorof(i,0))
            );
            for(int j = 1; j < MAP_SIZE; j++)
                print( (odd) 
                    ? colorof(i,  j-1) + " "   + hexTop(2, colorof(i+1,j))
                    : colorof(i+1,j-1) + "   " + hexBot(2, colorof(i,  j))
            );
            println( (odd) 
                    ? colorof(i,MAP_SIZE-1) + " "   + RESTORE + "/" 
                    : colorof(i,MAP_SIZE-1) + "   " + RESTORE + "\\"
            ); 
        }
            
        // Print scenario bottom
        print(" " + hexBot(1, colorof(MAP_SIZE-1,0)) );
        for(int j = 1; j < MAP_SIZE; j++)
            print(" " + hexBot(1, colorof(MAP_SIZE-1,j)) );
        println(RESTORE);
        
        print("  " + hexBot(2, colorof(MAP_SIZE-1,0)) );
        for(int j = 1; j < MAP_SIZE; j++)
            print("   " + hexBot(2, colorof(MAP_SIZE-1,j)) );
        println(RESTORE);
        
        for(int j = 0; j < MAP_SIZE-1; j++)
            print("   '  ");
        println("   '");
    }
}
