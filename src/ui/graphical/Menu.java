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
    // Interface MENU
    public Opts exhibit()
    {
        // Screen options
        this.setSize(740, 719);   
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
        // Make menu visible
        this.setVisible(true);
        
        // Untill user choose an option
        MenuPanel menu = new MenuPanel();
        this.add(menu);
        MENU.Opts option = menu.getOpt();
        
        // Then, get invisible
        this.setVisible(false);
        
        // And return player's option
        return option;
    }
}
