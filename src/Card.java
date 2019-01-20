/**
 * This class provides all functionality for the way individual cards behave in all card games
 * @author Mohamed Amadou
 * @since Monday, January 4
 */
public class Card {
	//cardValues[] represents the tangible values attached to the card faces(ACE, ONE, TWO, THREE, ..., KING)
	private final static int cardValues[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
	private final static String cardIDs[] = {"ACE_", "TWO_", "THREE_", "FOUR_", "FIVE_", "SIX_", "SEVEN_", "EIGHT_", "NINE_", "TEN_", "JACK_", "QUEEN_", "KING_"};
	private int value;
	
	//Name of the card, i.e. "ACE";
	private String cardID;
	
	/**
	 * Constructor
	 * @param v - card value(I.e. 1 for Ace or 13 for King)
	 */
	public Card(int v){
		setValue(v);
		setCardID(v);
	}
	
	/**
	 * Constructor
	 * @param v - card value(I.e. 1 for Ace or 13 for King)
	 * @param id - a manually set ID for the card(used for 'illegal' card instantiation)
	 */
	public Card(int v, String id){
		setValue(v);
		setCardID(id);
	}
	
	/**
	 * Returns the card ID
	 * @return - the card ID of the respective card
	 */
	public String getCardID(){
		return cardID;
	}
	
	/**
	 * Returns the card value
 	 * @return - the number value of the respective card
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * Setter method for card value
	 * @param v - value
	 */
	public void setValue(int v){
		//Checks to see if v is a valid cardValue
		if(v >= 1 && v <= 10){
			value = v;
		}
		//Sets all face cards to 10
		if(v >= 11 && v <= 13){
			value = 10;
		}
	}
	
	/**
	 * 'legal' setter method for card ID
	 * @param v - number value of the card
	 */
	public void setCardID(int v){
		//Checks to see if v is a valid cardValue
		if(v >= 1 && v <= 13){
			cardID = cardIDs[v - 1];
		}
	}
	
	/**
	 * 'illegal' setter method for card ID
	 * @param id - String value of the card ID
	 */
	public void setCardID(String id){
		cardID = id;
	}
	
	/**
	 * Retrieves the Image name (.png) for a card
	 * @return - image file name
	 */
	public String getIcon(){
		return "blackjack/" + this.getCardID() + ".png";
	}
}
