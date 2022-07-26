package eventManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * ALGORITHM DESIGN:
 * NOTE: cost is only calculated by the 4 booths in cardinal directions around any given booth (N, S, E, W, not diagonals)
 * Takes in: ArrayList of booths (or other iterable data structure), Two-dimensional array of booths, with unavailable spots marked with a special booth.
 * 1: Separates booths into "buckets" based on expected traffic (Likely in a LinkedList)
 * 2: Iterate through all booths in the "5" bucket.
 * 2.1: Evenly disperse all "5" booths in the available spots.
 * 3: Iterate through all "4"s.
 * 3.1: Space out all "4"s (likely trying two space gap first, and if it runs out of space, then one space gap)
 * 4: Iterate through all "3"s.
 * 4.1: Fill up remaining 0 cost spots with "3"s.
 * 5: Use the Hungarian algorithm on the remaining "3"s, "2"s, and "1"s.
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
		if (!cost5.isEmpty()) {
			updateCostsRunHungarian(cost5);
		}
		place4s();
		updateCostsRunHungarian(cost3);
		updateCostsRunHungarian(cost2);
		updateCostsRunHungarian(cost1);
		return spots;
	}
	
	private void place5s(int outerStart, int outerEnd, int innerStart, int innerEnd) {
		if (!cost5.isEmpty()) {
			if (getCost(outerStart,innerStart)==0) {
				if (spots[outerStart][innerStart] != null && spots[outerStart][innerStart].boothType() != Booth.BoothType.UNAVAILABLE) {
					Booth b = cost5.poll();
					spots[outerStart][innerStart] = b;
				}
			}
			if (getCost(outerEnd,innerStart)==0) {
				if (spots[outerEnd][innerStart] != null && spots[outerEnd][innerStart].boothType() != Booth.BoothType.UNAVAILABLE) {
					Booth b = cost5.poll();
					spots[outerEnd][innerStart] = b;
				}
			}
			if (getCost(outerStart,innerEnd)==0) {
				if (spots[outerStart][innerEnd] != null && spots[outerStart][innerEnd].boothType() != Booth.BoothType.UNAVAILABLE) {
					Booth b = cost5.poll();
					spots[outerStart][innerEnd] = b;
				}
			}
			if (getCost(outerEnd,innerEnd)==0) {
				if (spots[outerEnd][innerEnd] != null && spots[outerEnd][innerEnd].boothType() != Booth.BoothType.UNAVAILABLE) {
					Booth b = cost5.poll();
					spots[outerEnd][innerEnd] = b;
				}
			}
			if (getCost(outerEnd/2,innerEnd/2) == 0) {
				if (spots[outerEnd/2][innerEnd/2] != null && spots[outerEnd/2][innerEnd/2].boothType() != Booth.BoothType.UNAVAILABLE) {
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
	
	private void place4s() {
		for (int i = 0; i < spots.length; i++) {
			for (int j = 0; j < spots[0].length; j++) {
				if (getCost(i,j) == 0) {
					spots[i][j] = cost4.poll();
				}
			}
		}
		
		if (!cost4.isEmpty()) {
			updateCostsRunHungarian(cost4);
		}
	}
	
	private int getCost(int row, int col) {
		int cost = 0;
		cost += getPopularityAtSpot(row,col);
		if (row != 0) {
			cost += getPopularityAtSpot(row-1,col);
			if (col != 0) {
				cost += getPopularityAtSpot(row-1,col-1);
			}
			if (col < spots[0].length-1) {
				cost += getPopularityAtSpot(row-1,col+1);
			}
		}
		if (row < spots.length-1) {
			cost += getPopularityAtSpot(row+1,col);
			if (col != 0) {
				cost += getPopularityAtSpot(row+1,col-1);
			}
			if (col < spots[0].length-1) {
				cost += getPopularityAtSpot(row+1,col+1);
			}
		}
		if (col != 0) {
			cost += getPopularityAtSpot(row,col-1);
		}
		if (col < spots[0].length-1) {
			cost += getPopularityAtSpot(row,col+1);
		}
		return cost;
	}
	
	private int getPopularityAtSpot(int row, int col) {
		if (spots[row][col] == null) {
			return 0;
		} else {
			return spots[row][col].getPopularity();
		}
	}
	
	private void updateCostsRunHungarian(LinkedList<Booth> finding) {
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
	
	private class Coordinate {
		private int xCord;
		private int yCord;
		
		public Coordinate(int xCord, int yCord) {
			this.xCord = xCord;
			this.yCord = yCord;
		}
		
	}
}
