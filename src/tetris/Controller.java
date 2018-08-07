package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
	TetrisMain game;
	private boolean left,right,down,up, pause;
	
	public Controller(TetrisMain game) {
		//constructed by passing the actual game object
		this.game = game;
	}
	public void  keyPressed(KeyEvent e) {
		//System.out.println("yes fuckwit a key was typed");
		
		
		//get presed key
		String pressedKey = e.getKeyText(e.getKeyCode());
		//System.out.println(pressedKey);
		
		//Set flags  true when key released
		if(pressedKey.equals(Config.getLeft())) {
			System.out.println("Left pressed");
			left = true;
		}
		else if(pressedKey.equals(Config.getRight())) {
			System.out.println("Right pressed");
			right = true;

		}
		else if(pressedKey.equals(Config.getUp())) {
			System.out.println("Up pressed");
			up = true;

		}
		else if(pressedKey.equals(Config.getDown())) {
			System.out.println("Down pressed");
			down = true;

		}
		else if(pressedKey.equals(Config.getPause())) {
			System.out.println("Pause pressed");
			pause = true;

		}

	}
	
	/**
	 * Event listener routine to notice a key being released
	 * Occurs third in order of event generation for key presses
	 */
	public void  keyReleased(KeyEvent e) {
		String releasedKey = e.getKeyText(e.getKeyCode());
		//System.out.println(releasedKey);
		
		//set flags false when keys are released
		if(releasedKey.equals(Config.getLeft())) {
			System.out.println("Left released");
			left = false;
		}
		else if(releasedKey.equals(Config.getRight())) {
			System.out.println("Right released");
			right = false;

		}
		else if(releasedKey.equals(Config.getUp())) {
			System.out.println("Up released");
			up = false;

		}
		else if(releasedKey.equals(Config.getDown())) {
			System.out.println("Down released");
			down = false;

		}
		else if(releasedKey.equals(Config.getPause())) {
			System.out.println("Pause pressed");
			pause = false;

		}
	}
	
	/**
	 * Event listener routine to notice a key being typed
	 * Occurs second in order of event generation for key presses
	 */
	public void  keyTyped(KeyEvent e) {
	}
	
	
	
	/**
	 * -------------------------------
	 * Getter methods for boolean flags
	 * -------------------------------
	 */
	public boolean getUpFlag() {
		return new Boolean(up);
	}
	public boolean getDownFlag() {
		return new Boolean(down);
	}
	public boolean getLeftFlag() {
		return new Boolean(left);
	}
	public boolean getRightFlag() {
		return new Boolean(right);
	}
	public boolean getPauseFlag() {
		return new Boolean(pause);
	}
	
}
