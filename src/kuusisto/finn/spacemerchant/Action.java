package kuusisto.finn.spacemerchant;
/**
 * The Action class represents an action requested by the user.
 * 
 * @author Finn Kuusisto
 */
public class Action {
	
	/**
	 * Value of the "travel [destination]" action
	 */
	public static final int TRAVEL = 1;
	/**
	 * Value of the "buy [quantity] [commodity]" action
	 */
	public static final int BUY = 2;
	/**
	 * Value of the "sell [quantity] [commodity]" action
	 */
	public static final int SELL = 3;
	/**
	 * Value of the "list [commodities|distances|status]" action
	 */
	public static final int LIST = 4;
	/**
	 * Value of the "quit" action
	 */
	public static final int QUIT = 5;
	
	/**
	 * This field holds a value indicating what action the user has requested.
	 */
	private final int ACTION_VAL;
	
	/**
	 * This field holds a value indicating a quantity specified by the user
	 * (only used with "buy" and "sell" actions).
	 */
	private final int QUANTITY;
	
	/**
	 * This field holds text indicating a destination, list or commodity the
	 * user has requested (only used with "travel", "list", "buy" and "sell"
	 * actions).
	 */
	private final String NAME;
	
	/**
	 * Construct a new Action object with the specified action, a quantity and
	 * a name of a commodity to buy or sell if the action is a "buy" or "sell"
	 * action.
	 * @param action The action issued (BUY, SELL)
	 * @param quantity The quantity specified
	 * @param name The name of the commodity
	 */
	public Action(int action, int quantity, String name) {
		this.ACTION_VAL = action;
		this.QUANTITY = quantity;
		this.NAME = name;
	}
	
	/**
	 * Construct a new Action object with the specified action, a -1 for
	 * QUANTITY and name of a destination, list or commodity if the action is a
	 * "travel" or "list" 
	 * action.  Should not be used for BUY or SELL.
	 * @param action The action issued (TRAVEL, LIST)
	 * @param name The name of a destination or list if the action is TRAVEL or
	 * LIST.
	 */
	public Action(int action, String name) {
		this(action, -1, name);
	}
	
	/**
	 * Construct a new Action object with the specified action, a -1 for
	 * QUANTITY and a null value for the NAME field.  Should not be used for
	 * TRAVEL, LIST, BUY or SELL.
	 * @param action The action issued (should only ever be QUIT)
	 */
	public Action(int action) {
		this(action, -1, null);
	}
	
	/**
	 * Get the integer representation of the action requested by the user.
	 * Compare with Action.LIST, Action.TRAVEL etc. to determine which action
	 * the user requested.
	 * @return The int representing the action requested by the user
	 */
	public int getActionValue() {
		return this.ACTION_VAL;
	}
	
	/**
	 * Get the quantity associated with the action requested by the user.  This
	 * is only relevant when the user requests the "buy" or "sell" actions 
	 * where it stores the quantity of a commodity to buy or sell.
	 * @return The quantity of a commodity to buy or sell
	 */
	public int getQuantity() {
		return this.QUANTITY;
	}
	
	/**
	 * Get the name associated with the action requested by the user.  This is
	 * only relevant when the user requests the "buy", "sell", "travel" or
	 * "list" actions where it stores the commodity name, destination or list
	 * respectively.
	 * @return The commodity name, destination or list specified by the user
	 */
	public String getName() {
		return this.NAME;
	}

}