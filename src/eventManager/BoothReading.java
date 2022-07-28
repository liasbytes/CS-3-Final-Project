package eventManager;

import java.util.*;

import eventManager.Booth.BoothType;

import java.io.*;

public class BoothReading {

    /**
     * Main method which calls readFile method
     * 
     * @param args console input
     */
    public static void main(String[] args) {
        System.out.println(readFile("src/booth-data.txt"));
    }

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
                String boothName = scan.nextLine();
                String boothDesc = scan.nextLine();
                int popLvl = scan.nextInt();
                scan.nextLine();
                BoothType bType = BoothType.valueOf(scan.nextLine().toUpperCase());
                int curBoothId = scan.nextInt();
                scan.nextLine();
                boothData.add(new Booth(boothName, boothDesc, popLvl, bType, curBoothId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boothData;
    }
}
