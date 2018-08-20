package tetris;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// class to implement the config of key controls
//TODO make this shit soin
public class Config {	
	private static String UP_KEY = "Up";	//key to rotate block
	private static String DOWN_KEY = "Down";	//key to move block down
	private static String LEFT_KEY = "Left";	//key to move block left
	private static String RIGHT_KEY = "Right";	//key to move block right
	private static String PAUSE = "P";	//key to move
	
	private static String[] possibleKeys = {};
	
	public Config() {
		//get usable keys
		Field[] fields = java.awt.event.KeyEvent.class.getDeclaredFields();
		List<String> keyList = new ArrayList<>();
		for(Field f:fields) {
			//cycle through all fields
			if(Modifier.isStatic(f.getModifiers())) {
				if(f.getName().substring(0, 2).equals("VK")&&f.getName().length()<=4) {
					//is a VK field
						Object x = new Object();
						try {
							keyList.add(KeyEvent.getKeyText(f.getInt(x)));
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}
		keyList.addAll(new ArrayList(Arrays.asList("Down", "Up", "Left", "Right")));
		possibleKeys = keyList.toArray(possibleKeys);
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
}
