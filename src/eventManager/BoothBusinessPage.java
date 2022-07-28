package eventManager;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class BoothBusinessPage implements ActionListener{

	private JPanel g;
	private int boothID;
	private JLabel title;
	private JButton editBooth;
	private JButton deleteBooth;
	private JButton returnHome;
	private JFrame frame;
	
	private HashMap<Integer, Account> accounts;
	private int currentID;
	
	/**
	 * Creates the main Booth page for businesses, allowing them to edit or delete their booth.
	 * @param boothID ID of the booth/account
	 */
	public BoothBusinessPage(int boothID, JFrame frame) {
		this.boothID = boothID;
		this.frame = frame;
		
		UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
		frame.setTitle("Manage your booth");
		
		g = new JPanel();
		g.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Label: Booth Page
		JPanel p = new JPanel();
		title = new JLabel("Manage your booth");
		title.setFont(new Font("Arial", Font.BOLD, 50));
		p.setBorder(BorderFactory.createEmptyBorder(50,10,10,10));
		p.add(title);
		
		// Button: Edit booth
		editBooth = new JButton("Edit your booth");
		editBooth.addActionListener(this);
		
		// Button: Delete booth
		deleteBooth = new JButton("Delete your booth");
		deleteBooth.addActionListener(this);
		
		// Button: Return to home page
		returnHome = new JButton("Go to home page");
		returnHome.addActionListener(this);
		
		c.gridy = 0;
		c.insets = new Insets(10, 0, 10, 0);

		g.add(editBooth, c);
		c.gridy = 1;
		g.add(deleteBooth, c);
		c.gridy = 2;
		g.add(returnHome, c);
		
		frame.add(p, BorderLayout.NORTH);
		frame.add(g, BorderLayout.CENTER);
		
	}
	
	@Override
	/**
	 * Determines what action to take based on buttons pressed
	 * @param e Action performed
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deleteBooth) {
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
							if (line.equals(String.valueOf(this.boothID))) {
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
							accounts.remove(boothID);
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
		} else if (e.getSource() == editBooth) {
			// Open Edit Booth page
			disableComponents();
//			EditBoothPage page = new EditBoothPage(this.boothID);
		} else if (e.getSource() == returnHome) {
			// Open the Home panel
			disableComponents();
			OpeningPage o = new OpeningPage(this.frame);
		}
	}
	
	private void disableComponents() {
		title.setVisible(false);
		g.setVisible(false);
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
	
}
