import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import net.miginfocom.swing.MigLayout;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Casino extends JFrame implements ActionListener {

	public static Casino wlc;
	public JButton start, settings, scenario, music, backsui, backstart, login, 
	logout, createuser, blackjack, tcm, oddeven, slots, hit, stay, rules, 
	exit, getloan, payloan, balance, winnings, losings, loans, debt, creds;
	public static JLabel outline = new JLabel();
	public JLabel loanarea;
	private JRadioButton inf, reg;
	public boolean defaultSettings = true, beingPlayed = true, loggedIn = false;
	public final String infSetting = "INFINITE_DECK", regSetting = "NORMAL_DECK";
	public AudioInputStream inputStream = null;
	public Clip clip = null;
	public JPanel menus = new JPanel(new MigLayout("insets 0")), mainUI, startUI, settingsUI, scenarioUI, blackjackUI, oddorevenUI, tcmUI, overorunderUI, slotsUI;
	public MigLayout mig = new MigLayout("insets 0");
	public static int bjWindowsOpen = 0;
	public Blackjack blackjackGame;
	
	/**
	 * Constructor method
	 */
	
	public Casino(){
		
		//Credit to: http://stackoverflow.com/questions/11232131/centering-a-jframe for window centering algorithm
		Dimension ss = Toolkit.getDefaultToolkit ().getScreenSize ();
		Dimension frameSize = new Dimension ( 780, 700 );

		this.setBounds ( ss.width / 2 - frameSize.width / 2, 
		                 ss.height / 2 - frameSize.height / 2,
		                 frameSize.width, frameSize.height );
		
        //Adds menus to the JFrame that Casino extends
		this.add(menus);
		
        mainUI = new JPanel(new MigLayout("insets 0"));
        buildMainUI(mainUI);
        //Sets it as the current layout
        menus.add(mainUI);
        
        startUI = new JPanel(new MigLayout("insets 0"));
        buildStartUI(startUI);
        
        
        
        //Sets the display to the parent container 'menus'
        mig.layoutContainer(menus);
       
        //Plays the main menu audio, entitled "A Selfish Want - by Shinji Tamura"
		loop();
        

        setSize(780, 700);
		setResizable(false);
        setLayout(mig);
        setTitle("White Lily Casino");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
	}
	

	/**
	 * Retrieves settings selected
	 * @return settings value selected (either infSetting or regSetting)
	 */
	public String getSettings(){
		String settings = "";
		JPanel panel = new JPanel(new MigLayout());
		JPanel labels = new JPanel(new MigLayout());
		labels.add(new JLabel("Infinite Deck"), "pos 25px 0px");
		labels.add(new JLabel("Regular Deck"), "pos 130px 0px");
		
		ButtonGroup group = new ButtonGroup();
		inf = new JRadioButton();
		labels.add(inf, "pos 0px 0px");
		reg = new JRadioButton();
		group.add(inf);
		group.add(reg);
		labels.add(reg, "pos 105px 0px");
		
		panel.add(labels);
		
		//JOptionPane.showConfirmDialog(this, panel, "CHANGE SETTINGS", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));
		JOptionPane.showMessageDialog(null, "This feature has been removed due to the high \nunlikelihood of victory with an infinite deck.\nThe team at WLC apologizes for the \ninconvenience.", "Locked Feature", JOptionPane.OK_CANCEL_OPTION, new ImageIcon("casinoicon.png"));
		
		if(defaultSettings == false){
			return infSetting;
		}
		
		return regSetting;
	}
	/**
	 * Stops music being played
	 */
	public void stop(){
		clip.stop();
		beingPlayed = false;
	}
	
	/**
	 * Loops music continuously until interrupted
	 */
	public void loop(){
		
		try {
			clip = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(new File("57.wav"));
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
		beingPlayed = true;
	}
	
	/**
	 * Builds the components for the mainUI and displays them
	 * @param mainUI - the panel to build components to
	 */
	public void buildMainUI(JPanel mainUI){
		
	    getContentPane().add(mainUI);
	    mainUI.setBackground(new Color(53, 9, 9));
	    
	    creds = new JButton(new ImageIcon("creds.png"));
	    creds.setBorder(null);
	    creds.addActionListener(this);
	    mainUI.add(creds, "pos 90px 342px");
	    
	    balance = new JButton(new ImageIcon("balance.png"));
	    balance.setBorder(null);
	    balance.addActionListener(this);
	    mainUI.add(balance, "pos 300px 575px");
	    balance.setVisible(false);
	    
	    winnings = new JButton(new ImageIcon("winnings.png"));
	    winnings.setBorder(null);
	    winnings.addActionListener(this);
	    mainUI.add(winnings, "pos 10px 575px");
	    winnings.setVisible(false);
	    
	    losings = new JButton(new ImageIcon("losings.png"));
	    losings.setBorder(null);
	    losings.addActionListener(this);
	    mainUI.add(losings, "pos 155px 575px");
	    losings.setVisible(false);
	    
	    loans = new JButton(new ImageIcon("numloans.png"));
	    loans.setBorder(null);
	    loans.addActionListener(this);
	    mainUI.add(loans, "pos 475px 575 px");
	    loans.setVisible(false);
	    
	    debt = new JButton(new ImageIcon("debt.png"));
	    debt.setBorder(null);
	    debt.addActionListener(this);
	    mainUI.add(debt, "pos 615px 575px");
	    debt.setVisible(false);
		
	    login = new JButton(new ImageIcon("login.png"));
	    login.setBackground(new Color(53, 9, 9));
	    login.setBorder(null);
	    login.addActionListener(this);
	    mainUI.add(login, "pos 100px 175px");
	    
	    logout = new JButton(new ImageIcon("logout.png"));
	    logout.setBorder(null);
	    logout.addActionListener(this);
	    mainUI.add(logout, "pos 100px 175px");
	    logout.setVisible(false);
	    
	    loanarea = new JLabel(new ImageIcon("loanarea.png"));
	    loanarea.setBorder(null);
	    mainUI.add(loanarea, "pos 460px 130px");
	    loanarea.setVisible(false);
	    
	    getloan = new JButton(new ImageIcon("gloan.png"));
	    getloan.setBorder(null);
	    getloan.addActionListener(this);
	    mainUI.add(getloan, "pos 520px 250px" );
	    getloan.setVisible(false);
	    
	    payloan = new JButton(new ImageIcon("ploan.png"));
	    payloan.setBorder(null);
	    payloan.addActionListener(this);
	    mainUI.add(payloan, "pos 520px 330px" );
	    payloan.setVisible(false);
	    
	    createuser = new JButton(new ImageIcon("createuser.png"));
	    createuser.setBackground(new Color(53,9,9));
	    createuser.setBorder(null);
	    createuser.addActionListener(this);
	    mainUI.add(createuser, "pos 95px 245px");
	        
	    //Background items
	    JLabel title = new JLabel(new ImageIcon("title.png"));
	    mainUI.add(title, "pos 10px 0px");
	        
	    JLabel border = new JLabel(new ImageIcon("mainscreenborder.png"));
	    mainUI.add(border, "pos 0px 444px");
	        
	    JLabel filler = new JLabel(new ImageIcon("mainscreenfiller.png"));
	    mainUI.add(filler, "pos 0px 550px");
		settings = new JButton();
        ImageIcon s = new ImageIcon("settings-button.png");
        settings.setBackground(new Color(53, 9, 9));
        settings.setIcon(s);
        settings.addActionListener(this);
        settings.setBorder(null);
        mainUI.add(settings, "pos 300 150");
        
        music = new JButton();
        ImageIcon m = new ImageIcon("music-button.png");
        music.setBackground(new Color(53, 9, 9));
        music.setIcon(m);
        music.addActionListener(this);
        music.setBorder(null);
        mainUI.add(music, "pos 300 213");
        
        scenario = new JButton();
        ImageIcon sc = new ImageIcon("scenario-button.png");
        scenario.setBackground(new Color(53, 9, 9));
        scenario.setIcon(sc);
        scenario.addActionListener(this);
        scenario.setBorder(null);
        mainUI.add(scenario, "pos 300 279");
        
        start = new JButton();
        ImageIcon st = new ImageIcon("start-button.png");
        start.setBounds(320, 404, 122, 63);
        start.setBackground(new Color(53, 9, 9));
        start.setIcon(st);
        start.addActionListener(this);
        start.setBorder(null);
        mainUI.add(start, "pos 300 342");
	}
	
	/**
	 * Builds the components for the games menu UI
	 * @param startUI - the panel to build components to
	 */
	public void buildStartUI(JPanel startUI){
		getContentPane().add(startUI);
	    startUI.setBackground(new Color(53, 9, 9));
	    
	    blackjack = new JButton(new ImageIcon("start/blackjack-button.png"));
	    blackjack.setBackground(new Color(53, 9, 9));
	    blackjack.setBorder(null);
	    blackjack.addActionListener(this);
	    startUI.add(blackjack, "pos 50px 100px");
	    
	    tcm = new JButton(new ImageIcon("start/tcm.png"));
	    tcm.setBackground(new Color(53, 9, 9));
	    tcm.setBorder(null);
	    //startUI.add(tcm, "pos 50px 230px");
	    
	    oddeven = new JButton(new ImageIcon("start/oddevenbutton.png"));
	    oddeven.setBackground(new Color(53,9,9));
	    oddeven.setBorder(null);
	    //startUI.add(oddeven, "pos 50px 360px");
	    
	    slots = new JButton(new ImageIcon("start/slots.png"));
	    slots.setBackground(new Color(53, 9, 9));
	    slots.setBorder(null);
	    //startUI.add(slots, "pos 50px 490px");
	    
	    JLabel games = new JLabel(new ImageIcon("start/gamesmenu.png"));
	    startUI.add(games, "pos 0px 0px");
	    
	    backsui = new JButton(new ImageIcon("start/back.png"));
	    backsui.setBorder(null);
	    backsui.addActionListener(this);
	    startUI.add(backsui, "pos 694px 621px");
	}
	
	/**
	 * Displays a UI
	 * @param index - the UI to be displayed
	 */
	public void showStep(int index){
		menus.removeAll();

	    switch (index) {
	        case 0:
	            menus.add(mainUI);
	            break;

	        case 1:
	            menus.add(startUI);
	            break;

	        }

	    menus.validate();
	    menus.repaint();
}
	
	/**
	 * Retrieves login details
	 * @return The login info typed by user
	 */
	
	public Hashtable<String, String> loginInfo(){
	Hashtable<String, String> loginfo = new Hashtable<String, String>();
	JPanel panel = new JPanel(new MigLayout());
	
	JPanel label = new JPanel(new MigLayout());
	label.add(new JLabel("User ID"), "cell 0 0, wrap");
	label.add(new JLabel("Password"));
	panel.add(label);
	
	JPanel controls = new JPanel(new MigLayout());
	JTextField username = new JTextField();
	username.setColumns(25);
	controls.add(username , "wrap");
	JPasswordField password = new JPasswordField();
	password.setColumns(25);
	controls.add(password);
	panel.add(controls, "wrap");
	
	JOptionPane.showConfirmDialog(this, panel, "LOG-IN", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));

	String user = username.getText();
	String pass = new String(password.getPassword());
	
	if(user != null && pass != null ){
		
		 loginfo.put("user", username.getText().replaceAll(" ", "_"));
		 loginfo.put("pass", new String(password.getPassword()));
		 
	} else {
		
		loginfo.put("user", "");
	    loginfo.put("pass", "");
	    
	}
	
    return loginfo;
	}

	/**
	 * Sets the user with the details equal to info as the active user and replaces login button with a logout button
	 * @param info - Hashtable of user info
	 */
	public void login(Hashtable<String, String> info){
		String id = info.get("user");
		
		if(id.length() > 0){
			
			User.getUser(id).setActiveUser();
	
	
			JOptionPane.showMessageDialog(null, "	Welcome, " + User.getActiveUser().getUserID().replaceAll("_", " ") + "." + "\nYour Current Account Balance is : $" + Blackjack.displayNumber(User.getActiveUser().getAccountBalance()) + ".", "Logged In", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
			loggedIn = true;
			login.setVisible(false);
			logout.setVisible(true);
			loanarea.setVisible(true);
			getloan.setVisible(true);
			payloan.setVisible(true);
			winnings.setVisible(true);
			losings.setVisible(true);
			balance.setVisible(true);
			loans.setVisible(true);
			debt.setVisible(true);
			
		} else {
			
		}
	}
	/**
	 * Logs out the active user and replaces logout button with log in button
	 */
	public void logout(){
		int choice = JOptionPane.showConfirmDialog(null, "Do you really want to log out?", "End Session", JOptionPane.YES_NO_OPTION, 0, new ImageIcon("casinoicon.png"));
		
		if(choice == JOptionPane.YES_OPTION){

			JOptionPane.showMessageDialog(null, "We hope you enjoyed your stay at White Lily Casino!", "End Session", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
			loggedIn = false;
			logout.setVisible(false);
			login.setVisible(true);
			loanarea.setVisible(false);
			getloan.setVisible(false);
			payloan.setVisible(false);
			winnings.setVisible(false);
			losings.setVisible(false);
			balance.setVisible(false);
			loans.setVisible(false);
			debt.setVisible(false);
			
			User.endSession();
		}
	}
	
	/**
	 * Creates a new user based on information typed into textfield and password after validating whether or not the name is in use
	 */
	public void createUser(){
		Hashtable<String, String> loginfo = new Hashtable<String, String>();
		JPanel panel = new JPanel(new MigLayout());
		
		JPanel label = new JPanel(new MigLayout());
		label.add(new JLabel("User ID"), "cell 0 0, wrap");
		label.add(new JLabel("Password"));
		panel.add(label);
		
		JPanel controls = new JPanel(new MigLayout());
		JTextField username = new JTextField();
		username.setColumns(25);
		controls.add(username , "wrap");
		JPasswordField password = new JPasswordField();
		password.setColumns(25);
		controls.add(password);
		panel.add(controls, "wrap");
		
		JOptionPane.showConfirmDialog(this, panel, "CREATE USER", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon("casinoicon.png"));

		String user = username.getText();
		String pass = new String(password.getPassword());
		
		if(user != null && pass != null ){
			
			 loginfo.put("user", username.getText().replaceAll(" ", "_"));
			 loginfo.put("pass", new String(password.getPassword()));
			 
		} else {
			
			loginfo.put("user", "");
		    loginfo.put("pass", "");
		    
		}
		
		if(user.length() > 1 && pass.length() > 1){
			new User(loginfo);
	
		} else {
		
		
		
		}
	}
	
	/**
	 * Displays credits in a new pop-up JFrame
	 */
	public void showCredits(){
		JPanel credits = new JPanel(new MigLayout("insets 0"));
		JFrame creditsFrame = new JFrame();
		
		creditsFrame.add(credits);
		
		JLabel pic = new JLabel(new ImageIcon("credits.png"));
		pic.setBorder(null);
		credits.add(pic, "pos 0px 0px");
		
		
		creditsFrame.setSize(400, 675);
		creditsFrame.setResizable(false);
		creditsFrame.setLayout(mig);
		creditsFrame.setTitle("Credits");
		creditsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		creditsFrame.setVisible(true); 
	}
	/**
	 * ActionListener implementation
	 */
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == settings){
			getSettings();
		}
		if(e.getSource() == scenario){
			JOptionPane.showMessageDialog(null, "This feature has not yet been added.", "Locked Feature", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		}
		if(e.getSource() == start){
			
			if(loggedIn){
				
				stop();
				showStep(1);
				repaint();
				revalidate();
				
			} else {
				
				JOptionPane.showMessageDialog(null, "Kindly log in to gamble at White Lily Casino.", null, JOptionPane.ERROR_MESSAGE, new ImageIcon("casinoicon.png"));
				
			}
		}
		if(e.getSource() == music){
			if(beingPlayed){
				stop();
			}
			else {
				loop();
			}
	
		}
		if(e.getSource() == backsui){
			loop();
			
			showStep(0);
		}
		
		if(e.getSource() == createuser){
			createUser();
		}
		if(e.getSource() == login){
			
			Hashtable<String, String> loginf = loginInfo();
			
			if(User.isValidLogin(loginf)){
				
				login(loginf);
			} else {
				JOptionPane.showMessageDialog(null, "Sorry, an invalid username or password has been entered.", "Invalid Credentials", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
			}
		}
		if(e.getSource() == blackjack){
			
			if(bjWindowsOpen < 1){
				bjWindowsOpen++;
				blackjackGame = new Blackjack();
				
			} else {
				
			}
			
			
		}
		if(e.getSource() == inf){
			defaultSettings = false;
		}
		if(e.getSource() == reg){
			defaultSettings = true;
		}
		if(e.getSource() == logout){
			logout();
		}
		if(e.getSource() == getloan){
			User.getActiveUser().takeOutLoan();
		}
		if(e.getSource() == payloan){
			User.getActiveUser().payLoan();
		}
		if(e.getSource() == creds){
			showCredits();
		}
		if(e.getSource() == winnings){
			JOptionPane.showMessageDialog(null, "You have won $" + Blackjack.displayNumber(User.getActiveUser().getWinnings()) + " at the White Lily Casino.", "White Lily Casino", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		}
		if(e.getSource() == losings){
			JOptionPane.showMessageDialog(null, "You have lost $" + Blackjack.displayNumber(User.getActiveUser().getLosings()) + " at the White Lily Casino.", "White Lily Casino", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		}
		if(e.getSource() == balance){
			JOptionPane.showMessageDialog(null, "Your account balance is $" + Blackjack.displayNumber(User.getActiveUser().getAccountBalance()) + ".", "White Lily Casino", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
		}
		if(e.getSource() == loans){
			if(User.getActiveUser().getNumLoans() == 1){
				JOptionPane.showMessageDialog(null, "You have taken out " + Blackjack.displayNumber(User.getActiveUser().getNumLoans()) + " loan so far at the White Lily Casino.", "White Lily Casino", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));	
					
			} else {
				JOptionPane.showMessageDialog(null, "You have taken out " + Blackjack.displayNumber(User.getActiveUser().getNumLoans()) + " loans so far at the White Lily Casino.", "White Lily Casino", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));	
			}
		}
		if(e.getSource() == debt){
			if(User.getActiveUser().getDebt() > 0){
				JOptionPane.showMessageDialog(null, "You owe $" + Blackjack.displayNumber(User.getActiveUser().getDebt()) + " to the White Lily Casino.", "White Lily Casino", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
				
			} else {
				JOptionPane.showMessageDialog(null, "You are debt free at this moment.", "White Lily Casino", JOptionPane.OK_OPTION, new ImageIcon("casinoicon.png"));
			}
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		User.loadUsers();
		wlc = new Casino();
		
	}
}
