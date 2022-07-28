package eventManager;

import java.util.*;

import eventManager.Booth.BoothType;

import java.io.*;

public class BoothReading {
    /**
     * Reads the file and adds booth data into an object, which is added into an
     * arraylist
     * 
     * @param fileName name or path of file
     * @return arraylist storing Booth objects
     */
    public static ArrayList<Booth> readFile(String fileName) {
        Scanner scan = null;
        ArrayList<Booth> boothData = new ArrayList<>();
        try {
            scan = new Scanner(new File(fileName));
            while (scan.hasNextLine()) {
            	int curBoothId = scan.nextInt();
                scan.nextLine();
                String boothName = scan.nextLine();
                String boothDesc = scan.nextLine();
                int popLvl = scan.nextInt();
                scan.nextLine();
                BoothType bType = BoothType.valueOf(scan.nextLine().toUpperCase());
                boothData.add(new Booth(boothName, boothDesc, popLvl, bType, curBoothId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boothData;
    }
}
