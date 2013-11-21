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
