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
package gui.textual;

/**
 * <b>Interfaces</b><br>
 * Holds the default <em>ANSI Escape Colors</em>,
 * supported in Unix-alike shells and others.
 * <p>
 * It is used in the Textual interface for 
 * implementing prints with colors.
 * 
 * @see Textual
 * @author Renato Cordeiro Ferreira
 */
interface Colors
{
    // Restore default
    String CLEAR      = "\033[2J\033[;H";
    String RESTORE    = "\u001B[0m";
    
    // Normal
    String BLACK      = "\u001B[0;30m";  // Black
    String RED        = "\u001B[0;31m";  // Red
    String GREEN      = "\u001B[0;32m";  // Green
    String YELLOW     = "\u001B[0;33m";  // Yellow
    String BLUE       = "\u001B[0;34m";  // Blue
    String PURPLe     = "\u001B[0;35m";  // Purple
    String CYAN       = "\u001B[0;36m";  // Cyan
    String WHITE      = "\u001B[0;37m";  // White
    
    // Bold                           
    String BBLACK     = "\u001B[1;30m";  // Black
    String BRED       = "\u001B[1;31m";  // Red
    String BGREEN     = "\u001B[1;32m";  // Green
    String BYELLOW    = "\u001B[1;33m";  // Yellow
    String BBLUE      = "\u001B[1;34m";  // Blue
    String BPURPLE    = "\u001B[1;35m";  // Purple
    String BCYAN      = "\u001B[1;36m";  // Cyan
    String BWHITE     = "\u001B[1;37m";  // White
    
    // Background
    String ON_BLACK   = "\u001B[40m";    // Black
    String ON_RED     = "\u001B[41m";    // Red
    String ON_GREEN   = "\u001B[42m";    // Green
    String ON_YELLOW  = "\u001B[43m";    // Yellow
    String ON_BLUE    = "\u001B[44m";    // Blue
    String ON_PURPLE  = "\u001B[45m";    // Purple
    String ON_CYAN    = "\u001B[46m";    // Cyan
    String ON_WHITE   = "\u001B[47m";    // White

    // High Intensity backgrounds
    String ON_IBLACK  ="\u001B[0;100m";  // Black
    String ON_IRED    ="\u001B[0;101m";  // Red
    String ON_IGREEN  ="\u001B[0;102m";  // Green
    String ON_IYELLOW ="\u001B[0;103m";  // Yellow
    String ON_IBLUE   ="\u001B[0;104m";  // Blue
    String ON_IPURPLE ="\u001B[0;105m";  // Purple
    String ON_ICYAN   ="\u001B[0;106m";  // Cyan
    String ON_IWHITE  ="\u001B[0;107m";  // White
}
