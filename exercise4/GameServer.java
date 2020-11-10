/*
 *  GameServer.java
 *  ENSF 607 - Fall 2020 Lab 6
 *  Completed by: John Van Heurn #30001886
 *  Date: November 6th 2020
 */

package exercise4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Constants {
    private ServerSocket serverSocket;
    private ExecutorService pool;
 
    public GameServer() {
        try {
            serverSocket = new ServerSocket(9898);
            pool = Executors.newFixedThreadPool(20);
        } catch (IOException e) {
            System.out.println("IOException thrown at GameServer");
        }
    }
    
    private void runServer() throws IOException {
        try {
            System.out.println("Tic-Tac-Toe Server Online");

            while(true) {
                // wait for 2 players to connect to server
                Socket xPlayer_socket = serverSocket.accept();
                System.out.println("xPlayer Assigned");
                Player xPlayer = new Player(xPlayer_socket, Constants.LETTER_X);
                
                Socket oPlayer_socket = serverSocket.accept();
                System.out.println("oPlayer Assigned");
                Player oPlayer = new Player(oPlayer_socket, Constants.LETTER_O);
            
                // create a board and referee to intialize a new game
                Game game = new Game(new Referee(xPlayer, oPlayer));

                // allocate a new thread to the game so it may continue
                pool.execute(game);
            }
        } catch (IOException e) {
            System.out.println("IOException thrown at runServer"); 
            pool.shutdown();
        }
    }

    /**
     * Run a GameServer
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
        server.runServer();
    }
}
