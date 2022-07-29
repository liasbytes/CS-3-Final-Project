package eventManager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;

public class BoothView
implements ActionListener {

	// GUI Components
	private JPanel mainPanel, buttonPanel, boothPanel;
	private JLabel title;
	private JButton backButton;	
	private JFrame frame;

	private ArrayList<Booth> booths;
	private Booth[][] spots;


	/**
	 * Constructor to initialize GUI components
	 * @param frame Main JFrame to be passed through all constructors
	 */
	public BoothView(JFrame frame)
	{
		this.frame = frame;

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		title = new JLabel("Booth Map");
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setAlignmentX(0.5f);
		mainPanel.add(title);

		readSpots("spots.txt");
		boothPanel = new JPanel(new GridLayout(spots.length, spots[0].length));
		boothPanel.setMaximumSize(new Dimension(this.frame.getWidth(), this.frame.getHeight()-130));
		for (int i = 0; i < spots.length; i++) {
			for (int j = 0; j < spots[i].length; j++) {
				Dimension size =  new Dimension(this.frame.getWidth()/spots[i].length,(this.frame.getHeight()-130)/spots.length);
				BoothDisplay bd = new BoothDisplay(spots[i][j],size);
				boothPanel.add(bd);
			}
		}
		mainPanel.add(boothPanel);

		buttonPanel = new JPanel();
		backButton = new JButton("Back to Main Menu");
		backButton.setFont(new Font("Serif", Font.BOLD, 17));
		backButton.addActionListener(this);
		backButton.setPreferredSize(new Dimension(200,30));
		buttonPanel.add(backButton);
		mainPanel.add(buttonPanel);

		this.frame.add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * determine what program does next depending on user type
	 * @param: e action performed by user 
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == backButton) {
			mainPanel.setVisible(false);
			boothPanel.setVisible(false);
			buttonPanel.setVisible(false);
			OpeningPage f = new OpeningPage(this.frame);
		}
	}

	/**
	 * Reads the spots array from a file
	 * @param filePath The file to read from
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
}