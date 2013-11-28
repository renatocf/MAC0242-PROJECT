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

// Default libraries
import java.rmi.Remote;
import java.rmi.RemoteException;

// Libraries
import arena.*;
import players.*;
import stackable.Stackable;

public interface ServerRemote extends Remote
{
    // Receive and send messages
    public void        POST (Operation op) throws RemoteException;
    public Stackable[] GET  ()             throws RemoteException;
    
    // Communicate with other player
    /* public static void (Player player, String msg); */
    
    // Control the armies
    /* public static Robot insertArmy(Player player, String name, String pathToProg); */
    /* public static void  removeArmy(Robot dead); */
}
