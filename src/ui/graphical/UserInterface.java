/**********************************************************************/
/* Copyright 2013 KRV                                                 */
/*                                                                    */
/* Licensed under the Apache License, Version 2.0 (the "License");    */
/* you may not use this file except in compliance with the License.   */
/* You may obtain a copy of the License at                            */
/*                                                                    */
/*  http://www.apache.org/licenses/LICENSE-2.0                        */
/*                                                                    */
/* Unless required by applicable law or agreed to in writing,         */
/* software distributed under the License is distributed on an        */
/* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,       */
/* either express or implied.                                         */
/* See the License for the specific language governing permissions    */
/* and limitations under the License.                                 */
/**********************************************************************/
package ui.graphical;

// Graphical Libraries (AWT)
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

// Graphical Libraries (Swing)
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

// Libraries
import players.Player;

/**
 * <b>Graphical - User Interface</b><br>
 * Creates a menu bar in the game to 
 * be like a user interface.
 * @see Graphical
 * @see arena.Map
 * @see arena.World
 */
class UserInterface extends JPanel
{
    // Private variables
    private Player player;
    private MiniMapFrame minimap;
        
    /**
     * Default Constructor.<br>
     * @param p            Player owner of this GUI
     * @param miniMapFrame MiniMap window to be 
     *                     toggled
     */
    UserInterface(Player p, MiniMapFrame miniMapFrame)
    {
        this.minimap = miniMapFrame;
        this.player  = p;
        
        // Launch a new thread to deal with buttons
        new Thread() { public void run() {
            while(true) {
                try { Thread.sleep(200); }
                catch (InterruptedException e) {}
            }}
        }.start();
            
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        //* MINIMAP **************************************************/
            new JGameButton(null, new JGameAction() {
                boolean minControl = true;
                public void exec() {
                    minimap.setVisible(minControl ? false : true);
                    minControl = !minControl;
                }
            });
            
        //* ADD BUTTON ************************************************/
            new JGameButton(null, new JGameAction() {
                public void exec() {
                    player.insertArmy("Terminator", "test/scoutHL.pos");
                }
            });
        
        //* EXIT *****************************************************/
            new JGameButton(null, new JGameAction() {
                public void exec() { System.exit(0); }
            });
    }
    
    /**
     * <b>User Interface - JGameAction</b><br>
     * Auxiliar interface to hold an action
     * that should be executed by a JGameButton.
     */
    private interface JGameAction
    {
        /**
         * Method to be executed by a JGameButton.
         */
        void exec();
    }
    
    /**
     * <b>User Interface - JGameButton</b><br>
     * Auxiliar class to initialize a new button,
     * with its own design and a JGameAction.
     */
    private class JGameButton extends JButton
    {
        // Buttons size
        private final Dimension d = new Dimension(5,5);
        
        /**
         * Default constructor<br>
         * @param img    Icon to be exhibited as a button
         * @param action JGameAction to be executed by the
         *               button when it is clicked
         */
        JGameButton(ImageIcon img, final JGameAction action)
        {
            super(img);
            
            this.setEnabled(true);
            this.setPreferredSize(d);
            
            // Executing JGameAction
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    action.exec();
                }
            });
            
            UserInterface.this.add(this);
        }
    }
}
