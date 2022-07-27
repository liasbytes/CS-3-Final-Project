package eventManager;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class BoothView
		extends JFrame
		implements ActionListener {

		// GUI Components
		private JPanel panel, buttonPane;
		private JLabel title, label;
		private JButton backButton;	
		
		
		public static void boothView (ArrayList[][] data) {
			
			for (int r = 0; r < data.length; r++) {
				for (int c = 0; c < data[r].length; c++) {
					//call booth display to create booths
				}
			}
		}
		
		
		/**
		 * constructor to initialize GUI components
		 */
		public BoothView()
		{
			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			setTitle("Booth Map");
			setBounds(250, 150, 750, 500);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			title = new JLabel("Booth Map");
			title.setFont(new Font("Serif", Font.BOLD, 30));
			title.setBorder(BorderFactory.createEmptyBorder(10,275,10,0));
			panel.add(title);
			
			backButton = new JButton("Back to Main Menu");
			backButton.setFont(new Font("Serif", Font.BOLD, 17));
			backButton.addActionListener(this);
			panel.add(backButton);
			
			add(panel);
			setVisible(true);
		}

		/**
		 * determine what program does next depending on user type
		 * @param: e action performed by user 
		 */
		public void actionPerformed(ActionEvent e)
		{
			panel.setVisible(false);
			buttonPane.setVisible(false);

			if (e.getSource() == backButton) {
	            setVisible(false);
	            OpeningPage f = new OpeningPage();
	        }
		}
}