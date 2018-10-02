package tetris;
/**
 * Class responsible for retaining and adding to the game score
 * @author SP194E
 *
 */
public class ScoreHandler {
	private TetrisGrid grid;
	private int score;
	
	
	public ScoreHandler(TetrisGrid grid) {
		this.grid = grid;
		score = 0;
	}
	
	/** 
	 * Function to add score value after rows are removed with clearFullRows method in grid class
	 * @param numberOfRows
	 * Number of rows which were cleared in the clearFullRowFunction 
	 * number of rows variable should start at 1, and is a maximum of 4
	 * Calculation of score:
	 * 	Score = (n-1)*5 +n*10
	 */
	public void addScoreForRows(int numberOfRows) {
		if(numberOfRows>4) {
			System.out.println("somehow cleared more than 4 rows at once, error");
			System.exit(1);
		}
		else if(numberOfRows<1) {
			return;
		}
		else {
			int scoreIncreaseValue = (numberOfRows - 1)*5 + numberOfRows*10;
			this.score+= scoreIncreaseValue;
		}
		
	}
	
	/**
	 *Function to reset score value so as to start game
	 * @return
	 */
	public void resetScore() {
		this.score = 0;
	}
	
	/**
	 * getter method for score value
	 * @return
	 */
	public int getScore() {
		return this.score;
	}
}
