package eventManager;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import eventManager.Booth.BoothType;

public class EditBoothPage implements ActionListener{
	private JPanel c;
	private JPanel mainPanel;
	private JLabel title;
	private JLabel name;
	private JTextField tname;
	private JLabel desc;
	private JTextArea tdesc;
	private JLabel popularity;
	private JSlider npopularity;
	private JLabel boothType;
	private JComboBox tboothType;
	private JButton update;
	private JButton backBooth;
	
	private JPanel p;
	private JLabel thankYou;
	private JButton boothPage;
	private JButton homePage;
	
	private JFrame frame;
	private ArrayList<Booth> allBooths;
	private String boothName;
	private String boothDesc;
	private int boothPop;
	private BoothType boothTypeEnum;
	private int boothID;
	
	private BoothType types[]
			= { BoothType.ACTIVITY, BoothType.DRINK, BoothType.FOOD, BoothType.PRODUCTS, BoothType.UNAVAILABLE};
	
	/**
	 * Creates Booth Add Page
	 * @param boothID ID of the booth, should match account ID
	 * @param frame Main JFrame to pass through all constructors
	 */
	public EditBoothPage(int boothID, JFrame frame) {
		this.frame = frame;
		this.boothID = boothID;
		
		UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
		
		c = new JPanel(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		c.setPreferredSize(new Dimension(frame.getX()/2, frame.getY()*3/5));
				
		title = new JLabel("Edit your booth");
		title.setSize(300, 30);
		title.setFont(new Font("Arial", Font.BOLD, 35));
		
		update = new JButton("Update");
		update.setSize(100, 30);
		update.addActionListener(this);
		
		allBooths = BoothReading.readFile("booth-data.txt");
		for (Booth b : allBooths) {
			if (b.getBoothID() == this.boothID) {
				boothName = b.getName(); 
				boothDesc = b.getDescription();
				boothPop = b.getPopularity();
				boothTypeEnum = b.getBoothType();
			}
		}
		
		backBooth = new JButton("Back to Manage Booth");
		backBooth.setSize(100, 30);
		backBooth.addActionListener(this);
	        
		name = new JLabel("Booth name: ");
		desc = new JLabel("Booth description:");
		popularity = new JLabel("Estimated visitor traffic (1-5):");
		boothType = new JLabel("Type of booth: ");
		
		Dimension labels = new Dimension(200, 20);
		name.setPreferredSize(labels);
		desc.setPreferredSize(labels);
		popularity.setPreferredSize(labels);
		boothType.setPreferredSize(labels);
	
		
		tname = new JTextField(boothName);
		tdesc = new JTextArea(boothDesc);
		tdesc.setLineWrap(true);
		npopularity = new JSlider(1, 5, boothPop);
		npopularity.setMajorTickSpacing(1);
		npopularity.setMinorTickSpacing(1);
		npopularity.setPaintLabels(this.frame.isEnabled());
		
		
		String[] typesString = Arrays.toString(BoothType.values()).replaceAll("^.|.$", "").split(", ");
		tboothType = new JComboBox<String>(typesString);
		
		tname.setPreferredSize(new Dimension(200, 10));
		tdesc.setPreferredSize(new Dimension(200, 100));
		npopularity.setPreferredSize(new Dimension(215, 10));
		tboothType.setPreferredSize(new Dimension(100, 10));
		
		g.anchor = GridBagConstraints.LINE_START;
		g.anchor = GridBagConstraints.PAGE_START;
		g.ipady = 20;
		
		g.gridx = 0;
		g.gridy = 0;
		c.add(name, g);
		
		g.gridx = 1;
		g.gridy = 0;
		c.add(tname, g);
		
		g.gridx = 0;
		g.gridy = 1;
		c.add(desc, g);
		
		g.gridx = 1;
		g.gridy = 1;
		c.add(tdesc, g);
		
		g.gridx = 0;
		g.gridy = 2;
		c.add(popularity, g);
		
		g.anchor = GridBagConstraints.CENTER;
		g.gridx = 1;
		g.gridy = 2;
		c.add(npopularity, g);
		
		g.gridx = 0;
		g.gridy = 3;
		c.add(boothType, g);
		
		g.gridx = 1;
		g.gridy = 3;
		c.add(tboothType, g);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		mainPanel.add(Box.createGlue());
		title.setAlignmentX(0.5f);
		c.setAlignmentX(0.5f);
		update.setAlignmentX(0.5f);
		backBooth.setAlignmentX(0.5f);
		mainPanel.add(title);
		mainPanel.add(c);
		mainPanel.add(update);
		mainPanel.add(backBooth);
		
		this.frame.add(mainPanel);
		
		
		this.frame.setVisible(true);;
		
		// Thank you page
		p = new JPanel(new GridBagLayout());
		
		thankYou = new JLabel("Your booth has been edited.");
		boothPage = new JButton("View booth map");
		homePage = new JButton("Return to home page");
		
		boothPage.addActionListener(this);
		homePage.addActionListener(this);
		
		g.gridy = 0;
		p.add(thankYou, g);
		g.gridy = 1;
		p.add(boothPage, g);
		g.gridy = 2;
		p.add(homePage, g);
		
	}

	@Override
	/**
	 * Determines action to take based on buttons pressed, including updating booth information, returning to booth page, or returning to home page.
	 * @param e Action performed
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == update) {
			File tbm = new File("booth-data.txt");
			 String oldContent = "";
			 String oldString = "";
			 String newString = this.boothID + String.format("%n")+ tname.getText() + String.format("%n") + tdesc.getText() + String.format("%n") + npopularity.getValue() + String.format("%n") + tboothType.getSelectedItem() + String.format("%n");
			 
			 FileWriter fw = null;
			 BufferedReader reader = null;
			 boolean found = false;
			 
				try {
					reader = new BufferedReader(new FileReader(tbm));		        	
					String line = reader.readLine();

					while (line != null){
						if (line.equals(String.valueOf(this.boothID))) {
				        	for (int i = 0; i < 5; i++) {
					        	oldString = oldString.concat(line) + System.lineSeparator();;
					        	oldContent = oldContent.concat(line) + System.lineSeparator();
				        		line = reader.readLine();
				        	}
				        	found = true;
				        }
					        oldContent = oldContent + line + System.lineSeparator();
					        line = reader.readLine();
					        
					}
					
					reader.close();
					
					if (found == true) {
						fw = new FileWriter((tbm), false);
						String newContent = oldContent.replaceAll(oldString, newString);
						fw.write(newContent);				
						fw.close();
					} else {
				        JOptionPane.showMessageDialog(null, "Something went wrong. Try again later.");
				        reader.close();
				        return;
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}		
			
			// Show thank you page
			mainPanel.setVisible(false);
			this.frame.add(p);
			p.setVisible(true);
		} else if (e.getSource() == backBooth) {
			mainPanel.setVisible(false);
			BoothBusinessPage f = new BoothBusinessPage(this.boothID, this.frame);
        }
		
		if (e.getSource() == boothPage) {
			// Go to booth page
			p.setVisible(false);
			BoothView bf = new BoothView(this.frame);
		} else if (e.getSource() == homePage) {
			// Go to home page
			p.setVisible(false);
			OpeningPage f = new OpeningPage(this.frame);
		}
	}
}
