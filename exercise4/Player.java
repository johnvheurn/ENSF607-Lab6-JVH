/*
 *  Player.java
 *  ENSF 607 - Fall 2020 Lab 6
 *  Completed by: John Van Heurn #30001886
 *  Date: November 6th 2020
 */

package exercise4;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Player implements Constants {
    private String name;        // name of person
    private Board board;        // game board object
    private Player opponent;    // opponent object
    private char mark;          // X, O

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    /**
     * Constructor, initialize components
     * 
     * @param socket
     * @param letter
     */
    public Player(Socket socket, char letter) {
        this.mark = letter;
        this.socket = socket;

        try {
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            output = new PrintWriter(this.socket.getOutputStream());
        } catch(IOException e) {
            System.out.println("IOException thrown at Player");
        }

        if (this.mark == LETTER_X) {
            printPlayer("Finding an Opponent...");
        }

        return;
    }

    /**
     * Send text to one Player
     * @param out
     */
    public void printPlayer(String out) {
        output.println(out);
        output.flush();
    }

    /**
     * Print Both Players
     * @param out
     */
    public void printBothPlayers(String out) {
        printPlayer(out);
        opponent.printPlayer(out);
    }

    /**
     * Get players name from client
     */
    public void getName() {
        try {
            this.opponent.printPlayer("Waiting for Other Player to Input Name...");

            printPlayer("Please enter the name of player " + mark + ": ");
            printPlayer("USERINPUT");
            this.name = input.readLine();

            while (this.name == null) {
                printPlayer("Invalid Entry");
                printPlayer("Please enter the name of player " + mark + ": ");
                printPlayer("USERINPUT");
                this.name = input.readLine();
            }

        } catch (IOException e) {
            System.out.println("IOException thrown");
        }
    }

    /**
     * Check if game is complete, call the makeMove() function for this player to make a move
     */
    public void play() {
        // X Player has Won
        if (this.board.xWins() == true) {
            if (this.mark == 'X')
                printBothPlayers(this.name + " has won the game. (" + this.mark + ")");
            else 
                printBothPlayers(this.opponent.name + " has won the game. (" + this.opponent.mark + ")");
        }
        // O Player has Won
        else if (this.board.oWins() == true) {
            if (this.mark == 'O')
                printBothPlayers(this.name + " has won the game. (" + this.mark + ")");
            else 
                printBothPlayers(this.opponent.name + " has won the game. (" + this.opponent.mark + ")");
        }
        // Board is Full
        else if (this.board.isFull() == true) {
            printBothPlayers("The game has ended in a tie.");
        }
        // Play Move
        else {
            try {
                this.opponent.printPlayer("Waiting for Other Player to Make a Move...");
                this.makeMove();
            }
            catch (IOException e) {
                System.out.println("IOException in makeMove()."); 
            }
        }
        
        return;
    }

    /**
     * Takes user input for row and column, 
     * adds mark to game board,
     * displays game board,
     * calls opponent.play(), as it is the opponents turn next
     * @throws IOException
     */
    public void makeMove() throws IOException{
        int row, column;

        // retrieve row 
        do {
            printPlayer("\n" + this.name + ", please enter a row to place an " + this.mark + ": ");
            printPlayer("USERINPUT");
            row = Integer.parseInt(input.readLine());
            
            while (row < 0 || row > 2) {
                printPlayer("Please try again: ");
                printPlayer("USERINPUT");
                row = Integer.parseInt(input.readLine());
            }
            
            // retrieve column
            printPlayer(this.name + ", please enter a column to place an " + this.mark + ": ");
            printPlayer("USERINPUT");
            column = Integer.parseInt(input.readLine());

            while (column < 0 || column > 2) {
                printPlayer("Please try again: ");
                printPlayer("USERINPUT");
                column = Integer.parseInt(input.readLine());
            }
            if (this.board.checkClear(row, column) == false)
                printPlayer("The chosen space is not clear.");

        } while(this.board.checkClear(row, column) == false);

        // make a mark on the board
        this.board.addMark(row, column, this.mark);
        printBothPlayers(this.board.display());
        this.opponent.play();
        return;
    }

    /*
        SETTERS, used to initialize the game
    */
    public void setOpponent(Player player) {
        this.opponent = player;
        return;
    }

    public void setBoard(Board board) {
        this.board = board;
        return;
    }
 }
