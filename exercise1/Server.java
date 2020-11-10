/*
 *  Server.java
 *  ENSF 607 - Fall 2020 Lab 6
 *  Completed by: John Van Heurn #30001886
 *  Date: November 6th 2020
 */

package exercise1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket palinSocket;
    private ServerSocket serverSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;

    /**
     * Constructor for class Server
     * initializes the socket at localhost 8099
     */
    public Server() {
        try {
            serverSocket = new ServerSocket(8099);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function: is_palindrome
     * reads socketIn, outputs if the word is a palindrome
     */
    public void palindrome() {
        String input;
        while(true) {
            try {
                input = socketIn.readLine();
                if (input.equals("QUIT")) {
                    socketOut.println("Goodbye");
                }
                socketOut.println(is_palindrome(input));
            } 
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function: is_palindrome
     * returns a string that says if the word is a palindrome
     * @return String is or is not a Palindrome
     */
    public String is_palindrome(String line) {
        int j;
        for (int i = 0; i < line.length(); i++) {
            j = line.length() - 1 - i;
            if (line.charAt(i) != line.charAt(j)) {
                return line + " is not a Palindrome.";
            }
        }
        return line + " is a Palindrome";
    }

    /**
     * Main, run the server
     */
    public static void main(String[] args) throws IOException {
        
        Server myServer = new Server();

        // establishing the connection
        try {
            myServer.palinSocket = myServer.serverSocket.accept();
            System.out.println("Connection Accepted by the Server.");
            myServer.socketIn = new BufferedReader(new InputStreamReader(myServer.palinSocket.getInputStream()));
            myServer.socketOut = new PrintWriter(myServer.palinSocket.getOutputStream(), true);
            myServer.palindrome();

            myServer.socketIn.close();
            myServer.socketOut.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
