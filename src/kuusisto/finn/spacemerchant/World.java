package kuusisto.finn.spacemerchant;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * The World class loads and stores the entire game world.
 * 
 * @author Finn Kuusisto
 */
public class World {
	
	private static Random rand = new Random(42);
	private static Map<String,Commodity> commodities = 
		new HashMap<String,Commodity>();
	private static Map<String,Location> locations =
		new HashMap<String,Location>();
	private static Map<String,Pair> locationCoordinates =
		new HashMap<String,Pair>();
	private static List<Mission> missions = new ArrayList<Mission>();
	private static Player player;

	
	/**
	 * Get the Player in the game environment.
	 * @return The Player
	 */
	public static Player getPlayer() {
		return World.player;
	}
	
	/**
	 * Get a list of the Locations in the game environment.
	 * @return The Locations
	 */
	public static List<Location> getLocations() {
		return new ArrayList<Location>(World.locations.values());
	}
	
	/**
	 * Get a Location in the game environment by its name.
	 * @param name The name of the desired Location
	 * @return The Location requested by name
	 */
	public static Location getLocation(String name) {
		return World.locations.get(name);
	}
	
	/**
	 * Get a list of the Commodities in the game environment.
	 * @return The Commodities
	 */
	public static List<Commodity> getCommodities() {
		return new ArrayList<Commodity>(World.commodities.values());
	}
	
	/**
	 * Get a Commodity in the game environment by its name.
	 * @param name The name of the desired Commodity
	 * @return The Commodity requested by name
	 */
	public static Commodity getCommodity(String name) {
		return World.commodities.get(name);
	}
	
	/**
	 * Gets the distance between two Locations.
	 * @param locName1 The name of the first location
	 * @param locName2 The name of the second location
	 * @return The distance between the two named locations
	 */
	public static int getDistance(String locName1, String locName2) {
		if (!World.locationCoordinates.containsKey(locName1) ||
			!World.locationCoordinates.containsKey(locName2)) {
			return -1;
		}
		Pair p1 = World.locationCoordinates.get(locName1);
		Pair p2 = World.locationCoordinates.get(locName2);
		//L1 norm for whole numbers
		return Math.abs(p1.getVal1() - p2.getVal1()) + 
			Math.abs(p1.getVal2() - p2.getVal2());
	}
	
	/**
	 * Get the next incomplete Mission.
	 * @return The next Mission in the game that is incomplete
	 */
	public static Mission getNextMission() {
		for (Mission m : World.missions) {
			if (!m.isComplete()) { return m; }
		}
		return null;
	}
	
	/**
	 * Determines if all Missions in the game are complete.
	 * @return true if all Missions are complete, false otherwise
	 */
	public static boolean allMissionsComplete() {
		return World.getNextMission() == null;
	}
	
	/**
	 * Get a random integer with specified lowest and highest values.
	 * The smallest value this method will return is <tt>low</tt> and
	 * the largest value this method will return is <tt>high</tt>.
	 * <br>
	 * For example:<br>
	 * <tt>getRandomInt(1, 5)</tt> will return one of 1, 2, 3, 4 or 5.
	 * @param low The smallest integer desired
	 * @param high The largest integer desired
	 * @return A random integer from <tt>low</tt> up to and including
	 * <tt>high</tt>.
	 */
	public static int getRandomInt(int low, int high) {
		int range = (high - low) + 1;
		return low + rand.nextInt(range);
	}
	
	//strings for file parsing
	private static final String COMMODITIES = "COMMODITIES:";
	private static final String LOCATIONS = "LOCATIONS:";
	private static final String PRICES = "PRICES:";
	private static final String MISSIONS = "MISSIONS:";
	private static final String PLAYER = "PLAYER:";
	private static final String SEP = "#";
	private static final String CMT = "//";
	private static final int COMMODITIES_SEC = 0;
	private static final int LOCATIONS_SEC = 1;
	private static final int PRICES_SEC = 2;
	private static final int MISSIONS_SEC = 3;
	private static final int PLAYER_SEC = 4;
	
	/**
	 * Load a world file.  This loads a game and must be called once before the
	 * game can begin.
	 * @param filename The name of the world file to load
	 */
	public static void loadWorld(String filename) {
		//first see if we can even open the file
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("Failed to open world file: " + filename);
			System.exit(1);
		}
		
		//reset everything
		World.commodities.clear();
		World.locations.clear();
		World.locationCoordinates.clear();
		World.missions.clear();
		World.player = null;
		
		//start parsing
		int lineNum = 0;
		int currSec = -1;
		while (scan.hasNextLine()) {
			//grab a line
			String line = scan.nextLine().trim();
			lineNum++;
			//skip comments and blank lines
			if (line.startsWith(CMT) || line.length() == 0) { continue; }
			
			//check if we are starting a new section
			if (line.startsWith(World.COMMODITIES)) {
				currSec = World.COMMODITIES_SEC; continue;
			}
			else if (line.startsWith(World.LOCATIONS)) {
				currSec = World.LOCATIONS_SEC; continue;
			}
			else if (line.startsWith(World.PRICES)) {
				currSec = World.PRICES_SEC; continue;
			}
			else if (line.startsWith(World.MISSIONS)) {
				currSec = World.MISSIONS_SEC; continue;
			}
			else if (line.startsWith(PLAYER)) {
				currSec = World.PLAYER_SEC; continue;
			}
			
			//parse this line as part of the current section
			String[] parts = line.split(SEP);
			try {
				switch (currSec) {
					case World.COMMODITIES_SEC:
						World.handleCommodity(parts); break;
					case World.LOCATIONS_SEC:
						World.handleLocation(parts); break;
					case World.PRICES_SEC:
						World.handlePrice(parts); break;
					case World.MISSIONS_SEC:
						World.handleMission(parts); break;
					case World.PLAYER_SEC:
						World.handlePlayer(parts); break;
					default:
						throw new IllegalArgumentException();
				}
			}
			catch (Exception e) {
				System.out.println("Failed parsing " + filename + "!" +
					"[line " + lineNum + "]");
				System.exit(1);
			}
		}
	}
	
	private static void handleLocation(String[] parts) {
		if (parts.length != 3) {
			throw new IllegalArgumentException();
		}
		Location l = new Location(parts[0],parts[1]);
		Pair p = World.handlePair(parts[2]);
		World.locations.put(l.getName(),l);
		World.locationCoordinates.put(l.getName(), p);
	}
	
	private static void handleCommodity(String[] parts) {
		if (parts.length != 2) {
			throw new IllegalArgumentException();
		}
		Commodity c = new Commodity(parts[0],parts[1]);
		World.commodities.put(c.getName(), c);
	}
	
	private static void handlePrice(String[] parts) {
		if ((parts.length % 2) != 1 || parts.length < 1) {
			throw new IllegalArgumentException();
		}
		//grab the location for which we're adding prices
		Location l = World.locations.get(parts[0]);
		for (int i = 1; i < parts.length; i+= 2) {
			Commodity c = World.commodities.get(parts[i]);
			Pair p = World.handlePair(parts[i+1]);
			l.addCommodity(c, p);
		}
	}
	
	private static void handlePlayer(String[] parts) {
		if (parts.length != 4) {
			throw new IllegalArgumentException();
		}
		int money = Integer.parseInt(parts[1]);
		int cargoCap = Integer.parseInt(parts[2]);
		int fuelCap = Integer.parseInt(parts[3]);
		World.player = new Player(World.locations.get(parts[0]), money,
			cargoCap, fuelCap);
	}
	
	private static void handleMission(String[] parts) {
		if (parts.length != 6) {
			throw new IllegalArgumentException();
		}
		Location location = World.locations.get(parts[2]);
		Commodity commodity = World.commodities.get(parts[3]);
		int quantity = Integer.parseInt(parts[4]);
		int reward = Integer.parseInt(parts[5]);
		World.missions.add(new Mission(parts[0], parts[1], location, commodity,
			quantity, reward));
	}
	
	private static Pair handlePair(String pair) {
		String[] vals = pair.split(",");
		if (vals.length != 2) {
			throw new IllegalArgumentException();
		}
		try {
			return new Pair(Integer.parseInt(vals[0]),
				Integer.parseInt(vals[1]));
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}
	
}
