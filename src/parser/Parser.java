package parser;

// IO libraries
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;

// Default libraries
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// Libraries
import robot.*;
import exception.*;
import stackable.*;
import stackable.item.*;

public class Parser
{
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                                 LANGUAGE
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    private static final // arg: none
    String[] ins1 = {
        "ADD" , "DIV" , "DUP" , "END" , "EQ"  , "GE"  , "GT"  , 
        "MOD" , "LE"  , "LT"  , "MUL" , "NE"  , "POP" , "PRN" , 
        "SUB" , "RET" , "MOVE", "DRAG", "DROP", "HIT" , "LOOK", 
        "ITEM", "SEE" , "SEEK", "ASK" , "NOP"
    }; 

    private static final // arg: numeric (only) 
    String[] ins2 = { "RCL" , "STO" };                 
    
    private static final // arg: address/string
    String[] ins3 = { "JMP" , "JIF" , "JIT", "CALL" }; 
    
    private static final // arg: var name (only)
    String[] ins4 = { "ALOC", "FREE", "GET", "SET"  }; 
    
    private static final // stackables
    String[] stk =  { "crystal", "stone" }; 
    
    private static final //attacks
    String[] atk =  { "ranged", "melee" }; 
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                                 PATTERNS
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    
    private static final 
    /* String comment = "^\\s*#"; */
    Pattern comment = Pattern.compile("^\\s*#");
    
    private static final 
    /* String blankLine = "^\\s*$"; */
    Pattern blankLine = Pattern.compile("^\\s*$");
    
    private static final 
    /* String labelOnly = "^\\s*([A-Za-z]\\w*):\\s*$"; */
    Pattern labelOnly = Pattern.compile("^\\s*([A-Za-z]\\w*):\\s*$");
    
    private static final 
    /* String completeLine =  */
    Pattern completeLine = Pattern.compile(
        "^\\s*"                          // Spaces
        + "(?:"
        +       "([A-Za-z]\\w*):"        // LABEL: Starts with at least
                                         // one character ant the others
                                         // are alphanumeric or _
        + ")?"
        + "\\s*"                         // Spaces
        + "(?:"
        +       "([A-Z]+)"               // COMMAND
        + "(?:"                          
        +           "\\s+"               // Spaces
        +           "(" 
        +               "\\d+"           // NUMBER    }
        +               "|"              //           }
        +               "\\[\\w+\\]"     // VARIABLE  }
        +               "|"              //           }
        +               "\\(x\\)\\w+"    // ATTACK    }
        +               "|"              //           }
        +               "->\\w*"         // DIRECTION } ARGUMENT
        +               "|"              //           }
        +               "\"\\w+\""       // STRING    } 
        +               "|"              //           }
        +               "{\\w+}"         // STACKABLE }
        +               "|"              //           }
        +               "[A-Za-z]\\w*"   // LABEL     }
        +           ")"
        +       ")?"
        +   ")?"
        +   "\\s*"                       // Spaces
        +   "(?:\\#.*)?"                 // Comments
        + "$"                            // EOL
    , Pattern.COMMENTS);
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                                  METHODS
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    public static Vector<Command> parse(String pathToProg)
    {
        Vector<Command> PROG = new Vector<Command>();
        Path path = Paths.get(pathToProg);
        int nLine = 0;
        
        // Defines encoding of the file
        Charset cs = Charset.forName("utf8");
        
        // Read the lines
        try (BufferedReader reader = Files.newBufferedReader(path, cs)) 
        {
            String line = null;
            while ((line = reader.readLine()) != null) 
            {
                printf("[%03d] ", nLine++);
                say(line);
                
                Matcher m_comment      = comment.matcher      (line);
                Matcher m_blankLine    = blankLine.matcher    (line);
                Matcher m_labelOnly    = labelOnly.matcher    (line);
                Matcher m_completeLine = completeLine.matcher (line);
                
                if(m_comment.matches())        continue;
                else if(m_blankLine.matches()) continue;
                else if(m_labelOnly.matches())        
                { 
                    PROG.add(new Command(null, null, m_labelOnly.group(1)));
                }
                else if(m_completeLine.matches())
                {
                    String lab = m_completeLine.group(1);
                    String com = m_completeLine.group(2);
                    String arg = m_completeLine.group(3);
                    int err = 0;
                    
                    print("    Lab: ", lab, ", com ", com, "arg ", arg);
                }
            }
        } 
        
        // Exceptions
        catch (IOException e) {
            System.err.format("[Parser] IOException: %s%n", e);
        } 
        
        return PROG;
    }

    public static void main(String[] args)
    {
        parse(args[0]);
    }
    
    
    /*
    ////////////////////////////////////////////////////////////////////
    -------------------------------------------------------------------
                              AUXILIAR METHODS
    -------------------------------------------------------------------
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    */
    /** 
     * Print without a terminal newline.
     * @param strings Variable size list of objects,
     *                which will have their 'toString()'
     *                method used for being printed.
     */
    private static void print(Object ... strings)
    {
        for(Object s: strings) System.out.print(s.toString());
    }
    
    /** 
     * Print with a terminal newline.
     * @param strings Variable size list of objects,
     *                which will have their 'toString()'
     *                method used for being printed.
     */
    private static void say(Object ... strings)
    {
        print(strings); System.out.println();
    }
    
    /** 
     * Print formatted.
     * @param format String with sequences of formats to
     *               be printed.
     * @param args   Variable size list of objects,
     *               which will have their 'toString()'
     *               method used for being printed.
     */
    private static void printf(String format, Object ... args)
    {
        System.out.printf(format, args);
    }
}
