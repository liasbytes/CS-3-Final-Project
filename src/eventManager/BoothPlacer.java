package eventManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * A class to run the booth placing algorithm on a set of booths and spaces.
 */
public class BoothPlacer {
	private ArrayList<Booth> booths;
	private Booth[][] spots;
	private LinkedList<Booth> cost5;
	private LinkedList<Booth> cost4;
	private LinkedList<Booth> cost3;
	private LinkedList<Booth> cost2;
	private LinkedList<Booth> cost1;
	private LinkedList<Booth> cost0;
	private HashMap<Integer, Coordinate> oneToTwo;
	private HashMap<Coordinate, Integer> twoToOne;
	private int[][] costs;
	
	/**
	 * Initializes all variables in the class.
	 * @param booths The arraylist of booths to be placed
	 * @param spots A two-dimensional array of spots that booths can be placed into.
	 */
	public BoothPlacer(ArrayList<Booth> booths, Booth[][] spots) {
		this.booths = booths;
		this.spots = spots;
		cost5 = new LinkedList<>();
		cost4 = new LinkedList<>();
		cost3 = new LinkedList<>();
		cost2 = new LinkedList<>();
		cost1 = new LinkedList<>();
		cost0 = new LinkedList<>();
		oneToTwo = null;
		twoToOne = null;
		costs = null;
	}
	
	/**
	 * Splits the input array of booths into 6 "buckets" based on cost, then runs the booth placing algorithm.
	 * 
	 * @return The final array of spots, filled in with booths.
	 */
	public Booth[][] placeBooths() {
		Iterator<Booth> it = booths.iterator();
		while (it.hasNext()) {
			Booth b = it.next();
			switch(b.getPopularity()) {
			case 5:
				cost5.offer(b);
				break;
			case 4:
				cost4.offer(b);
				break;
			case 3:
				cost3.offer(b);
				break;
			case 2:
				cost2.offer(b);
				break;
			case 1:
				cost1.offer(b);
				break;
			default:
				cost0.offer(b);
			}
		}	
		place5s(0,spots.length-1,0,spots[0].length-1);
		updateCostsRunHungarian(cost5);
		
		place4s();
		LinkedList<Booth> allRemaining = new LinkedList<>();
		// If cost3 makes up a significant (more than 35%) portion of the booths, runs the Hungarian algorithm on it separately.
		if (cost3.size() >= booths.size()*0.40) {
			updateCostsRunHungarian(cost3);
			allRemaining.addAll(cost2);
			allRemaining.addAll(cost1);
			updateCostsRunHungarian(allRemaining);
		} else {
			allRemaining.addAll(cost3);
			allRemaining.addAll(cost2);
			allRemaining.addAll(cost1);
			updateCostsRunHungarian(allRemaining);
		}
		return spots;
	}
	
	/**
	 * Places the "5"s in the space array. Uses the following recursive algorithm.
	 * Check the four "corner" positions and the center and place a "5" there if nothing is around them.
	 * Run the algorithm four times, instead using the four boxes made when splitting the original space matrix into 4 (down the middle in both directions).
	 * 
	 * @param outerStart The starting position to consider for the outer array.
	 * @param outerEnd The ending position to consider for the outer array.
	 * @param innerStart The starting position to consider for the inner array.
	 * @param innerEnd The ending position to consider for the inner array.
	 */
	private void place5s(int outerStart, int outerEnd, int innerStart, int innerEnd) {
		if (!cost5.isEmpty()) {
			if (getCost(outerStart,innerStart)==0 && !cost5.isEmpty()) {
				if (spots[outerStart][innerStart] == null) {
					Booth b = cost5.poll();
					spots[outerStart][innerStart] = b;
				}
			}
			if (getCost(outerEnd,innerStart)==0 && !cost5.isEmpty()) {
				if (spots[outerEnd][innerStart] == null) {
					Booth b = cost5.poll();
					spots[outerEnd][innerStart] = b;
				}
			}
			if (getCost(outerStart,innerEnd)==0 && !cost5.isEmpty()) {
				if (spots[outerStart][innerEnd] == null) {
					Booth b = cost5.poll();
					spots[outerStart][innerEnd] = b;
				}
			}
			if (getCost(outerEnd,innerEnd)==0 && !cost5.isEmpty()) {
				if (spots[outerEnd][innerEnd] == null) {
					Booth b = cost5.poll();
					spots[outerEnd][innerEnd] = b;
				}
			}
			if (getCost(outerEnd/2,innerEnd/2) == 0 && !cost5.isEmpty()) {
				if (spots[outerEnd/2][innerEnd/2] == null) {
					Booth b = cost5.poll();
					spots[outerEnd/2][innerEnd/2] = b;
				}
			}
			place5s(outerStart,outerEnd/2-1,innerStart,innerEnd/2-1);
			place5s(outerEnd/2+1, outerEnd,innerStart,innerEnd/2-1);
			place5s(outerStart,outerEnd/2-1,innerEnd/2+1, innerEnd);
			place5s(outerEnd/2+1, outerEnd, innerEnd/2+1, innerEnd);
		}
	}
	
	/**
	 * Places the "4"s in the spaces array.
	 * Utilizes a greedy algorithm that simply checks all spaces with zero cost, placing a "4" in each of them.
	 * If "4"s still remain, runs the hungarian algorithm on them.
	 */
	private void place4s() {
		for (int i = 0; i < spots.length; i++) {
			for (int j = 0; j < spots[0].length; j++) {
				if (getCost(i,j) == 0 && spots[i][j] == null && cost4.peek() != null) {
					spots[i][j] = cost4.poll();
				}
			}
		}
		if (!cost4.isEmpty()) {
			updateCostsRunHungarian(cost4);
		}
	}
	
	/**
	 * Gets the cost of any particular tile in the spaces matrix.
	 * Totals up the cost of the square itself, as well as the four squares in the cardinal directions.
	 * 
	 * @param row The row number of the tile
	 * @param col The column number of the tile
	 * @return The total cost of the square
	 */
	private int getCost(int row, int col) {
		int cost = 0;
		cost += getPopularityAtSpot(row,col);
		if (row != 0) {
			cost += getPopularityAtSpot(row-1,col);
		}
		if (row < spots.length-1) {
			cost += getPopularityAtSpot(row+1,col);
		}
		if (col != 0) {
			cost += getPopularityAtSpot(row,col-1);
		}
		if (col < spots[0].length-1) {
			cost += getPopularityAtSpot(row,col+1);
		}
		return cost;
	}
	
	/**
	 * Simple helper method to return the popularity (otherwise referred to as cost) of any tile within the spots array.
	 * Checks to see if the tile is null before accessing it.
	 * 
	 * @param row The row number of the tile.
	 * @param col The column number of the tile.
	 * @return The popularity (cost) of the tile.
	 */
	private int getPopularityAtSpot(int row, int col) {
		if (spots[row][col] == null) {
			return 0;
		} else {
			return spots[row][col].getPopularity();
		}
	}
	
	/**
	 * Forms an array of costs for a given input list of booths to be placed, then runs the Hungarian algorithm on that array of costs.
	 * Then transforms the one-dimensional array outputted by the Hungarian algorithm back into the two-dimensional array needed for this algorithm and updates spaces accordingly.
	 * 
	 * @param finding The LinkedList of Booths to be placed.
	 */
	private void updateCostsRunHungarian(LinkedList<Booth> finding) {
		if (finding.isEmpty()) {
			return;
		}
		oneToTwo = new HashMap<>();
		twoToOne = new HashMap<>();
		int currentSpot = 0;
		for (int i = 0; i < spots.length; i++) {
			for (int j = 0; j < spots[0].length; j++) {
				if (spots[i][j]==null) {
					oneToTwo.put(currentSpot, new Coordinate(i,j));
					twoToOne.put(new Coordinate(i,j), currentSpot);
					currentSpot++;
				}
			}
		}
		
		costs = new int[finding.size()][oneToTwo.size()];
		for (int i = 0; i < finding.size(); i++) {
			for (int j = 0; j < oneToTwo.size(); j++) {
				costs[i][j] = getCost(oneToTwo.get(j).xCord, oneToTwo.get(j).yCord);
			}
		}
		Hungarian h = new Hungarian(costs);
		int[] choices = h.runHungarian();
		for (int i = 0; i < finding.size(); i++) {
			Coordinate spot = oneToTwo.get(choices[i]);
			spots[spot.xCord][spot.yCord] = finding.get(i);
		}
		finding.clear();
	}
	
	/**
	 * A helper class to store a coordinate.
	 */
	private class Coordinate {
		private int xCord;
		private int yCord;
		
		/**
		 * Creates a new coordinate with the given x and y coordinates
		 * @param xCord The x coordinate
		 * @param yCord The y coordinate
		 */
		public Coordinate(int xCord, int yCord) {
			this.xCord = xCord;
			this.yCord = yCord;
		}
		
	}
}
