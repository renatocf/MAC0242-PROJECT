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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

// Graphical Libraries (AWT)
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Graphical Libraries (Swing)
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JTextPane;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;

/**
 * <b>Graphical - Editor Frame</b><br>
 * Creates a general editor to the user,
 * allowing it to write his own programs
 * for the robots.
 * 
 * @author Karina Suemi
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
class EditorFrame extends JFrame
    implements ActionListener
{
    JButton compile;
    JTextPane txtPane;
    
    EditorFrame(Graphical gui)
    {
        super("Robot-Battle - Editor");
        
        this.setSize(640,800);
        
        //* TEXT PANE *************************************************/
            this.txtPane = new JTextPane();
            this.txtPane.setFont(new Font("Serif", Font.PLAIN, 18));
            this.add(new JScrollPane(txtPane), BorderLayout.CENTER);
        
        //* MENU ******************************************************/
            JMenu menu = new JMenu("File");
            menu.add(makeMenuItem("Open" ));
            menu.add(makeMenuItem("Save" ));
            menu.add(makeMenuItem("Clear"));
            menu.add(makeMenuItem("Quit" ));
            
            compile = new JButton("Compile");
            
                
            JMenuBar menuBar = new JMenuBar();
            menuBar.add(menu);
            menuBar.add(compile);
            this.setJMenuBar(menuBar);
    }
        
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        switch(command)
        {
            case "Quit" : this.setVisible(false); break;
            case "Open" : this.loadFile();        break;
            case "Clear": this.clearPage();       break;
            case "Save" : this.saveFile();        break;
        }
        if(e.getSource() ==compile)
        {
            this.uploadAndCompile();
        }
    }
 
    
    
    /**
     * Auxiliar method to load a user file.
     */
    private void loadFile()
    {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        
        if(result == JFileChooser.CANCEL_OPTION) return;
        
        try {
            File file = chooser.getSelectedFile();
            java.net.URL url = file.toURI().toURL();
            txtPane.setPage(url);
            
        } catch(Exception e) {
            txtPane.setText("Could not load file: " + e);
        }
    }
    
    /**
     * Auxiliar method to load a user file.
     * @param pathToProg Path to file to be 
     *                   loaded in the panel
     */
    public void loadFile(String pathToProg)
    {
        String line = "", prog = "";
        try(
            BufferedReader reader = new BufferedReader(new FileReader(pathToProg))
        ) {  
            while ((line = reader.readLine()) != null) 
                prog += line + "\n";
            
            this.txtPane.setText(prog);
                
        } catch (Exception k) {}  
    }
    
    
    public void uploadAndCompile()
    {
        System.out.println("000000000000000000000000000");
    }
    
    /**
     * Auxiliar method to save file
     * in the directory .robot-battle.
     */
    private void saveFile() 
    {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        
        if(result == JFileChooser.CANCEL_OPTION) return;
        File destFile = chooser.getSelectedFile();
        
        try( FileWriter fw = new FileWriter(destFile) ){
            fw.write(this.get());
            fw.flush();
        } catch(Exception e) {
            System.out.println("Erro ao salvar o arquivo!");
        }    
    }
    
    private void clearPage()
    {
        this.txtPane.setText("");
    }
    
    private JMenuItem makeMenuItem(String name)
    {
        JMenuItem m = new JMenuItem(name);
        m.addActionListener(this);
        return m;
    }
    
    public String get()
    {
        return this.txtPane.getText();
    }
    
    public void set(String tex)
    {
        this.txtPane.setText(tex);
    }
}
