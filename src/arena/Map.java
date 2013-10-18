package arena;

// Default libraries
import java.io.*;
import java.util.Vector;
import java.lang.reflect.*;
import java.lang.ProcessBuilder;

// Libraries
import robot.*;
import random.*;
import stackable.*;
import exception.*;
import parameters.*;
import stackable.item.*;
import scenario.Scenario;

public class Map implements Game
{
    // Map Matrixes
    public Terrain[][] map     = new Terrain[MAP_SIZE][MAP_SIZE];
    public char[][]    miniMap = new char[MAP_SIZE][MAP_SIZE];

    // Weather
    final private Weather w;
    
    public Map(Weather w)
    {
        this.w = w;
    }
    
    public Robot[][] genesis(int nPlayers)
        throws InvalidOperationException
    {
        RandomMap arena = new RandomMap (w, nPlayers, MAP_SIZE);
        miniMap = arena.getMatrix   (); 
        map     = arena.generateMap ();              
        
        //// TODO: receive PROG, generate robots
        //Parser user = new Parser();
        //Vector<Command> PROG = user.upload();
        //
        //Robot bender = new Robot("Bender", 8, 9, PROG);
        //Robot c3po   = new Robot("C3PO", 9, 8, PROG);
        //
        Robot[][] initial = new Robot[nPlayers][ROBOTS_NUM_INITIAL];
        
        return initial;
    }

    public Robot insertArmy(String name, int player, int ID, 
                            int i, int j, String pathToProg)
        throws SegmentationFaultException
    {
        if(i < 0 || j < 0 || i >= MAP_SIZE || j >= MAP_SIZE) 
            throw new SegmentationFaultException();
        
        // Precompiling
        precompile(pathToProg);
        
        // Creates a new string
        String prog = processInput(pathToProg);
        
        try { 
            Class<?> Parser = Class.forName("parser." + prog); 
            Method   upload = Parser.getMethod("upload");
            
            // Suppress warning of converting from Object class
            // to Vector<Command> (unhappily, it cannot be solved
            // due to the characteristics of generic types).
            @SuppressWarnings("unchecked")
            Vector<Command> PROG = (Vector<Command>) upload.invoke(null);
            
            Robot r = new Robot(name, player, ID, i, j, PROG);
            this.map[i][j].setScenario(r);
            return r;
        }
        catch(ClassNotFoundException e) 
        {
            System.err.println(
                "Class not found! Program \"" + 
                prog + "\" does not exist!"
            );
        }
        catch(NoSuchMethodException     e) {}
        catch(IllegalAccessException    e) {}
        catch(InvocationTargetException e) {}
        return null;
    }
    
    public Scenario removeScenario(int i, int j)
    {
        return map[i][j].removeScenario();
    }

    private static void precompile(String input)
    {
        // Generate and run process
        try {
            String [] params = { "perl", "bin/parser.pl", input };
            Process process = new ProcessBuilder(params).start();
            
            if(Verbosity.v)
            {
                InputStream is        = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br     = new BufferedReader(isr);
                
                System.out.printf("Output of running %s is:\n", input);
                String line;
                while ((line = br.readLine()) != null) {
                      System.out.println(line);
                }
                System.out.println();
            }
        }
        catch(IOException e)
        {
            System.err.print("[MAP] Impossible to print output ");
            System.err.print("of Parser.xml\n");
        }
    }

    private static String processInput(String input)
    {
        // Process input
        input = input.replaceFirst("^.*/"  , ""); // Takes out dir path
        input = input.replaceFirst("\\..*$", ""); // Takes out excension
        
        // Uppercase first
        char   first = Character.toUpperCase(input.charAt(0));
        String other = input.substring(1).toLowerCase();
        String prog  = first + other;

        // Debug message
        if(Verbosity.v && !prog.equals(input))
        {
            Verbosity.debug(input + " is not a valid name!");
            Verbosity.debug("Searching for " + prog);
            Verbosity.debug();
        }
        return prog;
    }
}
