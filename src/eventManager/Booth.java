package eventManager;

/**
 * Stores information about a booth
 */
public class Booth {
	
	public enum BoothType {
		  FOOD,
		  DRINK,
		  ACTIVITY,
		  PRODUCTS,
		  UNAVAILABLE
		}
	
	// Instance variables
	private String name;
	private String description;
	private int popularity;
	private BoothType boothType;
	private int boothID;
	
	/**
	 * Constructor method for Booth object if unavailable
	 */
	public Booth() {
		this.name = "Unavailable";
		this.description = "This booth is not available.";
		this.popularity = 0;
		this.boothType = BoothType.UNAVAILABLE;
		this.boothID = -1;
	}
	
	/**
	 * Constructor method for Booth object
	 * @param name Name of booth
	 * @param desc Description of booth
	 * @param pop Estimated popularity of booth
	 * @param boothType Type of booth
	 * @param boothID ID of the booth
	 */
	public Booth(String name, String desc, int pop, BoothType boothType, int boothID ) {
		this.name = name;
		this.description = desc;
		this.popularity = pop;
		this.boothType = boothType;
		this.boothID = boothID;
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
	public BoothType getBoothType() {
		return this.boothType;
	}
	
	/**
	 * BoothID accessor
	 * @return ID of booth
	 */
	public int getBoothID() {
		return this.boothID;
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
	 * BoothID modifier
	 * @param b New booth ID
	 */
	public void setBoothID(int b) {
		this.boothID = b;
	}
	
	/**
	 * Textual representation of Booth object
	 * @return a String with all Booth information
	 */
	public String toString() {
		return this.name + ": " 
				+ this.description + " - " 
				+ this.popularity + " - " 
				+ this.boothType + "ID:" 
				+ this.boothID;
	}
}
