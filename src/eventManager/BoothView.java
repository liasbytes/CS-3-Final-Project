package eventManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoothView
		implements ActionListener {

		// GUI Components
		private JPanel mainPanel, buttonPanel, boothPanel;
		private JLabel title;
		private JButton backButton;	
		private JFrame frame;
		
		
		/**
		 * Constructor to initialize GUI components
		 * 
		 * @param data The array of booths to display
		 */
		public BoothView(Booth[][] data, JFrame frame)
		{
			this.frame = frame;
			
			mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
			title = new JLabel("Booth Map");
			title.setFont(new Font("Serif", Font.BOLD, 30));
			title.setAlignmentX(0.5f);
			mainPanel.add(title);
			
			boothPanel = new JPanel(new GridLayout(data.length, data[0].length));
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[i].length; j++) {
					Dimension size =  new Dimension(this.frame.getWidth()/data.length,(this.frame.getHeight()-100)/data[i].length);
					boothPanel.add(new BoothDisplay(data[i][j], size));
				}
			}
			mainPanel.add(boothPanel);
			
			buttonPanel = new JPanel();
			backButton = new JButton("Back to Main Menu");
			backButton.setFont(new Font("Serif", Font.BOLD, 17));
			backButton.addActionListener(this);
			backButton.setPreferredSize(new Dimension(200,30));
			buttonPanel.add(backButton);
			mainPanel.add(buttonPanel);
			
			this.frame.add(mainPanel, BorderLayout.CENTER);
		}

		/**
		 * determine what program does next depending on user type
		 * @param: e action performed by user 
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == backButton) {
	            mainPanel.setVisible(false);
	            boothPanel.setVisible(false);
	            buttonPanel.setVisible(false);
	            OpeningPage f = new OpeningPage(this.frame);
	        }
		}
}