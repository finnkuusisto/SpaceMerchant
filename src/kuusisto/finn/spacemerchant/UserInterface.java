package kuusisto.finn.spacemerchant;
import java.util.Scanner;

/**
 * The UserInterface class provides a command-line interface to the player for
 * use in a text-based game.
 * 
 * @author Finn Kuusisto
 */
public class UserInterface {
	
	private static final String TRAVEL = "travel";
	private static final String BUY = "buy";
	private static final String SELL = "sell";
	private static final String LIST = "list";
	private static final String QUIT = "quit";
	private static final String[] INVAL = 
		{"Huh?", "What?", "Pardon?", "Say again?", "I don't understand."};

	private static Scanner scan = new Scanner(System.in);
	
	/**
	 * Prints "What would you like to do?" and gets input from the player. The
	 * input is then parsed and validated.  If the input is valid, an Action
	 * object is created to represent what the player entered. Otherwise, the
	 * player is re-prompted for input.
	 * @return An Action object representing the player's input
	 */
	public static Action getUserAction() {
		Action retAction = null;
		do {
			System.out.println("What would you like to do?");
			System.out.print(">");
			String input = scan.nextLine().trim();
			
			//grab the action
			String action = null;
			int firstSpace = input.indexOf(' ');
			int secondSpace = -1;
			if (firstSpace == -1) {
				action = input;
			}
			else {
				action = input.substring(0, firstSpace);
				//get the second space for later
				secondSpace = input.indexOf(' ', firstSpace + 1);
			}
			//check the action [name] pair
			if (action.equalsIgnoreCase(UserInterface.TRAVEL) && 
				firstSpace != -1) {
				retAction = new Action(Action.TRAVEL, 
						input.substring(firstSpace + 1));
			}
			else if (action.equalsIgnoreCase(UserInterface.BUY) && 
					secondSpace != -1) {
				String potentialQuant = input.substring(firstSpace + 1,
						secondSpace);
				String potentialComm = input.substring(secondSpace + 1);
				if (UserInterface.canParseInt(potentialQuant)) {
					retAction = new Action(Action.BUY,
							Integer.parseInt(potentialQuant), potentialComm);
				}
			}
			else if (action.equalsIgnoreCase(UserInterface.SELL) && 
					secondSpace != -1) {
				String potentialQuant = input.substring(firstSpace + 1,
						secondSpace);
				String potentialComm = input.substring(secondSpace + 1);
				if (UserInterface.canParseInt(potentialQuant)) {
					retAction = new Action(Action.SELL,
							Integer.parseInt(potentialQuant), potentialComm);
				}
			}
			else if (action.equalsIgnoreCase(UserInterface.LIST) &&
					firstSpace != -1) {
				retAction = new Action(Action.LIST,
						input.substring(firstSpace + 1));
			}
			else if (action.equalsIgnoreCase(QUIT) && firstSpace == -1) {
				retAction = new Action(Action.QUIT);
			}
			
			//did we successfully make an action?
			if (retAction == null) {
				//no we didn't
				int msgInd = (int)(Math.random() * 10) % INVAL.length;
				System.out.println(INVAL[msgInd]);
				System.out.println();
			}
			
		} while (retAction == null);
		return retAction;
	}
	
	private static boolean canParseInt(String s) {
		try { Integer.parseInt(s); }
		catch (NumberFormatException e) { return false; }
		return true;
	}
	
}
