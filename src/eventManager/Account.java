package eventManager;

public class Account {
	public String username;
	public String password;
	public int ID;
	
	/**
	 * Constructor for Account object
	 * @param username
	 * @param password
	 * @param ID
	 */
	public Account(String username, String password, int ID) {
		this.username = username;
		this.password = password;
		this.ID = ID;
	}
}