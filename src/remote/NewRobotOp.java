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
package remote;

// Libraries
import players.Player;

/**
 * <b>Remote - Operation</b> <br>
 * Validates the type and arguments of
 * a given operation and works as an 
 * interface betwen the arena and a RVM.
 * 
 * @author Karina Suemi 
 * @author Renato Cordeiro
 * @author Vinícius Silva 
 * @see robot.RVM 
 * @see arena.World
 */
public class NewRobotOp implements Request
{
    final public   Player player;
    final public   String name;
    final public   String path;
    
    /**
     * Class constructor.
     * @param action Action to be executed
     * @param arg    Required arguments
     * 
     * @throws WrongTypeException
     * @throws InvalidOperationException
     */
    public NewRobotOp(Player player, String name, String pathToProg)
    {
        this.player = player;
        this.name   = name;
        this.path   = pathToProg;
    }
}
