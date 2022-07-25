package eventManager;

public class Hungarian {
	private int[][] mainArr;
	private int[][] coveredSpaces;
	private int[][] coveredTwice;
	private int numCoveredLines;
	private int[] finalChoices;
	
	/**
	 * Sets the main array to the array passed in.
	 * @param mainArr
	 */
	public Hungarian(int[][] mainArr) {
		// Creates a copy of mainArr
		this.mainArr = new int[mainArr.length][mainArr.length];
		for (int i = 0; i < mainArr.length; i++) {
			this.mainArr[i] = mainArr[i].clone();
		}
		numCoveredLines = 0;
		coveredSpaces = null;
		finalChoices = new int[mainArr.length];
	}
	
	/**
	 * Runs the hungarian algorithm, using the data stored in this object.
	 */
	public int[] runHungarian() {
		step1();
		step2();
		while (numCoveredLines != mainArr.length) {
			step3();
			step2();
		}
		step4();
		return finalChoices;
	}
	
	/**
	 * Removes the local minimum from each row and column of the array
	 */
	public void step1() {
		// Goes through the rows
		for (int i = 0; i < mainArr.length; i++) {
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < mainArr.length; j++) {
				if (mainArr[i][j] < min) {
					min = mainArr[i][j];
				}
			}
			for (int j = 0; j < mainArr.length; j++) {
				mainArr[i][j] -= min;
			}
		}
		
		// Goes through the columns
		for (int i = 0; i < mainArr.length; i++) {
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < mainArr.length; j++) {
				if (mainArr[j][i] < min) {
					min = mainArr[j][i];
				}
			}
			for (int j = 0; j < mainArr.length; j++) {
				mainArr[j][i] -= min;
			}
		}
	}
	
	/**
	 * Covers all zeroes in the array with the minimum required amount of lines.
	 */
	public void step2 () {
		int[][] m1 = new int[mainArr.length][mainArr.length];
		coveredSpaces = new int[mainArr.length][mainArr.length];
		coveredTwice = new int[mainArr.length][mainArr.length];
		numCoveredLines = 0;
		
		// Fills the m1 array with the number of zeroes in the row/column it's in
		for (int i = 0; i < mainArr.length; i++) {
			for (int j = 0; j < mainArr.length; j++) {
				if (mainArr[i][j] == 0) {
					m1[i][j] = hvMax(i,j);
				}
			}
		}
		
		// Iterates through m1, drawing a vertical line if there are more vertical zeroes
		// and draws a horizontal line if there are more horizontal zeroes.
		for (int i = 0; i < mainArr.length; i++) {
			for (int j = 0; j < mainArr.length; j++) {
				if (m1[i][j] != 0) {
					clearNeighbors(m1,coveredSpaces,i,j);
				}
			}
		}
		
	}
	
	/**
	 * Finds the maximum number of zeroes at any given spot in the matrix.
	 * If there are more horizontal zeroes, returns a negative number
	 * @param row The row number to look at
	 * @param col The column number to look at
	 * @return The number of zeroes at the specified location.
	 */
	private int hvMax(int row, int col) {
		int horizontal = 0;
		int vertical = 0;
		
		for (int i = 0; i < mainArr.length; i++) {
			if (mainArr[row][i] == 0) {
				horizontal++;
			}
		}
		
		for (int i = 0; i < mainArr.length; i++) {
			if (mainArr[i][col] == 0) {
				vertical++;
			}
		}
		
		if (vertical > horizontal) {
			return vertical;
		} else {
			return -horizontal;
		}
	}
	
	/**
	 * "Clears" the neighbors of any row or column in the array of zeroes, as well as drawing a line through the row/column.
	 * @param m1 The array of counted zeroes to work on
	 * @param m2 The array of lines to modify
	 * @param row The row position of the cell
	 * @param col The column position of the cell
	 */
	private void clearNeighbors(int[][] m1, int[][] m2, int row, int col) {
		if (m1[row][col] < 0) {
			for (int i = 0; i < mainArr.length; i++) {
				if (m2[row][i] == 1) {
					coveredTwice[row][i] = 1;
				}
				m1[row][i] = 0;
				m2[row][i] = 1;
			}
		} else {
			for (int i = 0; i < mainArr.length; i++) {
				if (m2[i][col] == 1) {
					coveredTwice[row][i] = 1;
				}
				m1[i][col] = 0;
				m2[i][col] = 1;
			}
		}
		m1[row][col] = 0;
		m2[row][col] = 1;
		numCoveredLines++;
	}
	
	/**
	 * Creates additional zeroes, only required if the number of lines is less than the size of the matrix.
	 * Finds the smallest uncovered element, then subtracts that number from all uncovered elements and adds it to all double-covered elements.
	 */
	public void step3 () {
		int smallest = Integer.MAX_VALUE;
		for (int i = 0; i < mainArr.length; i++) {
			for (int j = 0; j < mainArr.length; j++) {
				if (coveredSpaces[i][j] == 0 && mainArr[i][j] < smallest) {
					smallest = mainArr[i][j];
				}
			}
		}
		
		for (int i = 0; i < mainArr.length; i++) {
			for (int j = 0; j < mainArr.length; j++) {
				if (coveredSpaces[i][j] == 0) {
					mainArr[i][j] -= smallest;
				} else if (coveredTwice[i][j] == 1) {
					mainArr[i][j] += smallest;
				}
			}
		}
	}
	
	/**
	 * Finds the optimal configuration from the covered zeroes.
	 */
	public void step4 () {
		for (int i = 0; i < mainArr.length; i++) {
			for (int j = 0; j < mainArr.length; j++) {
				if (coveredSpaces[i][j] == 1 && mainArr[i][j] == 0) {
					boolean validX = true;
					boolean validY = true;
					// Checks if there is another covered zero in the column or row the zero is in, adds if there isn't both
					for (int k = 0; k < mainArr.length; k++) {
						if (mainArr[i][k] == 0 && k != j  && coveredSpaces[i][k]==1) {
							validX = false;
						} else if (mainArr[k][j] == 0 && k != i && coveredSpaces[k][j]==1) {
							validY = false;
						}
					}
					if (validX || validY) {
						finalChoices[i] = j;
						// Uncovers the cross of spaces around the one just added
						for (int k = 0; k < mainArr.length; k++) {
							coveredSpaces[i][k] = 0;
							coveredSpaces[k][j] = 0;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < mainArr.length; i++) {
			for (int j = 0; j < mainArr.length; j++) {
				if (coveredSpaces[i][j] == 1 && mainArr[i][j] == 0) {
					finalChoices[i] = j;
					for (int k = 0; k < mainArr.length; k++) {
						coveredSpaces[i][k] = 0;
						coveredSpaces[k][j] = 0;
					}
				}
			}
		}
	}
}
