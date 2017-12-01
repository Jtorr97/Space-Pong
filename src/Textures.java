import java.awt.image.BufferedImage;

public class Textures
{
	public BufferedImage player;	// TODO: Add ball and ai paddle
	private SpriteSheet ss = null;
	
	public Textures(Game game)
	{
		ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
	}

	private void getTextures() 
	{
		player = ss.grabImage(1, 1, 45, 135);
		// TODO: Add ball and ai paddle
	}
}
