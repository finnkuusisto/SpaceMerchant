package kuusisto.finn.spacemerchant;
import java.util.ArrayList;
import java.util.List;

/**
 * The Location class represents a Location in a text-based game.  The Player
 * can buy and sell Commodities at Locations as well as complete Missions.
 * Each Location has a name, a description, a list of Commodities that it 
 * offers, a list of price ranges for each of its Commodities and a list of
 * current prices for all of its Commodities.
 * 
 * @author Finn Kuusisto
 */
public class Location {

	private List<Commodity> commodities;
	private List<Integer> prices;
	private List<Pair> priceRanges;
	private String name;
	private String description;
	
	/**
	 * Constructs a new Location with the given name and description.  A
	 * Location starts out with empty lists of Commodities, price ranges and
	 * prices.
	 * 
	 * @param name The name of this Location
	 * @param description The description of this Location
	 */
	public Location(String name, String description) {
		this.name = name;
		this.description = description;
		this.commodities = new ArrayList<Commodity>();
		this.prices = new ArrayList<Integer>();
		this.priceRanges = new ArrayList<Pair>();
	}
	
	/**
	 * Get the name of a Location.
	 * @return The name of this Location
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the description of a Location.
	 * @return The description of this Location
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Checks if the Player's current Mission is completed and updates the
	 * Mission and Player if so.  This should be called each time the Player
	 * travels to a Location.  If the Player's mission is completed (i.e. if
	 * the Location matches the location of the Player's current Mission and
	 * the Player has the required quantity of the required Commodity), it
	 * should print <tt>***Mission Completed***</tt> to the screen, mark the
	 * Mission as complete, remove the required quantity of the required
	 * Commodity from the Player's Ship, award the Player with the reward money
	 * and set the Player's Mission to the next Mission (if there is one) Use
	 * <tt>World.getNextMission</tt> to get the next Mission for the Player.
	 * @param player The Player
	 */
	public void checkMission(Player player) {
		Ship ship = player.getShip();
		Mission mission = player.getMission();
		Commodity commodity = mission.getCommodity();
		int quantity = mission.getQuantity();
		if (mission.getLocation().getName().equals(this.name) &&
			ship.getQuantity(commodity) >= quantity) {
			System.out.println("***Mission Completed***");
			System.out.println();
			mission.setComplete(true);
			ship.removeCommodity(commodity, quantity);
			player.addMoney(mission.getReward());
			Mission next = World.getNextMission();
			if (next != null) { player.setMission(next); }
		}
	}
	
	/**
	 * Adds a Commodity to the list of Commodities offered by a Location as
	 * well as a price range for which the Location should offer the Commodity.
	 * @param commodity The Commodity to add to this Location's offered 
	 * Commodities
	 * @param priceRange The price range for the added Commodity
	 */
	public void addCommodity(Commodity commodity, Pair priceRange) {
		this.commodities.add(commodity);
		this.priceRanges.add(priceRange);
	}
	
	/**
	 * Indicates whether or not a Location offers a particular Commodity.
	 * @param commodity The Commodity to look for in this Location
	 * @return true if this Location offers the Commodity, false otherwise
	 */
	public boolean hasCommodity(Commodity commodity) {
		return this.commodities.indexOf(commodity) >= 0;
	}
	
	/**
	 * Gets the current price of a particular Commodity at a Location.
	 * @param commodity The Commodity to find a current price
	 * @return The current price of the desired Commodity, or -1 if this
	 * Location does not offer the Commodity
	 */
	public int getPrice(Commodity commodity) {
		int index = this.commodities.indexOf(commodity);
		if (index >= 0) {
			return this.prices.get(index);
		}
		return -1;
	}
	
	/**
	 * Generates new prices for all of the Commodities offered by a Location.
	 * The prices generated must be in the price range for each Commodity.  Use
	 * <tt>World.getRandomInt</tt> to generate prices.
	 */
	public void drawCommodityPrices() {
		this.prices.clear();
		for (int i = 0; i < this.priceRanges.size(); i++) {
			Pair p = this.priceRanges.get(i);
			this.prices.add(World.getRandomInt(p.getVal1(), p.getVal2()));
		}
	}
	
	/**
	 * Get a human-readable list of the Commodities offered by a Location along
	 * with their current prices.  It should follow the convention:<br>
	 * <tt>[COMMODITIES]</tt><br>
	 * <tt>name - description: price</tt><br>
	 * <tt>name - description: price</tt><br>
	 * For example:<br>
	 * <tt>[COMMODITIES]</tt><br>
	 * <tt>Fuel - Everybody needs fuel to get around: 17</tt><br>
	 * <tt>Enriched Uranium - Don't touch it: 32</tt><br>
	 * <tt>Cow - Colonies need these for food: 15</tt><br>
	 * <tt>Scrap Metal - Probably good for something: 15</tt><br>
	 * @return The String as specified above
	 */
	public String getCommodityList() {
		StringBuilder str = new StringBuilder();
		str.append("[COMMODITIES]\n");
		for (int i = 0; i < this.commodities.size(); i++) {
			Commodity c = this.commodities.get(i);
			int price = this.prices.get(i);
			str.append(c.getName() + " - ");
			str.append(c.getDescription() + ": " + price);
			str.append("\n");
		}
		return str.toString();
	}
	
	/**
	 * Get a human-readable list of distances to all other Locations from a
	 * Location (use <tt>World.getLocations</tt> and <tt>World.getDistance</tt>
	 * .  It should follow the convention:<br>
	 * <tt>[DESTINATIONS]</tt><br>
	 * <tt>name: distance</tt><br>
	 * <tt>name: distance</tt><br>
	 * For example:<br>
	 * <tt>[DISTANCES]</tt><br>
	 * <tt>Space Station Alpha: 10</tt><br>
	 * <tt>Hoth: 8</tt><br>
	 * <tt>Arrakis: 12</tt><br>
	 * @return The String as specified above
	 */
	public String getDestinationList() {
		StringBuilder str = new StringBuilder();
		List<Location> locations = World.getLocations();
		str.append("[DESTINATIONS]\n");
		for (int i = 0; i < locations.size(); i++) {
			String otherName = locations.get(i).name;
			if (!this.name.equals(otherName)) {
				int distance = World.getDistance(this.name, otherName);
				str.append(otherName + ": " + distance);
				str.append("\n");
			}
		}
		return str.toString();
	}
	
	/**
	 * Converts a Location to a human-readable form.  It should follow the
	 * convention:<br>
	 * <tt>-name-</tt><br>
	 * <tt>description</tt><br>
	 * For example:<br>
	 * <tt>-Terra Prime-</tt><br>
	 * <tt>Nobody really liked Earth anyway</tt>
	 */
	public String toString() {
		return "-" + this.name + "-\n" + this.description;
	}
	
}
