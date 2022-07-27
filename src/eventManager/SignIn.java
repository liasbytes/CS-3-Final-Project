package eventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class SignIn implements ActionListener {
    private JLabel title, password1, label;
    private JTextField username;
    private JPasswordField password;
    private JButton logButton, signButton, backButton;
    private JFrame frame;
    private JPanel panel;
    private boolean OrganizerOrNot;

    /**
     * Constructor method for instance variables
     */
    public SignIn(boolean OrganizerOrNot, JFrame frame) {
    	this.frame = frame;
    	this.OrganizerOrNot = OrganizerOrNot;
        panel = new JPanel();

        panel.setLayout(null);

        title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(320, 120, 70, 30);
        panel.add(title);

        label = new JLabel("User");
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setBounds(250, 158, 70, 20);
        panel.add(label);

        username = new JTextField();
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        username.setBounds(250, 175, 193, 28);
        panel.add(username);

        password1 = new JLabel("Password");
        password1.setFont(new Font("Arial", Font.PLAIN, 15));
        password1.setBounds(250, 205, 70, 20);
        panel.add(password1);

        password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setBounds(250, 225, 193, 28);
        panel.add(password);

        logButton = new JButton("Login");
        logButton.setFont(new Font("Arial", Font.PLAIN, 15));
        logButton.setBounds(250, 260, 90, 25);
        logButton.addActionListener(this);
        panel.add(logButton);

        signButton = new JButton("Signup");
        signButton.setFont(new Font("Arial", Font.PLAIN, 15));
        signButton.setBounds(350, 260, 90, 25);
        signButton.addActionListener(this);
        panel.add(signButton);

        backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.setBounds(250, 300, 190, 25);
        backButton.addActionListener(this);
        panel.add(backButton);

        this.frame.add(panel);
    }

    /**
     * Acts based on user actions
     * 
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        String data = username.getText();
        String data2 = String.valueOf(password.getPassword());

        Map<String, String> map = new HashMap<>();
        if (e.getSource() == logButton) {
            if (map.containsKey(data2)) {
            	if (!data.equals("") && !data2.equals("")) {
                	if (OrganizerOrNot == false)
                        getBooth();
                	else
                		getOrganizer();
                }
            }
        } else if (e.getSource() == signButton) {
            map.put(data2, data);
            if (!data.equals("") && !data2.equals("")) {
            	if (OrganizerOrNot == false)
                    getBooth();
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
     * Getting booth holder panel
     */
    public void getBooth() {
    	panel.setVisible(false);
        BoothAddPage b = new BoothAddPage(1, this.frame);
        // Note from Emily: You have to add a number for the Booth ID, now
    }
    
    /**
     * Getting booth organizer panel
     */
    public void getOrganizer() {
        //BoothOrganizer b = new BoothOrganizer();
        // Note from Emily: You have to add a number for the Booth ID, now
    }
}