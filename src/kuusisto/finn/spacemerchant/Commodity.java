package kuusisto.finn.spacemerchant;
/**
 * The Commodity class represents a commodity that can be bought and sold in a
 * text-based game.  Each Commodity has a name and a description.
 * 
 * @author Finn Kuusisto
 */
public class Commodity {

	private String name;
	private String description;
	
	/**
	 * Construct a Commodity with the specified name and description.
	 * @param name The name of this Commodity
	 * @param description The description of this Commodity
	 */
	public Commodity(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Gets the name of a Commodity.
	 * @return The name of this Commodity
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the description of a Commodity.
	 * @return The description of this Commodity
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Determines whether two Commodities are the same Commodity.  It simply
	 * matches them by name.
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Commodity)) { return false; }
		Commodity other = (Commodity)o;
		return this.name.equals(other.name);
	}
	
}
