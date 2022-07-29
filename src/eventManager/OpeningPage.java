package eventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class OpeningPage
	implements ActionListener {

	// GUI Components
	private JPanel panel, buttonPane;
	private JLabel title, label;
	private JButton userEvent, userBooth, userVisitor, map;	
	private JFrame frame;
	
	/**
	 * constructor to initialize components
     * @param frame Main JFrame to be passed through all constructors
	 */
	public OpeningPage(JFrame frame)
	{
		//frame and panel
		this.frame = frame;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		
		//labels
		title = new JLabel("Event Manager");
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setAlignmentX(0.5f);
		title.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		panel.add(title);

		label = new JLabel("Hello, and welcome to Event Manager! ");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		label.setAlignmentX(0.5f);
		panel.add(label);
		
		label = new JLabel("This program enables the user to view and/or manage information");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		label.setAlignmentX(0.5f);
		panel.add(label);
		
		label = new JLabel("regarding the event booths. Below are all the available user roles: ");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setAlignmentX(0.5f);
		label.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		panel.add(label);
		
		label = new JLabel("Event organizer: manages all booth placement.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setAlignmentX(0.5f);
		panel.add(label);
		
		label = new JLabel("Booth holders: set up their booths with booth information.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setAlignmentX(0.5f);
		panel.add(label);
		
		label = new JLabel("Visitors: view an overview of event and layout of all event booths.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setAlignmentX(0.5f);
		label.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		panel.add(label);
		
		label = new JLabel("How would you like to proceed?");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setAlignmentX(0.5f);
		panel.add(label);
		
		//buttons
		userEvent = new JButton("Event Organizer");
		userEvent.setFont(new Font("Serif", Font.BOLD, 17));
		userEvent.addActionListener(this);
		
		userBooth = new JButton("Booth Holder");
		userBooth.setFont(new Font("Serif", Font.BOLD, 17));
		userBooth.addActionListener(this);
				
		userVisitor = new JButton("Event Visitor");
		userVisitor.setFont(new Font("Serif", Font.BOLD, 17));
		userVisitor.addActionListener(this);
		
		//panel to organize buttons in a horizontal line
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 50));
		buttonPane.add(userEvent);
		buttonPane.add(Box.createRigidArea(new Dimension(50, 0)));
		buttonPane.add(userBooth);
		buttonPane.add(Box.createRigidArea(new Dimension(50, 0)));
		buttonPane.add(userVisitor);
		this.frame.add(buttonPane, BorderLayout.CENTER);
		
		this.frame.add(panel, BorderLayout.NORTH);

		map = new JButton("Map");
		map.addActionListener(this);
		panel.add(map);
		map.setVisible(false);
	}

	/**
	 * determine what program does next depending on user type
	 * @param: e action performed by user 
	 */
	public void actionPerformed(ActionEvent e)
	{
		//hide panels
		panel.setVisible(false);
		buttonPane.setVisible(false);

		//event organizer page
		if (e.getSource() == userEvent) {
			SignIn s = new SignIn(true, this.frame);
		}
		
		//booth holder page
		else if (e.getSource() == userBooth) {
			SignIn s = new SignIn(false, this.frame);
		}
		
		//visitor page
		else if (e.getSource() == userVisitor) {
	        BoothView b = new BoothView(this.frame);
		}
	}
}