import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/** This class creates a reference object for accounts registered to use White Lily Casino
 * @author Mohamed Amadou
 * @since January 8, 2016
 */
public class User{
	
	//Initialization of inUse variables (would return a run-time error if not initialized globally)
	private boolean inUse = false, inDebt = false;
	private static User activeUser = null;
	private static ArrayList<User> registeredUsers = new ArrayList<User>();
	private long userNumber, numUsers, winnings = 0L, losings = 0L;
	private String userID, password, scenario = "default";
	private long startingAccountBalance = 10000, accountBalance = 10000L, numLoans = 0, debt = 0;
	private final long MAX_LOAN = 10000000, DEBT_CAP = 100000000;
	
	/**
	 * Constructor for Users created "legally" using the "create account" window
	 * @param acc - Credentials set by create acc window
	 */
	public User(Hashtable<String, String> acc) {
		String id = acc.get("user");
		String pw = acc.get("pass");
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		
		while(line != null){
			
			if(line.substring(0, line.indexOf("%")).equalsIgnoreCase(id)){
				inUse = true;
			}
			
			line = IO.readLine();
		}
		
		IO.closeInputFile();
		
		if(inUse == true){
			
			JOptionPane.showMessageDialog(null, "This User ID is currently in use!");
			
		} else if(inUse == false){
		
		
		IO.createOutputFile("users.txt", true);
		IO.println(id + "%" + pw + "%" + scenario + "%" + winnings + "%" + losings + "%" + accountBalance + "%" + startingAccountBalance + "%" + numLoans + "%" + debt);
		IO.closeOutputFile();
		
		
		registeredUsers.add(this);
		
		setUserID(id);
		setPassword(pw);
		
		numUsers++;
		userNumber = numUsers;
		
		JOptionPane.showMessageDialog(null, "Thank you for registering an account! Please log in.");
		}
	}
	
	/**
	 * Constructor method for users created "ilegally" within the program 
	 * @param userId - the user ID
	 * @param password - the user Password
	 */
	public User(String userId, String password){
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		
		while(line != null){
			if(line.substring(0, line.indexOf("%")).equalsIgnoreCase(userId)){
				inUse = true;
			}
			
			line = IO.readLine();
			
		}
		
		IO.closeInputFile();
		
		if(inUse){
			
			JOptionPane.showMessageDialog(null, "This User ID is currently in use!", "Error", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
			
		} else {
			
		IO.createOutputFile("users.txt", true);
		IO.println(userId + "%" + password);
		IO.closeOutputFile();
		
		
		registeredUsers.add(this);
		
		setUserID(userId);
		setPassword(password);
		
		numUsers++;
		userNumber = numUsers;
		
		}
	}
	
	/**
	 * Constructor method **ONLY** for use with User.loadUsers() method
	 * Creates object corresponding to information read in from users.txt by loadUsers() method
	 * @param userId - Existing user ID
	 * @param password - Existing password
	 * @param scenario - Existing scenario
	 * @param winnings - Existing winnings value
	 * @param losings - Existing losings value
	 * @param accountBalance - Existing account balance
	 * @param startingAccountBalance - Existing starting account balance
	 */
	public User(String userId, String password, String scenario, long winnings, long losings, long accountBalance, long startingAccountBalance, long numLoans, long debt){
		
		this.userID = userId;
		this.password = password;
		this.scenario = scenario;
		this.winnings = winnings;
		this.losings = losings;
		this.accountBalance = accountBalance;
		this.startingAccountBalance = startingAccountBalance;
		this.numLoans = numLoans;
		this.debt = debt;
		
		registeredUsers.add(this);
		
		numUsers++;
		userNumber = numUsers;
		
	}
	
	/**
	 * Setter method for User ID
	 * @param id - id to be set
	 */
	public void setUserID(String id){
		userID = id;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					String newLine = id + "%"  + tokens[1] + "%" + tokens[2] + "%" + tokens[3] + "%" + tokens[4] + "%" + tokens[5] + "%" + tokens[6] + "%" + tokens[7] + "%" + tokens[8];
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
			}
			line = IO.readLine();
		}
		
		newFile.setLength(newFile.length() - 1);
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}
	
	/**
	 * Getter method for User ID
	 * @return User ID
	 */
	public String getUserID(){
		return userID;
	}
	
	/**
	 * Setter method for password
	 * @param pw - password to be set
	 */
	public void setPassword(String pw){
		password = pw;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					String newLine = tokens[0] + "%"  + pw  + "%" + tokens[2] + "%" + tokens[3] + "%" + tokens[4] + "%" + tokens[5] + "%" + tokens[6] + "%" + tokens[7] + "%" + tokens[8];
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
			}
			
			line = IO.readLine();
		}
		
		newFile.setLength(newFile.length() - 1);
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}

	/**
	 * Getter method for password
	 * @return password
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Sets the scenario that the user plays according to
	 * @param scen - a final String that represents a playable scenario
	 */
	public void setScenario(String scen){
		scenario = scen;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					String newLine = tokens[0] + "%"  + tokens[1] + "%" + scen + "%"  + tokens[3] + "%" + tokens[4] + "%" + tokens[5] + "%" + tokens[6] + "%" + tokens[7] + "%" + tokens[8];
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
				line = IO.readLine();
			}
		}
		
		newFile.setLength(newFile.length() - 1);
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}

	/**
	 * Sets the winnings of a user account
	 * @param winnings2 - the new winnings value
	 */
	public void setWinnings(long winnings2) {
		winnings = winnings2;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					String newLine = tokens[0] + "%"  + tokens[1] + "%" + tokens[2] + "%"  + winnings2 + "%"  + tokens[4] + "%" + tokens[5] + "%" + tokens[6] + "%" + tokens[7] + "%" + tokens[8];
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
			}
			
			line = IO.readLine();
		}
		
		newFile.setLength(newFile.length() - 1);
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}

	/**
	 * @return - the amount of money this user has won at the casino
	 */
	public long getWinnings(){
		return winnings;
	}
	
	/**
	 * Sets the losings of a user account
	 * @param losings2 - the new losings value
	 */
	public void setLosings(long losings2) {
		losings = losings2;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					String newLine = tokens[0] + "%"  + tokens[1] + "%" + tokens[2] + "%"  + tokens[3] + "%" + losings2 + "%"  + tokens[5] + "%" + tokens[6] + "%" + tokens[7] + "%" + tokens[8];
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
			}
			
			line = IO.readLine();
		}
		
		newFile.setLength(newFile.length() - 1);
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}
	
	/**
	 * @return - the amount of money this user has lost at the casino
	 */
	public long getLosings(){
		return losings;
	}
	
	/**
	 * Sets the balance of a user account
	 * @param accountBalance2 - the new account balance
	 */
	public void setAccountBalance(long accountBalance2) {
		accountBalance = accountBalance2;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					String newLine = tokens[0] + "%"  + tokens[1] + "%" + tokens[2] + "%"  + tokens[3] + "%" + tokens[4] + "%" + accountBalance2 + "%"  + tokens[6] + "%" + tokens[7] + "%" + tokens[8];
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
			}
			
			line = IO.readLine();
		}
		
		newFile.setLength(newFile.length() - 1);
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}
	
	/**
	 * Getter method for account balance
	 * @return - Account Balance
	 */
	public long getAccountBalance() {
		return accountBalance;
	}
	
	/**
	 * Sets the starting account balance of an account (before winnings & losings)
	 * @param bal - the balance to set the balance to
	 */
	public void setStartingAccountBalance(long bal){
		startingAccountBalance = bal;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					String newLine = tokens[0] + "%" + tokens[1] + "%" +  tokens[2] + "%" +  tokens[3]+ "%" +  tokens[4]+ "%" +  tokens[5] + "%" + bal + "%" + tokens[7] + "%" + tokens[8];
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
			}
			line = IO.readLine();
		}
		
		newFile.setLength(newFile.length() - 1);
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}

	/**
	 * Getter method for debt
	 * @return - Debt
	 */
	public long getDebt(){
		return debt;
	}

	/**
	 * Sets the debt value for an account
	 * @param d - the new debt value
	 */
	public void setDebt(long d){
		debt = d;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					String newLine = tokens[0] + "%" + tokens[1] + "%" +  tokens[2] + "%" +  tokens[3]+ "%" +  tokens[4]+ "%" +  tokens[5] + "%" + tokens[6] + "%" + tokens[7] + "%" + d;
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
			}
			line = IO.readLine();
		}
		
		newFile.setLength(newFile.length() - 1);
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}
	
	/**
	 * Getter method for numLoans
	 * @return - numLoans
	 */
	public long getNumLoans(){
		return numLoans;
	}
	
	/**
	 * Sets the number of loans that the account has taken out
	 * @param l - the new numLoans value
	 */
	public void setNumLoans(long l){
		numLoans = l;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					String newLine = tokens[0] + "%" + tokens[1] + "%" +  tokens[2] + "%" +  tokens[3]+ "%" +  tokens[4]+ "%" +  tokens[5] + "%" + tokens[6] + "%" + l + "%" + tokens[8];
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
			}
			line = IO.readLine();
		}
		
		newFile.setLength(newFile.length() - 1);
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}
	
	//********************** START OF DYNAMIC METHODS **********************
	
	
	/**
	 * This method checks whether a player's account is out of money
	 * @return - true if accountBalance is equal to 0 or less, and false if not
	 */
	public boolean isOutOfMoney(){
		if(accountBalance <= 0){
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks whether a player is up, down, or even in terms of money made
	 * @return a String containing this information with respect to a specific user
	 */
	public String isUpOrDown(){
		if(winnings > losings){
			return "User " + userID + " is up! :-)";
		} else if(winnings < losings){
			return "User " + userID +  " is down. :-(";
		} 
		return "User " + userID + "is even.";
		
	}
	
	/**
	 * This method wipes the character's progress
	 */
	public void resetProgress(){
		IO.createOutputFile("users/" + userID + ".txt");
		IO.println(userID + "%" + password);
		IO.println(startingAccountBalance + "%" + "default");
		IO.println(0 + "%" + 0);
		IO.closeOutputFile();
		
		setUserID(userID);
		setPassword(password);
	}
	
	/**
	 * "Logs in" a user by setting it to the active user
	 */
	public void setActiveUser(){
		activeUser = this;
	}
	
	/**
	 * "Logs out" the active user
	 */
	public static void endSession(){
		activeUser = null;
	}
	/**
	 * This method is for use to find which user is currently logged in
	 * @return - the user that is currently logged in
	 */
	public static User getActiveUser(){
		return activeUser;
	}
	
	/**
	 * Checks to see if the parameters entered in the login text fields corresponds to a registered USER ID
	 * @param info - Info entered
	 * @return true if valid, false if not
	 */
	public static boolean isValidLogin(Hashtable<String, String> info){
		String id = info.get("user");
		String pw = info.get("pass");
		
		
		String reference = "";
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		
		while(line != null){
			
			
			if(line.substring(0, nthOccurence(line, "%", 1)).equalsIgnoreCase(id)){
				
				/*
				System.out.println(line.substring(0, nthOccurence(line, "%", 1)));
				System.out.println("USERID:" + id);
				System.out.println("LINE:" + line.substring(0, line.indexOf("%")));
				System.out.println(line.substring(0, line.indexOf("%")).equalsIgnoreCase(id));
				System.out.println("**********");
				*/
				
				reference = line;
				
			}
			
			line = IO.readLine();
		}
		
		IO.closeInputFile();
		
		if(reference.length() < 1){
			return false;
		}
		
		reference.replaceAll("_", " ");
		if(reference.substring(reference.indexOf("%") + 1 , nthOccurence(reference, "%", 2)).equals(pw)){
			return true;
		}
		
		/*
		System.out.println("PASSWORD:" + pw);
		System.out.println("REFERENCE:" + reference.substring(reference.indexOf("%") + 1, nthOccurence(reference, "%", 2)));
		System.out.println("MATCH:" + reference.substring(reference.indexOf("%") + 1, nthOccurence(reference, "%", 2)).equalsIgnoreCase(pw));
		System.out.println("**********");
		*/
		return false;
	}
	
	/**
	 * This method returns a user identifiable by name if it already exists within the registeredUsers list
	 * @param name - the name to identify the user by
	 * @return - the user object if exists, null if no such object exists
	 */
	public static User getUser(String name){
		for(int i = 0; i < registeredUsers.size(); i++){
			
			//System.out.println("INPUT ID:" + name);
			
			if(name.equalsIgnoreCase(registeredUsers.get(i).getUserID())){
				return registeredUsers.get(i);
			}
		}
		return null;
	}
	
	/**
	 *This static method loads all created users using file IO via the "users.txt" file
	 */
	public static void loadUsers(){
		
		long counter = 1;
		
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		
		while(line != null && line.length() > 5){
			System.out.println("**LOADING USER #" + counter + "**");
			String usn = line.substring(0, line.indexOf("%"));
			System.out.println("USERNAME: " + usn.replaceAll("_", " "));
			String pass = line.substring(line.indexOf("%") + 1, nthOccurence(line, "%", 2));
			System.out.println("PASSWORD: " + pass);
			String scenario = line.substring(nthOccurence(line, "%", 2) + 1, nthOccurence(line, "%", 3));
			System.out.println("SCENARIO: " + scenario);
			long winnings = Long.parseLong(line.substring(nthOccurence(line, "%", 3) + 1, nthOccurence(line, "%", 4)));
			System.out.println("WINNINGS: $" + Blackjack.displayNumber(winnings));
			long losings = Long.parseLong(line.substring(nthOccurence(line, "%", 4) + 1, nthOccurence(line, "%", 5)));
			System.out.println("LOSINGS: $" + Blackjack.displayNumber(losings));
			long accountBalance = Long.parseLong(line.substring(nthOccurence(line, "%", 5) + 1, nthOccurence(line, "%", 6)));
			System.out.println("ACCOUNT BALANCE: $" + Blackjack.displayNumber(accountBalance));
			long startingAccountBalance = Long.parseLong(line.substring(nthOccurence(line, "%", 6) + 1, nthOccurence(line, "%", 7)));
			System.out.println("STARTING ACCOUNT BALANCE: $" + Blackjack.displayNumber(startingAccountBalance));
			long numLoans = Long.parseLong(line.substring(nthOccurence(line, "%", 7) + 1, nthOccurence(line, "%", 8)));
			System.out.println("NUMBER OF LOANS TAKEN OUT: $" + Blackjack.displayNumber(numLoans));
			long debt = Long.parseLong(line.substring(nthOccurence(line, "%", 8) + 1, line.length()));
			System.out.println("MONEY OWED: $" + Blackjack.displayNumber(debt));
			System.out.println("**********");
			
			new User(usn, pass, scenario, winnings, losings, accountBalance, startingAccountBalance, numLoans, debt);
			counter++;
			line = IO.readLine();
		}
		
		IO.closeInputFile();
		
	}
	
	/**
	 * This class - used in conjunction with the setter methods - overwrites 
	 * the users.txt file to change values
	 * @param param - the number representing which value to change(which "%" marker to write from)
	 * @param newValue - the new value to replace that field
	 */
	public void modifyParam(int param, String newValue){
		IO.openInputFile("users.txt");
		String line = IO.readLine();
		StringBuilder newFile = new StringBuilder();
		
		while(line != null){
			String tokens[] = line.split("%");
			
			if(tokens.length > 0){
				
				if(tokens[0].equalsIgnoreCase(this.getUserID())){
					tokens[param] = newValue;
					
					String newLine = tokens[0] + tokens[1]+ tokens[2]+ tokens[3]+ tokens[4]+ tokens[5]+ tokens[6];
					
					newFile.append(newLine);
					newFile.append("\n");
				} else {
					newFile.append(line);
					newFile.append("\n");
				}
				
			}
		}
		
		IO.closeInputFile();
		IO.createOutputFile("users.txt");
		IO.println(newFile.toString());
		IO.closeOutputFile();
		
	}

	/**
	 * This static method returns the specified occurrence of a string within a given string
	 * @param s - reference string to search for index
	 * @param o - string to be found within reference
	 * @param n - nth occurrence
	 * @return
	 */
	public static int nthOccurence(String s, String o, long n){
		long occurence = 1;
		int i = -1;
		
		while(occurence <= n && i < s.length()){
			i++;
			if((s.charAt(i) + "").equals(o)){
				
				occurence++;
			}
		}
		
		return i;
	}

	/**
	 * Adds loan to debt and increments numLoans
	 */
	public void takeOutLoan(){
		
		if(numLoans == 0 && debt <= DEBT_CAP){
			int selection = JOptionPane.showConfirmDialog(null, "Do you really want to take out a loan?", "Loan Manager", 0, JOptionPane.OK_CANCEL_OPTION, new ImageIcon("casinoicon.png"));
		
			if(selection == JOptionPane.YES_OPTION){
				JPanel panel = new JPanel(new MigLayout());
				
				JPanel label = new JPanel(new MigLayout());
				label.add(new JLabel("Loan"), "cell 0 0, wrap");
				panel.add(label);
				
				JPanel controls = new JPanel(new MigLayout());
				JTextField loan = new JTextField();
				loan.setColumns(25);
				controls.add(loan , "wrap");
				panel.add(controls);
				
				int goThrough = JOptionPane.showConfirmDialog(null, panel, "Loan Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
				
				long loanValue = 0;
				
				if(goThrough == JOptionPane.OK_OPTION){
					
					try{
						loanValue = Blackjack.parseShortcuts(loan.getText());
						
					} catch(NumberFormatException e){
						loanValue = -1;
						
					}
					
					while(loanValue <= 0 || loanValue > MAX_LOAN){
						
						try{
							if(loanValue > MAX_LOAN || loanValue <= 0){
								JOptionPane.showMessageDialog(null, "Your loan can only be from $1 - $" + Blackjack.displayNumber(MAX_LOAN), "Loan Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
							}
							
							int confirm = JOptionPane.showConfirmDialog(null, panel, "Loan Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
							
							if(confirm == JOptionPane.CLOSED_OPTION || confirm == JOptionPane.CANCEL_OPTION){
								return;
							}
							
							loanValue = Blackjack.parseShortcuts(loan.getText());
						
							
						} catch(NumberFormatException e){
						
							loanValue = -1;
						}
					}
						
						
					if(loanValue >=1 && loanValue <= MAX_LOAN){
						JOptionPane.showMessageDialog(null, "Congratulations on taking out your first loan of $" + Blackjack.displayNumber(loanValue) + "!", "Loan Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
						
						setAccountBalance(getAccountBalance() + loanValue);
						setDebt(getDebt() + loanValue);
						setNumLoans(getNumLoans() + 1);
						inDebt = true;
					}
					
				}

			}

		} else if (numLoans > 0 && numLoans < 5 && debt <= DEBT_CAP){
			
			int selection = JOptionPane.showConfirmDialog(null, "Do you really want to take out a loan?", "Loan Manager", 0, JOptionPane.OK_CANCEL_OPTION, new ImageIcon("casinoicon.png"));
			
			if(selection == JOptionPane.YES_OPTION){
				JPanel panel = new JPanel(new MigLayout());
				
				JPanel label = new JPanel(new MigLayout());
				label.add(new JLabel("Loan"), "cell 0 0, wrap");
				panel.add(label);
				
				JPanel controls = new JPanel(new MigLayout());
				JTextField loan = new JTextField();
				loan.setColumns(25);
				controls.add(loan , "wrap");
				panel.add(controls);
				
				int goThrough = JOptionPane.showConfirmDialog(null, panel, "Loan Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
				
				long loanValue = 0;
				
				if(goThrough == JOptionPane.OK_OPTION){
					
					try{
						loanValue = Blackjack.parseShortcuts(loan.getText());
						
					} catch(NumberFormatException e){
						loanValue = -1;
						
					}
					
					while(loanValue <= 0 || loanValue > MAX_LOAN){
						
						try{
							if(loanValue > MAX_LOAN || loanValue <= 0){
								JOptionPane.showMessageDialog(null, "Your loan can only be from $1 - $" + Blackjack.displayNumber(MAX_LOAN), "Loan Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
							}
							
							int confirm = JOptionPane.showConfirmDialog(null, panel, "Loan Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
							
							if(confirm == JOptionPane.CLOSED_OPTION || confirm == JOptionPane.CANCEL_OPTION){
								return;
							}
							
							loanValue = Blackjack.parseShortcuts(loan.getText());
						
							
						} catch(NumberFormatException e){
						
							loanValue = -1;
						}
					}
						
						
					if(loanValue >=1 && loanValue <= MAX_LOAN){
						JOptionPane.showMessageDialog(null, "You have successfully taken out a loan of $" + Blackjack.displayNumber(loanValue) + "!", "Loan Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
						
						setAccountBalance(getAccountBalance() + loanValue);
						setDebt(getDebt() + loanValue);
						setNumLoans(getNumLoans() + 1);
						inDebt = true;
					}
					
				}

			}
		} else if (numLoans >= 5 && numLoans < 10 && debt <= DEBT_CAP){
			
			int selection = JOptionPane.showConfirmDialog(null, "Do you really want to take out a loan? You've taken out quite a few.", "Loan Manager", 0, JOptionPane.OK_CANCEL_OPTION, new ImageIcon("casinoicon.png"));
			
			if(selection == JOptionPane.YES_OPTION){
				JPanel panel = new JPanel(new MigLayout());
				
				JPanel label = new JPanel(new MigLayout());
				label.add(new JLabel("Loan"), "cell 0 0, wrap");
				panel.add(label);
				
				JPanel controls = new JPanel(new MigLayout());
				JTextField loan = new JTextField();
				loan.setColumns(25);
				controls.add(loan , "wrap");
				panel.add(controls);
				
				int goThrough = JOptionPane.showConfirmDialog(null, panel, "Loan Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
				
				long loanValue = 0;
				
				if(goThrough == JOptionPane.OK_OPTION){
					
					try{
						loanValue = Blackjack.parseShortcuts(loan.getText());
						
					} catch(NumberFormatException e){
						loanValue = -1;
						
					}
					
					while(loanValue <= 0 || loanValue > MAX_LOAN){
						
						try{
							if(loanValue > MAX_LOAN || loanValue <= 0){
								JOptionPane.showMessageDialog(null, "Your loan can only be from $1 - $" + Blackjack.displayNumber(MAX_LOAN), "Loan Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
							}
							
							int confirm = JOptionPane.showConfirmDialog(null, panel, "Loan Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
							
							if(confirm == JOptionPane.CLOSED_OPTION || confirm == JOptionPane.CANCEL_OPTION){
								return;
							}
							
							loanValue = Blackjack.parseShortcuts(loan.getText());
						
							
						} catch(NumberFormatException e){
						
							loanValue = -1;
						}
					}
						
						
					if(loanValue >=1 && loanValue <= MAX_LOAN){
						JOptionPane.showMessageDialog(null, "You have taken out a loan of $" + Blackjack.displayNumber(loanValue) + ".", "Loan Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
						
						setAccountBalance(getAccountBalance() + loanValue);
						setDebt(getDebt() + loanValue);
						setNumLoans(getNumLoans() + 1);
						inDebt = true;
					}
					
				}

			}
		} else if(numLoans >= 10 && debt <= DEBT_CAP){
			
			int selection = JOptionPane.showConfirmDialog(null, "Do you really want to take out a loan? You've borrowed quite the amount.", "Loan Manager", 0, JOptionPane.OK_CANCEL_OPTION, new ImageIcon("casinoicon.png"));
			
			if(selection == JOptionPane.YES_OPTION){
				JPanel panel = new JPanel(new MigLayout());
				
				JPanel label = new JPanel(new MigLayout());
				label.add(new JLabel("Loan"), "cell 0 0, wrap");
				panel.add(label);
				
				JPanel controls = new JPanel(new MigLayout());
				JTextField loan = new JTextField();
				loan.setColumns(25);
				controls.add(loan , "wrap");
				panel.add(controls);
				
				int goThrough = JOptionPane.showConfirmDialog(null, panel, "Loan Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
				
				long loanValue = 0;
				
				if(goThrough == JOptionPane.OK_OPTION){
					
					try{
						loanValue = Blackjack.parseShortcuts(loan.getText());
						
					} catch(NumberFormatException e){
						loanValue = -1;
						
					}
					
					while(loanValue <= 0 || loanValue > MAX_LOAN){
						
						try{
							if(loanValue > MAX_LOAN || loanValue <= 0){
								JOptionPane.showMessageDialog(null, "Your loan can only be from $1 - $" + Blackjack.displayNumber(MAX_LOAN), "Loan Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
							}
							
							int confirm = JOptionPane.showConfirmDialog(null, panel, "Loan Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
							
							if(confirm == JOptionPane.CLOSED_OPTION || confirm == JOptionPane.CANCEL_OPTION){
								return;
							}
							
							loanValue = Blackjack.parseShortcuts(loan.getText());
						
							
						} catch(NumberFormatException e){
						
							loanValue = -1;
						}
					}
						
						
					if(loanValue >=1 && loanValue <= MAX_LOAN){
						JOptionPane.showMessageDialog(null, "You have successfully taken out a loan of $" + Blackjack.displayNumber(loanValue) + ".", "Loan Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
						
						setAccountBalance(getAccountBalance() + loanValue);
						setDebt(getDebt() + loanValue);
						setNumLoans(getNumLoans() + 1);
						inDebt = true;
					}
					
				}

			}
		} else {
			
			JOptionPane.showMessageDialog(null, "You already owe more than $" + Blackjack.displayNumber(DEBT_CAP) + ". Please pay the debt before taking out another loan.", "Debt Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		}
	}
	
	/**
	 * Subtracts an amount from debt if the user owes money
	 */
	public void payLoan(){
		
		if(debt > 0){
			
			int selection = JOptionPane.showConfirmDialog(null, "Do you really want to pay a debt?", "Debt Manager", 0, JOptionPane.OK_CANCEL_OPTION, new ImageIcon("casinoicon.png"));
			
			if(selection == JOptionPane.YES_OPTION){
				JPanel panel = new JPanel(new MigLayout());
				
				JPanel label = new JPanel(new MigLayout());
				label.add(new JLabel("Pay"), "cell 0 0, wrap");
				panel.add(label);
				
				JPanel controls = new JPanel(new MigLayout());
				JTextField debtField = new JTextField();
				debtField.setColumns(25);
				controls.add(debtField , "wrap");
				panel.add(controls);
				
				int goThrough = JOptionPane.showConfirmDialog(null, panel, "Debt Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
				
				long debtValue = 0;
				
				if(goThrough == JOptionPane.OK_OPTION){
					
					try{
						debtValue = Blackjack.parseShortcuts(debtField.getText());
						
					} catch(NumberFormatException e){
						debtValue = -1;
						
					}
					
					while(debtValue <= 0 || debtValue > debt || debtValue > accountBalance){
						
						try{
							if(debtValue > debt){
								JOptionPane.showMessageDialog(null, "You can't pay more than you owe! You owe $" + Blackjack.displayNumber(debt) +".", "Debt Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
							}
							
							if(debtValue > accountBalance){
								JOptionPane.showMessageDialog(null, "You don't have enough money to do that. Your account balance is $" + Blackjack.displayNumber(accountBalance) +".", "Debt Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
							}
							
							int confirm = JOptionPane.showConfirmDialog(null, panel, "Debt Manager", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
							
							if(confirm == JOptionPane.CLOSED_OPTION || confirm == JOptionPane.CANCEL_OPTION){
								return;
							}
							
							debtValue = Blackjack.parseShortcuts(debtField.getText());
						
							
							
						} catch(NumberFormatException e){
						
							debtValue = -1;
						}
					}
						
						
					if(debtValue >=1 && debtValue <= debt && debtValue <= accountBalance){
						JOptionPane.showMessageDialog(null, "Thank for paying back $" + Blackjack.displayNumber(debtValue) + "!", "Debt Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
						setAccountBalance(getAccountBalance() - debtValue);
						setDebt(getDebt() - debtValue);
					}
					
					if(debt == 0){
						inDebt = false;
					}
					
				}

			}
			
		} else {
			
			JOptionPane.showMessageDialog(null, "You don't owe any money!", "Debt Manager", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		}
	}
}//end of User
