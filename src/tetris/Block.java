package tetris;

import java.awt.Dimension;
import java.util.Random;

public class Block {
	int rowPosn;	// holds the row number reference of the blocks position
	int colPosn;	// holds the col number reference of the blocks position
	int rotationalState; //holds rot state of the shapeData object
	ShapeData shape;
	boolean[][] currentShapeData = new boolean[4][4];
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
		// saves that information privately
		// grid is the object on which to draw the shape
		this.rowPosn = insertRow;
		this.colPosn = insertCol;
		this.shape = shape;
		this.rotationalState = 0;
		this.setShapeData();

	}
	
	private void setShapeData() {
		for(int i = 0; i<4;i++) {
			for(int j = 0; j<4; j++) {
				this.currentShapeData[i][j] = shape.rotationStates[rotationalState][i][j];
			}
		}
	}
	 	
	public Block(TetrisGrid grid,ShapeData shape) {
		this.rowPosn = 3;
		this.colPosn = 12;
		this.shape = shape;
		this.rotationalState = 0;
		this.setShapeData();
	}
	
	/** 
	 * Adds block to the grid object
	 */
	public void addBlock() {
	}
	
	
		
}
