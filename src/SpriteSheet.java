import java.awt.image.BufferedImage;

public class SpriteSheet 
{
	private BufferedImage image;
	public static final int PIXEL_SIZE = 50;
	
	public SpriteSheet(BufferedImage image)
	{
		this.image = image;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height)
	{
		BufferedImage img = image.getSubimage((col * PIXEL_SIZE) - PIXEL_SIZE, (row * PIXEL_SIZE) - PIXEL_SIZE, width, height);
		return img;
	}
}
