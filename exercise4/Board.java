/*
 *  Board.java
 *  ENSF 607 - Fall 2020 Lab 6
 *  Completed by: John Van Heurn #30001886
 *  Date: November 6th 2020
 */

package exercise4;

public class Board implements Constants {
	private char theBoard[][]; 	// gameboard (3x3 in constructor)
	private int markCount;		// mark count, if 9 gameboard is full

	/*
		CONSTRUCTOR
	*/
	// create blank board and set markCount to 0
	public Board() {
		markCount = 0;
		theBoard = new char[3][3];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR; // initiate spaces in the board as blank
		}
	}

	/**
	 * return a specific square on the board
	 * @param row
	 * @param col
	 * @return the board square
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * indicate board is full if all 9 tiles are marked 
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * check if player x wins
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * check if player o wins 
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	/**
	 * display the game board 
	 */
	public String display() {
		String output = displayColumnHeaders();
		output += addHyphens();

		for (int row = 0; row < 3; row++) {
			output += addSpaces();
			output += "    row " + row + ' ';
			for (int col = 0; col < 3; col++)
				output += "|  " + getMark(row, col) + "  ";
			output += "|\n";
			output += addSpaces();
			output += addHyphens();
		}

		return output;
	}

	/**
	 * add mark to the board at a given row and column
	 * @param row
	 * @param col
	 * @param mark
	 */
	public void addMark(int row, int col, char mark) {
		theBoard[row][col] = mark;
		markCount++;
	}

	/**
	 * reset the board 
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * confirm the grid is empty before inserting marker into grid
	 */
	public boolean checkClear(int row, int col) {
		if (theBoard[row][col] == SPACE_CHAR)
			return true;
		else 
			return false;
	}

	/**
	 * goes through possible win cases for a given mark
	 * @param mark
	 */ 
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/*
		PRINT ASSIST FUNCTIONS
	*/
	String displayColumnHeaders() {
		String output = "          ";
		for (int j = 0; j < 3; j++)
			output += "|col " + j;
		output += "\n";

		return output;
	}

	String addHyphens() {
		String output = "          ";
		for (int j = 0; j < 3; j++)
			output += "+-----";
		output += "+" + "\n";

		return output;
	}

	String addSpaces() {
		String output = "          ";
		for (int j = 0; j < 3; j++)
			output += ("|     ");
		output += "|" + "\n";

		return output;
	}
}
