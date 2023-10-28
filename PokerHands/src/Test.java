
public class Test {
	
	public static void main(String[] args) {
		
		HandEvaluator test = new HandEvaluator();
		
		test.dealHands(7, 10000);
		
		test.writeToCSV("PokerHands.csv");
		
	}
}
