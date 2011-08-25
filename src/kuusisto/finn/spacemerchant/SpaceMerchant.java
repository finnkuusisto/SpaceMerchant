package kuusisto.finn.spacemerchant;
/**
 * The SpaceMerchant class is the main class of the game.  It loads a World
 * from a file and plays the game to completion.
 * 
 * @author Finn Kuusisto
 */
public class SpaceMerchant {
	
	/**
	 * This is the game's main method.  It begins by loading the World from a 
	 * file and printing a welcome message.  While the player has not completed
	 * all of the Missions (use <tt>World.allMissionsComplete</tt>), it gets a
	 * user requested Action from the UserInterface (use 
	 * <tt>UserInterface.getUserAction</tt>) and carries them out.  A message
	 * is printed to the user upon completion along with their final status
	 * information.
	 * @param args Unused
	 */
	public static void main(String[] args) {
		World.loadWorld("universe.world");
		Player player = World.getPlayer();
		
		System.out.println("Welcome To Space Merchant!");
		System.out.println("--------------------------");
		player.list("commands");
		System.out.println("--------------------------\n");
		
		player.describeLocation();
		player.setMission(World.getNextMission());
		while (!World.allMissionsComplete()) {
			Action action = UserInterface.getUserAction();
			switch (action.getActionValue()) {
				case Action.TRAVEL:
					player.travel(action.getName()); break;
				case Action.BUY:
					player.buy(action.getName(), action.getQuantity()); break;
				case Action.SELL:
					player.sell(action.getName(), action.getQuantity()); break;
				case Action.LIST:
					player.list(action.getName()); break;
				case Action.QUIT:
					System.out.println("Bye!"); return;
			}
		}
		System.out.println("Finally!  You won!");
		player.list("status");
	}
	
}
