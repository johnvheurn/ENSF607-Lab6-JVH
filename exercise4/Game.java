/*
 *  Game.java
 *  ENSF 607 - Fall 2020 Lab 6
 *  Completed by: John Van Heurn #30001886
 *  Date: November 6th 2020
 */

package exercise4;

public class Game implements Constants, Runnable {

	private Referee theRef; // referee
	
	/*
		CONSTRUCTOR
	*/
    public Game(Referee r) {
		this.theRef = r;
	}
	
	/**
	 * Runnable function - run the game using the two players and the referee
	 */
	@Override public void run() {
		System.out.println("Starting a Game");
		theRef.runTheGame(); // tell the referee to start the game
	}
}
