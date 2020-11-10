/*
 *  DateClient.java
 *  ENSF 607 - Fall 2020 Lab 6
 *  Completed by: John Van Heurn #30001886
 *  Date: November 6th 2020
 */

package exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DateClient {
    private PrintWriter socketOut;
    private Socket dateSocket;
    private BufferedReader stdIn;
    private BufferedReader socketIn;


    /**
     * Initialize the server - client components
     */
    public DateClient(String serverName, int portNumber) {
        try {
            dateSocket = new Socket(serverName, portNumber);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader(dateSocket.getInputStream()));
            socketOut = new PrintWriter((dateSocket.getOutputStream()), true);
        }
        catch (IOException e) {
            System.err.println(e.getStackTrace());
        }
    }

    /**
     * Communicate with the server 
     */
    public void communicate() {
        String line = "";
        String response = "";
        boolean running = true;
        while (running) {
            try {
                System.out.println("Please select an option (DATE/TIME)");
                line = stdIn.readLine();
                if (!line.equals("QUIT")) {
                    System.out.println(line);
                    socketOut.println(line);
                    response = socketIn.readLine();
                    System.out.println(response);
                }
                else {
                    running = false;
                }
            }
            catch (IOException e) {
                System.out.println("Sending error: " + e.getMessage());
                running = false;

            }
        }
        try {
            stdIn.close();
            socketIn.close();
            socketOut.close();
        } catch(IOException e) {
            System.out.println("Sending error: " + e.getMessage());
        }
    }

    /**
     * Main: run the client
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        DateClient a = new DateClient("localhost", 9090);
        a.communicate();
    }

}
