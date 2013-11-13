package gui.graphical;

// Graphical Libraries (AWT)
import java.awt.*;

// Graphical Libraries (Swing)
import javax.swing.*;
import javax.swing.text.*;

class EditorFrame extends JFrame
{
    JTextPane txtPane;
    
    EditorFrame()
    {
        this.setTitle("Robot's Battle - Editor");
        this.setSize(640,800);
        
        txtPane = new JTextPane();
        txtPane.setFont(new Font("Serif", Font.PLAIN, 24));
        
        SimpleAttributeSet red = new SimpleAttributeSet();
        StyleConstants.setForeground(red, Color.RED);
        StyleConstants.setBold(red, true);
       
        append("if", red);
        append(" ", null);
        
        this.add(new JScrollPane(txtPane), BorderLayout.CENTER);
        
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() { setVisible(true); }
            });
    }
    
    void append(String s, AttributeSet attributes)
    {
        Document d = txtPane.getDocument();
        
        try{ d.insertString(d.getLength(), s, attributes); }
        catch(BadLocationException b){}
    }
    
    String getText()
    {
        return this.txtPane.getText();
    }
        
        
        
    
}
