package tetris;

import java.awt.Graphics2D;
import java.awt.Image;

public class TetrisGrid {
	private int WIDTH;
	private int HEIGHT;
	private int LAST_ROW;	// integer x coord represneting final row placement posn
	private int LAST_COL;	// integer y co-ord representing final placement position
	private int[][] grid;		//x,y representation of tetris grid points
	private final int GRID_SIZE;	// defined grid size
	
	private int[] EMPTY_ROW;
	private int emptyRowSum;
	
	private Image[] tetrisBlocks;
	
	public Block currentBlock;
	
	/**REP 
	 * Grid:
	 * Grid is an integer 2d array representing XY points in the tetris play screen
	 * value of -1 represents  empty grid position
	 * values 0-6 represent colours of the tetris block resoucre
	 */
	public TetrisGrid(int width, int height,int gridSize, Image[] tetrisBlocks) {
		this.WIDTH = width/gridSize;
		this.HEIGHT = height/gridSize;
		this.tetrisBlocks = tetrisBlocks;
		this.GRID_SIZE  = gridSize;
		
		grid =  new int[this.HEIGHT][this.WIDTH]; //final block position at height-1 and width -1
		this.EMPTY_ROW = new int[this.WIDTH];
		for(int x  = 0;x<this.WIDTH ;x++) {
			for(int y = 0; y<this.HEIGHT;y++) {
				grid[y][x] = -1;
			}
			EMPTY_ROW[x] = -1;
		}
		
		this.emptyRowSum = sumRow(0);
		this.LAST_COL = this.WIDTH-1;
		this.LAST_ROW = this.HEIGHT-1;
		
		grid[this.LAST_ROW][this.LAST_COL] = 6;
		grid[0][this.LAST_COL] = 5;
		grid[0][0] = 4;
		grid[this.LAST_ROW][0] = 3;
		
		grid[0][0] = 2;
		grid[1][1] = 2;
		grid[2][3] = 3;
		grid[2][1] = 2;
		grid[1][3] = 6;
		for(int i = 0 ; i <this.WIDTH ;i ++) {
			grid[15][i] = 1;
		}
		
		for(int i = 0 ; i <this.WIDTH ;i ++) {
			grid[16][i] = 1;
		}
		grid[17][1] = 1;
		grid[14][1] = 1;
		
		for(int i = 0 ; i <this.WIDTH ;i ++) {
			grid[10][i] = 1;
		}
		
		for(int i = 0 ; i <this.WIDTH ;i ++) {
			grid[5][i] = 1;
		}
		
	}
	
	
	/**
	 * Check rep function for grid class
	 * @return
	 * boolean result determining whether the grid calss rep has been maintained or not
	 */
	private boolean checkRep() {
		//check that there are no disallowed values in grid storage
		// check that size and width variables are acceptable (grid can actually be made from the given sizes)
		return true;
	}
	
	
	
	public void drawGrid(Graphics2D g) {
		for(int x  = 0;x<this.WIDTH ;x++) {
			for(int y = 0; y<this.HEIGHT;y++) {
				// now draw colour of grid
				
				if(this.grid[y][x] == -1) {
					//draw nothing since -1 represents empty block
				}
				else {
					//draw relevant image
					g.drawImage(tetrisBlocks[this.grid[y][x]], (x*this.GRID_SIZE), (y*this.GRID_SIZE),this.GRID_SIZE, this.GRID_SIZE,null);
				}
				
			}
		}
		
	}
	
	/** 
	 * Method to detect and clear all full lines of the grid object
	 * Cyles from top row down to bottom row, first listing rows which require removal then removing them
	 */
	public void clearFullLines() {
		//cycle through each row
		//check each row is not full
		//if row is full
			// set that row equal to 
		
		//cycle through rows until you find first non-empty row - that row = firstRow
		//Cycle through rows thereafter
		// if row.equals full
			// row  = row-1 for rows(current row, first row,row --)
			// first row ++
		//next row
		
		
		int firstNonEmptyRow = 0;
		//find first NonEMpty row
		for(int row = 0; row <this.HEIGHT;row ++) {
			//local variable row now contains the row number
			if(sumRow(row) != emptyRowSum) {
				//is not an empty row
				firstNonEmptyRow = row; 
				break;
			}
		}
		
		//cycle through from there
		for(int row = firstNonEmptyRow; row<this.HEIGHT;row++) {
			if(isFullRow(row)) {
				clearFullRow(row,firstNonEmptyRow);
				firstNonEmptyRow +=1;
			}
			else {
				continue;
			}
		}
		
	}
	
	
	/**
	 * Determines if a given row is a fully complete row
	 * @param row number as an integer
	 * @return
	 * True =  the row is full, can be deleted
	 *false = row is false, move on
	 */
	public boolean isFullRow(int row) {
		for(int col = 0; col <this.WIDTH; col++) {
			if(grid[row][col] ==-1) {
				return false;
			}
		}
		return true;
	}
	
	
	/** 
	 * Gets the interger sum of a rows rep values 
	 * @param row number for reference in grid
	 * @return
	 * Integer value of row
	 */
	public int sumRow(int row) {
		int sum = 0;
		for(int i = 0; i<this.WIDTH;i++) {
			sum += grid[row][i];
		}
		return sum;
	}
	
	/**
	 * Method to clear the full row of a grid object, and shift rows down to form complete grid shape
	 * @param fullRow
	 * The y value of the row which is full, and to be removed
	 * @param firstNonEmptyRow
	 * first non empty row above that which should now become completely empty with shift upward
	 * 
	 * fullROW>= firstNonEmpty row for normal calls
	 * fullRow = firstNonEmpty row for call where the first detected row is also a row to be cleared
	 */
	public void clearFullRow(int fullRow,int firstNonEmptyRow) {
		if(fullRow == firstNonEmptyRow) {
			grid[fullRow] = EMPTY_ROW;
		}
		else if(fullRow< firstNonEmptyRow) {
			System.out.println("somehow have passed a non-empty row which is lower than the full row to clear row function");
			System.exit(1);
		}
		else {
			//fullrow must be larger than non empty row
			for(int row = fullRow; row>firstNonEmptyRow;row --){
					grid[row] = grid[row-1];
				}
				grid[firstNonEmptyRow] = EMPTY_ROW;
			}
	}
	
	/**
	 * Adds a block to the grid with its current position and shapedata 
	 * @param block
	 * addBlock method assumes that the placement of block
	 * has already been checked and is okay to add in given loc
	 */
	public void addBlock(Block block) {
		//shape 
		currentBlock = block;
		for(int i= 0; i<4; i++) {
			for(int j =0 ; j<4; j++) {
				if(block.currentShapeData[i][j]){
					grid[block.rowPosn+i][block.colPosn+j] = block.shape.colourIndex;
				}
			}
		}
		
	}
	
	/**
	 * Deletes block from existing location in grid. bloc spaces become blank
	 * @param block
	 * block to be deleted from grid. assumes block already exists at that loc
	 */
	public void removeBlock(Block block) {
		for(int i = 0; i<4; i++) {
			for (int j = 0; j<4;j++) {
				if(block.currentShapeData[i][j]) {
					grid[block.rowPosn+i][block.colPosn+j] = -1;
				}
			}
		}
	}
	
	
	/** 
	 * Handles checking entire legitimacy of move
	 * Will return true only if move is allowed:
	 * - Does not move a part of the block off the play screen
	 * - Does not move part of a block into a different block
	 * 
	 * @param block 
	 * Block object to be checkd. block object does not need to be drawn already
	 * @param rowNumber
	 * Row number for insertion point of block
	 * @param columnNumber
	 * Column number for insertion point of block
	 * @return
	 * TRUE = move is allowed
	 * FALSE  = move is not allowed
	 */
	public boolean checkMove(Block block, int rowNumber, int columnNumber) {
		//TODO FINISH CHECK MOVE FUNCTIONALITY
		// check if inserton coord are out of range	// THIS DOES NOT MATTER, INSERTION POINT IS BELOW 
		//if(rowNumber< 0 || rowNumber> LAST_ROW
			//	|| columnNumber<0|| columnNumber> LAST_COL) {
			//return false;
		//}
		// reached hear means 
		
		
		//TODO Implement automated testing for this method
		//TODO move to seperate function
		//Check each of the 16 squares to see if they are in range
		for(int i = 0; i<4; i++) {
			for(int j =0; j<4;j++) {
				if(block.currentShapeData[i][j]) {
					if(rowNumber+i<0 || rowNumber+i>LAST_ROW
							||columnNumber+j<0||columnNumber+j>LAST_COL) {
						//Position is out of range
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Getter method for current block being moved by grid
	 * @return
	 * returns the grids current block 
	 */
	public Block getCurrentBlock() {
		return this.currentBlock;
	}
	
	/**
	 * Method for checking if the grid has a block at current or not
	 * Returns true iff the grid has a block drawn
	 */
	public boolean checkBlock() {
		if(currentBlock == null) {
			return false;
		}
		return true;
		
	}
	
	public int getWidth() {
		return this.WIDTH;
	}
	
}
