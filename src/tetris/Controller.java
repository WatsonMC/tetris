package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
	TetrisMain game;
	private Config conf;
	private boolean left,right,down,up, pause;
	

	public Controller(TetrisMain game, Config conf) {
		//constructed by passing the actual game object
		this.game = game;
		this.conf = conf;
	}
	public void  keyPressed(KeyEvent e) {
		//get presed key
		String pressedKey = KeyEvent.getKeyText(e.getKeyCode());

		//Set flags  true when key released
		if(pressedKey.equals(conf.getLeft())) {
			System.out.println("Left pressed");
			left = true;
			if(!getPauseFlag()&& game.getGrid().getCurrentBlock()!=null) {
				game.getGrid().getCurrentBlock().moveLeft();
			}
		}
		else if(pressedKey.equals(conf.getRight())) {
			System.out.println("Right pressed");
			right = true;
			if(!getPauseFlag() && game.getGrid().getCurrentBlock()!=null) {
				game.getGrid().getCurrentBlock().moveRight();
			}
		}
		else if(pressedKey.equals(conf.getUp())) {
			System.out.println("Up pressed");
			up = true;
			//TODO change to rotate
			if(!getPauseFlag()&& game.getGrid().getCurrentBlock()!=null) {
				game.getGrid().getCurrentBlock().rotateBlock();
			}
		}
		else if(pressedKey.equals(conf.getDown())) {
			System.out.println("Down pressed");
			down = true;
			if(!getPauseFlag()&& game.getGrid().getCurrentBlock()!=null) {
				game.getGrid().getCurrentBlock().moveDown();
			}
		}
		else if(pressedKey.equals(conf.getPause())) {
			System.out.println("Pause pressed");
		}
		else if(pressedKey.equals("Space")){
			System.out.println("Space Pressed");
			if(game.checkGameOver()){
				game.showMainMenu();
			}
		}

	}
	
	/**
	 * Event listener routine to notice a key being released
	 * Occurs third in order of event generation for key presses
	 */
	public void  keyReleased(KeyEvent e) {
		String releasedKey = KeyEvent.getKeyText(e.getKeyCode());
		//System.out.println(releasedKey);
		
		//set flags false when keys are released
		if(releasedKey.equals(conf.getLeft())) {
			System.out.println("Left released");
			left = false;
		}
		else if(releasedKey.equals(conf.getRight())) {
			System.out.println("Right released");
			right = false;

		}
		else if(releasedKey.equals(conf.getUp())) {
			System.out.println("Up released");
			up = false;

		}
		else if(releasedKey.equals(conf.getDown())) {
			System.out.println("Down released");
			down = false;

		}
		else if(releasedKey.equals(conf.getPause())) {
			System.out.println("Pause pressed");
			pause = togglePauseFlag();
		}
	}
	
	/**
	 * Event listener routine to notice a key being typed
	 * Occurs second in order of event generation for key presses
	 */
	public void  keyTyped(KeyEvent e) {
	}
	
	public boolean togglePauseFlag() {
		return !pause;
	}
	
	/**
	 * -------------------------------
	 * Getter methods for boolean flags
	 * -------------------------------
	 */
	public boolean getUpFlag() {
		return  this.up;
	}
	public boolean getDownFlag() {
		return (down);
	}
	public boolean getLeftFlag() {
		return (left);
	}
	public boolean getRightFlag() {
		return  (right);
	}
	public boolean getPauseFlag() {
		return new Boolean(pause);
	}
	
}
