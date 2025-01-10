package tetris;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

// class to implement the config of key controls
//TODO make this shit soin
public class Config {	
	private static String UP_KEY = "Up";	//key to rotate block
	private static String DOWN_KEY = "Down";	//key to move block down
	public static String LEFT_KEY = "Left";	//key to move block left
	private static String RIGHT_KEY = "Right";	//key to move block right
	private static String PAUSE = "P";	//key to move
	private static TetrisMain game;
	
	public static Map<String, String> keyListMap = new HashMap<>();
	static {
		Map<String, String> aMap = new HashMap<>();
		aMap.put("1","VK_1");
		aMap.put("2","VK_2");
		aMap.put("3","VK_3");
		aMap.put("4","VK_4");
		aMap.put("5","VK_5");
		aMap.put("6","VK_6");
		aMap.put("7","VK_7");
		aMap.put("8","VK_8");
		aMap.put("9","VK_9");
		aMap.put("0","VK_0");
		aMap.put("Q","VK_Q");
		aMap.put("W","VK_W");
		aMap.put("E","VK_E");
		aMap.put("R","VK_R");
		aMap.put("T","VK_T");
		aMap.put("Y","VK_Y");
		aMap.put("U","VK_U");
		aMap.put("I","VK_I");
		aMap.put("O","VK_O");
		aMap.put("P","VK_P");
		aMap.put("A","VK_A");
		aMap.put("S","VK_S");
		aMap.put("D","VK_D");
		aMap.put("F","VK_F");
		aMap.put("G","VK_G");
		aMap.put("H","VK_H");
		aMap.put("J","VK_J");
		aMap.put("K","VK_K");
		aMap.put("L","VK_L");
		aMap.put("Z","VK_Z");
		aMap.put("X","VK_X");
		aMap.put("C","VK_C");
		aMap.put("V","VK_V");
		aMap.put("B","VK_B");
		aMap.put("N","VK_N");
		aMap.put("M","VK_M");
		aMap.put("Up","VK_Up");
		aMap.put("Down","VK_Down");
		aMap.put("Left","VK_Left");
		aMap.put("Right","VK_Right");
		keyListMap = Collections.unmodifiableMap(aMap);
	}
	
	private static String[] possibleKeys = {};
	//TODO cjamge keylist into a key map, mapping display strings to key selection strings, then use selections to set key controls
	public Config(TetrisMain game) {
		this.game = game;
		//get usable keys
		//keyList.addAll(new ArrayList(Arrays.asList("Down", "Up", "Left", "Right")));
		//possibleKeys = keyList.toArray(possibleKeys);
	}
	
	
	

	
	
//----------------------------
	//Getter methods
//---------------------------
	/**
	 * Gets left key code as a string
	 * @return
	 * String copy of the key for left
	 */
	public  String getLeft() {
		return new String(LEFT_KEY);
	}
	
	/**
	 * Gets right key code as a string
	 * @return
	 * String copy of the key for right
	 */
	public   String getRight() {
		return new String(RIGHT_KEY);
	}
	
	/**
	 * Gets up key code as a string
	 * @return
	 * String copy of the key for up
	 */
	public  String getUp() {
		return new String(UP_KEY);
	}
	
	/**
	 * Gets down key code as a string
	 * @return
	 * String copy of the key for down
	 */
	public  String getDown() {
		return new String(DOWN_KEY);
	}
	
	/**
	 * Gets pause key code as a string
	 * @return
	 * String copy of the key for pause
	 */	
	public  String getPause() {
		return new String(PAUSE);
	}
	
	public String[] getKeyList() {
		return possibleKeys.clone(); 
	}
	
	public void openConfigMenu(JFrame frame) {
		
		JFrame configMenu = new JFrame("Config");
		
		configMenu.setBounds(0,0,game.getWidth(),game.getHeight()/2);	//500,300
		configMenu.setSize(game.getWidth(),game.getHeight()/2+50);
		configMenu.setResizable(false);
		configMenu.setLayout(null);
		configMenu.setLocationRelativeTo(null);
		configMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		String[] keyList = new String[keyListMap.size()];
		keyList = keyListMap.keySet().toArray(keyList);
		
		int comboWidth = 100;
		int comboHeight = 25;
		
		JComboBox<String> leftKeySelection = new JComboBox<>(keyList);
		leftKeySelection.setSelectedItem(LEFT_KEY);
		leftKeySelection.setSize(comboWidth,comboHeight);
		leftKeySelection.setBounds(100, 50, comboWidth,comboHeight);
		leftKeySelection.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	LEFT_KEY = leftKeySelection.getSelectedItem().toString();
		    }
		});
		
		JTextField leftKeyTitle = new JTextField("Left Key");
		leftKeyTitle.setBounds(100, 25,comboWidth,comboHeight);
		leftKeyTitle.setEditable(false);
		leftKeyTitle.setHorizontalAlignment(0);
		leftKeyTitle.setFont(new Font("ARIAL",Font.BOLD,16));
		configMenu.add(leftKeyTitle);
		
		JComboBox<String> rightKeySelection = new JComboBox<>(keyList);
		rightKeySelection.setSelectedItem(RIGHT_KEY);
		rightKeySelection.setSize(comboWidth,comboHeight);
		rightKeySelection.setBounds(300, 50, comboWidth,comboHeight);
		rightKeySelection.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	RIGHT_KEY = rightKeySelection.getSelectedItem().toString();
		    }
		});
		
		JTextField rightKeyTitle = new JTextField("Right Key");
		rightKeyTitle.setBounds(300, 25,comboWidth,comboHeight);
		rightKeyTitle.setEditable(false);
		rightKeyTitle.setHorizontalAlignment(0);
		rightKeyTitle.setFont(new Font("ARIAL",Font.BOLD,16));
		configMenu.add(rightKeyTitle);
		
		JComboBox<String> upKeySelection = new JComboBox<>(keyList);
		upKeySelection.setSelectedItem(UP_KEY);
		upKeySelection.setSize(comboWidth,comboHeight);
		upKeySelection.setBounds(100, 125, comboWidth,comboHeight);
		upKeySelection.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	UP_KEY= upKeySelection.getSelectedItem().toString();
		    }
		});
		
		JTextField upKeyTitle = new JTextField("Rotate Key");
		upKeyTitle.setBounds(100, 100,comboWidth,comboHeight);
		upKeyTitle.setEditable(false);
		upKeyTitle.setHorizontalAlignment(0);
		upKeyTitle.setFont(new Font("ARIAL",Font.BOLD,16));
		configMenu.add(upKeyTitle);
		
		JComboBox<String> downKeySelection = new JComboBox<>(keyList);
		downKeySelection.setSelectedItem(DOWN_KEY);
		downKeySelection.setSize(comboWidth,comboHeight);
		downKeySelection.setBounds(300, 125, comboWidth,comboHeight);
		downKeySelection.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	DOWN_KEY = downKeySelection.getSelectedItem().toString();
		    }
		});
		
		JTextField downKeyTitle = new JTextField("Down Key");
		downKeyTitle.setBounds(300, 100,comboWidth,comboHeight);
		downKeyTitle.setEditable(false);
		downKeyTitle.setHorizontalAlignment(0);
		downKeyTitle.setFont(new Font("ARIAL",Font.BOLD,16));
		configMenu.add(downKeyTitle);
		
		JComboBox<String> pauseKeySelection = new JComboBox<>(keyList);
		pauseKeySelection.setSelectedItem(PAUSE);
		pauseKeySelection.setSize(comboWidth,comboHeight);
		pauseKeySelection.setBounds(200, 200, comboWidth,comboHeight);
		pauseKeySelection.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	PAUSE = pauseKeySelection.getSelectedItem().toString();
		    }
		});
		
		JTextField pauseKeyTitle = new JTextField("Pause Key");
		pauseKeyTitle.setBounds(200, 175,comboWidth,comboHeight);
		pauseKeyTitle.setEditable(false);
		pauseKeyTitle.setHorizontalAlignment(0);
		pauseKeyTitle.setFont(new Font("ARIAL",Font.BOLD,16));
		configMenu.add(pauseKeyTitle);
		
		JButton okay = new JButton("OKAY");
		okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Saving button config");
				//TODO code to save config file for buttons
				
				configMenu.dispose();
				
			}
		});
		okay.setBounds(200,250, 200,50);
		okay.setSize(100,25);
		
		configMenu.add(okay);
		configMenu.add(downKeySelection);
		configMenu.add(upKeySelection);
		configMenu.add(leftKeySelection);
		configMenu.add(rightKeySelection);
		configMenu.add(pauseKeySelection);
		
		configMenu.setVisible(true);
		
		
	}
}
