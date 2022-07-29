package eventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignIn implements ActionListener {
    private JLabel title, password1, label;
    private JTextField username;
    private JPasswordField password;
    private JButton logButton, signButton, backButton;
    private JFrame frame;
    private JPanel panel;
    private boolean OrganizerOrNot;
    private Map<String, Account> accounts;
    
    public final static String BOOTH_ACCOUNT_PATH = "booth-accounts.txt";
    public final static String ORGANIZER_ACCOUNT_PATH = "organizer-accounts.txt";
    private static int currentID;

   /**
    * Constructor method for the Sign In page
    * @param OrganizerOrNot Whether the sign-in is for an event organizer (or a booth holder)
    * @param frame Main JFrame to be passed through all constructors
    */
    public SignIn(boolean OrganizerOrNot, JFrame frame) {
    	this.frame = frame;
    	this.OrganizerOrNot = OrganizerOrNot;
        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(Box.createGlue());

        title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setPreferredSize(new Dimension(70,30));
        title.setAlignmentX(0.5f);
        panel.add(title);

        label = new JLabel("User");
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setPreferredSize(new Dimension(70,20));
        label.setAlignmentX(0.5f);
        panel.add(label);

        username = new JTextField();
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        username.setMaximumSize(new Dimension(200,30));
        username.setAlignmentX(0.5f);
        panel.add(username);

        password1 = new JLabel("Password");
        password1.setFont(new Font("Arial", Font.PLAIN, 15));
        password1.setPreferredSize(new Dimension(70,20));
        password1.setAlignmentX(0.5f);
        panel.add(password1);

        password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setMaximumSize(new Dimension(200,30));
        password.setAlignmentX(0.5f);
        panel.add(password);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        logButton = new JButton("Login");
        logButton.setFont(new Font("Arial", Font.PLAIN, 15));
        logButton.addActionListener(this);
        buttonPanel.add(logButton);

        signButton = new JButton("Signup");
        signButton.setFont(new Font("Arial", Font.PLAIN, 15));
        signButton.addActionListener(this);
        if (!OrganizerOrNot) {
        	buttonPanel.add(signButton);
        }
        
        buttonPanel.setMaximumSize(new Dimension(200,60));
        buttonPanel.setAlignmentX(0.5f);
        
        panel.add(buttonPanel);

        backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.addActionListener(this);
        backButton.setAlignmentX(0.5f);
        panel.add(backButton);
        
        panel.add(Box.createGlue());

        this.frame.add(panel, BorderLayout.CENTER);
    }

    /**
     * Acts based on user actions
     * 
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        String data = username.getText();
        String data2 = String.valueOf(password.getPassword());

        accounts = new HashMap<>();
        if (e.getSource() == logButton) {
        	if (!OrganizerOrNot) {
        		readAccounts(BOOTH_ACCOUNT_PATH);
        	} else {
        		readAccounts(ORGANIZER_ACCOUNT_PATH);
        	}
            if (accounts.containsKey(data)) {
            	if (!data.equals("") && !data2.equals("")) {
                	if (OrganizerOrNot == false) {
                		panel.setVisible(false);
                		BoothBusinessPage bp = new BoothBusinessPage(accounts.get(data).ID, this.frame);
                	}
                	else
                		getOrganizer();
                }
            }
        } else if (e.getSource() == signButton) {
        	if (!OrganizerOrNot) {
        		readAccounts(BOOTH_ACCOUNT_PATH);
        	}
        	currentID++;
            accounts.put(data, new Account(data,data2,currentID));
            if (!OrganizerOrNot) {
            	writeAccounts(BOOTH_ACCOUNT_PATH);
            }
            if (!data.equals("") && !data2.equals("")) {
            	if (OrganizerOrNot == false)
                    getBooth(accounts.get(data).ID);
            	else
            		getOrganizer();
            }
            String def = "";
            username.setText(def);
            password.setText(def);
        } else if (e.getSource() == backButton) {
            panel.setVisible(false);
            OpeningPage f = new OpeningPage(this.frame);
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
        		accounts.put(username, new Account(username,password,boothID));
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
    private void writeAccounts(String filePath) {
    	FileWriter fw = null;
    	try {
    		fw = new FileWriter(new File(filePath), false);	
    		fw.write(String.valueOf(currentID));
    		fw.write(String.format("%n"));
    		for (String username : accounts.keySet()) {
    			String password = accounts.get(username).password;
    			int accountID = accounts.get(username).ID;
    			fw.write(username);
    			fw.write(String.format("%n"));
    			fw.write(password);
    			fw.write(String.format("%n"));
    			fw.write(String.valueOf(accountID));
    			fw.write(String.format("%n"));
    		}
    		fw.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Getting booth holder panel
     * @param boothID
     */
    public void getBooth(int boothID) {
    	panel.setVisible(false);
        BoothAddPage b = new BoothAddPage(boothID, this.frame);
    }
    
    /**
     * Getting event organizer panel
     */
    public void getOrganizer() {
    	panel.setVisible(false);
    	Organizer b = new Organizer(this.frame);
    }
    
    
}