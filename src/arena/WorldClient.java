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
import java.util.*;

// Libraries
import remote.*;

public class WorldClient
{
    public WorldClient(String host)
    {
        try {
            ServerRemote server = (ServerRemote)
                Naming.lookup("rmi://" + host + "/Neutron");
            server.GET();
        
        } catch(java.io.IOException e) {
            System.err.println("[WORLD_CLIENT]");
            e.printStackTrace();
        
        } catch(NotBoundException e) {
            System.err.println("[WORLD_CLIENT]");
            System.err.println("Server not registered");
            e.printStackTrace();
        }  
    }
    
    public static void main(String[] args)
        throws RemoteException
    {
        new WorldClient(args[0]);
    }
}
