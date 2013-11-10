package gui.graphical;

// Graphical Libraries (AWT)
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

// Graphical Libraries (Swing)
import javax.swing.*;
import javax.swing.text.JTextComponent;

// Libraries
import gui.*;
import arena.Map;
import parameters.*;

// Import links
import static parameters.Game.*;

@SuppressWarnings("serial")
public class Scrollable extends JPanel 
{
    // Directions
    private static final String UP    = "Up";
    private static final String DOWN  = "Down";
    private static final String LEFT  = "Left";
    private static final String RIGHT = "Right";
    
    private JTextArea area = new JTextArea(20, 40);
    private JScrollPane scrollPane;
    
    private Panel screen;
        
    public Scrollable(Map map)
    {
        screen = new Panel(25,
            (int)(25*MAP_SIZE*Math.sqrt(3)+MAP_SIZE*Math.sqrt(3)/2), 
            (int)(25*1.5*MAP_SIZE), map
        );
        
        JScrollPane scrollPane2 = new JScrollPane(
            screen,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        
        add(scrollPane);

        // have JTextArea tell us how tall a line of text is.
        int vInc = area.getScrollableUnitIncrement(
            scrollPane.getVisibleRect(), SwingConstants.VERTICAL, 1
        );
        int hInc = area.getScrollableUnitIncrement(
            scrollPane.getVisibleRect(), SwingConstants.HORIZONTAL, 1
        );

        // add key bindings to the JTextArea 
        int condition = JTextComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inMap = area.getInputMap(condition);
        ActionMap actMap = area.getActionMap();
        
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,    0), UP   );
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,  0), DOWN );
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,  0), LEFT );
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), RIGHT);
        
        BoundedRangeModel vs, hs;
        vs = scrollPane.getVerticalScrollBar().getModel();
        hs = scrollPane.getHorizontalScrollBar().getModel();
        
        actMap.put(UP,    new MovementAction(UP,    vs, hs, vInc));
        actMap.put(DOWN,  new MovementAction(DOWN,  vs, hs, vInc));
        actMap.put(LEFT,  new MovementAction(LEFT,  vs, hs, vInc));
        actMap.put(RIGHT, new MovementAction(RIGHT, vs, hs, vInc));
    }

    // Action for our key binding to perform when bound event occurs
    private class MovementAction extends AbstractAction 
    {
        private BoundedRangeModel vScrollBarModel;
        private BoundedRangeModel hScrollBarModel;
        private int vInc, hInc;
        
        public MovementAction
        (
            String name, 
            BoundedRangeModel vModel, 
            BoundedRangeModel hModel, 
            int vInc
        ) 
        {
            super(name);
            this.vScrollBarModel = vModel;
            this.hScrollBarModel = hModel;
            this.vInc = vInc;
            this.hInc = hInc;
        }

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            String name = getValue(AbstractAction.NAME).toString();
            int value = vScrollBarModel.getValue();
            
            switch(name)
            {
                // Vertical
                case UP:    //↑
                    value -= vInc;
                    vScrollBarModel.setValue(value);
                    break;
                case DOWN:  //↓
                    value += vInc;
                    vScrollBarModel.setValue(value);
                    break;
                //
                
                // Horizontal
                case LEFT:  //←
                    value -= hInc;
                    hScrollBarModel.setValue(value);
                    break;
                case RIGHT: //→
                    value += hInc;
                    hScrollBarModel.setValue(value);
                    break;
                //
                default:
            }
        } // actionPerformed
    }
}
