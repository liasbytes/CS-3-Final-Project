package eventManager;

import java.util.ArrayList;
import java.util.Arrays;

import eventManager.Booth.BoothType;

public class Test {

	public static void main(String[] args) {
//		int[][] arr = {{18,	41,	92,	59,	34,	62,	29,	50,	16,	82},
//				{67,95,	22,	28,	35,	55,	73,	36,	22,	35},
//				{46,80,	61,	84,	1,	81,	29,	23,	60,	50},
//				{43,73,	20,	49,	84,	11,	65,	99,	74,	28},
//				{47,76,	41,	11,	62,	76,	99,	88,	6,	21},
//				{44,3,	6,	19,	95,	11,	87,	85,	66,	81},
//				{38,48,	35,	16,	40,	67,	53,	95,	62,	61},
//				{84,88,	41,	6,	51,	39,	49,	4,	25,	67},
//				{27,79,	27,	93,	2,	16,	99,	19,	72,	41}};
//		Hungarian h = new Hungarian(arr);
//		System.out.println(Arrays.toString(h.runHungarian()));
		
		Booth b1 = new Booth("Ray's Food", "Description", 4, BoothType.FOOD, 1); 
		Booth b2 = new Booth("Mustache Milk Tea", "Description", 5, BoothType.DRINK, 2);
		Booth b3 = new Booth("Tornado Potato", "Description", 2, BoothType.FOOD, 3);
		Booth b4 = new Booth("Paella House", "Description", 3, BoothType.FOOD, 4);
		Booth b5 = new Booth("Live 2 Dance Bollywood", "Description", 4, BoothType.ACTIVITY, 5);
		Booth b6 = new Booth("Left Bank Books", "Description", 5, BoothType.PRODUCTS, 6);
		Booth b7 = new Booth("The Uncommon Cottage", "Description", 2, BoothType.PRODUCTS, 7);
		Booth b8 = new Booth("Portage Bay Goods", "Description", 1, BoothType.PRODUCTS, 8);
		Booth b9 = new Booth("Drip Tea", "Description", 4, BoothType.DRINK, 9);
		
		ArrayList<Booth> list = new ArrayList<>();
		list.add(b1);
		list.add(b2);
		list.add(b3);
		list.add(b4);
		list.add(b5);
		list.add(b6);
		list.add(b7);
		list.add(b8);
		list.add(b9);
		
		Booth[][] spots = new Booth[3][3];
		BoothPlacer placer = new BoothPlacer(list,spots);
		spots = placer.placeBooths();
		
		// Name, description, int popularity, type
	}

	public static Booth[][] getTestBooths(){
		Booth b1 = new Booth("Ray's Food", "Description", 4, BoothType.FOOD, 1); 
		Booth b2 = new Booth("Mustache Milk Tea", "Description", 5, BoothType.DRINK, 2);
		Booth b3 = new Booth("Tornado Potato", "Description", 2, BoothType.FOOD, 3);
		Booth b4 = new Booth("Paella House", "Description", 3, BoothType.FOOD, 4);
		Booth b5 = new Booth("Live 2 Dance Bollywood", "Description", 4, BoothType.ACTIVITY, 5);
		Booth b6 = new Booth("Left Bank Books", "Description", 5, BoothType.PRODUCTS, 6);
		Booth b7 = new Booth("The Uncommon Cottage", "Description", 2, BoothType.PRODUCTS, 7);
		Booth b8 = new Booth("Portage Bay Goods", "Description", 1, BoothType.PRODUCTS, 8);
		Booth b9 = new Booth("Drip Tea", "Description", 4, BoothType.DRINK, 9);
		
		ArrayList<Booth> list = new ArrayList<>();
		list.add(b1);
		list.add(b2);
		list.add(b3);
		list.add(b4);
		list.add(b5);
		list.add(b6);
		list.add(b7);
		list.add(b8);
		//list.add(b9);
		
		Booth[][] spots = new Booth[3][3];
		spots[0][0] = new Booth();
		BoothPlacer placer = new BoothPlacer(list,spots);
		spots = placer.placeBooths();
		return spots;
	}
}
