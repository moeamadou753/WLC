/**
 * This Class provides the functionality for the way cards assemble in decks and behave
 * @author Mohamed Amadou
 * @since Monday, January 4
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Deck {
	private String cardSuits[] = {"SPADES", "HEARTS", "DIAMONDS", "CLUBS"};
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	/**
	 * Constructor
	 */
	public Deck(){
		
		//Will keep track of the index of the 52 cards created
		int counter = 0;
				
		for(int y = 0; y < 4; y++){
			String suit = cardSuits[y];
				for(int z = 1; z < 14; z++){
					//Adds a new card and initializes it with its number value
					cards.add(new Card(z));
						
					//Replaces the card ID with a new ID containing the card suit (i.e. SPADES)
					if(cards.get(counter).getCardID().indexOf("_") == cards.get(counter).getCardID().length() - 1){
						String newID = cards.get(counter).getCardID() + suit;
						cards.get(counter).setCardID(newID);
						counter++;
					} else {
						
					}
					}
			}
	}
	
	/**
	 * Shuffles the deck to a random order
	 */
	public void shuffle(){
		Collections.shuffle(cards);
	}
	
	/**
	 * Adds a card to the deck(Is not used for game such as Over or Under which use absolute decks)
	 * @param card - the card to be added
	 */
	public void addCard(Card card){
		cards.add(card);
	}
	
	/**
	 * Removes a card from the deck - gets rid of the object in the array of cards once it has been drawn
	 * @param id - the card to be removed
	 */
	public void removeCard(String id){
		
		for(int i = 0; i < cards.size(); i++){
			
			try{
				if(cards.get(i).getCardID().equalsIgnoreCase(id)){
					cards.remove(i);
				}
			} catch (Exception e){
				
			}
		}
	}
	
	/**
	 * Returns the object of the card within a deck
	 * @param c - the index of the card within the deck
	 * @return the card object
	 */
	public Card getCard(int c){
		return cards.get(c);
	}
	
	/**
	 * Returns a random card from the array of cards in the deck
	 * @return - a random card object
	 */
	public Card getRandomCard(){
		Random r = new Random();
		int index = r.nextInt(cards.size());
		return cards.get(index);
	}
	
	/**
	 * Resets the deck to its original, 'perfect' order
	 */
	public void reset(){
		//for(int x = 0; x<cards.size(); x++){
			
			//cards.remove(x);
			
		//}
		cards.clear();
		
		//Will keep track of the index of the 52 cards created
		int counter = 0;
		
		for(int y = 0; y < 4; y++){
			String suit = cardSuits[y];
			
			for(int z = 1; z < 14; z++){
				//Adds a new card and initializes it with its number value
				cards.add(new Card(z));
				
				//Replaces the card ID with a new ID containing the card suit (i.e. SPADES)
				String newID = cards.get(counter).getCardID() + suit;
				cards.get(counter).setCardID(newID);
				
				counter++;
			}
		}
	}
	
	
	
}
