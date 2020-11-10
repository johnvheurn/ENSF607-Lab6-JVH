/*
 *  GameClient.java
 *  ENSF 607 - Fall 2020 Lab 6
 *  Completed by: John Van Heurn #30001886
 *  Date: November 6th 2020
 */

package exercise4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {

    private PrintWriter socketOut;
    private Socket gameSocket;
    private BufferedReader stdIn;
    private BufferedReader socketIn;

    /**
     * GameClient constructor, initialize required components
     * @param serverName
     * @param portNumber
     */
    public GameClient(String serverName, int portNumber) {
        try {
            gameSocket = new Socket(serverName, portNumber);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));
            socketOut = new PrintWriter(gameSocket.getOutputStream(), true);
        } catch(IOException e) {
            System.out.println("IOException thrown at GameClient");
        }
    }

    /**
     * Communicate with the GameClient server
     */
    public void communicate() {
        String line = "";
        String response = "";

        try {
            while(true) {
                // read and print from socket
                line = socketIn.readLine();
                
                // write to socket if end of outputs
                if (line.contains("USERINPUT")){
                    response = stdIn.readLine();
                    socketOut.println(response);
                    socketOut.flush();
                    continue; // don't want to print USERINPUT line
                }

                System.out.println(line);
            }

        } catch(IOException e){
            System.out.println("Sending error: " + e.getMessage());
        }
    }

    /**
     * Run a GameClient
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient("localhost", 9898);
        client.communicate();
    }
}
