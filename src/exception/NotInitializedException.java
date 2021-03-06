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
package exception;

/**
 * Exception used inside RVM to warn when the variable
 * has not been previously initialized.
 * @author Karina Awoki
 * @author Renato Cordeiro
 * @author Vinicius Silva
 * @see    robot.Var
 */
public final class NotInitializedException extends Exception 
{
    /** 
     * Default constructor.
     */
    public NotInitializedException(String var) 
    { super("Variable " + var + " not initialized.\n"); }
}
