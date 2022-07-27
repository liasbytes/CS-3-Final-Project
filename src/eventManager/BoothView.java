package eventManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoothView
		extends JFrame
		implements ActionListener {

		// GUI Components
		private JPanel titlePanel, buttonPanel, boothPanel;
		private JLabel title;
		private JButton backButton;	
		
		public static void main(String[] args) {
			new BoothView(Test.getTestBooths());
		}
		
		/**
		 * Constructor to initialize GUI components
		 * 
		 * @param data The array of booths to display
		 */
		public BoothView(Booth[][] data)
		{
			setTitle("Booth Map");
			setBounds(250, 150, 1250, 1000);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			titlePanel = new JPanel();
			title = new JLabel("Booth Map");
			title.setFont(new Font("Serif", Font.BOLD, 30));
//			title.setBorder(BorderFactory.createEmptyBorder(10,275,10,0));
			titlePanel.add(title);
			add(titlePanel, BorderLayout.NORTH);
			
			boothPanel = new JPanel(new GridLayout(data.length, data[0].length));
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[i].length; j++) {
					Dimension size =  new Dimension(this.getWidth()/data.length,(this.getHeight()-30)/data[i].length);
					boothPanel.add(new BoothDisplay(data[i][j], size));
				}
			}
			add(boothPanel, BorderLayout.CENTER);
			
			buttonPanel = new JPanel();
			backButton = new JButton("Back to Main Menu");
			backButton.setFont(new Font("Serif", Font.BOLD, 17));
			backButton.addActionListener(this);
			backButton.setPreferredSize(new Dimension(200,30));
			buttonPanel.add(backButton);
			
			add(buttonPanel, BorderLayout.SOUTH);
			setVisible(true);
		}

		/**
		 * determine what program does next depending on user type
		 * @param: e action performed by user 
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == backButton) {
	            setVisible(false);
	            OpeningPage f = new OpeningPage();
	        }
		}
}