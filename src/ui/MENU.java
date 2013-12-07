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
package ui;

/**
 * <b>MENU</b><br>
 * Methods that a MENU must provide to 
 * work with the game characteristics.
 */
public interface MENU
{
    /**
     * <b>MENU - Opts</b><br>
     * The option that a MENU
     * must implement.
     */
    public enum Opts
    {
        NEW_GAME,
        EXIT
    }
    
    /**
     * Exhibit the menu.
     */
    public Opts exhibit();
}
