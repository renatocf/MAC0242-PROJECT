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
package stackable;

/**
 * <b>Stackable - Text</b><br>
 * Packs strings inside a simple 
 * container, allowing them to
 * be identified as stackables.
 * 
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
public class Text implements Stackable
{
    private final String text;
    
    /** 
     * Default constructor.
     * @param text String with a text to be
     *             packed inside this container
     *             class.
     */
    public Text(String text)
    {
        this.text = text;
    }
    
    /** 
     * Getter for the string packed inside the
     * class.
     * @return String with the text.
     */
    public String getText()  { return this.text; }
    
    public String toString() { return this.text; }
}
