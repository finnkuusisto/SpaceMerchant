package kuusisto.finn.spacemerchant;
/**
 * The Mission class represents a single Mission in a text-based game.  The
 * goal of each Mission is to bring some quantity of a particular Commodity to
 * a particular Location.  There is also a monetary reward for completing a
 * Mission.  Each Mission has a title, description, a location at which it is
 * to be completed, a Commodity associated with the Mission, a quantity of that
 * Commodity, a monetary reward and a way to track when the Mission has been
 * completed.
 * 
 * @author Finn Kuusisto
 */
public class Mission {

	private String title;
	private String description;
	private Location location;
	private Commodity commodity;
	private int quantity;
	private int reward;
	private boolean complete;
	
	/**
	 * Constructs a new Mission with the specified parameters.
	 * @param title The title of the Mission
	 * @param description The description of the Mission
	 * @param location The location at which the Mission is completed
	 * @param commodity The Commodity required by the Mission
	 * @param quantity The quantity of the Commodity required
	 * @param reward The monetary reward given when the Mission is completed
	 */
	public Mission(String title, String description, Location location,
		Commodity commodity, int quantity, int reward) {
		this.title = title;
		this.description = description;
		this.location = location;
		this.commodity = commodity;
		this.quantity = quantity;
		this.reward = reward;
		this.complete = false;
	}
	
	/**
	 * Gets the title of a Mission.
	 * @return The title of this Mission
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Gets the description of a Mission.
	 * @return The description of this Mission
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Indicates whether a Mission has been completed.
	 * @return true if this Mission has been completed, false otherwise
	 */
	public boolean isComplete() {
		return this.complete;
	}
	
	/**
	 * Marks a Mission as complete or incomplete.
	 * @param complete The value to mark the Mission
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	/**
	 * Gets the Location at which a Mission is completed.
	 * @return The Location at which this Mission is completed
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Gets the Commodity required by a Mission.
	 * @return The Commodity required by this Mission
	 */
	public Commodity getCommodity() {
		return this.commodity;
	}
	
	/**
	 * Gets the quantity of the required Commodity that is required to complete
	 * a Mission.
	 * @return The quantity of the Commodity required by this Mission
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Gets the amount of money that is rewarded to the Player when a Mission
	 * is completed.
	 * @return The monetary reward for completing this Mission
	 */
	public int getReward() {
		return this.reward;
	}
	
	/**
	 * Converts a Mission to a human-readable form. It should follow the
	 * convention:<br>
	 * <tt>Mission: title</tt><br>
	 * <tt>description</tt><br>
	 * For example:<br>
	 * <tt>Mission: Getting Started</tt><br>
	 * <tt>You've heard word that someone on Terra Prime wants Spice.</tt>
	 */
	public String toString() {
		return "Mission: " + this.title + "\n" + this.description;
	}
	
}
