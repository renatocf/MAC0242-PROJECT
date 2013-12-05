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
import java.awt.Graphics;

// Graphical Libraries (Swing)
import javax.swing.JPanel;

class Menu extends JPanel 
{
<<<<<<< HEAD
    private javax.swing.JButton botao1;

=======
    private int state = 0;

    public int iterator()
    {
        return this.state;
    }    
    
>>>>>>> master
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
    }
}
