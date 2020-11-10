/*
 *  Referee.java
 *  ENSF 607 - Fall 2020 Lab 6
 *  Completed by: John Van Heurn #30001886
 *  Date: November 6th 2020
 */

package exercise4;

public class Referee {
    private Player xPlayer; // player with mark X
    private Player oPlayer; // player with mark O
    private Board board;    // board object

    /*
        CONSTRUCTOR
    */
    public Referee(Player xPlayer, Player oPlayer) {
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
        this.board = new Board();

        // set up opponents
        this.xPlayer.setOpponent(this.oPlayer);
        this.oPlayer.setOpponent(this.xPlayer);

        // set up board
        this.xPlayer.setBoard(this.board);
        this.oPlayer.setBoard(this.board);

        return;
    }

    /**
     * Initiate the game of Tic-Tac-Toe 
     */ 
    public void runTheGame() {
        // get each player's names
        xPlayer.getName();
        oPlayer.getName();

        // start the game
        this.xPlayer.printBothPlayers(this.board.display());

        this.xPlayer.play(); // starts the game
        return;
    }
}
