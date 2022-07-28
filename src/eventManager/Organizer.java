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

	// Components
	private static JPanel panel, deletePanel;
	private static JLabel title, label;
	private JButton deleteBooth, addBooth, myDelete;
	private JFrame frame;
	private HashMap<Integer, Account> accounts;
	private HashMap<String, Booth> tempMap;
	private int currentID;
	private ArrayList<Booth> booths;
	private String boothID;
	
	/**
	 * constructor to initialize components
	 */
	public Organizer(JFrame frame)
	{
		this.frame = frame;
		booths = BoothReading.readFile("booth-data.txt");
		tempMap = new HashMap<String, Booth>();
		
		deletePanel = new JPanel(new GridBagLayout());
		deletePanel.setLayout(new javax.swing.BoxLayout(deletePanel, javax.swing.BoxLayout.Y_AXIS));
		deletePanel.setPreferredSize(new Dimension(frame.getX()/2, frame.getY()*3/5));

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		frame.setTitle("Event Manager");
		frame.setBounds(250, 150, 750, 500);
		
		title = new JLabel("Event Organizer");
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setBorder(BorderFactory.createEmptyBorder(10,275,10,0));
		panel.add(title);

		label = new JLabel("Hello, and welcome to Event Organizer! "
				+ "You can delete booths.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
		panel.add(label);

		label = new JLabel("What would you like to do?");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(15,30,0,0));
		panel.add(label);
		
		deleteBooth = new JButton("Delete a booth");
		deleteBooth.setFont(new Font("Serif", Font.BOLD, 17));
		deleteBooth.addActionListener(this);
		panel.add(deleteBooth);
		
		addBooth = new JButton("Add an unavailable booth");
		addBooth.setFont(new Font("Serif", Font.BOLD, 17));
		addBooth.addActionListener(this);
		panel.add(addBooth);
		
		frame.add(panel);
		frame.setVisible(true);
	}

	public String displayBooths() {
		
		for (int i = 0; i < booths.size(); i++) {
			String tempName = booths.get(i).getName();
			tempMap.put(tempName, booths.get(i));
			}
		
		ArrayList<String> tempBooths = new ArrayList<String>();
		for (Booth b : booths)
			tempBooths.add(b.getName());
		
		String[] myBooths = tempBooths.toArray(new String[0]);
	        JComboBox<String> jComboBox = new JComboBox<>(myBooths);
	        jComboBox.setBounds(80, 50, 140, 20);

	        myDelete = new JButton("Delete");
	        myDelete.setBounds(100, 100, 90, 20);
	        myDelete.addActionListener(this);

	        JLabel jLabel = new JLabel();
	        jLabel.setBounds(90, 100, 400, 100);

	        deletePanel.add(myDelete);
	        deletePanel.add(jComboBox);
	        deletePanel.add(jLabel);
	        
	        deletePanel.setLayout(null);
	        deletePanel.setSize(350, 250);
	        deletePanel.setVisible(true);
	        
	        String boothID = (String) jComboBox.getSelectedItem();
	        return boothID;
	}
	
	public void deleteBooth(Booth booth) {
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
				        	System.out.println(oldString);
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
						System.out.println(newContent);
						fw.write(newContent);
						JOptionPane.showMessageDialog(null, "Your booth has been deleted.");
						fw.close();
						disableComponents();
						// go to home page
						OpeningPage o = new OpeningPage(this.frame);
						
					} else {
				        JOptionPane.showMessageDialog(null, "Something went wrong. Try again later.");
				        fw.close();
				        disableComponents();
						OpeningPage o = new OpeningPage(this.frame);
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}			
			 
		 } else if (input == 1) {
			 // no
		 } else if (input == 2) {
			 // cancel
		 }
	}
	
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
    
    private void writeAccounts(String filePath) {
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
    
	private static void disableComponents() {
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
			System.out.println("Test");
	        deleteBooth(tempMap.get(boothID));
		}
		if (e.getSource() == deleteBooth) {
			frame.add(deletePanel);
			panel.setVisible(false);
			boothID = displayBooths();
			
			
		//add unavailable booth
		} else if (e.getSource() == addBooth) {
			panel.setVisible(false);
			//data[x][x]new Booth(); to create new unavailable booth TODO position ask for user input
		}
	}
}