package kuusisto.finn.spacemerchant;
/**
 * The Player class represents the Player in a text-based game.  A Player has a
 * current Location, a Ship, a current Mission and money.
 * 
 * @author Finn Kuusisto
 */
public class Player {

	private Location location;
	private Ship ship;
	private Mission mission;
	private int money;
	
	/**
	 * Construct a Player with the specified starting Location, starting money,
	 * the cargo capacity and fuel capacity of the Player's Ship.
	 * @param location The Player's starting Location
	 * @param money The Player's starting money
	 * @param shipCargoCapacity The cargo capacity of the Player's Ship
	 * @param shipFuelCapacity The fuel capacity of the Player's Ship
	 */
	public Player(Location location, int money, int shipCargoCapacity,
		int shipFuelCapacity) {
		this.location = location;
		this.location.drawCommodityPrices();
		this.money = money;
		this.ship = new Ship(shipCargoCapacity, shipFuelCapacity);
	}
	
	/**
	 * Get the Location of the Player.
	 * @return This Player's Location
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Get the Player's count of money.
	 * @return This Player's count of money
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Add to the Player's money.
	 * @param money The amount of money to add to this Player's money
	 */
	public void addMoney(int money) {
		this.money += money;
	}
	
	/**
	 * Get the Player's Ship.
	 * @return This Player's Ship
	 */
	public Ship getShip() {
		return this.ship;
	}
	
	/**
	 * Get the Player's current Mission.
	 * @return This Player's current Mission
	 */
	public Mission getMission() {
		return this.mission;
	}
	
	/**
	 * Set the Player's current Mission.  It should also print the 
	 * human-readable form of the new Mission to the screen.
	 * @param mission This Player's new current Mission
	 */
	public void setMission(Mission mission) {
		System.out.println(mission);
		System.out.println();
		this.mission = mission;
	}

	/**
	 * Prints the human-readable form of the Player's Location to the screen.
	 */
	public void describeLocation() {
		System.out.println(this.location);
		System.out.println();
	}

	/**
	 * Request that the Player travel to the named Location.  The fuel required
	 * to travel to any Location is equal to the distance to that Location.  If
	 * the Location doesn't exist, the Player should say "I don't know where 
	 * location_name is.".  If the Player doesn't have enough fuel to travel to 
	 * the Location (use <tt>World.getDistance</tt> to determine the distance 
	 * to the Location), the Player should say "I don't have enough fuel to get
	 * to location_name."  Otherwise, the Player's Location should be changed,
	 * the proper amount of fuel should be removed from the Player's Ship,
	 * prices at the Player's new Location should be randomly drawn 
	 * (<tt>location.drawCommodityPrices</tt>) and the Player's current Mission
	 * should be checked (<tt>location.checkMission</tt>).
	 * @param name The name of the desired Location
	 */
	public void travel(String name) {
		int distance = World.getDistance(this.location.getName(), name);
		if (distance == -1) {
			System.out.println("I don't know where " + name + " is.");
			System.out.println();
		}
		else if (distance > this.ship.getFuel()) {
			System.out.println("I don't have enough fuel to get to " + name +
					".");
			System.out.println();
		}
		else {
			this.ship.removeFuel(distance);
			this.location = World.getLocation(name);
			this.location.drawCommodityPrices();
			this.describeLocation();
			this.location.checkMission(this);
		}
	}
	
	/**
	 * Request that the Player buy a specified quantity of a named Commodity at
	 * their current Location.  If the Player's current Location doesn't have
	 * the desired Commodity, the Player should say "I can't buy commodity_name
	 * here."  If the Player can't afford the specified quantity or the
	 * quantity is negative, the Player should say  "I can't buy quantity 
	 * commodity_name."  Otherwise, it will have to check if the Player has
	 * space for the Commodity.  <i>Fuel is a special Commodity</i>.  If the
	 * desired Commodity is Fuel, it must check the Player's Ship's fuel
	 * capacity.  If the Ship doesn't have enough space for the desired
	 * quantity of fuel, the Player should say "I can't store that much Fuel."
	 * Otherwise, the desired quantity should be added to Ship's fuel, the
	 * Player's money should be decremented appropriately and the Player should
	 * say "I bought quantity Fuel."  For all other Commodities, it must check
	 * the Player's Ship's cargo capacity.  If the Player doesn't have enough
	 * cargo space, the Player should say "I can't store that much cargo."
	 * Otherwise, the desired quantity of the Commodity should be added to the
	 * Ship's cargo, the Player's money should be decremented appropriately and
	 * the Player should say "I bought quantity commodity_name." <i>You can get
	 * a Commodity object by name by calling <tt>World.getCommodity</tt></i>.
	 * @param name The name of the desired Commodity to buy
	 * @param quantity The quantity of the desired Commodity to buy
	 */
	public void buy(String name, int quantity) {
		Commodity commodity = World.getCommodity(name);
		if (!this.location.hasCommodity(commodity)) {
			System.out.println("I can't buy " + name + " here.");
		}
		else if (this.location.getPrice(commodity) * quantity > this.money ||
				quantity < 0) {
			System.out.println("I can't buy " + quantity + " " + name + ".");
		}
		else if (name.equals("Fuel")) {
			if (this.ship.getFuel() + quantity > this.ship.getFuelCapacity()) {
				System.out.println("I can't store that much Fuel.");
			}
			else {
				this.money -= this.location.getPrice(commodity) * quantity;
				this.ship.addFuel(quantity);
				System.out.println("I bought " + quantity + " Fuel.");
			}
		}
		else {
			if (this.ship.getTotalCargoQuantity() + quantity > 
				this.ship.getCargoCapacity()) {
				System.out.println("I can't store that much cargo.");
			}
			else {
				this.money -= this.location.getPrice(commodity) * quantity;
				this.ship.addCommodity(commodity, quantity);
				System.out.println("I bought " + quantity + " " + name + ".");
			}
		}
		System.out.println();
	}

	/**
	 * Request that the Player sell a specified quantity of a named Commodity
	 * at their current Location.  If the Player's current Location doesn't 
	 * have the desired Commodity, the Player should say "I can't sell 
	 * commodity_name here."  Otherwise, it will have to check if the Player
	 * has the desired quantity to sell.  <i>Fuel is a special Commodity</i>.
	 * If the desired Commodity to sell is Fuel, it must check the Player's
	 * Ship's fuel.  If the Ship doesn't have enough fuel to sell, the Player
	 * should say "I can't sell quantity Fuel."  Otherwise, the desired 
	 * quantity should be removed from the Ship's fuel, the Player's money
	 * should be incremented accordingly and the Player should say "I sold
	 * quantity Fuel."  For other Commodities, it must check the Ship's stored
	 * quantity of the Commodity in cargo.  If the Ship doesn't have enough of
	 * the Commodity to sell, the Player should say "I can't sell quantity
	 * commodity_name."  Otherwise, the desired quantity should be removed from
	 * the Ship's cargo, the Player's money should be incremented appropriately
	 * and the Player should say "I sold quantity commodity_name."  <i>You can
	 * get a Commodity object by name by calling
	 * <tt>World.getCommodity</tt></i>.
	 * @param name The name of the desired Commodity to sell
	 * @param quantity The quantity of the desired Commodity to sell
	 */
	public void sell(String name, int quantity) {
		Commodity commodity = World.getCommodity(name);
		if (!this.location.hasCommodity(commodity)) {
			System.out.println("I can't sell " + name + " here.");
		}
		else if (name.equals("Fuel")) {
			if (quantity > this.ship.getFuel() || quantity < 0) {
				System.out.println("I can't sell " + quantity + " " + name +
						".");
			}
			else {
				this.money += this.location.getPrice(commodity) * quantity;
				this.ship.removeFuel(quantity);
				System.out.println("I sold " + quantity + " Fuel.");
			}
		}
		else if (quantity > this.ship.getQuantity(commodity) ||
			quantity < 0) {
			System.out.println("I can't sell " + quantity + " " + name + ".");
		}
		else {
			this.money += this.location.getPrice(commodity) * quantity;
			this.ship.removeCommodity(commodity, quantity);
			System.out.println("I sold " + quantity + " " + name + ".");
		}
		System.out.println();
	}

	/**
	 * Request that the Player list a named set of information.  If the desired
	 * information is "commodities", the Player should print their current
	 * Location's commodity list (the list with available Commodities and
	 * prices).  If the desired information is "destinations", the Player
	 * should print their current Location's destinations list (the list with
	 * distances to all other Locations).  If the desired information is
	 * "status", the Player should print out information about their current
	 * status, including their current Location, Mission, money and Ship
	 * information.  It should follow the convention:<br>
	 * <tt>[Status]</tt><br>
	 * <tt>Location: location_name</tt><br>
	 * <tt>Mission: title - description</tt><br>
	 * <tt>Money: money</tt><br>
	 * <tt>Ship:</tt><br>
	 * <tt>Fuel Capacity - fuel_capacity</tt><br>
	 * <tt>Fuel - fuel</tt><br>
	 * <tt>Cargo Capacity - cargo_capacity</tt><br>
	 * <tt>Total Cargo Quantity - total_cargo_quantity</tt><br>
	 * <tt>Inventory -</tt><br>
	 * <tt>&nbsp;&nbsp;commodity_name: quantity</tt><br>
	 * <tt>&nbsp;&nbsp;commodity_name: quantity</tt><br>
	 * For example:<br>
	 * <tt>[Status]</tt><br>
	 * <tt>Location: Terra Prime</tt><br>
	 * <tt>Mission: Getting Started - You've heard word that someone on Terra
	 * Prime wants Spice.</tt><br>
	 * <tt>Money: 250</tt><br>
	 * <tt>Ship:</tt><br>
	 * <tt>Fuel Capacity - 100</tt><br>
	 * <tt>Fuel - 100</tt><br>
	 * <tt>Cargo Capacity - 1000</tt><br>
	 * <tt>Total Cargo Quantity - 650</tt><br>
	 * <tt>Inventory -</tt><br>
	 * <tt>&nbsp;&nbsp;Cow: 600</tt><br>
	 * <tt>&nbsp;&nbsp;Scrap Metal: 50</tt><br>
	 * If the desired information is "commands" it should print out the
	 * commands available to the user.  It should look like:<br>
	 * <tt>[COMMANDS]</tt><br>
	 * <tt>travel [destination]</tt><br>
	 * <tt>&nbsp;&nbsp;&nbsp;-to travel to the specified destination</tt><br>
	 * <tt>buy [quantity] [commodity]</tt><br>
	 * <tt>&nbsp;&nbsp;&nbsp;-to buy the specified commodity</tt><br>
	 * <tt>sell [quantity] [commodity]</tt><br>
	 * <tt>&nbsp;&nbsp;&nbsp;-to sell the specified commodity</tt><br>
	 * <tt>list [commodities|destinations|status|commands]</tt><br>
	 * <tt>&nbsp;&nbsp;&nbsp;-to list information:</tt><br>
	 * <tt>&nbsp;&nbsp;&nbsp;-"commodities" to see the commodities available at
	 * the current location and their prices</tt><br>
	 * <tt>&nbsp;&nbsp;&nbsp;-"destinations" to see the travel distances from
	 * the current location</tt><br>
	 * <tt>&nbsp;&nbsp;&nbsp;-"status" to see information about the player's
	 * location, mission, money and ship</tt><br>
	 * <tt>&nbsp;&nbsp;&nbsp;-"commands" to see these commands</tt><br>
	 * <tt>quit</tt><br>
	 * <tt>&nbsp;&nbsp;&nbsp;-to quit without finishing</tt><br>
	 * If the desired information is anything other than "commodities",
	 * "destinations", "status" or "commands", the Player should say "I don't
	 * know how to list information_name."
	 * @param name The name of the desired information to list
	 */
	public void list(String name) {
		if (name.equals("commodities")) {
			System.out.println(this.location.getCommodityList());
		}
		else if (name.equals("destinations")) {
			System.out.println(this.location.getDestinationList());
		}
		else if (name.equals("status")){
			StringBuilder str = new StringBuilder();
			str.append("[STATUS]\n");
			str.append("Location: " + this.location.getName() + "\n");
			str.append("Mission: " + this.mission.getTitle() + " - " +
				this.mission.getDescription() + "\n");
			str.append("Money: " + this.money + "\n");
			str.append("Ship:\n");
			str.append(this.ship.toString());
			System.out.println(str.toString());
		}
		else if (name.equals("commands")) {
			System.out.println("[COMMANDS]");
			System.out.println("travel [destination]");
			System.out.println("   -to travel to the specified destination");
			System.out.println("buy [quantity] [commodity]");
			System.out.println("   -to buy the specified commodity");
			System.out.println("sell [quantity] [commodity]");
			System.out.println("   -to sell the specified commodity");
			System.out.println("list [commodities|destinations|status|" + 
				"commands]");
			System.out.println("   -to list information:");
			System.out.println("   -\"commodities\" to see the commodities " +
				"available at the current location and their prices");
			System.out.println("   -\"destinations\" to see the travel " +
				"distances from the current location");
			System.out.println("   -\"status\" to see information about the " +
				"player's location, mission, money and ship");
			System.out.println("   -\"commands\" to see these commands");
			System.out.println("quit\n   -to quit without finishing\n");
		}
		else {
			System.out.println("I don't know how to list " + name + ".");
			System.out.println();
		}
	}
	
}
