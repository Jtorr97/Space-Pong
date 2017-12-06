import java.awt.image.BufferedImage;

public class Textures
{
	public BufferedImage ball;
	public BufferedImage player;	
	public BufferedImage computer;
	private SpriteSheet ss = null;
	
	public Textures(Game game)
	{
		ss = new SpriteSheet(game.getSpriteSheet());
		getTextures();
	}

	private void getTextures() 
	{
		ball = ss.grabImage(3, 1, SpriteSheet.PIXEL_SIZE, SpriteSheet.PIXEL_SIZE);
		player = ss.grabImage(1, 1, SpriteSheet.PIXEL_SIZE, SpriteSheet.PIXEL_SIZE * 3);
		computer = ss.grabImage(2, 1, SpriteSheet.PIXEL_SIZE, SpriteSheet.PIXEL_SIZE * 3);
	}
}
