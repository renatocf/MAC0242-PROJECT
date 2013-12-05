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

// Libraries
import gui.MENU;
import gui.MENU.Opts;

// Default libraries
import java.util.Scanner;

/**
 * <b>MENU - Textual Mode</b><br>
 * Makes an implementation of the interface
 * MENU for exhibiting the game,
 * in a Unix shell.
 * <p>
 * The program may also work if the shell
 * accepts the ANSI Escape Codes.
 */
public class Menu implements MENU
{
    // Interface MENU
    public Opts exhibit()
    {
        MENU: while(true)
        {
            System.out.println(":: MENU ::");
            System.out.println("==========");
            System.out.println("[1] Start Game");
            System.out.println("[2] Exit");
            System.out.println();
            System.out.println("Which option do you want?");
            
            Scanner sc = new Scanner(System.in);
            int opt = sc.nextInt();
            
            switch(opt)
            {
                case 1: return MENU.Opts.NEW_GAME;
                case 2: return MENU.Opts.EXIT;
                default: 
                    System.out.println("Invalid option! Try again \n"); 
                    continue MENU;
            }
        }
    }
}



