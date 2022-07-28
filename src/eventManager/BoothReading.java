package eventManager;

import java.util.*;

import eventManager.Booth;
import eventManager.Booth.BoothType;

import java.io.*;

public class BoothReading {

    /**
     * Main method which calls readFile method
     * 
     * @param args console input
     */
    public static void main(String[] args) {
        readFile("src/booth-data.txt");
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
        try {
            scan = new Scanner(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Booth> boothData = new ArrayList<>();
        while (scan.hasNext()) {
            boothData.add(new Booth(scan.nextLine(), scan.nextLine(), scan.nextInt(), BoothType.valueOf(scan.nextLine()),
                    scan.nextInt()));
        }
        return boothData;
    }
}
