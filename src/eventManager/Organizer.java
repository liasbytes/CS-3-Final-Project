package eventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Organizer
implements ActionListener {

	// GUI Components
	private static JPanel panel, deletePanel;
	private static JLabel title, label;
	private JButton deleteBooth, boothPositions, myDelete, mainMenu;
	private JFrame frame;
	private JComboBox<String> jComboBox;
	
	// Temporary variables used in delete methods
	private HashMap<Integer, Account> accounts;
	private String boothName;
	
	// Temporary variables used in display method
	private HashMap<String, Booth> tempMap;
	private int currentID;
	private ArrayList<Booth> booths;

	/**
	 * constructor to initialize components
	 * @param frame Main JFrame to pass through all constructors
	 */
	public Organizer(JFrame frame)
	{
		this.frame = frame;
		booths = BoothReading.readFile("booth-data.txt");
		tempMap = new HashMap<String, Booth>();

		//panel for deleting booths
		deletePanel = new JPanel(new GridBagLayout());
		deletePanel.setLayout(new javax.swing.BoxLayout(deletePanel, javax.swing.BoxLayout.Y_AXIS));
		deletePanel.setPreferredSize(new Dimension(frame.getX()/2, frame.getY()*3/5));

		//main panel
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		frame.setTitle("Event Manager");

		//labels
		title = new JLabel("Event Organizer");
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		title.setAlignmentX(0.5f);
		panel.add(title);

		label = new JLabel("Hello, and welcome to Event Organizer!");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
		label.setAlignmentX(0.5f);
		panel.add(label);

		label = new JLabel("You can delete and manage the placement of booths.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
		label.setAlignmentX(0.5f);
		panel.add(label);

		label = new JLabel("What would you like to do?");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(15,30,0,0));
		label.setAlignmentX(0.5f);
		panel.add(label);

		//buttons
		deleteBooth = new JButton("Delete a booth");
		deleteBooth.setFont(new Font("Serif", Font.BOLD, 17));
		deleteBooth.addActionListener(this);
		deleteBooth.setAlignmentX(0.5f);
		panel.add(deleteBooth);

		boothPositions = new JButton("Manage booth positions");
		boothPositions.setFont(new Font("Serif", Font.BOLD, 17));
		boothPositions.addActionListener(this);
		boothPositions.setAlignmentX(0.5f);
		panel.add(boothPositions);

		mainMenu = new JButton("Return to main menu");
		mainMenu.setFont(new Font("Serif", Font.BOLD, 17));
		mainMenu.addActionListener(this);
		mainMenu.setAlignmentX(0.5f);
		panel.add(mainMenu);

		frame.add(panel);
		frame.setVisible(true);
	}

	/**
	 * displays drop-down list of all booths to be selected
	 */
	public void displayBooths()
	{
		//add all existing booths to temporary HashMap with names and booth objects
		for (int i = 0; i < booths.size(); i++) {
			String tempName = booths.get(i).getName();
			tempMap.put(tempName, booths.get(i));
		}

		//create ArrayList of all booth names
		ArrayList<String> tempBooths = new ArrayList<String>();
		for (Booth b : booths)
			tempBooths.add(b.getName());

		//convert ArrayList to String for jComboBox
		String[] myBooths = tempBooths.toArray(new String[0]);
		
		title = new JLabel("Booth Deletion");
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setAlignmentX(0.5f);
		deletePanel.add(title);

		//use jComboBox to create drop-down list of all booths
		jComboBox = new JComboBox<>(myBooths);
		jComboBox.setAlignmentX(0.5f);
		deletePanel.add(jComboBox);
		
		myDelete = new JButton("Delete"); //delete button
		myDelete.setFont(new Font("Serif", Font.BOLD, 17));
		myDelete.addActionListener(this);
		myDelete.setAlignmentX(0.5f);

		deletePanel.add(Box.createRigidArea(new Dimension(panel.getWidth(), 50)));
		deletePanel.add(myDelete);
		deletePanel.add(mainMenu);
		deletePanel.add(Box.createRigidArea(new Dimension(panel.getWidth(), (int)(panel.getHeight()*0.75))));
		deletePanel.setLayout(new BoxLayout(deletePanel,BoxLayout.Y_AXIS));
		deletePanel.setVisible(true);
	}

	/**
	 * Deletes a selected booth
	 * @param booth The booth to delete
	 */
	public void deleteBooth(Booth booth) 
	{
		int input = JOptionPane.showConfirmDialog(null, 
				"Are you sure you want to continue?", "Select an Option...",JOptionPane.YES_NO_CANCEL_OPTION);
		if (input == 0) {
			// yes
			File tbm = new File("booth-data.txt");
			String oldContent = "";
			String oldString = "";
			FileWriter fw = null;
			BufferedReader reader = null;
			boolean found = false;

			try {
				reader = new BufferedReader(new FileReader(tbm));

				String line = reader.readLine();
				while (line != null){
					if (booth != null && line.equals(String.valueOf(booth.getBoothID()))) {
						for (int i = 0; i < 5; i++) {
							oldString = oldString.concat(line);
							oldString = oldString.concat(System.lineSeparator());
							line = reader.readLine();
						}
						found = true;
					}
					oldContent = oldContent + line + System.lineSeparator();
					line = reader.readLine();
				}
				reader.close();
				if (found == true) {
					fw = new FileWriter(tbm, false);
					readAccounts(SignIn.BOOTH_ACCOUNT_PATH);
					accounts.remove(booth.getBoothID());
					writeAccounts(SignIn.BOOTH_ACCOUNT_PATH);
					String newContent = oldContent.replaceAll(oldString, "");
					fw.write(newContent);
					JOptionPane.showMessageDialog(null, "Your booth has been deleted.");
					fw.close();
					disableComponents();
					// go to home page
					OpeningPage o = new OpeningPage(this.frame);

				} else {
					JOptionPane.showMessageDialog(null, "Something went wrong. Try again later.");
					disableComponents();
					OpeningPage o = new OpeningPage(this.frame);
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}			

		}
	}
	
	/**
	 * Reads information about all accounts from file
	 * @param filePath File to be read
	 */
	private void readAccounts(String filePath) {
		accounts = new HashMap<>();
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filePath));
			currentID = scan.nextInt();
			scan.nextLine();
			while(scan.hasNextLine()) {
				String username = scan.nextLine();
				String password = scan.nextLine();
				int boothID = Integer.parseInt(scan.nextLine());
				accounts.put(boothID, new Account(username,password,boothID));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			FileWriter fw = null;
			try {
				fw = new FileWriter(new File(filePath));
				fw.write("");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Writes information about accounts to file
	 * @param filePath File to be written to
	 */
	private void writeAccounts(String filePath) 
	{
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(filePath), false);	
			fw.write(String.valueOf(currentID));
			fw.write(String.format("%n"));
			for (Integer ID : accounts.keySet()) {
				String username = accounts.get(ID).username;
				String password = accounts.get(ID).password;
				fw.write(username);
				fw.write(String.format("%n"));
				fw.write(password);
				fw.write(String.format("%n"));
				fw.write(String.valueOf(ID));
				fw.write(String.format("%n"));
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets all visible panels of this object to hidden
	 */
	private static void disableComponents() 
	{
		deletePanel.setVisible(false);
	}

	/**
	 * determine what program does next depending on user type
	 * @param: e action performed by user 
	 */
	public void actionPerformed(ActionEvent e)
	{
		//delete a booth
		if (e.getSource() == myDelete) {
			boothName = (String) jComboBox.getSelectedItem();
			deleteBooth(tempMap.get(boothName));
		}
		if (e.getSource() == deleteBooth) {
			frame.add(deletePanel);
			panel.setVisible(false);
			displayBooths();
		} 
		
		//manage positions of booths
		else if (e.getSource() == boothPositions) {
			panel.setVisible(false);
			BoothPositionManager bpm = new BoothPositionManager(this.frame);
		}
		
		//return to main menu
		else if (e.getSource() == mainMenu) {
			panel.setVisible(false);
			deletePanel.setVisible(false);
			OpeningPage op = new OpeningPage(this.frame);
		}
	}
}