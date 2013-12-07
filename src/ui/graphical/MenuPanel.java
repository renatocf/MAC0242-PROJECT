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
import java.awt.*;
import java.awt.event.*;

// Graphical Libraries (Swing)
import javax.swing.*;

// Libraries
import ui.MENU;

/**
 * <b>MENU - Menu Panel</b><br>
 * Panel with the menu screen and the
 * buttons with its properties.
 */
public class MenuPanel extends JPanel 
{
    // Buttons
    private JButton exitButton;
    private JButton newGameButton;
    
    // Flag to interrupt menu execution
    private MENU.Opts option  = null;
    
    /**
     * Default constructor.<br>
     */
    MenuPanel()
    {
        // Create a RGB color
        this.setBackground(Color.decode("#a69f8f"));
        
        //* EXIT *****************************************************/
            this.exitButton = new JButton("Exit");
            this.exitButton.setEnabled(true);
            this.exitButton.setBackground(Color.white);
            
            this.exitButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    MenuPanel.this.option  = MENU.Opts.EXIT;
                }
            });
            
            this.add(this.exitButton);
        
        //* NEW GAME *************************************************/
            this.newGameButton = new JButton("New Game");
            this.newGameButton.setEnabled(true);
            this.newGameButton.setBackground(Color.white);
            
            this.newGameButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    MenuPanel.this.option = MENU.Opts.NEW_GAME;
                }
            });
            
            this.add(this.newGameButton);
    } 
    
    /**
     * Keeps the menu panel in an infinite loop,
     * listening for buttons' actions.
     * @return MENU option
     */
    MENU.Opts getOpt()
    {
        while(this.option == null)
            try { Thread.sleep(200); }
            catch (InterruptedException e) {}
        return option;
    }
    
    /**
     * Draw the menu with an image in the 
     * background and the buttons in the 
     * correct positions.
     * @param g Graphical context
     */
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        // Put buttons in the menu screen
        this.exitButton.setBounds    (590,140,120,80);
        this.newGameButton.setBounds (590, 30,120,80);
        
        // Set image in the background
        Graphics2D g2d = (Graphics2D) g;
        Image red = Images.MENU_ROBOT.img().getSubimage(0, 0, 610, 719);
        g2d.drawImage(red, 0, 0, null);
    }
}
