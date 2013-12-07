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
package gui.graphical;

// Graphical Libraries (AWT)
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;

// Graphical Libraries (Swing)
import javax.swing.*;

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
            //JButton minimapButton = new JButton("m");//(im);
            //minimapButton.setEnabled(true);
            //minimapButton.setPreferredSize(new Dimension(5,5));
            //
            //minimapButton.addMouseListener(new MouseAdapter() {
            //    boolean minControl = true;
            //    @Override
            //    public void mouseClicked(MouseEvent e) {
            //        minimap.setVisible(minControl ? false : true);
            //        minControl = !this.minControl;
            //    }
            //});
            JGameAction toggle = new JGameAction() 
            {
                boolean minControl = true;
                public void exec() 
                {
                    minimap.setVisible(minControl ? false : true);
                    minControl = !minControl;
                }
            };
            this.add(new JGameButton(null, toggle));
            
        //* ADD BUTTON ************************************************/
            JGameAction addNew = new JGameAction() 
            {
                public void exec() 
                {
                    player.insertArmy("Terminator", "test/scoutHL.pos");
                }
            };
            this.add(new JGameButton(null, addNew));
            // JButton newRobotButton = new JButton("A");
            // newRobotButton.setEnabled(true);
            // newRobotButton.setPreferredSize(new Dimension(5,5));
            // 
            // newRobotButton.addMouseListener(new MouseAdapter() {
            //     @Override
            //     public void mouseClicked(MouseEvent e) {
            //         player.insertArmy("Terminator", "test/scoutHL.pos");
            //     }
            // });
        
        //* EXIT *****************************************************/
            JGameAction exit = new JGameAction() 
            {
                public void exec() { System.exit(0); }
            };
            
            this.add(new JGameButton(null, exit));
            //JButton exitButton = new JButton("X");
            //exitButton.setEnabled(true);
            //exitButton.setPreferredSize(new Dimension(5,5));
            //
            //exitButton.addMouseListener(new MouseAdapter() {
            //    @Override
            //    public void mouseClicked(MouseEvent e) {
            //        System.exit(0);
            //    }
            //});
    }
    
    private interface JGameAction
    {
        void exec();
    }
    
    private class JGameButton extends JButton
    {
        private final Dimension d = new Dimension(5,5);
        
        JGameButton(ImageIcon img, final JGameAction action)
        {
            super(img);
            
            this.setEnabled(true);
            this.setPreferredSize(d);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    action.exec();
                }
            });
        }
    }
}
