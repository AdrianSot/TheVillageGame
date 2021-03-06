/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.*;
import java.net.*;

/**
 *
 * @author adrian
 */
public class GameClient {
    public static void main(String[] args) throws IOException, InterruptedException {
         
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }
 
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
 
        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
           
                    
            while ((fromServer = in.readLine()) != null) {
                
                System.out.println("\nServer: " + fromServer.replace("%1%", "\n"));
                if (fromServer.equals("Game over") || fromServer.equals("Not a registered username. Goodbye"))
                    break;
                 
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    //System.out.println("\nClient: " + fromUser);
                    out.println(fromUser);
                }
                
                
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}
