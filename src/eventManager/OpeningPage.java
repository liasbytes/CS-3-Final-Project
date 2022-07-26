package eventManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class OpeningPage
	extends JFrame
	implements ActionListener {

	// Components
	private Container c;
	private JLabel title;
	private JLabel label;
	private JTextField iD;
	private JButton userEvent;
	private JButton userBooth;
	private JButton userVisitor;
	private JButton map;
	
	
	/**
	 * constructor to initialize components
	 */
	public OpeningPage()
	{
		setTitle("Event Manager");
		setBounds(250, 150, 750, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		c = getContentPane();
		c.setLayout(null);

		title = new JLabel("Event Manager");
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setSize(300, 50);
		title.setLocation(275, 20);
		c.add(title);

		label = new JLabel("Hello, welcome to Event Manager! This program enables the user");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setSize(575, 25);
		label.setLocation(100, 80);
		c.add(label);
		
		label = new JLabel("to view and/or manage information regarding event booths.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setSize(575, 25);
		label.setLocation(100, 105);
		c.add(label);
		
		label = new JLabel("Event organizer: manages all booth placement.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setSize(575, 25);
		label.setLocation(100, 150);
		c.add(label);
		
		label = new JLabel("Booth holders: set up their booths with booth information.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setSize(575, 25);
		label.setLocation(100, 180);
		c.add(label);
		
		label = new JLabel("Visitors: view an overview of event and layout of all event booths.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setSize(575, 25);
		label.setLocation(100, 210);
		c.add(label);
		
		label = new JLabel("How would you like to proceed?");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setSize(450, 25);
		label.setLocation(100, 250);
		c.add(label);

		userEvent = new JButton("Event Organizer");
		userEvent.setFont(new Font("Serif", Font.BOLD, 17));
		userEvent.setSize(170, 30);
		userEvent.setLocation(100, 300);
		userEvent.addActionListener(this);
		c.add(userEvent);
		

		userBooth = new JButton("Booth Holder");
		userBooth.setFont(new Font("Serif", Font.BOLD, 17));
		userBooth.setSize(170, 30);
		userBooth.setLocation(300, 300);
		userBooth.addActionListener(this);
		c.add(userBooth);
				
		userVisitor = new JButton("Event Visitor");
		userVisitor.setFont(new Font("Serif", Font.BOLD, 17));
		userVisitor.setSize(170, 30);
		userVisitor.setLocation(500, 300);
		userVisitor.addActionListener(this);
		c.add(userVisitor);
		
		setVisible(true);

		map = new JButton("Map");
		map.setSize(635, 350);
		map.setLocation(50, 80);
		map.addActionListener(this);
		c.add(map);
		map.setVisible(false);
	}

	/**
	 * determine what program does next depending on user type
	 * @param: e action performed by user 
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == userEvent) {
		
			}
		else if (e.getSource() == userVisitor) {
			map.setVisible(true);
			userEvent.setVisible(false);
			userBooth.setVisible(false);
			userVisitor.setVisible(false);
		}
	}
}