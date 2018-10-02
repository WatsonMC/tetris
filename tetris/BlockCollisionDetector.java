package tetris;

public class BlockCollisionDetector {
/**
 * Class for handling detection of collisions between blocks and the rest of the grid
 * 
 */
	private TetrisGrid grid;
	
	
	public BlockCollisionDetector(TetrisGrid grid) {
		this.grid = grid;
		
	}
	/**
	 * every move check if the move will collide
	 * or
	 * Afeter evvery move check if block is adjacent with other blovks
	 * 
	 *  no no
	 *  better to test move and if the move causes cause an illegal collision. If it does, then it's not allowed
	 *  If the move was a down move, and the move is disallowed, then the block should stick and become part of the grid
	 */
	
	/**
	 * returns true if given block and coordinates represent a colision scenaro
	 * assumes that the given position is in range and valid (ie. check move in range first)
	 * assumes block is not currently present in grid (all calls to this should pre-remove the block from the grid)
	 * @param block
	 * block object to check
	 * @param row
	 * insertion row of location to check
	 * @param col
	 * insertion column of location to check
	 * @return
	 */
	public boolean checkCollision(Block block, int row, int col) {
		
		for(int i = 0; i<4;i++) {
			for(int j = 0; j<4;j++) {
				if(block.currentShapeData[i][j]) {
					if(!grid.checkGridPositionAvailable(row+i, col+j)) {
						//returns true because grid position is not available, hence a collision
						return true;
						}	
				}
			}
			
		}
		return false;
	}
	
}
