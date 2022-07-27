package eventManager;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class BoothBusinessPage extends JFrame implements ActionListener{

	private JPanel g;
	private int boothID;
	private JLabel title;
	private JButton editBooth;
	private JButton deleteBooth;
	private JButton returnHome;
	
	public static void main(String[] args) {
		BoothBusinessPage b = new BoothBusinessPage(7); // Number here is the booth ID
	}
	
	/**
	 * Creates the main Booth page for businesses, allowing them to edit or delete their booth.
	 * @param boothID ID of the booth/account
	 */
	public BoothBusinessPage(int boothID) {
		this.boothID = boothID;
		UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
		setBounds(250, 150, 750, 500);
		setTitle("Manage your booth");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		g = new JPanel();
		g.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Label: Booth Page
		title = new JLabel("Manage your booth");
		title.setLocation(200, 50);
		title.setSize(400, 50);
		title.setFont(new Font("Arial", Font.BOLD, 35));
		
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
		
		add(title);
		add(g);
		
		setVisible(true);
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
				 
				 File tbm = new File("src/booth-data.txt");
				 String oldContent = "";
				 String oldString = "";
				 Writer fw = null;
				 BufferedReader reader = null;
				 boolean found = false;
				 
					try {
						reader = new BufferedReader(new FileReader(tbm));
						fw = new FileWriter((tbm), false);
			        	
						String line = reader.readLine();

						while (line != null){
							if (line.equals(String.valueOf(this.boothID))) {
					        	for (int i = 0; i < 5; i++) {
						        	oldString = oldString.concat(line);
					        		line = reader.readLine();
					        	}
					        	found = true;
					        	System.out.println(oldString);
					        }
						        oldContent = oldContent + line + System.lineSeparator();
						        line = reader.readLine();
						        
						}
						
						if (found == true) {
							String newContent = oldContent.replaceAll(oldString, "");
							fw.write(newContent);
							JOptionPane.showMessageDialog(null, "Your booth has been deleted.");
							// go to home page
							OpeningPage o = new OpeningPage();
							
						} else {
					        JOptionPane.showMessageDialog(null, "Something went wrong. Try again later.");
							OpeningPage o = new OpeningPage();
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
			EditBoothPage page = new EditBoothPage();
		} else if (e.getSource() == returnHome) {
			// Open the Home panel
			OpeningPage o = new OpeningPage();
		}
	}
	
}
