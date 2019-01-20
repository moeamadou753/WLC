/**
 * This class provides all of the functionality of the blackjack game
 * @author Mohamed Amadou
 *
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class Blackjack extends JFrame implements ActionListener{

	public MigLayout mig = new MigLayout("insets 0");
	public Deck bjDeckHouse = new Deck(), bjDeckUser = new Deck();
	public long dealerOffset = 30, userOffset = 250, houseHand = 0, userHand = 0, bet = 0L, userAces = 0, dealerAces = 0;
	public final long MAX_BET = 1000000000000000L;
	public JPanel bj = new JPanel(new MigLayout("insets 0")), blackjack = new JPanel(new MigLayout("insets 0"));
	public int userCardsDrawn = 0, dealerCardsDrawn = 0;
	public boolean isValidBet, userBust = false, dealerBust = false, validNumber = false, inSession = false;
	public AudioInputStream inputStream = null;
	public Clip clip = null;
	public JButton hit, stay, rules, exit, play;
	public JLabel outline;
	public ArrayList<JLabel> userCards = new ArrayList<JLabel>(), dealerCards = new ArrayList<JLabel>();
	
	
	/**
	 * Constructor
	 */
	public Blackjack(){
			
		//If the user has at least $1 in their account, play blackjack. Otherwise tell them they don't
		if(User.getActiveUser().getAccountBalance() > 0){
			bjDeckHouse.shuffle();
			bjDeckUser.shuffle();
			
			buildUI();
			mig.layoutContainer(bj);
			loop();
			
			setSize(780, 700);
			setResizable(false);
	        setLayout(mig);
	        setTitle("Blackjack");
	        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	        setVisible(true);
	        
	        JOptionPane.showMessageDialog(null, "Welcome to Blackjack!", "Blackjack", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		} else {
			JOptionPane.showMessageDialog(null, "You don't have enough money to play Blackjack!", "Error", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
			Casino.bjWindowsOpen = 0;
		}
	       
		
	}
	/**
	 * Completely resets all values related to game excluding account balance and 
	 */
	public void wipe(){
		bjDeckUser.reset();
		bjDeckHouse.reset();
		bjDeckHouse.shuffle();
		bjDeckUser.shuffle();
		
		for(int u = 0; u < userCards.size(); u++){
			try{
				bj.remove(userCards.get(u));
				
				validate();
				repaint();
			} catch (Exception e){
				
			}
		}
		
		for(int i = 0; i < dealerCards.size(); i++){
			try{
				bj.remove(dealerCards.get(i));
				
				validate();
				repaint();
			} catch (Exception e){
				
			}
		}
		
		userCards.clear();
		dealerCards.clear();
		
		userHand = 0;
		houseHand = 0;
		dealerOffset = 30;
		userOffset = 250;
		inSession = false;
		bet = 0;
		userCardsDrawn = 0;
		dealerCardsDrawn = 0;
	}
	
	/**
	 * Builds all components of the blackjack user interface and displays them
	 */
	public void buildUI(){
		getContentPane().add(bj);
		bj.setBackground(new Color(32, 82, 31));
		
		exit = new JButton(new ImageIcon("blackjack/exit.png"));
		exit.setBorder(null);
		exit.addActionListener(this);
		bj.add(exit, "pos 40px 285px");
		
		rules = new JButton(new ImageIcon("blackjack/rules.png"));
		rules.setBorder(null);
		rules.addActionListener(this);
		bj.add(rules, "pos 140px 285px");
		
		hit = new JButton(new ImageIcon("blackjack/hit.png"));
		hit.addActionListener(this);
		hit.setBorder(null);
		bj.add(hit, "pos 570px 285px");
		
		stay = new JButton(new ImageIcon("blackjack/stay.png"));
		stay.setBorder(null);
		stay.addActionListener(this);
		bj.add(stay, "pos 670px 285px");
		
		play = new JButton(new ImageIcon("blackjack/play.png"));
		play.setBorder(null);
		play.addActionListener(this);
		bj.add(play, "pos 250px 250px");
		
		
		outline = new JLabel(new ImageIcon("blackjack/blackjack.png"));
		bj.add(outline);
		
		
	}
	
	/**
	 * Pops up a window that shows a JLabel containing the rules for White Lily Blackjack
	 */
	public void showRules(){
		JPanel rules = new JPanel(new MigLayout("insets 0"));
		JFrame rulesFrame = new JFrame();
		
		rulesFrame.add(rules);
		
		JLabel pic = new JLabel(new ImageIcon("blackjack/blackjackrules.png"));
		pic.setBorder(null);
		rules.add(pic, "pos 0px 0px");
		
		
		rulesFrame.setSize(400, 400);
		rulesFrame.setResizable(false);
		rulesFrame.setLayout(mig);
		rulesFrame.setTitle("Blackjack Rules");
		rulesFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		rulesFrame.setVisible(true);
	}
	
	/**
	 * Draws a random card and adds it to the user's hand
	 */
	public void hitUser(){
		
		if(userCardsDrawn < 5 && !checkBlackjack(userHand)){
			
			if(checkBust(userHand) && userAces > 0){
				
				userHand -=10;
				userAces--;
			}
			
			
			if(!checkBust(userHand)){
				
				Card hitCard = bjDeckUser.getRandomCard();
				
				if(hitCard.getValue() == 1){
					
					
					userAces++;
					userHand += 11;
					
					if(checkBust(userHand)){
						
						userHand -=10;
					}
					
					
				} else {
					
					userHand += hitCard.getValue();
				}
				
					String position = "pos "+ userOffset + "px" + " 510px";
				
					userCards.add(new JLabel(new ImageIcon(hitCard.getIcon())));
					bj.add(userCards.get(userCardsDrawn), position);
			
					bjDeckUser.removeCard(hitCard.getCardID());
					userCardsDrawn++;
					
					
					userBust = checkBust(userHand);
					userOffset += 100;
					
					validate();
					repaint();
				
				
			}
		}
			
		
	}
	
	/**
	 * Draws a random card and adds it to the dealer's hand
	 */
	public void hitDealer(){
		if(dealerCardsDrawn < 5 && !checkBlackjack(houseHand)){
			
			if(checkBust(houseHand) && dealerAces > 0){
				
				houseHand -=10;
				dealerAces--;
			}
			
			
			if(!checkBust(houseHand)){
				Card hitCard = bjDeckHouse.getRandomCard();
				
				if(hitCard.getValue() == 1){
					
					dealerAces++;
					houseHand += 11;
					
				} else {
					
					houseHand += hitCard.getValue();
				}
				
					String position = "pos "+ dealerOffset + "px" + " 35px";
				
					dealerCards.add(new JLabel(new ImageIcon(hitCard.getIcon())));
					bj.add(dealerCards.get(dealerCardsDrawn), position);
			
					bjDeckHouse.removeCard(hitCard.getCardID());
					dealerCardsDrawn++;
					
					
					dealerBust = checkBust(houseHand);
					dealerOffset += 100;
					
					validate();
					repaint();
				
				
			} 	
		}
	}
	
	/**
	 * Checks whether or not the hand provided has busted (gone over 21)
	 * @param hand - the long value to parse
	 * @return true if hand is greater than 21 and false if not
	 */
	public boolean checkBust(long hand){
		if(hand > 21){
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Adds winnings to the players account. What good is winning money if you don't keep it?
	 * @param win - the long value to add to the character's winnings and account balance
	 */
	public void addWinnings(long win){
		
		User.getActiveUser().setWinnings(User.getActiveUser().getWinnings() + Long.valueOf(win));
		User.getActiveUser().setAccountBalance(User.getActiveUser().getAccountBalance() + Long.valueOf(win));
		System.out.println("The user " + User.getActiveUser().getUserID().replaceAll("_", " ") + " has just won $" + displayNumber(win) + ".");
		System.out.println(User.getActiveUser().getUserID().replaceAll("_", " ") + "'s new account balance is " + displayNumber(User.getActiveUser().getAccountBalance()) + ".");
		System.out.println("***");
	}
	
	/**
	 * Adds losings to the players account. What good is gambling without consequences?
	 * @param lose - the long value to add to the character's losings and subtract from account balance
	 */
	public void addLosings(long lose){
		
		User.getActiveUser().setLosings(User.getActiveUser().getLosings() + Long.valueOf(lose));
		User.getActiveUser().setAccountBalance(User.getActiveUser().getAccountBalance() - Long.valueOf(lose));
		System.out.println("The user " + User.getActiveUser().getUserID().replaceAll("_", " ") + " has just lost $" + displayNumber(lose) + ".");
		System.out.println(User.getActiveUser().getUserID().replaceAll("_", " ") + "'s new account balance is " + displayNumber(User.getActiveUser().getAccountBalance()) + ".");
		System.out.println("***");
	}
	
	
	/**
	 * Starts blackjack and retrieves a bet value
	 */
	public void startGame(){
		
		bet = 0;
		validNumber = false;
		
		//Instantiating Layout and contents of input getter
		JPanel panel = new JPanel(new MigLayout("insets 0"));
		JPanel label = new JPanel(new MigLayout("insets 0"));
		JPanel controls = new JPanel(new MigLayout());
		
		label.add(new JLabel("(Current Balance: $" + displayNumber(User.getActiveUser().getAccountBalance()) + ")Set Bet"), "pos 0px 0px");
		JTextField betField = new JTextField();
		betField.setColumns(25);
		controls.add(betField, "pos 0px 10px");
		
		
		panel.add(label);
		panel.add(controls);
		
		long selection = JOptionPane.showConfirmDialog(this, panel, "Play Blackjack", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
		
		String input = betField.getText();
		
		//If they pressed cancel on the input window or closed the window, do nothing
		if(selection == JOptionPane.CANCEL_OPTION || selection == JOptionPane.CLOSED_OPTION){
			inSession = false;
			play.setVisible(true);
		
		} else {
			
			try {
					bet = parseShortcuts(input);
					
			} catch (Exception e) {
					
			}
			
			
			loop : while(bet < 1 || bet > MAX_BET || bet > User.getActiveUser().getAccountBalance()){
				
				/*If nothing was entered keep asking until either proper format is entered or
				 * cancel / exit button has been pressed
				 */
				
				if(bet > User.getActiveUser().getAccountBalance()){
					
					JOptionPane.showMessageDialog(null, "You don't have enough money!", "Error", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
					validNumber = false;
				}
				
				
				while(input == null || input.length() < 1 || validNumber == false){
					
					
						JOptionPane.showMessageDialog(null, "Please enter a dollar value from $1 - $" + displayNumber(MAX_BET) + " to bet!", "Blackjack", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
						selection = JOptionPane.showConfirmDialog(this, panel, "Play Blackjack", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
							
						if(selection == JOptionPane.CANCEL_OPTION || selection == JOptionPane.CLOSED_OPTION){
							
							inSession = false;
							play.setVisible(true);
							//Breaks the verification process as the user has indicated the desire to not play 
							break loop;
							
						}
							
						input = betField.getText();
							
						try {
							bet = parseShortcuts(input);
							
							if(bet >= 1 && bet <= MAX_BET){
								validNumber = true;
							} else {
								validNumber = false;
							}
								
						} catch (Exception e1) {
								
						validNumber = false;
						
						}
					
				}
				
				
			}
			
			if(bet >= 1 && bet <= MAX_BET && bet <= User.getActiveUser().getAccountBalance()){
				
				inSession = true;
				play.setVisible(false);
				
				JOptionPane.showMessageDialog(null, "Good luck! (Pot: $" + displayNumber((long) (bet * 1.5)) +")", "Bet", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
			}
		}
	}
	
	/**
	 * A method to shorten the display of numbers. 1B is a lot better than 1000000000
	 * @param n - the number to display
	 * @return the String showing the shortened number if the number is > 999
	 */
	public static String displayNumber(long n){
		
		String s = String.valueOf(n);
		
		if(s.length() >= 4 && s.length() <=6 ){
			
			if(s.length() == 4){
				
				if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") ){
					return s.substring(0, s.length() - 3) + "." + s.substring(1, 4) + "K";
				} else if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 3) + "." + s.substring(1, 3) + "K";
				} else if(!(s.charAt(1) + "").equals("0")){
					return s.substring(0, s.length() - 3) + "." + s.substring(1, 2) + "K";
				} 
			} else if (s.length() == 5){
				
				if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") ){
					return s.substring(0, s.length() - 3) + "." + s.substring(2, 5) + "K";
				} else if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 3) + "." + s.substring(2, 4) + "K";
				} else if(!(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 3) + "." + s.substring(2, 3) + "K";
				} 
			} else if (s.length() == 6){
				
				if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") && !(s.charAt(5) + "").equals("0") ){
					return s.substring(0, s.length() - 3) + "." + s.substring(3, 6) + "K";
				} else if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0")){
					return s.substring(0, s.length() - 3) + "." + s.substring(3, 5) + "K";
				} else if(!(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 3) + "." + s.substring(3, 4) + "K";
				} 
			}

			return s.substring(0, s.length() - 3) + "K";
			
		} else if (s.length() >= 7 && s.length() <= 9){
			
			if(s.length() == 7){
				
				if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") ){
					return s.substring(0, s.length() - 6) + "." + s.substring(1, 4) + "M";
				} else if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 6) + "." + s.substring(1, 3) + "M";
				} else if(!(s.charAt(1) + "").equals("0")){
					return s.substring(0, s.length() - 6) + "." + s.substring(1, 2) + "M";
				} 
			} else if (s.length() == 8){
				
				if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") ){
					return s.substring(0, s.length() - 6) + "." + s.substring(2, 5) + "M";
				} else if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 6) + "." + s.substring(2, 4) + "M";
				} else if(!(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 6) + "." + s.substring(2, 3) + "M";
				} 
			} else if (s.length() == 9){
				
				if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") && !(s.charAt(5) + "").equals("0") ){
					return s.substring(0, s.length() - 6) + "." + s.substring(3, 6) + "M";
				} else if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0")){
					return s.substring(0, s.length() - 6) + "." + s.substring(3, 5) + "M";
				} else if(!(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 6) + "." + s.substring(3, 4) + "M";
				} 
			}

			return s.substring(0, s.length() - 6) + "M";
			
		} else if (s.length() >= 10  && s.length() <= 12){
			
			if(s.length() == 10){
				
				if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") ){
					return s.substring(0, s.length() - 9) + "." + s.substring(1, 4) + "B";
				} else if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 9) + "." + s.substring(1, 3) + "B";
				} else if(!(s.charAt(1) + "").equals("0")){
					return s.substring(0, s.length() - 9) + "." + s.substring(1, 2) + "B";
				} 
			} else if (s.length() == 11){
				
				if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") ){
					return s.substring(0, s.length() - 9) + "." + s.substring(2, 5) + "B";
				} else if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 9) + "." + s.substring(2, 4) + "B";
				} else if(!(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 9) + "." + s.substring(2, 3) + "B";
				} 
			} else if (s.length() == 12){
				
				if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") && !(s.charAt(5) + "").equals("0") ){
					return s.substring(0, s.length() - 9) + "." + s.substring(3, 6) + "B";
				} else if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0")){
					return s.substring(0, s.length() - 9) + "." + s.substring(3, 5) + "B";
				} else if(!(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 9) + "." + s.substring(3, 4) + "B";
				} 
			}
			
		
			return s.substring(0, s.length() - 9) + "B";
			

		} else if (s.length() >= 13 && s.length() <= 15){
			
			if(s.length() == 13){
				
				if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") ){
					return s.substring(0, s.length() - 12) + "." + s.substring(1, 4) + "T";
				} else if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 12) + "." + s.substring(1, 3) + "T";
				} else if(!(s.charAt(1) + "").equals("0")){
					return s.substring(0, s.length() - 12) + "." + s.substring(1, 2) + "T";
				} 
			} else if (s.length() == 14){
				
				if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") ){
					return s.substring(0, s.length() - 12) + "." + s.substring(2, 5) + "T";
				} else if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 12) + "." + s.substring(2, 4) + "T";
				} else if(!(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 12) + "." + s.substring(2, 3) + "T";
				} 
			} else if (s.length() == 15){
				
				if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") && !(s.charAt(5) + "").equals("0") ){
					return s.substring(0, s.length() - 12) + "." + s.substring(3, 6) + "T";
				} else if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0")){
					return s.substring(0, s.length() - 12) + "." + s.substring(3, 5) + "T";
				} else if(!(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 12) + "." + s.substring(3, 4) + "T";
				} 
			}
			
		
			return s.substring(0, s.length() - 12) + "T";
			
		}  else if (s.length() >= 16 && s.length() <= 18){
			
			if(s.length() == 16){
				
				if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") ){
					return s.substring(0, s.length() - 15) + "." + s.substring(1, 4) + "Q";
				} else if(!(s.charAt(1) + "").equals("0") && !(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 15) + "." + s.substring(1, 3) + "Q";
				} else if(!(s.charAt(1) + "").equals("0")){
					return s.substring(0, s.length() - 15) + "." + s.substring(1, 2) + "Q";
				} 
			} else if (s.length() == 17){
				
				if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") ){
					return s.substring(0, s.length() - 15) + "." + s.substring(2, 5) + "Q";
				} else if(!(s.charAt(2) + "").equals("0") && !(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 15) + "." + s.substring(2, 4) + "Q";
				} else if(!(s.charAt(2) + "").equals("0")){
					return s.substring(0, s.length() - 15) + "." + s.substring(2, 3) + "Q";
				} 
			} else if (s.length() == 18){
				
				if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0") && !(s.charAt(5) + "").equals("0") ){
					return s.substring(0, s.length() - 15) + "." + s.substring(3, 6) + "Q";
				} else if(!(s.charAt(3) + "").equals("0") && !(s.charAt(4) + "").equals("0")){
					return s.substring(0, s.length() - 15) + "." + s.substring(3, 5) + "Q";
				} else if(!(s.charAt(3) + "").equals("0")){
					return s.substring(0, s.length() - 15) + "." + s.substring(3, 4) + "Q";
				} 
			}
			
		
			return s.substring(0, s.length() - 15) + "Q";
			
		}
		
		return s;
	}
	
	/**
	 * A method to make typing in bets easier. 1M is a lot easier to type than 1000000
	 * @param s - the String to convert into a number
	 * @return the number after it has been processed
	 */
	public static long parseShortcuts(String s){
	
		long val = 0;
		long occurences = 0;
		String shortcut = "";
		boolean decimal = false;
		
		for(int i = s.length() - 1; i > -1; i--){
			
			if((s.charAt(i) + "").equals(".")){
				s.replace(".", "");
				decimal = true;
			}
			if((s.charAt(i) + "").equalsIgnoreCase("k")){
				shortcut += s.charAt(i) + "";
				s = s.substring(0, i);
				occurences++;
				
			} else if((s.charAt(i) + "").equalsIgnoreCase("m")){
				shortcut += s.charAt(i) + "";
				s = s.substring(0, i);
				occurences++;
			} else if((s.charAt(i) + "").equalsIgnoreCase("b")){
				shortcut += s.charAt(i) + "";
				s = s.substring(0, i);
				occurences++;
			} else if((s.charAt(i) + "").equalsIgnoreCase("t")){
				shortcut += s.charAt(i) + "";
				s = s.substring(0, i);
				occurences++;
			} else if((s.charAt(i) + "").equalsIgnoreCase("q")){
				shortcut += s.charAt(i) + "";
				s = s.substring(0, i);
				occurences++;
			}
		}
		
		
		try{
			if(decimal){
				
				val = (long) (Double.parseDouble(s));
				if(shortcut.equalsIgnoreCase("k")){
					val = (long) (Double.parseDouble(s)* 1000L);
				} else if(shortcut.equalsIgnoreCase("m")){
					val = (long) (Double.parseDouble(s) * 1000000L);
				} else if(shortcut.equalsIgnoreCase("b")){
					val = (long) (Double.parseDouble(s) * 1000000000L);
				} else if(shortcut.equalsIgnoreCase("t")){
					val = (long) (Double.parseDouble(s) * 1000000000000L);
				}else if(shortcut.equalsIgnoreCase("q")){
					val = (long) (Double.parseDouble(s) * 1000000000000000L);
				}
				
			} else if(!decimal){
				
				val = Long.parseLong(s);
				
				if(shortcut.equalsIgnoreCase("k")){
					val = val * 1000L;
				} else if(shortcut.equalsIgnoreCase("m")){
					val = val * 1000000L;
				} else if(shortcut.equalsIgnoreCase("b")){
					val = val * 1000000000L;
				} else if(shortcut.equalsIgnoreCase("t")){
					val = val * 1000000000000L;
				} else if(shortcut.equalsIgnoreCase("q")){
					val = val * 1000000000000000L;
				}
			}
			
		} catch (Exception e){
			
		}
		
		if(shortcut.length() > 1 || occurences > 1){
			
			JOptionPane.showMessageDialog(null, "Please note that you can only use one number shortcut when declaring your bet.", "Blackjack", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
			return -1;
		} else {
			return Long.valueOf(val);
		}
	}
	
	/**
	 * Checks to see if the user wants to play Blackjack again
	 * Will reset the game if they click yes and close Blackjack if they click no
	 */
	public void replay(){
		
		long replay = JOptionPane.showConfirmDialog(null, "Play again?", "Replay", JOptionPane.YES_NO_OPTION, 0, new ImageIcon("casinoicon.png"));
	
		if(replay == JOptionPane.YES_OPTION ){
			
			if(User.getActiveUser().getAccountBalance() > 0){
				wipe();
				startGame();
			} else {
				JOptionPane.showMessageDialog(null, "You don't have enough money to play Blackjack. How unfortunate.", "Blackjack", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
				this.dispose();
				stop();
				Casino.bjWindowsOpen = 0;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Thank you for playing blackjack!", "Blackjack", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
			this.dispose();
			stop();
			Casino.bjWindowsOpen = 0;
		}
	}
	
	/**
	 * Checks to see if the user has achieved Blackjack (Has a hand exactly equal to 21)
	 * @param hand - The hand to evaluate
	 * @return - true if the hand is equal to 21
	 * @return - false if the hand is not equal to 21
	 */
	public boolean checkBlackjack(long hand){
		if(hand == 21){
			return true;
		} else {
			return false;
		}
		
	}
	/**
	 * Reveals who won the round of Blackjack~!
	 */
	public void showOutcome(){
		
		if(dealerCardsDrawn == 5 && userCardsDrawn == 5 && !checkBust(houseHand) && !checkBust(userHand)){
			
			showPushMessage();
			replay();
			
		} else if (dealerCardsDrawn == 5 && userCardsDrawn != 5 && !checkBust(houseHand)){
			
			showLossMessage();
			replay();
			
		} else if (userCardsDrawn ==5 && dealerCardsDrawn != 5 && !checkBust(userHand)){
			
			showWinMessage();
			replay();
			
		} else {
			
			if(!checkBust(houseHand) && !checkBust(userHand)){
				
				if(userHand > houseHand){
					showWinMessage();
					replay();
					
				} if (userHand == houseHand){
					showPushMessage();
					replay();
					
				} if (userHand < houseHand){
					showLossMessage();
					replay();
					
				}
			}
			
			if(!checkBust(houseHand) && checkBust(userHand)){
				showLossMessage();
				replay();
				
			}
			if(checkBust(userHand) && checkBust(houseHand)){
				showPushMessage();
				replay();
				
			}
			if(!checkBust(userHand) && checkBust(houseHand)){
				showWinMessage();
				replay();
				
			}
		}
		
	}
	
	/**
	 * Tells the user how much money they've lost and adds the loss to their account
	 */
	public void showLossMessage(){
		JOptionPane.showMessageDialog(null, "Bad luck, you lost $" + displayNumber(bet) + ". Better luck next time!", "Blackjack", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		addLosings(bet);
	}
	
	/**
	 * Lets the user know that they lost because they tied and adds the loss to their account
	 */
	public void showPushMessage(){
		JOptionPane.showMessageDialog(null, "The Dealer wins by default since you tied. You lost $" + displayNumber(bet) + ".", "Blackjack", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		addLosings(bet);
	}
	
	/**
	 * Tells the user how much money they've won and adds the winnings to their account
	 */
	public void showWinMessage(){
		JOptionPane.showMessageDialog(null, "Congratulations " + User.getActiveUser().getUserID().replaceAll("_", " ") + ", you've won $" + displayNumber((long) (bet * 1.5)) + "!", "Blackjack", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		addWinnings((long) (bet * 1.5));
	}

	/**
	 * Stops the music that is being played
	 */
	public void stop(){
		clip.stop();
	}
	
	/**
	 * Loops the Blackjack sound track until interrupted
	 */
	public void loop(){
		try {
			clip = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(new File("blackjack/fortunereel(strings).wav"));
			clip.open(inputStream);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
        try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		} 
        
		//clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * ActionListener implementation
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == exit && !inSession){
			long close = JOptionPane.showConfirmDialog(null, "Do you really want to exit Blackjack?", "Exit Game", JOptionPane.YES_NO_OPTION, 0, new ImageIcon("casinoicon.png"));
		
			if(close == JOptionPane.YES_OPTION){
				this.dispose();
				stop();
				Casino.bjWindowsOpen = 0;
				
			} else {
				
			}
		}
		if(e.getSource() == play){
			
			startGame();
			
		}
		if(e.getSource() == rules){
			showRules();
		}
		if(e.getSource() == hit){
			if(inSession){
				hitUser();
			} else {
				
			}
		}
		if(e.getSource() == stay){
			
			if(userHand > 0){
				
				while(houseHand < 17){
					
					hitDealer();
				}
				
				if(!checkBust(userHand)){
					
					while(houseHand < userHand){
						
						hitDealer();
					}
				}
				showOutcome();
				
			} else {
				
				JOptionPane.showMessageDialog(null, "You can't stay if you haven't hit at least once!", "Blackjack", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
				
			}
			
			
			
		}

	}
	
	
	
	
	
}
