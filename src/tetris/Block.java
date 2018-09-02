package tetris;

import java.awt.Dimension;
import java.util.Random;

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
		insertBlock();

	}
	
	/** 
	 * Adds block to the grid object
	 */
	public void insertBlock() {
		
		grid.addBlock(this);
		drawn = true;
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
		if(grid.checkMove(this, this.rowPosn, this.colPosn-1)){
			grid.removeBlock(this);
			this.colPosn-=1;
			grid.addBlock(this);
		}
	}
	
	public void moveRight() {
		if(!drawn) {
			System.out.println("MoveRight command given to block not yet drawn");
			System.exit(1);
		}
		if(grid.checkMove(this, this.rowPosn, this.colPosn+1)){
			grid.removeBlock(this);
			this.colPosn+=1;
			grid.addBlock(this);
		}
	}
	
	public void moveDown() {
		if(!drawn) {
			System.out.println("MoveDown command given to block not yet drawn");
			System.exit(1);
		}
		if(grid.checkMove(this, this.rowPosn+1, this.colPosn)){
			grid.removeBlock(this);
			this.rowPosn+=1;
			grid.addBlock(this);
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
	
	
		
}
