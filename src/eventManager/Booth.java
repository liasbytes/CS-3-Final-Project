package eventManager;

public class Booth {
	
	public enum BoothType {
		  FOOD,
		  DRINK,
		  ACTIVITY,
		  PRODUCTS
		}
	
	// Instance variables
	private String name;
	private String description;
	private int popularity;
	private BoothType boothType;
	
	/**
	 * Constructor method for Booth object
	 * @param name Name of booth
	 * @param desc Description of booth
	 * @param pop Estimated popularity of booth
	 * @param boothType Type of booth
	 */
	public Booth(String name, String desc, int pop, BoothType boothType ) {
		this.name = name;
		this.description = desc;
		this.popularity = pop;
		this.boothType = boothType;
	}
	
	// Getter methods
	
	/**
	 * Name accessor
	 * @return Name of booth
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Description accessor
	 * @return Description of booth
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Popularity accessor
	 * @return Popularity of booth
	 */
	public int getPopularity() {
		return this.popularity;
	}
	
	/**
	 * BoothType accessor
	 * @return Type of booth
	 */
	public BoothType boothType() {
		return this.boothType;
	}
	
	// Setter methods
	
	/**
	 * Name modifier
	 * @param n New name
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Description modifier
	 * @param d New description
	 */
	public void setDescription(String d) {
		this.description = d;
	}
	
	/**
	 * Popularity modifier
	 * @param p New popularity
	 */
	public void setPopularity(int p) {
		this.popularity = p;
	}
	
	/**
	 * BoothType modifier
	 * @param b New booth type
	 */
	public void setBoothType(BoothType b) {
		this.boothType = b;
	}
	
	/**
	 * Textual representation of Booth object
	 * @return a String with all Booth information
	 */
	public String toString() {
		return this.name + ": " 
				+ this.description + "/n" 
				+ this.popularity + " - " 
				+ this.boothType;
	}
}
