package eventManager;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoothPositionManager implements ActionListener{

	private JFrame frame;
	private JPanel panel, sizePanel, spotPanel, miscPanel, titlePanel;
	private JLabel sizeXLabel, sizeYLabel, spotXLabel, spotYLabel, title;
	private JTextField sizeX, sizeY, spotX, spotY;
	private JButton confirmSize, confirmSpot, placeBooths, organizerPage, mainPage;
	private Booth[][] spots;
	private ArrayList<Booth> booths;
	
	/**
	 * Constructor that initializes and places all elements in the GUI for organizers to manage booth placement
	 * @param inputFrame The JFrame to add objects to
	 */
	public BoothPositionManager(JFrame inputFrame) {
		Font f = new Font("Serif", Font.PLAIN, 14);
		frame = inputFrame;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		
		titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.9),100));
		sizePanel = new JPanel();
		sizePanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.9), 100));
		spotPanel = new JPanel();
		spotPanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.9), 100));
		miscPanel = new JPanel();
		miscPanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.9), 100));
		
		title = new JLabel("Manage Booth Layout");
		title.setFont(new Font("Serif", Font.BOLD, 45));
		titlePanel.add(title);
		
		sizeXLabel = new JLabel("Width of event venue (in spots):");
		sizeXLabel.setFont(f);
		sizePanel.add(sizeXLabel);
		
		sizeX = new JTextField();
		sizeX.setFont(f);
		sizeX.setPreferredSize(new Dimension(100,20));
		sizePanel.add(sizeX);
		
		sizeYLabel = new JLabel("Height of event venue (in spots):");
		sizeYLabel.setFont(f);
		sizePanel.add(sizeYLabel);
		
		sizeY = new JTextField();
		sizeY.setFont(f);
		sizeY.setPreferredSize(new Dimension(100,20));
		sizePanel.add(sizeY);
		
		confirmSize = new JButton("Confirm new venue size");
		confirmSize.setFont(f);
		confirmSize.addActionListener(this);
		sizePanel.add(confirmSize);
		
		spotXLabel = new JLabel("X position of new unavailable booth:");
		spotXLabel.setFont(f);
		spotPanel.add(spotXLabel);
		
		spotX = new JTextField();
		spotX.setFont(f);
		spotX.setPreferredSize(new Dimension(100,20));
		spotPanel.add(spotX);
		
		spotYLabel = new JLabel("Y position of new unavailable booth:");
		spotYLabel.setFont(f);
		spotPanel.add(spotYLabel);
		
		spotY = new JTextField();
		spotY.setFont(f);
		spotY.setPreferredSize(new Dimension(100,20));
		spotPanel.add(spotY);
		
		confirmSpot = new JButton("Add unavailable booth");
		confirmSpot.setFont(f);
		confirmSpot.addActionListener(this);
		spotPanel.add(confirmSpot);
		
		placeBooths = new JButton("Run booth placing algorithm");
		placeBooths.setFont(f);
		placeBooths.addActionListener(this);
		miscPanel.add(placeBooths);
		
		organizerPage = new JButton("Return to organizer page");
		organizerPage.setFont(f);
		organizerPage.addActionListener(this);
		miscPanel.add(organizerPage);
		
		mainPage = new JButton("Main Page");
		mainPage.setFont(f);
		mainPage.addActionListener(this);
		miscPanel.add(mainPage);
		
		panel.add(titlePanel);
		panel.add(sizePanel);
		panel.add(spotPanel);
		panel.add(miscPanel);
		
		frame.add(panel);
	}
	
	/**
	 * Handles button input and runs the required methods.
	 * 
	 * @param e The event passed in by the object calling this method.
	 */
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
	
	/**
	 * Reads the spots array from a file,
	 * @param filePath The file path to read from.
	 */
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
	
	/**
	 * Writes the spots array to a file.
	 * @param filePath The file to write to.
	 */
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
