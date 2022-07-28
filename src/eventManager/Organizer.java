package eventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Organizer
	implements ActionListener {

	// Components
	private static JPanel panel, deletePanel, buttonPane;
	private static JLabel title, label;
	private JButton deleteBooth, addBooth;
	private static JCheckBox booth;	
	private JFrame frame;
	
	/**
	 * constructor to initialize components
	 */
	public Organizer(JFrame frame)
	{
		this.frame = frame;
		
		deletePanel = new JPanel(new GridBagLayout());
		deletePanel.setLayout(new javax.swing.BoxLayout(deletePanel, javax.swing.BoxLayout.Y_AXIS));
		deletePanel.setPreferredSize(new Dimension(frame.getX()/2, frame.getY()*3/5));
		GridBagConstraints g = new GridBagConstraints();

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
		
		addBooth = new JButton("Add a booth");
		addBooth.setFont(new Font("Serif", Font.BOLD, 17));
		addBooth.addActionListener(this);
		panel.add(addBooth);

		/*
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 50));
		buttonPane.add(deleteBooth);
		buttonPane.add(Box.createRigidArea(new Dimension(50, 0)));
		buttonPane.add(addBooth);
		panel.add(buttonPane, BorderLayout.CENTER);
		*/
		
		frame.add(panel);
		frame.setVisible(true);
	}

	public static void deleteBooth(Booth[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
			String tempName = data[i][j].getName();
			System.out.println(tempName);
			booth = new JCheckBox(tempName);
			booth.setFont(new Font("Serif", Font.PLAIN, 17));
			booth.setBorder(BorderFactory.createEmptyBorder(10,15,0,0));
			deletePanel.add(booth);
			}
		}
	}
	
	/**
	 * determine what program does next depending on user type
	 * @param: e action performed by user 
	 */
	public void actionPerformed(ActionEvent e)
	{
	
		if (e.getSource() == deleteBooth) {
			frame.add(deletePanel);
			panel.setVisible(false);
			deleteBooth(Test.getTestBooths());
		} else if (e.getSource() == addBooth) {
			panel.setVisible(false);
			BoothAddPage b = new BoothAddPage(1, this.frame);
		}
	}
}