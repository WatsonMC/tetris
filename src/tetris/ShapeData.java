package tetris;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ShapeData {
	// dataclass for shape data objects for each possible tetris shape
	// These will be 
	
	// each of the four boolean[][] objects contains a 4x4 grid, that 4x4 grid is a rep of the shape in the 4x4 possible grid
	boolean[][][] rotationStates = new boolean[4][4][4];
	String name;
	int defaultState;
	int colourIndex;
	
	public static final ShapeData line = new ShapeData("line", "/line.txt",0,1);
	public static final ShapeData lshape = new ShapeData("lshape", "/lshape.txt",0,2);
	public static final ShapeData jshape = new ShapeData("jshape", "/jshape.txt",0,3);
	public static final ShapeData sshape = new ShapeData("sshape", "/sshape.txt",0,4);
	public static final ShapeData zshape = new ShapeData("zshape", "/zshape.txt",0,5);
	public static final ShapeData square = new ShapeData("square", "/square.txt",0,6);
	public static final ShapeData tee = new ShapeData("tee", "/tee.txt",0,0);
	
	/**REP
	 * - Default state 
	 * Value of 0 to 3, indexes the first array of the rotation states boolean array
	 * 0 => rotation state = rotationStates[0][][]
	 * 
	 * -rotationStates
	 * set of four 2D boolean arrays which represent 4x4 grid of shapes pattern
	 */
	
	/**
	 * REO
	 * @param name
	 * @param fileName
	 * @param defaultState
	 * @param colourIndex
	 */
	
	public ShapeData(String name, String fileName, int defaultState, int colourIndex) {
		
		this.name = name;
		this.colourIndex = colourIndex;
		this.defaultState= defaultState;
		
		Scanner sc = new Scanner(ShapeData.class.getResourceAsStream(fileName));
		
		//using sc, read the entire file object and store shape data
		// first array
		for(int i = 0; i<4;i++) {
			//second array
			for(int j = 0 ; j<4;j++) {
				//third array
				String line = sc.nextLine();
				for(int k=0;k<4;k++) {
					//now rotationStates[i][j][k] = set a single state
					if(line.charAt(k)=='1') {
						//set state true
						rotationStates[i][j][k] = true;
					}
				}
			}
		}
		
		sc.close();
	}
	
	/**
	 * setter method for colour index
	 * @param colourIndex
	 */
	public void setColour(int colourIndex) {
		this.colourIndex = colourIndex;
	}
	
}
