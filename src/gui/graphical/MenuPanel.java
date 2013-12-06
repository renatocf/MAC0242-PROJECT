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

public class MenuPanel extends JPanel 
                       implements ActionListener
{

    private JButton new_game;
    private JButton exitB;
    
    private boolean pressed;
    
    public MenuPanel()
    {
        this.pressed = false;
        
        exitB    = new JButton("Exit");
        new_game = new JButton("New Game");
        
        add(exitB);
        add(new_game);
    } 
    
    public void testando()
    {
        while(!this.pressed)
        {
            new_game.addActionListener(this);
            exitB.addActionListener(this);
        }
    }

    
    private void image(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        Image red = Images.MENU_ROBOT.img().getSubimage(0, 0, 610, 400);
        
        g2d.drawImage(red, 0, 0, null);
    }
    
    
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        new_game.setBounds(640,30,120,80);
        // new_game.setBackground(Color.white);
        exitB.setBounds(640,140,120,80);
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
