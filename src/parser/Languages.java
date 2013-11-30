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
package parser;

/**
 * <b>Parser - Languages</b>
 * The types of languages 
 * understandable by the 
 * parser.
 */
enum Languages
{
    POSITRON ("pos"),
    QUARK    ("asm");
    
    /* Auxiliar private variables */
    private String ext;
    
    /** 
     * Default Constructor.<br>
     * @param ext Extension to files 
     *            with that type of
     *            language
     */
    private Languages(String ext)
    {
        this.ext = ext;
    }
    
    /** 
     * Getter for the language extension
     * @return String with the language 
     *         extension
     */
    private String ext()
    {
        return this.ext;
    }
    
    /** 
     * Define which type of language there
     * is in the string
     * @param  s String to have its extension
     *           tested
     * @return Language correspondent to that
     *         type of file (null, if none 
     *         matches)
     */
    static Languages define(String s)
    {
        for(Languages lang: Languages.values())
        {
            String regex = ".*\\." + lang.ext();
            if(s.matches(regex)) return lang;
        }
        return null;
    }
}
