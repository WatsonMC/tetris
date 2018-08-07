package tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TetrisMain extends Canvas implements Runnable {

	private static final Integer WIDTH = 500;
	private static final Integer HEIGHT = 600;
	private static final Integer HS_WIDTH = 300;
	private static final Integer HS_HEIGHT = 500;
	private Image[] tetrisBlocks;
	
	Controller cont;
	Config conf;

	public static void main(String [] args) {
		//creating frame for game
		JFrame frame = new JFrame("Tetris");	//creating the JFrame with the name Trtris
		frame.setSize(WIDTH, HEIGHT);; 	//Setting size of the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Telling the frame to actually exit when the cross is pressed
		frame.setLocationRelativeTo(null);	//sets frame in middle of screen
		frame.setResizable(false);	// user cannot resize frame
		
		
		TetrisMain tetrisMain = new TetrisMain();
		frame.add(tetrisMain);
		frame.setLayout(null);
		tetrisMain.setBounds(0,25,WIDTH,HEIGHT-25);
		
		//tetrisMain.addKeyListener(tetrisMain);
		//add menu bar
		Config conf = new Config();
		tetrisMain.conf = conf;
	
		tetrisMain.constructMenuBar(frame);
		
		frame.setVisible(true);
		
		tetrisMain.start();
		
		
		
	}
	/**
	 * Main running loop of program
	 */
	public void run() {
		init();	//can still make a static call since this method is non static
		boolean running = true;
		while(running) {
			update();	// calls update method
			BufferStrategy buff = getBufferStrategy();	//creates the method of buffering for the window
			if(buff == null) {
				createBufferStrategy(3);	// sets the buffering strategy as triple 
				continue;
			}
			Graphics2D background = (Graphics2D) buff.getDrawGraphics(); //graphics2d is a fundamental class for rendering 2-d shapes. takes user space co-ordinates
			render(background);
			buff.show(); 
		}

	}
	
	public void init() {
		
		Controller  cont = new Controller(this);
		this.cont = cont;
		
		this.addKeyListener(cont);	//The keylistener for this canvas is going to be exclusively held in the controller class
		
		requestFocus();
		try{
			
			tetrisBlocks = ImageLoader.loadImage("/tetris.png",25);
		}
		catch(IOException e)
		{	
			
			System.out.println("Error loading image file");
			System.exit(1);
		}
		
		
	}
	
	public void update() {
		// update current keys being pressed:
		String keys ="Left: " + cont.getLeftFlag() +
				", Right: " + cont.getRightFlag() +
				", Down: " + cont.getDownFlag() +
				", Up: " + cont.getUpFlag() +
				", Pause: " + cont.getPauseFlag();
		//System.out.println(keys);
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		g.setFont(new Font("Calibri", Font.PLAIN, 24));
		g.drawString("tetris", WIDTH/2, HEIGHT/2);
		g.drawImage(tetrisBlocks[0], 100, 100,25, 25,null);
		g.drawImage(tetrisBlocks[1], 75, 100,25, 25,null);
	}

	
	/**
	 * Method to start running of game
	 */
	public void start() {
		Thread thread = new Thread(this);
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}
	
	/**
	 * constructs menu bar at first run
	 * @Jframe frame
	 * the frame to add the menubar to
	 */
	public void constructMenuBar(JFrame frame) {
		
				// creating menu bar
				JMenuBar menuBar = new JMenuBar();
				menuBar.setBounds(0,0,WIDTH,25);
				frame.add(menuBar);
				
				
				//creating the menu options
				JMenu file = new JMenu("File");	//string argument is name
				file.setBounds(0,0,45,24);
				menuBar.add(file);	// add the menu item to the menubar not the frame!
				
				//Create JMenu Items
				//new gamme
				JMenuItem newGame = new JMenuItem("New Game");
				newGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//NEWGAME CODE;
						System.out.println("Starting new game...");
					}
				});
				file.add(newGame);
				
				//see high scores
				JMenuItem highScores = new JMenuItem("Highscores");
				highScores.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//highScorescode
						System.out.println("Opening highscores...");
						openHighscores(frame);
					}
				});
				file.add(highScores);
				
				//exit;
				JMenuItem exit = new JMenuItem("Exit");
				exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Exiting game");
						System.exit(0);
					}
				});
				file.add(exit);
				
				//testEnv;
				JMenuItem testEnv = new JMenuItem("TESTING");
				testEnv.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Opening Test box");
						openTestEnv(frame);
					}
				});
				file.add(testEnv);
				
				//configMenu;
				JMenuItem config = new JMenuItem("Config Menu");
				config.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						openConfigMenu(frame);
					}
				});
				file.add(config);
				
				
				return;
	}
	
	/**
	 * Opens the highscores window
	 * 
	 */
	public  void openHighscores(JFrame frame) {
		JDialog hs = new JDialog(frame,"Highscores");
		//HS frame
		hs.setLayout(null);
		hs.setSize(HS_WIDTH, HS_HEIGHT);
		hs.setBounds(0,0,HS_WIDTH,HS_HEIGHT);
		hs.setLocationRelativeTo(null);
		hs.setResizable(false);
		hs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//HS close button
		JButton hsOkayButton = new JButton("Okay");
		hsOkayButton.setBounds(10,HS_HEIGHT - 70,100,30);
		hsOkayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exiting highscores...");
				hs.dispose();
			}
		});
		
		//TODO
		//Add in save to-file functionality, load from file functionality
		// for HS, which shows a table of the highscores 
		hs.add(hsOkayButton);
		hs.setVisible(true);
	}
	
	/**
	 * test environment for learning how to use key Listeners
	 * @param frame
	 */
	public void openTestEnv(JFrame frame) {
		JFrame testEnv = new JFrame("testing");
		testEnv.setBounds(0,0,WIDTH,HEIGHT);
		testEnv.setSize(WIDTH,HEIGHT);
		testEnv.setResizable(false);
		testEnv.setLayout(null);
		testEnv.setLocationRelativeTo(null);
		testEnv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Controller cont = new Controller(this);
		testEnv.addKeyListener(cont);
		
		testEnv.setVisible(true);
	}
	
	/**
	 * opens the config menu to adjust keys
	 * @param frame
	 * THe frame object which is opened from
	 */
	public void openConfigMenu(JFrame frame) {
		JFrame configMenu = new JFrame("Config");
		configMenu.setBounds(0,0,WIDTH,HEIGHT/2);	//500,300
		configMenu.setSize(WIDTH,HEIGHT/2);
		configMenu.setResizable(false);
		configMenu.setLayout(null);
		configMenu.setLocationRelativeTo(null);
		configMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JButton okay = new JButton("OKAY");
		okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Saving button config");
				//TODO code to save config file for buttons
				configMenu.dispose();
				
			}
		});
		
		okay.setBounds(200,225, 200,50);
		okay.setSize(100,25);
		
		String[] keyList = conf.getKeyList();
		
		int comboWidth = 100;
		int comboHeight = 25;
		
		JComboBox leftKeySelection = new JComboBox(keyList);
		leftKeySelection.setSelectedItem(conf.getLeft());
		leftKeySelection.setSize(comboWidth,comboHeight);
		leftKeySelection.setBounds(100, 25, comboWidth,comboHeight);
		
		
		JComboBox rightKeySelection = new JComboBox(keyList);
		rightKeySelection.setSelectedItem(conf.getRight());
		rightKeySelection.setSize(comboWidth,comboHeight);
		rightKeySelection.setBounds(300, 25, comboWidth,comboHeight);
		
		JComboBox upKeySelection = new JComboBox(keyList);
		upKeySelection.setSelectedItem(conf.getUp());
		upKeySelection.setSize(comboWidth,comboHeight);
		upKeySelection.setBounds(100, 75, comboWidth,comboHeight);
		
		JComboBox downKeySelection = new JComboBox(keyList);
		downKeySelection.setSelectedItem(conf.getDown());
		downKeySelection.setSize(comboWidth,comboHeight);
		downKeySelection.setBounds(300, 75, comboWidth,comboHeight);
		
		JComboBox pauseKeySelection = new JComboBox(keyList);
		pauseKeySelection.setSelectedItem(conf.getPause());
		pauseKeySelection.setSize(comboWidth,comboHeight);
		pauseKeySelection.setBounds(200, 150, comboWidth,comboHeight);
		
		configMenu.add(okay);
		configMenu.add(downKeySelection);
		configMenu.add(upKeySelection);
		configMenu.add(leftKeySelection);
		configMenu.add(rightKeySelection);
		configMenu.add(pauseKeySelection);
		
		configMenu.setVisible(true);
		
	}
	
	
}
