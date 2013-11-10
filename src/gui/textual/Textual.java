package gui.textual;

// Libraries
import gui.*;
import arena.*;
import scenario.*;
import players.Base;
import stackable.item.*;

// Import links
import static parameters.Game.*;

/**
 * <b>GUI - Textual Mode</b><br>
 * Makes an implementation of the interface
 * GUI for exhibiting the game, with colors,
 * in a Unix shell.
 * <p>
 * The program may also work if the shell
 * accepts the ANSI Escape Codes.
 * 
 * @author Renato Cordeiro Ferreira
 */
public class Textual implements Colors, GUI
{
    private Map map;

    /** 
     * Default constructor.<br>
     * @param map Object of the class map
     *            from package arena.
     */
    public Textual(Map map)
    {
        this.map = map;
    }
    
    public void paint()
    {
        try { Thread.sleep(SPEED); } 
        catch (Exception e) { }
        
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
    
    /*
     *  .--------------------------------------------.
     *  |                   INDEX                    |
     *  |---------------.----------------------------.
     *  | Items:        | Scenarios:                 |
     *  |===============|============================|
     *  |               |                            |
     *  | - Crystal: /\ | - Base:  ßß   - Robot: ()  |
     *  |            \/ |          ßß            /\  |
     *  | - Stone:   .. | - Rock:  ⌈⌉                |
     *  |            ¨¨ |          ⌊⌋                |
     *  |               | - Trees: ☘☘                |
     *  |               |          ☘☘                |
     *  '---------------'----------------------------'
     */
    public void printMap()
    {
        // Print map top
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
            // Checks it the line is odd or even.
            boolean odd = (i % 2 == 1) ? true : false;
            
            // Center top of the hexagons
            print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item1 = " ", item2 = " ";
                String scen1 = " ", scen2 = " ";
                
                Scenario S = scen(i,j); 
                Item     I = item(i,j);
                
                if      (S == null)            { scen1 = " "; scen2 = " "; }
                else if (S instanceof Base )   { Base b = (Base) S; String crs = ""+ b.getCrystals(); 
                                                 scen1 = team(i,j) + crs; scen2 = team(i,j) + "ß";  }
                else if (S instanceof Rock )   { scen1 = ON_BLACK  + "⌈"; scen2 = ON_BLACK  + "⌉";  }
                else if (S instanceof Tree )   { scen1 = ON_GREEN  + "☘"; scen2 = ON_GREEN  + "☘";  }
                else if (S instanceof Robot)   { scen1 = team(i,j) + "("; scen2 = team(i,j) + ")";  }
                
                if      (I == null)            { item1 = " "; item2 = " "; }
                else if (I instanceof Crystal) { item1 = ON_YELLOW + "/"; item2 = ON_YELLOW + "\\"; }
                else if (I instanceof Stone)   { item1 = ON_BLACK  + "."; item2 = ON_BLACK  + ".";  }
                
                item1 += colorof(i,j);
                item2 += colorof(i,j);
                scen1 += colorof(i,j);
                scen2 += colorof(i,j);
                
                print(RESTORE + "|" + colorof(i,j)
                      + scen1 + scen2 + " " + item1 + item2);
            }
            println(RESTORE + "|");
            
            // Center bottom of the hexagons
            print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item1 = " ", item2 = " ";
                String scen1 = " ", scen2 = " ";
                
                Scenario S = scen(i,j); 
                Item     I = item(i,j);
                
                if      (S == null)            { scen1 = " "; scen2 = " "; }
                else if (S instanceof Base )   { scen1 = team(i,j) + "ß"; scen2 = team(i,j) + "ß";  }
                else if (S instanceof Rock )   { scen1 = ON_BLACK  + "⌊"; scen2 = ON_BLACK  + "⌋";  }
                else if (S instanceof Tree )   { scen1 = ON_GREEN  + "☘"; scen2 = ON_GREEN  + "☘";  }
                else if (S instanceof Robot)   { scen1 = team(i,j) + "/"; scen2 = team(i,j) + "\\"; }
                
                if      (I == null)            { item1 = " "; item2 = " "; }
                else if (I instanceof Crystal) { item1 = ON_YELLOW + "\\"; item2 = ON_YELLOW + "/"; }
                else if (I instanceof Stone)   { item1 = ON_BLACK  + "¨";  item2 = ON_BLACK  + "¨"; }
                
                item1 += colorof(i,j);
                item2 += colorof(i,j);
                scen1 += colorof(i,j); 
                scen2 += colorof(i,j); 
                
                print(RESTORE + "|" + colorof(i,j)
                      + scen1 + scen2 + " " + item1 + item2);
            }
            println(RESTORE + "|");
            
            if(i == MAP_SIZE-1) break;

            // First line of hexagons top/bottom
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
            
            // Second line of hexagons top/bottom
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
            
        // Print map bottom
        String pre = (MAP_SIZE % 2 == 0) ? "   " : "";
        print(pre + " " + hexBot(1, colorof(MAP_SIZE-1,0)) );
        for(int j = 1; j < MAP_SIZE; j++)
            print(" " + hexBot(1, colorof(MAP_SIZE-1,j)) );
        println(RESTORE);
        
        print(pre + "  " + hexBot(2, colorof(MAP_SIZE-1,0)) );
        for(int j = 1; j < MAP_SIZE; j++)
            print("   " + hexBot(2, colorof(MAP_SIZE-1,j)) );
        println(RESTORE);
        
        print(pre);
        for(int j = 0; j < MAP_SIZE-1; j++)
            print("   '  ");
        println("   '");
    }
    
    public boolean gameOver()
    {
        System.out.print(CLEAR);
        System.out.println(
            "                                             "
            +"  ######### ######### ##      ## ########## "
            +"  ##        ###   ### ###    ### ###        "
            +"  ##   #### ###   ### ####  #### ########## "
            +"  ##     ## ######### ## #### ## ###        "
            +"  ######### ###   ### ##  ##  ## ###        "
            +"  ######### ###   ### ##      ## ########## "
            +"                                            "
            +"  ######### ##     ## ########## #########  "
            +"  ##     ## ##     ## ###        ###   ###  "
            +"  ##     ##  ##   ##  ########## ###   ###  "
            +"  ##     ##   #####   ###        #########  "
            +"  ##     ##    ###    ###        ###   ###  "
            +"  #########     #     ########## ###    ### "
        );
        return true;
    }
    
    /**
     * Auxiliar function for printing: hexagon top.<br>
     * Encapsulates the printing of an hexagon top.
     * 
     * @param  type     If 1, is the top most part of the 
     *                  hexagon. Otherwise, is the base of it
     * @param  midColor Color which may be between the 
     *                  lines of the form.
     * @return String with one of the parts of the hexagon top.
     */
    final private String hexTop(int type, String midColor)
    {
        if(type == 1)
            return RESTORE + "/" + midColor +  " "  + RESTORE + "\\"; /*  / \\ */
        else
            return RESTORE + "/" + midColor + "   " + RESTORE + "\\"; /* /   \\ */
    }
    
    /**
     * Auxiliar function for printing: hexagon bottom.<br>
     * Encapsulates the printing of an hexagon top.
     * 
     * @param  type     If 1, is the upper part of the bottom of the 
     *                  hexagon. Otherwise, is the end of it.
     * @param  midColor Color which may be between the 
     *                  lines of the form.
     * @return String with one of the parts of the hexagon bottom.
     */
    final private String hexBot(int type, String midColor)
    {
        if(type == 1)
            return RESTORE + "\\" + midColor + "   " + RESTORE + "/"; /* \   // */
        else
            return RESTORE + "\\" + midColor +  " "  + RESTORE + "/"; /*  \ / */
    }
    
    /**
     * Auxiliar getter for: terrain's appearence color.<br>
     * Encapsulates the printing of the terrain's appearence.
     * 
     * @param  i Line position in the terrain matrix
     *           (from 0 to MAP_SIZE-1)
     * @param  j Colum position in the terrain matrix
     *           (from 0 to MAP_SIZE-1)
     * @return String with the color which matches the 
     *         appearence for the terrain in the (i,j) 
     *         position 
     */
    final private String colorof(int i, int j) //(Appearence ap)
    {
        Appearence ap = map.map[i][j].getAppearence();
        
        if(ap == null) return RESTORE;
        switch (ap)
        {
            case DIRT   : return ON_BLACK; 
            case DEEP   : return ON_BLUE;  
            case GRASS  : return ON_IGREEN;
            case WATER  : return ON_IBLUE;  
            case TUNDRA : return ON_WHITE; 
            
            case ROCKY  : return ON_IBLACK;
            case ICE    : return ON_CYAN;  
            case JUNGLE : return ON_IGREEN;
            case SAND   : return ON_YELLOW;
            default     : return RESTORE;  
        }
    }
    
    /**
     * Auxiliar getter for: robot's team color.<br>
     * Encapsulates the printing of a team color.
     * 
     * @param  i Line position in the terrain matrix
     *           (from 0 to MAP_SIZE-1)
     * @param  j Colum position in the terrain matrix
     *           (from 0 to MAP_SIZE-1)
     * @return String with the color of a robot's team.
     */
    final private String team(int i, int j)
    {
        Scenario s = scen(i, j);
        String color = s.getTeam().color();
        switch(color)
        {
            case ""       : return "";
            case "BLACK"  : return ON_BLACK;
            case "RED"    : return ON_RED;
            case "GREEN"  : return ON_GREEN;
            case "YELLOW" : return ON_YELLOW;
            default       : return "";
        }
    }
    
    /**
     * Auxiliar getter for: terrain's scenario.<br>
     * Encapsulates the printing of the terrain's scenario.
     * 
     * @param  i Line position in the terrain matrix
     *           (from 0 to MAP_SIZE-1)
     * @param  j Colum position in the terrain matrix
     *           (from 0 to MAP_SIZE-1)
     * @return Scenario for the terrain in the (i,j) position 
     */
    final private Scenario scen(int i, int j)
    {
        if(i < 0 || j < 0 || i >= MAP_SIZE || j >= MAP_SIZE) return null;
        return map.map[i][j].getScenario();
    }
    
    /**
     * Auxiliar getter for: terrain's item.<br>
     * Encapsulates the printing of an hexagon top.
     * 
     * @param  i Line position in the terrain matrix
     *           (from 0 to MAP_SIZE-1)
     * @param  j Colum position in the terrain matrix
     *           (from 0 to MAP_SIZE-1)
     * @return Item for the terrain in the (i,j) position 
     */
    final private Item item(int i, int j)
    {
        if(i < 0 || j < 0 || i >= MAP_SIZE || j >= MAP_SIZE) return null;
        return map.map[i][j].getItem();
    }

    /**
     * Encapsulate printing function (without \n).
     * @param s String to be printed.
     */
    final private void print(String s)
    {
        System.out.print(s);
    }
    
    /**
     * Encapsulate printing function (with \n).
     * @param s String to be printed.
     */
    final private void println(String s)
    {
        System.out.println(s);
    }
}
