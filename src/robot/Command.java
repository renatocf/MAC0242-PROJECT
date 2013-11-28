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
package robot;

// Libraries
import stackable.*;

/**
 * <b>Commands</b><br>
 * Generates a triple [ function, argument, label ] from
 * strings for being used to process a robot's program. 
 *
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinícius Silva
 * @see Ctrl
 */
public class Command
{
    final private String    function;
    private Stackable attribute;
    final private String    label;

    /**
     * Default constructor. <br>
     * Stores a triple with assembly function, attribute
     * stackable and label. If one of them does not exist,
     * a null reference must be passed.
     * <p> 
     * Once created, the command cannot be changed.
     *
     * @param func String with an assembly function 
     *             (or null, if none avaiable)
     * @param attr String with the function's attribute
     *             (or null, if none avaiable or required)
     * @param lbl  String with a specified label
     *             (or null, if none avaiable)
     */
    public Command(String func, Stackable attr, String lbl)
    {
        this.function  = func;
        this.attribute = attr;
        this.label     = lbl;
    }

    /** @param attr Stackable to set the attribute of the line. */
    public void setAttribute (Stackable attr) { this.attribute = attr; }
    
    /** @return String with the function name. */
    public String    getFunction  () { return this.function;  }
    
    /** @return String with the attribute of the line. */
    public Stackable getAttribute () { return this.attribute; }
    
    /** @return String with a label */
    public String    getLabel     () { return this.label;     }

    public String toString()
    {
        String label = (this.label == null)     ? "" : this.label + ": ";
        String func  = (this.function == null)  ? "" : this.function;
        String attr  = (this.attribute == null) ? "" : this.attribute.toString();
        return String.format("%-10s %-4s    %s", label, func, attr);
    }
}
