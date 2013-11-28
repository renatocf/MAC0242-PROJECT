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
package arena;

// Default libraries
import java.rmi.*;
import java.util.Random;

// Libraries
import remote.*;
import players.*;
import random.Weather;
import parameters.Game;
import stackable.Stackable;
import java.rmi.registry.LocateRegistry;

public class WorldServer
    extends java.rmi.server.UnicastRemoteObject
    implements ServerRemote
{
    int nPlayers = 0;
    
    public WorldServer
        (int np, int nAI, Weather w, Random rand)
        throws RemoteException
    {
        System.out.print("Creating new host... ");
    }
    
    // Interface ServerRemote 
    public void POST (Operation op) 
        throws RemoteException
    {
        System.out.println("Posting");
    }
    
    // Interface ServerRemote 
    public Stackable[] GET()             
        throws RemoteException
    {
        System.out.println("Getting");
        return null;
    }
    
    public static void main(String[] args)
    {
        try {
            LocateRegistry.createRegistry(3742);
            ServerRemote server = new WorldServer(2, 0, Weather.TROPICAL, Game.RAND);
            System.out.println("[OK]");
            Naming.rebind("Neutron", server);
            System.out.println("Waiting for clients");
            
        } catch (java.io.IOException e) {
            System.err.println("Problem registering server!");
            e.printStackTrace();
        }
    }
}
