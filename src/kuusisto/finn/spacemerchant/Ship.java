package kuusisto.finn.spacemerchant;
import java.util.ArrayList;
import java.util.List;

/**
 * The Ship class represents a Ship used for travel in a text-based game.  Each
 * ship has a list of Commodities that it is carrying along with a list of
 * their quantities, a maximum cargo capacity, a current amount of fuel and a 
 * maximum capacity of fuel.
 * 
 * @author Finn Kuusisto
 */
public class Ship {

	private List<Commodity> commodities;
	private List<Integer> quantities;
	private int cargoCapacity;
	private int fuelCapacity;
	private int fuel;
	
	/**
	 * Construct a new Ship with the specified maximum cargo capacity and
	 * maximum fuel capacity.  A Ship starts with no Commodities in cargo and
	 * a full tank of fuel.  <i>Fuel does not count as cargo</i>.
	 * @param cargoCapacity The maximum cargo capacity of this Ship
	 * @param fuelCapacity The maximum fuel capacity of this Ship
	 */
	public Ship(int cargoCapacity, int fuelCapacity) {
		this.cargoCapacity = cargoCapacity;
		this.fuelCapacity = fuelCapacity;
		this.fuel = fuelCapacity;
		this.commodities = new ArrayList<Commodity>();
		this.quantities = new ArrayList<Integer>();
	}
	
	/**
	 * Gets the cargo capacity of a Ship.
	 * @return The maximum cargo capacity of this Ship
	 */
	public int getCargoCapacity() {
		return this.cargoCapacity;
	}
	
	/**
	 * Gets the total quantity of cargo currently carried by a Ship.  <i>Fuel
	 * does not count as cargo</i>.
	 * @return The total quantity of cargo currently carried by this Ship
	 */
	public int getTotalCargoQuantity() {
		int quantity = 0;
		for (int i = 0; i < this.quantities.size(); i++) {
			quantity += quantities.get(i);
		}
		return quantity;
	}
	
	/**
	 * Gets the quantity of a particular Commodity carried by a Ship.
	 * @param commodity The Commodity to find the quantity
	 * @return The quantity of the specified Commodity carried by this Ship, 0
	 * if the Commodity is not in this Ship's list of Commodities
	 */
	public int getQuantity(Commodity commodity) {
		int index = this.commodities.indexOf(commodity);
		if (index >= 0) {
			return this.quantities.get(index);
		}
		return 0;
	}
	
	/**
	 * Adds some quantity of a particular Commodity to a Ship.  If the
	 * specified Commodity is not on the list of Commodities carried by the
	 * Ship, the Commodity is first added to the list and then the quantity is
	 * added to the list of quantities.  If the Commodity is on the list of
	 * Commodities carried by the Ship, the quantity is simply modified.
	 * @param commodity The Commodity to add
	 * @param quantity The quantity of the Commodity to add
	 */
	public void addCommodity(Commodity commodity, int quantity) {
		int index = this.commodities.indexOf(commodity);
		if (index >= 0) {
			int newQuant = this.quantities.get(index) + quantity;
			this.quantities.set(index, newQuant);
		}
		else {
			this.commodities.add(commodity);
			this.quantities.add(quantity);
		}
	}
	
	/**
	 * Removes some quantity of a particular Commodity from a Ship.  If the
	 * specified Commodity is not on the list of Commodities carried by the
	 * Ship, nothing needs to be done.  If the Commodity is on the list of
	 * Commodities carried by the Ship, the quantity is modified.
	 * @param commodity The Commodity to remove
	 * @param quantity The quantity of the Commodity to remove
	 */
	public void removeCommodity(Commodity commodity, int quantity) {
		int index = this.commodities.indexOf(commodity);
		if (index >= 0) {
			int newQuant = this.quantities.get(index) - quantity;
			this.quantities.set(index, newQuant);
		}
	}
	
	/**
	 * Gets the fuel capacity of a Ship.
	 * @return The maximum fuel capacity of this Ship
	 */
	public int getFuelCapacity() {
		return this.fuelCapacity;
	}
	
	/**
	 * Gets the amount of fuel currently in a Ship.
	 * @return The amount of fuel currently in this Ship
	 */
	public int getFuel() {
		return this.fuel;
	}
	
	/**
	 * Adds to the fuel in a Ship.
	 * @param fuel The ammount of fuel to add to this Ship
	 */
	public void addFuel(int fuel) {
		this.fuel += fuel;
	}
	
	/**
	 * Removes fuel from a Ship.
	 * @param fuel The amount of fuel to remove from this Ship
	 */
	public void removeFuel(int fuel) {
		this.fuel -= fuel;
	}
	
	/**
	 * Converts a Ship to a human-readable format.  It should follow the
	 * convention:<br>
	 * <tt>Fuel Capacity - fuel_capacity</tt><br>
	 * <tt>Fuel - fuel</tt><br>
	 * <tt>Cargo Capacity - cargo_capacity</tt><br>
	 * <tt>Total Cargo Quantity - total_cargo_quantity</tt><br>
	 * <tt>Inventory -</tt><br>
	 * <tt>&nbsp;&nbsp;commodity_name: quantity</tt><br>
	 * <tt>&nbsp;&nbsp;commodity_name: quantity</tt><br>
	 * For example:<br>
	 * <tt>Fuel Capacity - 100</tt><br>
	 * <tt>Fuel - 100</tt><br>
	 * <tt>Cargo Capacity - 1000</tt><br>
	 * <tt>Total Cargo Quantity - 650</tt><br>
	 * <tt>Inventory -</tt><br>
	 * <tt>&nbsp;&nbsp;Cow: 600</tt><br>
	 * <tt>&nbsp;&nbsp;Scrap Metal: 50</tt><br>
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Fuel Capacity - " + this.fuelCapacity + "\n");
		str.append("Fuel - " + this.fuel + "\n");
		str.append("Cargo Capacity - " + this.cargoCapacity + "\n");
		str.append("Total Cargo Quantity - " + this.getTotalCargoQuantity() + 
				"\n");
		str.append("Inventory - \n");
		for (int i = 0; i < this.commodities.size(); i++) {
			String name = this.commodities.get(i).getName();
			int quantity = this.quantities.get(i);
			if (quantity > 0) {
				str.append("  " + name + ": " + quantity);
				str.append("\n");
			}
		}
		return str.toString();
	}
	
}
