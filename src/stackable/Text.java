package stackable;

/**
 * <b>Stackable Text</b><br>
 * This class controls the type of
 * stackables that represents words,
 * more specifically, Strings.
 * 
 * @author Karina Awoki, Renato Cordeiro, Vinicius Silva
 */
public class Text implements Stackable
{
    private final String text;
    /** 
     * Is the constructor of the type Text, it
     * needs Strings format parameters to
     * construct it.
     */
    public Text(String text)
    {
        this.text = text;
    }
    
    /** 
     * This function return the Text in String format.
     */
    public String getText()  { return this.text; }
    
    /** 
     * This function return the Text in String format
     * and in case of use IO functions, it prints
     * the text in String formar.
     */
    public String toString() { return this.text; }
}
