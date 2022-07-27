package eventManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class BoothDisplay extends JPanel{

	private JLabel name;
	private JTextArea description;
	private JLabel boothType;
	
	/**
	 * Creates a new BoothDisplay object, which acts as a JPanel and can be added as a component to another panel or frame.
	 * @param b The booth to be displayed
	 * @param d The size of the panel.
	 */
	public BoothDisplay(Booth b, Dimension d) {
		super();
		
		Font bold = new Font("Arial", Font.BOLD, 15);
		Font normal = new Font("Arial", Font.PLAIN, 15);
		Font italic = new Font("Arial", Font.ITALIC, 15);
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		Color borderColor = Color.black;
		this.setPreferredSize(d);
		
		if (b == null) {
			
		} else if (b.boothType() == Booth.BoothType.UNAVAILABLE) {
			name = new JLabel("Unavailable Spot");
			name.setFont(bold);
			name.setAlignmentX(0);
			name.setPreferredSize(new Dimension(d.width,d.height/5));
			this.add(name);
			borderColor = Color.GRAY;
		} else {
			name = new JLabel(b.getName());
			name.setFont(bold);
			name.setAlignmentX(0);
			name.setPreferredSize(new Dimension(d.width,30));
			this.add(name);
			
			description = new JTextArea(b.getDescription());
			description.setFont(italic);
			description.setAlignmentX(0);
			description.setMaximumSize(new Dimension(d.width,d.height-55));
			description.setLineWrap(true);
			description.setEditable(false);
			this.add(description);
			
			String type = b.boothType().toString().toLowerCase();
			char[] chars = type.toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			type = new String(chars);
			boothType = new JLabel(type);
			boothType.setFont(italic);
			boothType.setAlignmentX(0);
			boothType.setPreferredSize(new Dimension(d.width,25));
			this.add(boothType);
			
			if (b.boothType() == Booth.BoothType.FOOD) {
				borderColor = new Color(244,191,191);
			} else if (b.boothType() == Booth.BoothType.DRINK) {
				borderColor = new Color(255,217,192);
			} else if (b.boothType() == Booth.BoothType.ACTIVITY) {
				borderColor = new Color(255,248,100);
			} else if (b.boothType() == Booth.BoothType.PRODUCTS) {
				borderColor = new Color(140,192,222);
			}
		}
		
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColor, 5), BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setBorder(border);
	}
}
