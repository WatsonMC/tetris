package tetris;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static Image[] loadImage(String path, int width) throws IOException {
		// returns an array of images trimmed from orignal immage
		// trims the 6 block of differente tetris colours into single blocks
		
		BufferedImage load = ImageIO.read(ImageLoader.class.getResource(path));
		Image[] images = new Image[load.getWidth()/width];	// generates array of the individual blocks to b used
		
		for(int i =0; i<images.length;i++) {
			images[i] = load.getSubimage(i*width, 0, width, width);
		}
		return images;
	}
	
	
}
