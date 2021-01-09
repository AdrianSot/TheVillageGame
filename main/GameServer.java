/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.net.*;
import java.io.*;
import game.GameEngine;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author adrian
 */
public class GameServer {
    public static void main(String[] args) throws IOException {
         
        if (args.length != 1) {
            System.err.println("Usage: java <file> <port number>");
            System.exit(1);
        }
       
        int portNumber = Integer.parseInt(args[0]);
        
        try (ServerSocket listener = new ServerSocket(portNumber)) {
            System.out.println("The Village server is running...");
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new GameServ(listener.accept()));
            }
        }

        

    }
    private static class GameServ implements Runnable{
        private Socket socket;
        GameServ(Socket socket){
            this.socket = socket;
        }
        
        @Override
        public void run(){
            try ( 
            
            PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        ) {
         
            String inputLine, outputLine;
             
            // Initiate conversation with client
            GameEngine ge = new GameEngine();
            outputLine = ge.runGame(null);
            out.println(outputLine);
 
            while ((inputLine = in.readLine()) != null) {
                outputLine = ge.runGame(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Game over")|| outputLine.equals("Not a registered username. Goodbye"))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when listening for a connection");
            System.out.println(e.getMessage());
        }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Closed: " + socket);
        }
        }
    }
}
