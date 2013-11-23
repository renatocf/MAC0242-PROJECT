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
import java.net.*;
import java.io.*;

public class RobotsBattleServer
{
    public static void main(String[] args) throws IOException 
    {
        // Get arguments
        if(args.length != 1) 
        {
            System.err.println("Usage: java RobotServer <port number>");
            System.exit(1);
        }
        
        // Get port number
        int portNumber = Integer.parseInt(args[0]);
        
        
        try 
        ( 
            // Creating server socket and waiting for clients
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            
            // Server communication: output to clients
            PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true
            );
            
            // Server communication: input from clients
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );
        ) {
            
            // Server stuff ...
            
        } catch (IOException e) {
            System.out.println(
                "Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection:"
            );
            System.out.println(e.getMessage());
        }
    }
}
