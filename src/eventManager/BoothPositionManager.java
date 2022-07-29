package eventManager;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class BoothPositionManager implements ActionListener{

	private JFrame frame;
	private JPanel panel;
	private JLabel sizeXLabel, sizeYLabel, spotXLabel, spotYLabel;
	private JTextField sizeX, sizeY, spotX, spotY;
	private JButton confirmSize, confirmSpot, placeBooths, organizerPage, mainPage;
	private Booth[][] spots;
	private ArrayList<Booth> booths;
	
	public BoothPositionManager(JFrame inputFrame) {
		Font f = new Font("Arial", Font.PLAIN, 16);
		frame = inputFrame;
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,5));
		
		sizeXLabel = new JLabel("Width of event venue (in spots):");
		sizeXLabel.setFont(f);
		panel.add(sizeXLabel);
		
		sizeX = new JTextField();
		sizeX.setFont(f);
		panel.add(sizeX);
		
		sizeYLabel = new JLabel("Height of event venue (in spots):");
		sizeYLabel.setFont(f);
		panel.add(sizeYLabel);
		
		sizeY = new JTextField();
		sizeY.setFont(f);
		panel.add(sizeY);
		
		confirmSize = new JButton("Confirm new venue size");
		confirmSize.setFont(f);
		confirmSize.addActionListener(this);
		panel.add(confirmSize);
		
		spotXLabel = new JLabel("X position of new unavailable booth:");
		spotXLabel.setFont(f);
		panel.add(spotXLabel);
		
		spotX = new JTextField();
		spotX.setFont(f);
		panel.add(spotX);
		
		spotYLabel = new JLabel("Y position of new unavailable booth:");
		spotYLabel.setFont(f);
		panel.add(spotYLabel);
		
		spotY = new JTextField();
		spotY.setFont(f);
		panel.add(spotY);
		
		confirmSpot = new JButton("Add unavailable booth");
		confirmSpot.setFont(f);
		confirmSpot.addActionListener(this);
		panel.add(confirmSpot);
		
		placeBooths = new JButton("Run booth placing algorithm");
		placeBooths.setFont(f);
		placeBooths.addActionListener(this);
		panel.add(placeBooths);
		
		organizerPage = new JButton("Return to organizer page");
		organizerPage.setFont(f);
		organizerPage.addActionListener(this);
		panel.add(organizerPage);
		
		mainPage = new JButton("Main Page");
		mainPage.setFont(f);
		mainPage.addActionListener(this);
		panel.add(mainPage);
		
		frame.add(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirmSize) {
			try {
				String s = sizeX.getText();
				int xSize = Integer.parseInt(s);
				s = sizeY.getText();
				int ySize = Integer.parseInt(s);
				if (xSize <= 0 || ySize <= 0) {
					throw new Exception();
				}
				spots = new Booth[ySize][xSize];
				writeSpots("spots.txt");
			} catch (Exception err) {
				JOptionPane.showMessageDialog(frame, "Invalid size", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == confirmSpot) {
			try {
				String s = spotX.getText();
				int xPos = Integer.parseInt(s);
				s = spotY.getText();
				int yPos = Integer.parseInt(s);
				readSpots("spots.txt");
				if (xPos > spots[0].length || yPos > spots.length) {
					throw new Exception();
				}
				spots[yPos-1][xPos-1] = new Booth();
				writeSpots("spots.txt");
			} catch (Exception err) {
				err.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Invalid position", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == placeBooths) {
			readSpots("spots.txt");
			int numOpenSpots = 0;
			for (int i = 0; i < spots.length; i++) {
				for (int j = 0; j < spots[0].length; j++) {
					if (spots[i][j] == null) {
						numOpenSpots++;
					} else if (spots[i][j].getBoothID() != -1) {
						spots[i][j] = null;
						numOpenSpots++;
					}
				}
			}
			if (booths.size() > numOpenSpots) {
				JOptionPane.showMessageDialog(frame, "Not enough open spots", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				BoothPlacer bp = new BoothPlacer(booths, spots);
				spots = bp.placeBooths();
				writeSpots("spots.txt");
			}
		} else if (e.getSource() == organizerPage) {
			panel.setVisible(false);
			Organizer o = new Organizer(this.frame);
		} else if (e.getSource() == mainPage) {
			panel.setVisible(false);
			OpeningPage op = new OpeningPage(this.frame);
		}
		
	}
	
	private void readSpots(String filePath) {
		booths = BoothReading.readFile("booth-data.txt");
		HashMap<Integer, Booth> IDMap = new HashMap<>();
		for (Booth b : booths) {
			IDMap.put(b.getBoothID(), b);
		}
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filePath));
			int rows = scan.nextInt();
			int cols = scan.nextInt();
			spots = new Booth[rows][cols];
			for (int i = 0; i < spots.length; i++) {
				for (int j = 0; j < spots[0].length; j++) {
					String s = scan.next();
					if (s.equals("-1")) {
						spots[i][j] = new Booth();
					} else if (!s.equals("null")) {
						spots[i][j] = IDMap.get(Integer.parseInt(s));
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void writeSpots(String filePath) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(filePath), false);
			fw.write(String.valueOf(spots.length));
			fw.write(String.format("%n"));
			fw.write(String.valueOf(spots[0].length));
			fw.write(String.format("%n"));
			for (int i = 0; i < spots.length; i++) {
				for (int j = 0; j < spots[0].length; j++) {
					if (spots[i][j] == null) {
						fw.write("null");
						fw.write(String.format("%n"));
					} else {
						fw.write(String.valueOf(spots[i][j].getBoothID()));
						fw.write(String.format("%n"));
					}
				}
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
