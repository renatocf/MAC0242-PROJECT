package stackable;

public class Text implements Stackable
{
    private final String text;
    
    public Text(String text)
    {
        this.text = text;
    }
    
    public boolean looksLikeNumber() { return false; }
    
    public String getText()  { return this.text; }
    public String toString() { return this.text; }
}
