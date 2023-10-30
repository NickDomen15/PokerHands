import java.util.Random;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

/**
 * HandEvalutator --- program that utilizes card objects to shuffle a 
 * standard 52 card deck of playing cards and deal a number of poker hands.
 * Evaluates what kind of hand each one would be and prints the results to a CSV file.
 * @author Nick Domenico
 */
public class HandEvaluator {
	
	Random rand = new Random();
	
	// lists for deck, hand, suits, and values
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
	private int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
	
	
	// instance variables for writing to CSV
	private int numberOfRoyalFlush = 0;
	private int numberOfStraightFlush = 0;
	private int numberOfFourOfAKind = 0;
	private int numberOfFullHouse = 0;
	private int numberOfFlush = 0;
	private int numberOfStraight = 0;
	private int numberOfThreeOfAKind = 0;
	private int numberOfTwoPair = 0;
	private int numberOfPair = 0;
	private int numberOfHighCard = 0;
	private int numberOfHands = 0;
	
	/**
	 * Initializes the deck.
	 */
	public HandEvaluator() {
		resetDeck();
	}
	
	
	/**
	 * Removes all existing cards from the deck and 
	 * resets it back to 52 cards in random order.
	 */
	public void resetDeck() {
		ArrayList<Card> allCards = new ArrayList<Card>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				allCards.add(new Card(suits[i], values[j]));
			}
		}
		
		deck.clear();
		
		for (int i = 0; i < 52; i++) {
			int cardAdded = rand.nextInt(allCards.size());
			deck.add(allCards.get(cardAdded));
			allCards.remove(cardAdded);
		}
	}
	
	
	/**
	 * Adds first card from ArrayList deck to ArrayList 
	 * hand, then removes first card from deck.
	 */
	public void drawCard() {
		Card cardDrawn = deck.get(0);
		hand.add(cardDrawn);
		deck.remove(0);
	}
	
	
	/**
	 * Calls the drawCard method handSize number of times,
	 * then calls the sortHand method.
	 * @param int handSize
	 */
	public void drawHand(int handSize) {
		for (int i = 0; i < handSize; i++) {
			drawCard();
		}
		sortHand(handSize);
	}
	
	
	/**
	 * Sorts the cards in ArrayList hand 
	 * by value from largest to smallest.
	 * @param int handSize
	 */
	public void sortHand(int handSize) {
		for (int i = 0; i < handSize; i++) {
			for (int j = 0; j < handSize; j++) {
				if (hand.get(i).getValue() > hand.get(j).getValue()) {
					Card temp = hand.get(i);
					hand.set(i, hand.get(j));
					hand.set(j, temp);
				}
			}
		}
	}
	
	
	/**
	 * Prints all cards in ArrayList hand.
	 */
	public void printHand() {
		for (int i = 0; i < hand.size(); i++) {
			System.out.println((hand.get(i)));
		}
	}
	
	
	/**
	 * Deals "int numberOfHands" hands of "int handSize" 
	 * size, determines what each hand is based on poker hand 
	 * rankings, and counts how many of each type was dealt.
	 * @param int handSize
	 * @param int numberOfHands
	 */
	public void dealHands(int handSize, int numberOfHands) {
		for (int i = 0; i < numberOfHands; i++) {
			drawHand(handSize);
			
			/*
			 *  Calls each evaluator method in descending hand ranking order 
			 *  to see if the hand is that type of hand. Adds to respective counts.
			 *  If the hand has no specific type, adds to high card instead
			 */
			if (isRoyalFlush() == true) {
				numberOfRoyalFlush++;
			}
			else if (isStraightFlush() == true) {
				numberOfStraightFlush++;
			}
			else if (isFourOfAKind() == true) {
				numberOfFourOfAKind++;
			}
			else if (isFullHouse() == true) {
				numberOfFullHouse++;
			}
			else if (isFlush() == true) {
				numberOfFlush++;
			}
			else if (isStraight() == true) {
				numberOfStraight++;
			}
			else if (isThreeOfAKind() == true) {
				numberOfThreeOfAKind++;
			}
			else if (isTwoPair() == true) {
				numberOfTwoPair++;
			}
			else if (isPair() == true) {
				numberOfPair++;
			}
			else {
				numberOfHighCard++;
			}
			this.numberOfHands++;
			
			// resets hand and deck for next run
			hand.clear();
			resetDeck();
		}
	}
	
	
	/**
	 * Writes the results of the dealHands method to a 
	 * CSV file using BufferedWriter and FileWriter
	 * @param String fileName
	 */
	/*
	 * Code used from:
	 * https://beginnersbook.com/2014/01/how-to-write-to-file-in-java-using-bufferedwriter/
	 */
	public void writeToCSV(String fileName) {
		
		try {
			
			File file = new File(fileName);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fWriter); 
			
			writer.write("HandType, NumberOfHands \n"); // header for CSV
			
			// each line contains the hand type, followed by the number of that type
			writer.write("Royal Flush, " + numberOfRoyalFlush + "\n");
			writer.write("Straight Flush, " + numberOfStraightFlush + "\n");
			writer.write("Four of a kind, " + numberOfFourOfAKind + "\n");
			writer.write("Full house, " + numberOfFullHouse + "\n");
			writer.write("Flush, " + numberOfFlush + "\n");
			writer.write("Straight, " + numberOfStraight + "\n");
			writer.write("Three of a kind, " + numberOfThreeOfAKind + "\n");
			writer.write("Two pair, " + numberOfTwoPair + "\n");
			writer.write("Pair, " + numberOfPair + "\n");
			writer.write("High card, " + numberOfHighCard + "\n");
			writer.write("Total, " + numberOfHands);
			
			writer.close();
			
		}
		
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/*
	 * NOTE FOR ALL EVALUATOR METHODS: since hands will always be 
	 * sorted highest value to lowest value, methods don't need to 
	 * be concerned with positions of the cards in the hand. All 
	 * repeating values for pairs will be next to each other, 
	 * straights will be in sorted order, etc.
	 */
	
	/**
	 * Returns true the cards in ArrayList hand result 
	 * in a royal flush and false if otherwise.
	 * Two ways for solving based on hand size (5 or greater than 5).
	 * @return boolean
	 */
	public boolean isRoyalFlush() {
		if (hand.size() == 5) {
			if (hand.get(0).getValue() == 14 && hand.get(1).getValue() == 13 && hand.get(2).getValue() == 12 && hand.get(3).getValue() == 11 && hand.get(4).getValue() == 10) {
				return isFlush();
			}
			else {
				return false;
			}
		}
		
		else { // hand size > 5
			// checks to see if hand contains an Ace, King, Queen, Jack, and 10
			boolean hasAce = false;
			int acePos = 0;
			boolean hasKing = false;
			int kingPos = 0;
			boolean hasQueen = false;
			int queenPos = 0;
			boolean hasJack = false;
			int jackPos = 0;
			boolean hasTen = false;
			int tenPos = 0;
			
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).getValue() == 14) {
					hasAce = true;
					acePos = i;
				}
				else if (hand.get(i).getValue() == 13) {
					hasKing = true;
					kingPos = i;
				}
				else if (hand.get(i).getValue() == 12) {
					hasQueen = true;
					queenPos = i;
				}
				else if (hand.get(i).getValue() == 11) {
					hasJack = true;
					jackPos = i;
				}
				else if (hand.get(i).getValue() == 10) {
					hasTen = true;
					tenPos = i;
				}
			}
			
			// if A, K, Q, J, and 10 are all found, checks to see if they are all the same suit
			if (hasAce == true && hasKing == true && hasQueen == true && hasJack == true && hasTen == true) {
				String suit = hand.get(acePos).getSuit();
				if (hand.get(kingPos).getSuit() == suit && hand.get(queenPos).getSuit() == suit && hand.get(jackPos).getSuit() == suit && hand.get(tenPos).getSuit() == suit) {
					return true; // returns true if suits match
				}
				else {
					return false;
				}
			}
			
			else {
				return false;
			}
		}
	}
	
	
	/**
	 * Returns true if the cards in ArrayList hand result 
	 * in a straight flush and false if otherwise.
	 * Two ways for solving based on hand size (5 or greater than 5).
	 * @return boolean
	 */
	public boolean isStraightFlush() {
		if (hand.size() == 5) {
			if (isStraight() == true && isFlush() == true) {
				return true;
			}
			else {
				return false;
			}
		}
		
		else { // hand size > 5
			int count = 0; // counts the number of cards in the straight, must be >= 5 to be a straight
			boolean sameSuit = true;
			
			for (int i = 0; i < hand.size() - 1; i++) {
				// if next card value is 1 less than current card value, add 1 to count
				if (hand.get(i).getValue() - 1 == hand.get(i + 1).getValue()) {
					count++;
				}
				else {
					count = 0; // resets count if straight is broken
				}
				if (hand.get(i).getSuit() != hand.get(i + 1).getSuit()) {
					sameSuit = false; // sets sameSuit to false if suits don't match
				}
			}
			
			if (count < 4) {
				return false;
			}
			else if (sameSuit == false) {
				return false;
			}
			else {
				return true; // only returns true if straight and flush
			}
		}
	}
	
	
	/**
	 * Returns true if the cards in ArrayList hand result 
	 * in a four of a kind and false if otherwise.
	 * @return boolean
	 */
	public boolean isFourOfAKind() {
		int count = 0;
		
		// if there are 4 consecutive repeating values, returns true
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getValue() == hand.get(i + 1).getValue()) {
				count++;
			}
			else {
				count = 0;
			}
			if (count == 3) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Returns true if the cards in ArrayList hand 
	 * result in a full house and false if otherwise.
	 * @return
	 */
	public boolean isFullHouse() {
		int value1 = 0; // value of the 3 of a kind in the full house
		int count = 0;
		
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getValue() == hand.get(i + 1).getValue()) {
				count++;
			}
			else {
				count = 0;
			}
			if (count == 2) { // if 3 of a kind is found, set value1 equal to 3 of a kind value
				value1 = hand.get(i).getValue();
				break;
			}
		}
		
		if (count < 2) { 
			return false; // if 3 of a kind is not found, return false
		}
		
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getValue() != value1 && hand.get(i).getValue() == hand.get(i + 1).getValue()) {
				return true; // if a pair of a different value is found after the 3 of a kind, return true
			}
		}
		
		return false;
	}
	
	
	/**
	 * Returns true if the cards in ArrayList hand result 
	 * in a flush and false if otherwise.
	 * Two ways of solving for different hand 
	 * sizes (5 or greater than 5).
	 * @return
	 */
	public boolean isFlush() {
		// for hand size of 5, only need to see if every card in the hand has the same suit
		if (hand.size() == 5) {
			boolean sameSuit = true;
			
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (hand.get(i).getSuit() != hand.get(j).getSuit()) {
						sameSuit = false;
					}
				}
			}
			
			return sameSuit;
		}
		
		else { // hand size > 5
			int numberOfHearts = 0;
			int numberOfDiamonds = 0;
			int numberOfClubs = 0;
			int numberOfSpades = 0;
			
			/*
			 * for hand size > 5, need to track the number of each suit in the hand,
			 * returns true if there are 5 or more of 1 suit in hand
			 */
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).getSuit() == "Hearts") {
					numberOfHearts++;
				}
				else if (hand.get(i).getSuit() == "Diamonds") {
					numberOfDiamonds++;
				}
				else if (hand.get(i).getSuit() == "Clubs") {
					numberOfClubs++;
				}
				else {
					numberOfSpades++;
				}
			}
			
			if (numberOfHearts >= 5 || numberOfDiamonds >= 5 || numberOfClubs >= 5 || numberOfSpades >= 5) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	
	/**
	 * Returns true if the cards in ArrayList hand 
	 * result in a straight and false if otherwise.
	 * @return
	 */
	public boolean isStraight() {
		int count = 0;
		
		// checks to see if next card value is 1 less than current card
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getValue() - 1 == hand.get(i+1).getValue()) {
				count++;
			}
			else {
				count = 0;
			}
		}
		
		if (count < 4) {
			return false;
		}
		else {
			return true; // only returns true if there are 5 or more consecutive descending values
		}
	}
	
	
	/**
	 * Returns true if the cards in ArrayList hand result 
	 * in a three of a kind and false if otherwise.
	 * @return
	 */
	public boolean isThreeOfAKind() {
		int count = 0;
		
		// if there are 3 consecutive repeating values, returns true
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getValue() == hand.get(i + 1).getValue()) {
				count++;
			}
			else {
				count = 0;
			}
			if (count == 2) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Returns true if the cards in ArrayList hand 
	 * result in a two pair and false if otherwise.
	 * @return
	 */
	public boolean isTwoPair() {
		int value1 = 0; // the value of the first pair
		
		// checks to see if there is a pair
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getValue() == hand.get(i + 1).getValue()) {
				value1 = hand.get(i).getValue(); // sets value1 to the value of the 1st pair
				break;
			}
		}
		
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getValue() != value1 && hand.get(i).getValue() == hand.get(i + 1).getValue()) {
				return true; // if another pair is found of a different value than the first, return true
			}
		}
		
		return false;
	}
	
	
	/**
	 * Returns true if the cards in ArrayList hand 
	 * result in a pair and false if otherwise.
	 * @return
	 */
	public boolean isPair() {
		// if there are 2 cards with the same value next to each other, returns true
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getValue() == hand.get(i + 1).getValue()) {
				return true;
			}
		}
		
		return false;
	}
	
}
