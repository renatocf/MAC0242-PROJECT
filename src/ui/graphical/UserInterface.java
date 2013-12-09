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
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

 import java.io.*;

// Libraries
import arena.Robot;
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
    //private JTextArea tex;
    //private JFrame text;
    
    private EditorFrame editor;
    
    private boolean texBool;
    final private Graphical gui;
        
    /**
     * Default Constructor.<br>
     * @param gui Graphical set in which the 
     *            Panel is set
     * @param p   Player owner of this GUI
     */
    UserInterface(Graphical gui, Player p)
    {
        this.gui = gui;
        this.player  = p;
        
        // Prog Editor
        
        editor = gui.editorFrame;
        editor.setVisible(false);
      
        
        //text = new JFrame();
        //text.setSize(300, 500);
        //tex = new JTextArea("");
        
        //text.add(tex);
        //text.setVisible(false);
        texBool = false;
        
        // Launch a new thread to deal with buttons
        new Thread() { public void run() {
            while(true) {
                try { Thread.sleep(200); }
                catch (InterruptedException e) {}
            }}
        }.start();
            
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        //* MINIMAP **************************************************/
            new JGameButton(Images.MENU_MINMAP.ico(), new JGameAction() {
                public void exec() { UserInterface.this.gui.miniMapFrame.toggle(); }                
            });
            
        //* ADD BUTTON ************************************************/
            JGameButton newRobot = new JGameButton("A", new JGameAction() {
                public void exec() {
                    String program;
                    String prog;
                    String robotName = JOptionPane.showInputDialog("What is the robot's name?");
                    program = editor.get();
                    
                    // Saving the user program in a file.
                    File arquivo = new File("test/user.pos");
                    try( FileWriter fw = new FileWriter(arquivo) ){
                        fw.write(program);
                        fw.flush();
                    } catch(Exception e) {
                        System.out.println("Erro ao salvar o arquivo!");
                    }
          
                    if (program.equals(""))
                    {
                        prog = "test/" + JOptionPane.showInputDialog("Which program controls " + robotName + "?") + ".pos";
                    System.out.println("Aquiiii");
                    
                    }
                    else
                    {
                         prog = "test/user.pos";
                         System.out.println("Aquiiii2222");
                    }
                    player.insertArmy(robotName, prog);
 
                }
            });
        
        //* PROG  ****************************************************/
             new JGameButton("E", new JGameAction() {
                 public void exec() 
                 {
                     UserInterface.this.gui.editorFrame.setVisible(true);
                 }
             }); 
        
        //* CLEAN TEXT AREA  *****************************************/
            new JGameButton("C", new JGameAction() {
                public void exec() { editor.set(""); }                
            });
            
        
        //* EXIT *****************************************************/
            new JGameButton("X", new JGameAction() {
                public void exec() { System.exit(0); }
            });
        
        //* LIST OF ROBOTS ******************************************/
            // Robot[] robots = this.player.getRobots().toArray(new Robot[0]);
            // JComboBox robotList = new JComboBox(robots);
            // robotList.setSize(20,20);
            // this.add(robotList);
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
        private final Dimension d = new Dimension(5,10);
        
        /**
         * Default constructor<br>
         * @param img    Icon to be exhibited as a button
         * @param action JGameAction to be executed by the
         *               button when it is clicked
         */
        JGameButton(ImageIcon img, final JGameAction action)
        {
            super(img); init(action);
        }
        
        /**
         * Secondary constructor<br>
         * @param text   Text to te the label of the button
         * @param action JGameAction to be executed by the
         *               button when it is clicked
         */
        JGameButton(String text, final JGameAction action)
        {
            super(text); init(action);
        }
        
        /**
         * Initialize Button.
         * @param action JGameAction to be executed by the
         *               button when it is clicked
         */
        private void init(final JGameAction action)
        {
            this.setEnabled(true);
            this.setPreferredSize(d);
            
            // Executing JGameAction
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    action.exec();
                }
            });
            
            UserInterface.this.add(this);
        }
    }
}
