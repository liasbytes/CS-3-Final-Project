package eventManager;

public class Hungarian {
	private int[][] mainArr;
	
	public Hungarian(int[][] mainArr) {
		this.mainArr = mainArr;
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
		
	}
	
	/**
	 * Creates additional zeroes, only required if the number of lines is less than the size of the matrix.
	 */
	public void step3 () {
		
	}
	
	/**
	 * Finds the optimal configuration from the covered zeroes.
	 */
	public void step4 () {
		
	}
}
