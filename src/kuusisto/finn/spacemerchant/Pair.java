package kuusisto.finn.spacemerchant;
/**
 * The Pair class stores a pair of ints.
 * 
 * @author Finn Kuusisto
 */
public class Pair {

	private int val1;
	private int val2;
	
	/**
	 * Construct a new Pair of ints.
	 * @param val1 The first value in the pair
	 * @param val2 The second value in the pair
	 */
	public Pair(int val1, int val2) {
		this.val1 = val1;
		this.val2 = val2;
	}
	
	/**
	 * Gets the first value in a Pair.
	 * @return The first value of this Pair
	 */
	public int getVal1() {
		return this.val1;
	}
	
	/**
	 * Gets the second value in a Pair.
	 * @return The second value of this Pair
	 */
	public int getVal2() {
		return this.val2;
	}
	
}
