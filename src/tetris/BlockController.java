package tetris;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BlockController {
	private TetrisMain game;
	private int insertRow = 0;
	private int[] insertCol = {1,5,10,7};
	private int insertColCycler=0;
	private String[] shapeData = {"line","lshape","jshape","zshape","sshape","square","tee"};
	private int shapeDataCycler = 0;
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
				if(!game.getGrid().checkBlock()) {
					insertNewBlock();
				}
				else{
					moveBlock();
				}
			}
		};
		ScheduledFuture<?> lockMvmntHandler = scheduler.scheduleAtFixedRate(blockMvmnt, 1, 1, TimeUnit.SECONDS );
		
	}
	
	/**
	 * Attempts to mvoe the current block of the game down
	 */
	public void moveBlock() {
		System.out.println("it's workign");
		
		game.getGrid().getCurrentBlock().moveDown();
	}
	
	
	public void insertNewBlock() {
		//Select the block type to be added
		//Create block
		Block newBlock  = RandomBlockGenerator.getRandomBlock(game.getGrid());
		game.getGrid().addBlock(newBlock);
		
		System.out.println("block created");
		//Add block to screen
		
	}
}
