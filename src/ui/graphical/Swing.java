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

// Graphical Libraries (Swing)
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * <b>Swing</b>
 * Initialize global setting related 
 * to the GUI Swing.
 */
public class Swing
{
    // Singleton object Swing
    private static Swing swing = null;
    
    // No instances of this class allowed
    /**
     * Initialize Swing to ser Look &amp; Feels and 
     * any other global configuration required.
     */
    private Swing()
    { 
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
               
            // To avoid error in the JDK
            System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        } 
        catch (UnsupportedLookAndFeelException e) {
            System.err.println("[SWING] Unsupported L&F");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.err.println("[SWING] Class not found");
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            System.err.println("[SWING] Problems with instantiation");
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            System.err.println("[SWING] Illegal access");
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize Swing only once.
     */
    static void init()
    {
        if(Swing.swing != null) return;
        Swing.swing = new Swing();
    }
}
