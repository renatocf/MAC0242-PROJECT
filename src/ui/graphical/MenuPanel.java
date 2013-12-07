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
import java.awt.*;
import java.awt.event.*;

// Graphical Libraries (Swing)
import javax.swing.*;

/**
 * Is the class representing the initial screen.
 */
public class MenuPanel extends JPanel 
                       implements ActionListener
{

    private JButton new_game;
    private JButton exitB;
    
    private boolean pressed;
    
    public MenuPanel()
    {
        // Create a RGB color
        Color myColor = Color.decode("#a69f8f");
        this.setBackground(myColor);
    
        this.pressed = false;
        
        exitB    = new JButton("Exit");
        new_game = new JButton("New Game");
        
        add(exitB);
        add(new_game);
    } 
    
    /**
     * Is called by Menu and set the configurations
     * of the initial screen.
     */
    public void ListenButtonsInit()
    {
        while(!this.pressed)
        {
            new_game.addActionListener(this);
            exitB.addActionListener(this);
        }
    }

    /**
     * Control the image.
     */
    private void image(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        Image red = Images.MENU_ROBOT.img().getSubimage(0, 0, 610, 719);
        
        g2d.drawImage(red, 0, 0, null);
    }
    
    
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        // Position of Buttons
        new_game.setBounds(590,30,120,80);
        exitB.setBounds(590,140,120,80);
        
        // Color of Buttons
        exitB.setBackground(Color.white);
        new_game.setBackground(Color.white);
        
        image(g);      
    }
    
    // Button Event
    public void actionPerformed(ActionEvent evt) 
    {  
        Object obj=evt.getSource();  
          
        if(obj == new_game)
        {
            this.pressed = true;
        }  
        else if(obj == exitB)
        {
            System.exit(0);
        }

    } 
}
