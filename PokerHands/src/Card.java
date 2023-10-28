/**
 * Card --- program to create card objects with a 
 * suit and a value for use in the HandEvaluator class
 * @author Nick Domenico
 */
public class Card {

	// instance variables
	private String suit;
	private int value;
	
	
	/**
	 * Creates a card with a suit and a value
	 * @param String suit
	 * @param int value
	 */
	public Card(String suit, int value) {
		this.suit = suit;
		this.value = value;
	}

	
	// getters
	
	/**
	 * Returns the card's suit
	 * @return String
	 */
	public String getSuit() {
		return suit;
	}

	
	/**
	 * Returns the card's value
	 * @return int
	 */
	public int getValue() {
		return value;
	}

	
	/**
	 * Returns a string of the cards suit and value.
	 * For values 11, 12, 13, and 14, prints the corresponding
	 * card value (Jack, Queen, King, or Ace).
	 */
	@Override
	public String toString() {
		if (value == 11) {
			return ("Jack of " + suit);
		}
		else if (value == 12) {
			return ("Queen of " + suit);
		}
		else if (value == 13) {
			return ("King of " + suit);
		}
		else if (value == 14) {
			return ("Ace of " + suit);
		}
		else {
			return (value + " of " + suit);
		}
	}
	
}
