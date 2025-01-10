package tetris;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
 * Class to generate a random block object
 */
public class RandomBlockGenerator {
	/*
	 * Colour mappings:
	 * 0 = BLUE
	 * 1 = RED
	 * 2 = YELOW 
	 * 3 = GREEN
	 * 4 = PINK
	 * 5 = ORANGE
	 * 6 = CYAN
	 */
	
	private static Map<Integer, ShapeData> shapeMap;
	private static Random rng =  new Random();
	static {
		Map<Integer,ShapeData> aMap = new HashMap<>();
		aMap.put(0,ShapeData.jshape);
		aMap.put(1,ShapeData.line);
		aMap.put(2,ShapeData.jshape);
		aMap.put(3,ShapeData.zshape);
		aMap.put(4,ShapeData.sshape);
		aMap.put(5,ShapeData.square);
		aMap.put(6,ShapeData.tee);
		shapeMap = Collections.unmodifiableMap(aMap);
		
		
	}
	
	/**
	 * Used to generate a block of random shape, colour and position data
	 * @Grid 
	 * grid object on which the block will be drawn. Used to determine max column width
	 * @return
	 * Block object with randomly selected paramaters
	 */
	public static Block getRandomBlock(TetrisGrid grid) {
		int width = grid.getWidth();
		
		//select insert column, knowing that the block is 4 wide and is inserted from the top left corner
		int insertCol = rng.nextInt(width -4);
		
		//select shape
		int shapeSelector = rng.nextInt(7);
		
		//select colour
		int colourSelector = rng.nextInt(7);
		
		ShapeData shapeData = shapeMap.get(shapeSelector);
		shapeData.setColour(colourSelector);
		
		Block newBlock = new Block(grid,0, insertCol, shapeData );
		return newBlock;
	}
	
	
}
