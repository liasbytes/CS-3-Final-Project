package eventManager;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import eventManager.Booth.BoothType;

public class BoothAddPage extends JFrame implements ActionListener{

	private JPanel c;
	private JLabel title;
	private JLabel name;
	private JTextField tname;
	private JLabel desc;
	private JTextArea tdesc;
	private JLabel popularity;
	private JSlider npopularity;
	private JLabel boothType;
	private JComboBox tboothType;
	private JButton submit;
	
	public JPanel p;
	public JLabel thankYou;
	public JButton boothPage;
	public JButton homePage;
	
	private BoothType types[]
			= { BoothType.ACTIVITY, BoothType.DRINK, BoothType.FOOD, BoothType.PRODUCTS, BoothType.UNAVAILABLE};
	
	public static void main(String[] args) {
		BoothAddPage p = new BoothAddPage();
	}
	
	public BoothAddPage() {
		
		UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));


		setBounds(250, 150, 750, 500);
		setTitle("Add your booth");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		c = new JPanel(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		c.setPreferredSize(new Dimension(400, 300));
				
		title = new JLabel("Add a booth");
		title.setLocation(275, 40);
		title.setSize(300, 30);
		title.setFont(new Font("Arial", Font.BOLD, 35));
		
		submit = new JButton("Submit");
		submit.setLocation(320, 400);
		submit.setSize(100, 30);
		submit.addActionListener(this);
		
		name = new JLabel("Booth name: ");
		desc = new JLabel("Booth description:");
		popularity = new JLabel("Estimated visitor traffic (1-5):");
		boothType = new JLabel("Type of booth: ");
		
		Dimension labels = new Dimension(300, 20);
		name.setPreferredSize(labels);
		desc.setPreferredSize(labels);
		popularity.setPreferredSize(labels);
		boothType.setPreferredSize(labels);
	
		
		tname = new JTextField();
		tdesc = new JTextArea();
		npopularity = new JSlider(1, 5, 3);
		npopularity.setMajorTickSpacing(1);
		npopularity.setMinorTickSpacing(1);
		npopularity.setPaintLabels(isEnabled());
		
		
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
		
		add(title);
		add(submit);
		add(c);
		
		
		setVisible(true);
		
		// Thank you page
		p = new JPanel(new GridBagLayout());
		
		thankYou = new JLabel("Thank you for registering your booth!");
		boothPage = new JButton("Go to booth page");
		homePage = new JButton("Go to home page");
		
		g.gridy = 0;
		p.add(thankYou, g);
		g.gridy = 1;
		p.add(boothPage, g);
		g.gridy = 2;
		p.add(homePage, g);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			//Store as a Booth object
			
			
			// Show thank you page
			c.setVisible(false);
			submit.setVisible(false);
			title.setVisible(false);
			add(p);
			p.setVisible(true);
		} if (e.getSource() == boothPage) {
			
		} else if (e.getSource() == homePage) {
			setVisible(false);
    		OpeningPage f = new OpeningPage();
		}
	}
	
}
