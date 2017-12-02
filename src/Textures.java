import java.awt.image.BufferedImage;

public class Textures
{
	public BufferedImage ball;
	public BufferedImage player;	// TODO: Add ball and ai paddle
	private SpriteSheet ss = null;
	
	public Textures(Game game)
	{
		ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
	}

	private void getTextures() 
	{
		ball = ss.grabImage(3, 1, 50, 50);
		player = ss.grabImage(1, 1, 50, 150);
	}
}
