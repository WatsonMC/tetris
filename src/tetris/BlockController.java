package tetris;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BlockController {
	private TetrisMain game;
	public static final ShapeData line = new ShapeData("line", "/line.txt",0,1);
	public static final ShapeData lshape = new ShapeData("lshape", "/lshape.txt",0,2);
	public static final ShapeData jshape = new ShapeData("jshape", "/jshape.txt",0,3);
	public static final ShapeData sshape = new ShapeData("sshape", "/sshape.txt",0,4);
	public static final ShapeData zshape = new ShapeData("zshape", "/zshape.txt",0,5);
	public static final ShapeData square = new ShapeData("square", "/square.txt",0,6);
	public static final ShapeData tee = new ShapeData("tee", "/tee.txt",0,7);
	
	
	public BlockController(TetrisMain game) {
		this.game = game;
		ScheduledExecutorService  scheduler =  Executors.newScheduledThreadPool(1);
		
		Runnable blockMvmnt =  new Runnable() {
			public void run() {
				if(!game.getGrid().checkGridHasBlock()) {
					insertNewBlock();
					System.out.println("new block added to game");
				}
				else{
					moveBlock();
				}
			}
		};
		ScheduledFuture<?> lockMvmntHandler = scheduler.scheduleAtFixedRate(blockMvmnt, 1000, game.gameSpeed, TimeUnit.MILLISECONDS );
		
	}
	
	/**
	 * Attempts to mvoe the current block of the game down
	 */
	private void moveBlock() {
		
		game.getGrid().getCurrentBlock().moveDown();
	}
	
	
	private void insertNewBlock() {
		//Select the block type to be added
		//Create block
		Block newBlock  = RandomBlockGenerator.getRandomBlock(game.getGrid());
		if(game.getGrid().checkMove(newBlock, newBlock.getRow(), newBlock.getCol())) {
			game.getGrid().addBlock(newBlock);
			System.out.println("block created");
		}else {
			System.out.println("randomly generated block could not be inserted at target position");
			System.out.println("Will now cycle through possible locations to find a viable insertion point");
			if(findBlockInsertionLocation(newBlock)) {
				game.getGrid().addBlock(newBlock);
			}else {
				//game over
				System.out.println("Game Over");
			}
		}
		//Add block to screen
	}
	
	/**
	 * Method to attempt all possible insertion locations for the new Block.
	 * Will cycle through all of the possible columns the block could be inserted into
	 * Assigns the correct col and row number if position found, and returns true
	 * else returns false
	 * @param block
	 * block which we are going to try to insert
	 * @return
	 * TRUE if block was successfully inserted, col and row number of new insertion point assigned
	 * FALSE if block could not be inserted anywhere, game over scrub.
	 */
	private boolean findBlockInsertionLocation(Block block) {
		int width = game.getGrid().getWidth();
		int colPos = block.getCol();
		for (int i = 1;i<width ;i++) {
			colPos = (colPos + i) % width;
			if(game.getGrid().checkMove(block, block.rowPosn,colPos)) {
				//pos found
				block.setCol(colPos);
				return true;
			}
		}
		return false;
	}
}
