package eventManager;

import javax.swing.JFrame;

public class OpeningDriver{

	/**
	 * creates new OpeningPage class
	 * @param: args console input
	 */
	public static void main(String[] args) throws Exception
	{
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("Event Organizer");
		mainFrame.setResizable(true);
		mainFrame.setBounds(0,0,1000,750);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		OpeningPage f = new OpeningPage(mainFrame);
	}
	
}