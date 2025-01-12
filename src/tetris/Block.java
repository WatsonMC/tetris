package tetris;

import java.util.List;

public class Block {
	int rowPosn;	// holds the row number reference of the blocks position
	int colPosn;	// holds the col number reference of the blocks position
	int rotationalState; //holds rot state of the shapeData object
	ShapeData shape;
	boolean[][] currentShapeData = new boolean[4][4];
	private TetrisGrid grid;
	private boolean drawn = false;
	/**
	 * 
	 * @param grid
	 * Grid object to draw the shape on
	 * @param insertRow
	 * Insert row to place in
	 * @param insertCol
	 * Insert column to place in
	 */
	public Block(TetrisGrid grid, int insertRow, int insertCol, ShapeData shape) {
		//constructs the block shape
		// generates the required image blocks,aseembling them in some way which can be used
		// generates the required image blocks,aseembling them in some way which can be used
		// saves that information privately
		// grid is the object on which to draw the shape
		this.rowPosn = insertRow;
		this.colPosn = insertCol;
		this.shape = shape;
		this.rotationalState = 0;
		this.grid = grid;
		this.setShapeData();
		insertBlock();
	}
	
	private void setShapeData() {
		for(int i = 0; i<4;i++) {
			for(int j = 0; j<4; j++) {
				this.currentShapeData[i][j] = shape.rotationStates[rotationalState][i][j];
			}
		}
	}
	 
	//TODO DRY!! work our how to get this to call the main construcor
	public Block(TetrisGrid grid,ShapeData shape) {
		this.rowPosn = 3;
		this.colPosn = 12;
		this.shape = shape;
		this.rotationalState = 0;
		this.grid = grid;

		this.setShapeData();
		//insertBlock();

	}
	
	/** 
	 * Adds block to the grid object
	 */
	public void insertBlock() {
		grid.currentBlock = this;
		drawn = true;	//Drawn really means inserted into the block at all
		//not necessarily that at this specific instant the block is on the canvas

	}
	
	
	/**
	 * Method to move block left from current location by 1 unit
	 * If block is not already on grid, will add it in a position 1 left of initial positions
	 * 
	 *
	 */
	public void moveLeft() {
		if(!drawn) {
			System.out.println("Moveleft command given to block not yet drawn");
			System.exit(1);
		}
		//remove block, check new location, then reinsert in old or new location
		grid.removeBlock(this);
		if(grid.checkMove(this, this.rowPosn, this.colPosn-1)){
			this.colPosn-=1;
			grid.addBlock(this);
		}
		else {
			grid.addBlock(this);
		}
	}
	
	public void moveRight() {
		if(!drawn) {
			System.out.println("MoveRight command given to block not yet drawn");
			System.exit(1);
		}
		grid.removeBlock(this);
		if(grid.checkMove(this, this.rowPosn, this.colPosn+1)){
			this.colPosn+=1;
			grid.addBlock(this);
		}
		else {
			grid.addBlock(this);
		}
	}
	
	public void moveDown() {
		if(!drawn) {
			System.out.println("MoveDown command given to block not yet drawn");
			System.exit(1);
		}
		grid.removeBlock(this);
		if(grid.checkMove(this, this.rowPosn+1, this.colPosn)){
			this.rowPosn+=1;
			grid.addBlock(this);
		}
		else {
			grid.addBlock(this);
			grid.removeBlockFromGrid(this);	//will delete the block, and add a new one
			System.out.println("block permanantly added to grid, dereferenced from grid");
		}
	}
	public void moveUp() {
		if(!drawn) {
			System.out.println("MoveDown command given to block not yet drawn");
			System.exit(1);
		}
		if(grid.checkMove(this, this.rowPosn-1, this.colPosn)){
			grid.removeBlock(this);
			this.rowPosn-=1;
			grid.addBlock(this);
		}
	}
	
	/**
	 * Method to rotate a block. Will check that the rotation is allowed, then proceed if so, or do nothing
	 */
	public void rotateBlock() {
		//drawn check
		if(!drawn) {
			System.out.println("MoveDown command given to block not yet drawn");
			System.exit(1);
		}
		//rotate and check
		grid.removeBlock(this);
		this.rotationalState = (this.rotationalState+1)%4 ;
		this.setShapeData();
		if(grid.checkMove(this,this.rowPosn,this.colPosn)) {
			grid.addBlock(this);
		}
		else {
			this.rotationalState--;
			this.setShapeData();
			grid.addBlock(this);
		}
	}
	
	/**
	 * getter/setter methods for the row and column position of the block
	 * @return
	 */
	public int getCol() {
		return this.colPosn;
	}
	public int getRow() {
		return this.rowPosn;
	}
	
	public void setCol(int col) {
		this.colPosn = col; 
	}
	public void setRow(int row) {
		this.rowPosn = row; 
	}
	
	
		
}
