package stackable;

/**
 * <b>Stackable - Text</b><br>
 * Packs strings inside a simple 
 * container, allowing them to
 * be identified as stackables.
 * 
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
public class Text implements Stackable
{
    private final String text;
    
    /** 
     * Default constructor.
     * @param text String with a text to be
     *             packed inside this container
     *             class.
     */
    public Text(String text)
    {
        this.text = text;
    }
    
    /** 
     * Getter for the string packed inside the
     * class.
     * @return String with the text.
     */
    public String getText()  { return this.text; }
    
    public String toString() { return this.text; }
}
