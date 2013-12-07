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

// Default libraries
import ui.MENU;
import ui.MENU.Opts;

// Graphical Libraries (AWT)
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;

// Graphical Libraries (Swing)
import javax.swing.*;

/**
 * <b>MENU - Graphical Mode</b><br>
 * Makes an implementation of the interface
 * MENU for exhibiting the game, using Java's
 * default graphic libraries (AWT and Swing).
 */
public class Menu extends JFrame implements MENU
{   
    private boolean pressed = false;
    private JButton new_game;
    private JButton exitB;    
    
    private MenuPanel menu;
    
    
    
    // Interface MENU
    /*private JPanel Grid()
    {
        setLayout(new GridLayout(2, 1));
        add(new JButton("1"));
        add(new JButton("2"));        
    }*/
    
    public Opts exhibit()
    {
        // Close the frame
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        //this.setComponentPanel(new MenuPanel());
        menu = new MenuPanel();
        add(menu);
        
        // Screen config.     
        this.setSize(740, 719);   
        this.setLocationRelativeTo(null);
        
        
        // Create buttons
       /* new_game = new JButton(":: Restart ::");
        exitB = new JButton("Exit");
        */
        
        
        
        // Button new game config.
       /* this.add(new_game);
        this.add(exitB);

        // Listeners
        new_game.addActionListener(this);
        exitB.addActionListener(this); */
        
        this.setVisible(true);
        
        menu.ListenButtonsInit();

        // Wait the press of some buttonJpanel 
        //while(!menu.getPressed());
            //new_game.addActionListener(this);
            
        this.setVisible(false);
        return MENU.Opts.NEW_GAME;
    }


}
