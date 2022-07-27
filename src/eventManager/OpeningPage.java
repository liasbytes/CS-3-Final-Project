package eventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class OpeningPage
	implements ActionListener {

	// Components
	private JPanel panel, buttonPane;
	private JLabel title, label;
	private JButton userEvent, userBooth, userVisitor, map;	
	private JFrame frame;
	
	/**
	 * constructor to initialize components
	 */
	public OpeningPage(JFrame frame)
	{
		this.frame = frame;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		title = new JLabel("Event Manager");
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setBorder(BorderFactory.createEmptyBorder(10,275,10,0));
		panel.add(title);

		label = new JLabel("Hello, and welcome to Event Manager! "
				+ "This program enables the user to view and/or");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
		panel.add(label);
		
		label = new JLabel("manage information regarding event booths. Below are the available user roles: ");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
		panel.add(label);
		
		label = new JLabel("Event organizer: manages all booth placement.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(15,30,0,0));
		panel.add(label);
		
		label = new JLabel("Booth holders: set up their booths with booth information.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(5,30,0,0));
		panel.add(label);
		
		label = new JLabel("Visitors: view an overview of event and layout of all event booths.");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(5,30,5,0));
		panel.add(label);
		
		label = new JLabel("How would you like to proceed?");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setBorder(BorderFactory.createEmptyBorder(10,30,0,0));
		panel.add(label);
		
		userEvent = new JButton("Event Organizer");
		userEvent.setFont(new Font("Serif", Font.BOLD, 17));
		userEvent.addActionListener(this);
		panel.add(userEvent);
		
		userBooth = new JButton("Booth Holder");
		userBooth.setFont(new Font("Serif", Font.BOLD, 17));
		userBooth.addActionListener(this);
		panel.add(userBooth);
				
		userVisitor = new JButton("Event Visitor");
		userVisitor.setFont(new Font("Serif", Font.BOLD, 17));
		userVisitor.addActionListener(this);
		panel.add(userVisitor);
		
		buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 120, 110));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(userEvent);
		buttonPane.add(Box.createRigidArea(new Dimension(50, 0)));
		buttonPane.add(userBooth);
		buttonPane.add(Box.createRigidArea(new Dimension(50, 0)));
		buttonPane.add(userVisitor);
		this.frame.add(buttonPane, BorderLayout.PAGE_END);
		
		this.frame.add(panel);

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
		panel.setVisible(false);
		buttonPane.setVisible(false);

		if (e.getSource() == userEvent) {
			SignIn s = new SignIn(true, this.frame);
			}
		else if (e.getSource() == userBooth) {
			SignIn s = new SignIn(false, this.frame);
			}
		else if (e.getSource() == userVisitor) {
	        BoothView b = new BoothView(Test.getTestBooths(), this.frame);
		}
	}
}