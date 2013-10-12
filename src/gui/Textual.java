package gui;

import arena.*;
import scenario.*;
import parameters.*;
import stackable.item.*;

public class Textual implements Game, Colors
{
    Map map;

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
        print();
    }
    
    public void printMiniMap()
    {
        map.print();
        try { 
            Thread.sleep(3000);
        } catch (Exception e) {
        }
    }
    
    private String hexTop(int type, String midColor)
    {
        if(type == 1)
            return RESTORE + "/" + midColor +  " "  + RESTORE + "\\"; /*  / \\ */
        else
            return RESTORE + "/" + midColor + "   " + RESTORE + "\\"; /* /   \\ */
    }
    
    private String hexBot(int type, String midColor)
    {
        if(type == 1)
            return RESTORE + "\\" + midColor + "   " + RESTORE + "/"; /* \   // */
        else
            return RESTORE + "\\" + midColor +  " "  + RESTORE + "/"; /*  \ / */
    }
    
    private String colorof(Appearence ap)
    {
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
    
    private Appearence terrainLook(int i, int j)
    {
        // For arrays out of bounds, we'll have null.
        // In colorof function, its color will be RESTORE
        if(i < 0 || j < 0 || i >= MAP_SIZE || j >= MAP_SIZE) return null;
        return map.map[i][j].getAppearence();
    }
    
    public void print()
    {
        // Print scenario top
        for(int j = 0; j < MAP_SIZE-1; j++)
            System.out.print("   .  ");
        System.out.println("   .");
        
        System.out.print("  " + hexTop( 1, colorof(terrainLook(0,0)) ));
        for(int j = 1; j < MAP_SIZE; j++)
            System.out.print("   " + hexTop( 1, colorof(terrainLook(0,j)) ));
        System.out.println(RESTORE);
        
        System.out.print(" " + hexTop(2, colorof(terrainLook(0,0)) ));
        for(int j = 1; j < MAP_SIZE; j++)
            System.out.print(" " + hexTop(2, colorof(terrainLook(0,j)) ));
        System.out.println(RESTORE);
        
        // Print ordinary lines
        for(int i = 0; i < MAP_SIZE; i++)
        {
            /* System.out.print(ON_GREEN); */
            boolean odd = (i % 2 == 1) ? true : false;
            
            // Print itens and scenario
            System.out.print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item1     = " ", item2     = " ";
                String scenario1 = " ", scenario2 = " ";
                
                if      (map.map[i][j].getScenario() == null)          { scenario1 = " "; }
                else if (map.map[i][j].getScenario() instanceof Base ) { scenario1 = ON_RED   + "ß"; scenario2 = ON_RED   + "ß"; }
                else if (map.map[i][j].getScenario() instanceof Rock ) { scenario1 = ON_BLACK + "⌈"; scenario2 = ON_BLACK + "⌉"; }
                else if (map.map[i][j].getScenario() instanceof Tree ) { scenario1 = ON_GREEN + "☘"; scenario2 = ON_GREEN + "☘"; }
                else if (map.map[i][j].getScenario() instanceof Water) { scenario1 = ON_BLUE  + "≈"; scenario2 = ON_BLUE  + "≈"; }
                else if (map.map[i][j].getScenario() instanceof Robot) { scenario1 = ON_BLACK + "("; scenario2 = ON_BLACK + ")"; }
                
                if      (map.map[i][j].getItem() == null)            { item1 = " "; item2 = " "; }
                else if (map.map[i][j].getItem() instanceof Crystal) { item1 = ON_YELLOW + "/"; item2 = ON_YELLOW + "\\"; }
                else if (map.map[i][j].getItem() instanceof Stone)   { item1 = ON_BLACK  + "."; item2 = ON_BLACK  + "."; }
                
                item1     += colorof(terrainLook(i,j));
                item2     += colorof(terrainLook(i,j));
                scenario1 += colorof(terrainLook(i,j));
                scenario2 += colorof(terrainLook(i,j));
                
                System.out.print(RESTORE + "|" + colorof(terrainLook(i,j)) 
                                + scenario1 + scenario2 + " " + item1 + item2);
            }
            System.out.println(RESTORE + "|");
            
            // Center of hexagons
            System.out.print( (odd) ? "   " : "");
            for(int j = 0; j < MAP_SIZE; j++)
            {
                String item1     = " ", item2     = " ";
                String scenario1 = " ", scenario2 = " ";
                
                if      (map.map[i][j].getScenario() == null)          { scenario1 = " "; }
                else if (map.map[i][j].getScenario() instanceof Base ) { scenario1 = ON_RED   + "ß"; scenario2 = ON_RED   + "ß"; }
                else if (map.map[i][j].getScenario() instanceof Rock ) { scenario1 = ON_BLACK + "⌊"; scenario2 = ON_BLACK + "⌋"; }
                else if (map.map[i][j].getScenario() instanceof Tree ) { scenario1 = ON_GREEN + "☘"; scenario2 = ON_GREEN + "☘"; }
                else if (map.map[i][j].getScenario() instanceof Water) { scenario1 = ON_BLUE  + "≈"; scenario2 = ON_BLUE  + "≈"; }
                else if (map.map[i][j].getScenario() instanceof Robot) { scenario1 = ON_BLACK + "/"; scenario2 = ON_BLACK + "\\"; }
                
                if      (map.map[i][j].getItem() == null)            { item1 = " "; item2 = " "; }
                else if (map.map[i][j].getItem() instanceof Crystal) { item1 = ON_YELLOW + "\\"; item2 = ON_YELLOW + "/"; }
                else if (map.map[i][j].getItem() instanceof Stone)   { item1 = ON_BLACK  + "¨";  item2 = ON_BLACK  + "¨"; }
                
                item1     += colorof(terrainLook(i,j));
                item2     += colorof(terrainLook(i,j));
                scenario1 += colorof(terrainLook(i,j)); 
                scenario2 += colorof(terrainLook(i,j)); 
                
                System.out.print(RESTORE + "|" + colorof(terrainLook(i,j)) 
                                + scenario1 + scenario2 + " " + item1 + item2);
            }
            System.out.println(RESTORE + "|");
            
            if(i == MAP_SIZE-1) break;

            // First line of hexagons
            System.out.print( (odd) 
                    ? "  "  + hexTop(1, colorof( terrainLook(i,  0) )) 
                    : " "   + hexBot(1, colorof( terrainLook(i+1,0) )) 
            );
            for(int j = 1; j < MAP_SIZE; j++)
                System.out.print( (odd) 
                    ? colorof( terrainLook(i,  j-1) ) + "   " + hexTop(1, colorof( terrainLook(i+1,j) )) 
                    : colorof( terrainLook(i+1,j-1) ) + " "   + hexBot(1, colorof( terrainLook(i,  j) ))
            );
            System.out.println( (odd) 
                    ? colorof( terrainLook(i,MAP_SIZE-1) ) + "   " + RESTORE + "/" 
                    : colorof( terrainLook(i+1,MAP_SIZE-1) ) + " "   + RESTORE + "\\"
            );
            
            // Second line of hexagons
            System.out.print( (odd) 
                    ? " "   + hexTop(2, colorof( terrainLook(i,0) ))
                    : "  "  + hexBot(2, colorof( terrainLook(i,0) ))
            );
            for(int j = 1; j < MAP_SIZE; j++)
                System.out.print( (odd) 
                    ? colorof( terrainLook(i,  j-1) ) + " "   + hexTop(2, colorof( terrainLook(i+1,j) ))
                    : colorof( terrainLook(i+1,j-1) ) + "   " + hexBot(2, colorof( terrainLook(i,  j) ))
            );
            System.out.println( (odd) 
                    ? colorof( terrainLook(i,MAP_SIZE-1) ) + " "   + RESTORE + "/" 
                    : colorof( terrainLook(i,MAP_SIZE-1) ) + "   " + RESTORE + "\\"
            ); 
        }
            
        // Print scenario bottom
        System.out.print(" " + hexBot(1, colorof(terrainLook(MAP_SIZE-1,0)) ));
        for(int j = 1; j < MAP_SIZE; j++)
            System.out.print(" " + hexBot(1, colorof(terrainLook(MAP_SIZE-1,j)) ));
        System.out.println(RESTORE);
        
        System.out.print("  " + hexBot(2, colorof(terrainLook(MAP_SIZE-1,0)) ));
        for(int j = 1; j < MAP_SIZE; j++)
            System.out.print("   " + hexBot(2, colorof(terrainLook(MAP_SIZE-1,j)) ));
        System.out.println(RESTORE);
        
        for(int j = 0; j < MAP_SIZE-1; j++)
            System.out.print("   '  ");
        System.out.println("   '");
    }
}
