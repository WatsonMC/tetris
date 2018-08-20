package tetris;

import java.awt.Graphics2D;
import java.awt.Image;

public class TetrisGrid {
	private int WIDTH;
	private int HEIGHT;
	private int[][] grid;		//x,y representation of tetris grid points
	private final int GRID_SIZE;	// defined grid size
	private Image[] tetrisBlocks;
	
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
		
		grid =  new int[this.WIDTH ][this.HEIGHT ];
		for(int x  = 0;x<this.WIDTH ;x++) {
			for(int y = 0; y<this.HEIGHT;y++) {
				grid[x][y] = -1;
			}
		}
		grid[0][0] = 4;
		grid[1][1] = 2;
		grid[2][3] = 3;
		grid[2][1] = 2;
		grid[1][3] = 6;
		grid[this.WIDTH-5][this.HEIGHT-5] = 4;
		grid[this.WIDTH-5][this.HEIGHT-4] = 3;
		grid[this.WIDTH-5][this.HEIGHT-3] = 2;
		grid[this.WIDTH-1][this.HEIGHT-1] = 1;
		for(int i = 0; i <this.WIDTH;i++) {
			grid[i][this.HEIGHT-1] = 1;
		}
		for(int i = 0; i <this.HEIGHT;i++) {
			grid[this.WIDTH -1][i] = 2;
		}
		
	}
	
	
	public void drawGrid(Graphics2D g) {
		for(int x  = 0;x<this.WIDTH ;x++) {
			for(int y = 0; y<this.HEIGHT;y++) {
				// now draw colour of grid
				
				if(this.grid[x][y] == -1) {
					//draw nothing since -1 represents empty block
				}
				else {
					//draw relevant image
					g.drawImage(tetrisBlocks[this.grid[x][y]], (x*this.GRID_SIZE), (y*this.GRID_SIZE),this.GRID_SIZE, this.GRID_SIZE,null);

				}
				
			}
		}
		
	}
}
