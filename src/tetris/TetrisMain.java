package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;


public class TetrisMain extends Canvas implements Runnable {

	private static final Integer WIDTH = 500;
	private static final Integer HEIGHT = 600;
	private static final Integer HS_WIDTH = 300;
	private static final Integer HS_HEIGHT = 500;
	private static final Integer BORDER_WIDTH = 5; // Width of border around game screen
	private static final Integer SIDEBAR_WIDTH = 200;

	public static int gameSpeed = 500;
	private Image[] tetrisBlocks;
	private final int BLOCK_SIZE = 25;
	private TetrisGrid grid;
	private RandomBlockGenerator blockGenerator;
	private Block testBlock;	//testing

	//graphical components
	private JFrame mainFrame;
	private JPanel gamePanel;
	private JPanel sbPanel;
	private JPanel menuPanel;
	private JTextArea scoreField;
	private Canvas nextBlockCanvas;

	private boolean running;	//used for new game/end game functionality
	private boolean gameOver = false;	//used to trigger exit of the main loop when end game pressed

	private Graphics2D background;
	private BufferStrategy buff;

	private Thread thread;	//main game thread, want only one

	private Controller cont;
	private Config conf;
	private BlockController blockController;

	public static void main(String [] args) {
		//creating frame for game
		JFrame frame = new JFrame("Tetris");	//creating the JFrame with the name Trtris
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH+3*BORDER_WIDTH + SIDEBAR_WIDTH, HEIGHT+25 +2*BORDER_WIDTH)); 	//Setting size of the frame, +25 added for menu bar, 4 for 2mm borders....
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Telling the frame to actually exit when the cross is pressed

		//set frame in middle of screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - WIDTH)/2, (screenSize.height - HEIGHT)/2);

		//Set frame options
		frame.setResizable(false);	// user cannot resize frame
		frame.setLayout(null);

		//create the game and define its size
		TetrisMain tetrisMain = new TetrisMain();
		tetrisMain.setBounds(0,0,WIDTH,HEIGHT);	// starts at 25 to allow menu bar,
		tetrisMain.mainFrame = frame;
		
		//add menu bar
		tetrisMain.constructMenuBar(frame);

		//show main menu
		tetrisMain.showMainMenu();

		frame.pack();
		frame.setVisible(true);
		tetrisMain.init();
	}
	/**
	 * Main running loop of program
	 */
	public void run() {
		this.requestFocusInWindow();
		if (buff == null) {
			createBufferStrategy(3);    // sets the buffering strategy as triple
		}
		buff = getBufferStrategy();    //creates the method of buffering for the window
		buff.show();	// feels so dirty to call buff.show() twice like this.
		background = (Graphics2D) buff.getDrawGraphics();
		buff.show();

		while(running) {

				update();    // calls update method
//				buff = getBufferStrategy();    //creates the method of buffering for the window
//				if (buff == null) {
//					createBufferStrategy(3);    // sets the buffering strategy as triple
//					continue;
//				}
//				background = (Graphics2D) buff.getDrawGraphics(); //graphics2d is a fundamental class for rendering 2-d shapes. takes user space co-ordinates

				render(background);

				if (cont.getPauseFlag()) {
					drawPauseMessage(background);
				}

				buff.show();
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

	public void  showMainMenu(){
		System.out.println("Show Main Menu called");
		if(gamePanel != null){		mainFrame.getContentPane().remove(gamePanel);}
		if(sbPanel !=null){mainFrame.getContentPane().remove(sbPanel);}
//		mainFrame.getContentPane().remove(this); //This currently removes the menubar as well as the game surface. Think I want to put the game on it's own content JPanel perhapsd

		//Set gameover flag false
		gameOver = false;

		//create panel to house main menu
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(2,1,20,20));
		menuPanel.setBounds((WIDTH+SIDEBAR_WIDTH)/4 + BORDER_WIDTH,HEIGHT/2 - HEIGHT/8 +25 + BORDER_WIDTH,(WIDTH+SIDEBAR_WIDTH) /2,HEIGHT/4);

		//create start button
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.getContentPane().remove(menuPanel);
				mainFrame.revalidate();
				mainFrame.repaint();
				startNewGame();
			}
		});

		JButton btnHS = new JButton("Highscores");

		menuPanel.add(btnStart);
		menuPanel.add(btnHS);

		menuPanel.setVisible(true);
//		mainFrame.getContentPane().removeAll();
		mainFrame.getContentPane().add(menuPanel);
		mainFrame.pack();
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	public void init() {
		//create config
		conf = new Config(this);

		//create the controller object
		cont = new Controller(this,this.conf);

		//create the block generator object
		blockGenerator = new RandomBlockGenerator();

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
		
		//Initialize the grid object for the game
		grid=  new TetrisGrid(WIDTH,HEIGHT,this.BLOCK_SIZE, this.tetrisBlocks);

		// Initialize the block controller for the game

		blockController = new BlockController(this);
	}

	/**
	 * drawings is done by passing around the graphics 2D object
	 * @param g
	 */
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		g.setFont(new Font("Calibri", Font.PLAIN, 24));
//		g.drawString(Integer.toString(this.grid.getScore()), WIDTH/2, HEIGHT/2);
		scoreField.setText(Integer.toString(this.grid.getScore()));
		grid.drawGrid(g);
	}
	
	/**
	 * Method to draw an un-interactable textbox to tell user game is over, and press space to return to main menu
	 * @param g
	 * Graphics object on which to draw the message
	 */
	public void drawGameOverMessage(Graphics2D g) {
		String gameOverMessage = "GAME OVER\nSPACE\n to continue";
		int msgBoxWidth = WIDTH/2;
		int msgBoxHeight = 200;
			
		g.setColor(Color.RED);
		g.fillRect(WIDTH/2 - (msgBoxWidth+10)/2, HEIGHT/2 - (msgBoxHeight+10)/2, msgBoxWidth+10, msgBoxHeight+10);
		g.setColor(Color.DARK_GRAY);
		
		g.fillRect(WIDTH/2 - (msgBoxWidth)/2, HEIGHT/2 - (msgBoxHeight/2), msgBoxWidth, msgBoxHeight);
		g.setColor(Color.WHITE);
		Font messageFont = new Font("Calibri", Font.PLAIN, 24);
		
		int i = 0;
		Rectangle messageArea = new Rectangle(msgBoxWidth, msgBoxHeight);
		for(String s: gameOverMessage.split("\n")) {
			messageArea.setLocation(WIDTH/2-msgBoxWidth/2 , HEIGHT/2 -msgBoxHeight+i*msgBoxHeight/3);
			drawCenteredString(g, s, messageArea, messageFont);
			i++;
		}
	}

	public void drawPauseMessage(Graphics2D g) {
		String pauseMessage = "Game paused, press\n"+ conf.getPause() + "\nto continue";
		int msgBoxWidth = WIDTH/2;
		int msgBoxHeight = 200;

		g.setColor(Color.WHITE);
		g.fillRect(WIDTH/2 - (msgBoxWidth+10)/2, HEIGHT/2 - (msgBoxHeight+10)/2, msgBoxWidth+10, msgBoxHeight+10);
		g.setColor(Color.DARK_GRAY);

		g.fillRect(WIDTH/2 - (msgBoxWidth)/2, HEIGHT/2 - (msgBoxHeight/2), msgBoxWidth, msgBoxHeight);
		g.setColor(Color.WHITE);
		Font messageFont = new Font("Calibri", Font.PLAIN, 24);

		int i = 0;
		Rectangle messageArea = new Rectangle(msgBoxWidth, msgBoxHeight);
		for(String s: pauseMessage.split("\n")) {
			messageArea.setLocation(WIDTH/2-msgBoxWidth/2 , HEIGHT/2 -msgBoxHeight+i*msgBoxHeight/3);
			drawCenteredString(g, s, messageArea, messageFont);
			i++;
		}
	}
	
	
	public void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height + metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}

	
	/**
	 * Method to start running of game
	 */
	public void start() {
		running = false;
		thread = new Thread(this);
		thread.setPriority(Thread.MAX_PRIORITY);
		running = true;
		thread.start();
	}

	/**
	 * method to start new round of the game
	 */
	public void startNewGame(){
		System.out.println("Start New Game called");
		// Clear the grid and reset the score
		this.grid.resetGridForNewGame();
		this.constructSideBar(mainFrame);

		mainFrame.getContentPane().remove(menuPanel);
		//change the JFrame content pane from showing the newGame Menu to showing the game.
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.add(this);
		gamePanel.setVisible(true);
		gamePanel.setBounds(BORDER_WIDTH,BORDER_WIDTH + 25,WIDTH ,HEIGHT );	// starts at 25 to allow menu bar,
		mainFrame.getContentPane().add(gamePanel);

//		this.setPreferredSize(new Dimension(WIDTH, HEIGHT+25));	// set preffered size then pack instead of setbounds alone, allows removal of border
//		this.setBounds(0,25,WIDTH,HEIGHT);	// starts at 25 to allow menu bar,
		mainFrame.revalidate();
		mainFrame.pack();
		running = true;

		//start blockgeneration
		this.blockController.startBlockGeneration();
		this.start();
	}

	/**
	 * Called by BlockController when no new insertion location is found
	 * - stops running
	 * - flahses blocks random colours seconds
	 * - Displays rectangle in middle with 'GAME OVER Press any key to continue'
	 */
	public void gameOver(){
		running = false;
		this.blockController.stopBlockGeneration();
		for(int i =0; i<10;i++){
			if(i<9){
				System.out.println("Randomly colouring blocks");
				this.grid.drawGridRandomColours(background);
				buff.show();
			}else{
				this.drawGameOverMessage(background);
				buff.show();
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println("Runtime Exception");
				throw new RuntimeException(e);
			}
        }
		buff.show();	//this feels dirty but with triple buffering I have to force it from back to mid then to front
		gameOver = true;
	}

	public void constructSideBar(JFrame frame){
		//create the panel
		sbPanel = new JPanel();
		sbPanel.setLayout(null);
		sbPanel.setSize(new Dimension(SIDEBAR_WIDTH,HEIGHT));
		sbPanel.setBounds(BORDER_WIDTH*2 + WIDTH, 25 + BORDER_WIDTH, SIDEBAR_WIDTH,HEIGHT);
		sbPanel.setBackground(Color.GRAY);
		sbPanel.setVisible(true);

		//add the nextBlock canvas
		nextBlockCanvas = new Canvas(){
			@Override
			public void paint(Graphics g) {
				// Clear the canvas
				super.paint(g);
				//draw the block
				if(blockController.getNextBlock()!=null){
					Block block = blockController.getNextBlock();
					for (int i = 0; i < 4; i++) {	//x
						for (int j = 0; j < 4; j++) {	//y
							if (block.currentShapeData[i][j]) {	//draw
								g.drawImage(
										tetrisBlocks[block.shape.colourIndex],
										i*25 + BORDER_WIDTH,
										j*25 + BORDER_WIDTH,
										25,
										25,null
								);
							}
						}
					}
				}
			}
		};
		nextBlockCanvas.setBackground(Color.white);
		sbPanel.add(nextBlockCanvas);
		nextBlockCanvas.setBounds(BORDER_WIDTH,BORDER_WIDTH, 100+2*BORDER_WIDTH, 100+2*BORDER_WIDTH);


		//create and add scorefield
		//TODO make this look better, looks scat as
		scoreField = new JTextArea(3,4);
		scoreField.setSize(new Dimension(SIDEBAR_WIDTH-2*BORDER_WIDTH,200));
		scoreField.setBounds(BORDER_WIDTH ,BORDER_WIDTH + 25 + HEIGHT/2,200-2*BORDER_WIDTH,200);
		scoreField.setBackground(Color.GRAY);
		scoreField.setText("1000");
		scoreField.setLineWrap(true);
		scoreField.setFont(new Font("Calibri", Font.PLAIN, 75));
		sbPanel.add(scoreField);

		frame.getContentPane().add(sbPanel);

		/**
		 * I want this side bar to have
		 * - New block screen
		 * - Current score
		 *
		 * SO I need to have hooks into the score handler and the block generator
		 *
		 * block generator will need to
		 */
	}
	
	/**
	 * constructs menu bar at first run
	 * @Jframe frame
	 * the frame to add the menubar to
	 */
	public void constructMenuBar(JFrame frame) {
		
				// creating menu bar
				JMenuBar menuBar = new JMenuBar();
				menuBar.setBounds(0,0,WIDTH + 3*BORDER_WIDTH + SIDEBAR_WIDTH,25);
				frame.getContentPane().add(menuBar);
				
				
				//creating the menu options
				JMenu file = new JMenu("File");	//string argument is name
				file.setBounds(0,0,45,24);
				menuBar.add(file);	// add the menu item to the menubar not the frame!
				
				//Create JMenu Items
				//new gamme
				JMenuItem newGame = new JMenuItem("New Game");
				newGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						startNewGame();
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
						conf.openConfigMenu(frame);
					}
				});
				file.add(config);
	}


	/**
	 * helper function to updte next block canvas
	 */
	public void updateNextBlockCanvas(){
		nextBlockCanvas.paint(nextBlockCanvas.getGraphics());
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
		Controller cont = new Controller(this, this.conf);
		testEnv.addKeyListener(cont);
		
		JButton jbDeleteRows =  new JButton("Clear full rows");
		jbDeleteRows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Successfully pressed deleterow button");
				grid.clearFullRows();
			}
		});
		jbDeleteRows.setBounds(100,100,100,50);
		testEnv.add(jbDeleteRows);
		
		JComboBox<String> shapeTypeBox = new JComboBox<>(new String[]{"line","S","Z","square", "L","J","T"});
		Map<String, String> shapeSelectMap = new HashMap<>();
		shapeSelectMap.put("line", "/line.txt");
		shapeSelectMap.put("S", "/sshape.txt");
		shapeSelectMap.put("Z", "/zshape.txt");
		shapeSelectMap.put("square", "/square.txt");
		shapeSelectMap.put("T", "/tee.txt");
		shapeSelectMap.put("L", "/lshape.txt");
		shapeSelectMap.put("J", "/jshape.txt");
		
		shapeTypeBox.setBounds(300,200,150,50);
		testEnv.add(shapeTypeBox);
		JButton jbTestShape =  new JButton("testShapeConstructor");
		jbTestShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(testBlock != null) {
					//if block is already placed in screen, delete it
					grid.removeBlock(testBlock);
				}
				System.out.println("shape testing");
				ShapeData s = new ShapeData("L", shapeSelectMap.get(shapeTypeBox.getSelectedItem()),0,3);
				System.out.println("shape data created");
				testBlock = new Block(grid,s);
				System.out.println("block created successfully");
				grid.addBlock(testBlock);
			}
		});
		
		jbTestShape.setBounds(300,100,150,50);
		testEnv.add(jbTestShape);
		
		JButton moveLeft = new JButton("TestButton");
				
		moveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawGameOverMessage(background);
			}
		});
		moveLeft.setBounds(100,200,100,50);
		testEnv.add(moveLeft);

		JButton deleteCurrentBlock  = new JButton("Delete current block");
		deleteCurrentBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.removeBlock(grid.getCurrentBlock());
			}
		});
		deleteCurrentBlock.setBounds(150,400,150,50);
		testEnv.add(deleteCurrentBlock);
		
		JButton clearGrid  = new JButton("Cleargrid");
		clearGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.clearGrid();
			}
		});
		clearGrid.setBounds(300,400,150,50);
		testEnv.add(clearGrid);
		testEnv.setVisible(true);

		JButton gameOver  = new JButton("gameOver");
		gameOver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameOver();
			}
		});
		gameOver.setBounds(150,450,150,50);
		testEnv.add(gameOver);


		JButton fillScreen  = new JButton("fillScreen");
		fillScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.fillMostOfGrid();
			}
		});
		fillScreen.setBounds(300,450,150,50);
		testEnv.add(fillScreen);
		testEnv.setVisible(true);
	}
	
	/**
	 * opens the config menu to adjust keys
	 * @param frame
	 * THe frame object which is opened from
	 */
	
	public void openConfigMenuSUPERSEDED(JFrame frame) {
		JFrame configMenu = new JFrame("Config");
		configMenu.setBounds(0,0,WIDTH,HEIGHT/2);	//500,300
		configMenu.setSize(WIDTH,HEIGHT/2);
		configMenu.setResizable(false);
		configMenu.setLayout(null);
		configMenu.setLocationRelativeTo(null);
		configMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		String[] keyList = conf.getKeyList();
		
		int comboWidth = 100;
		int comboHeight = 25;
		
		JComboBox<String> leftKeySelection = new JComboBox<>(keyList);
		leftKeySelection.setSelectedItem(conf.getLeft());
		leftKeySelection.setSize(comboWidth,comboHeight);
		leftKeySelection.setBounds(100, 25, comboWidth,comboHeight);
		
		
		JComboBox<String> rightKeySelection = new JComboBox<>(keyList);
		rightKeySelection.setSelectedItem(conf.getRight());
		rightKeySelection.setSize(comboWidth,comboHeight);
		rightKeySelection.setBounds(300, 25, comboWidth,comboHeight);
		
		JComboBox<String> upKeySelection = new JComboBox<>(keyList);
		upKeySelection.setSelectedItem(conf.getUp());
		upKeySelection.setSize(comboWidth,comboHeight);
		upKeySelection.setBounds(100, 75, comboWidth,comboHeight);
		
		JComboBox<String> downKeySelection = new JComboBox<>(keyList);
		downKeySelection.setSelectedItem(conf.getDown());
		downKeySelection.setSize(comboWidth,comboHeight);
		downKeySelection.setBounds(300, 75, comboWidth,comboHeight);
		
		JComboBox<String> pauseKeySelection = new JComboBox<>(keyList);
		pauseKeySelection.setSelectedItem(conf.getPause());
		pauseKeySelection.setSize(comboWidth,comboHeight);
		pauseKeySelection.setBounds(200, 150, comboWidth,comboHeight);
		
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
		
		configMenu.add(okay);
		configMenu.add(downKeySelection);
		configMenu.add(upKeySelection);
		configMenu.add(leftKeySelection);
		configMenu.add(rightKeySelection);
		configMenu.add(pauseKeySelection);
		
		configMenu.setVisible(true);
		
		
	}
	
	/**
	 * Getter method for grid object of game
	 * @return
	 * Returns grid object in game
	 */
	protected TetrisGrid getGrid() {
		return this.grid;
	}

	/**
	 * Getter for controller object
	 * @return
	 */
	protected Controller getCont() {
		return this.cont;
	}

	/**
	 * Getter for gameOver flag
	 * @return
	 */
	protected boolean checkGameOver(){return gameOver;}

	
	
}
