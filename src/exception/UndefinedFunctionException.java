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
 * Exception used inside RVM to declare when there is no
 * of the required label defined in the code.
 * @author Renato Cordeiro Ferreira
 * @see    robot.RVM
 */
public final class UndefinedFunctionException extends Exception 
{
    /** 
     * Constructor with error message indicating the 
     * position, within the code, of the error.
     */
    public UndefinedFunctionException (int pos) 
    { super("Undefined function in position " + pos + ".\n"); }
    
    /** 
     * Constructor eith error message indicating which
     * function was unknown.
     */
    public UndefinedFunctionException (String function) 
    { super("Undefined function: " + function + ".\n"); }
}
