import java.awt.Graphics;

public class Ball 
{
	private double x;
	private double y;
	private double velocityX;
	private double velocityY = 2;
	
	private Textures textures;
	
	public Ball(double x, double y, Textures textures)
	{
		this.x = x;
		this.y = y;
		this.textures = textures;
	}
	
	public void tick()
	{
		x += velocityX;
		y += velocityY;
		
		if(y < 0)
		{
			velocityY = -velocityY;
		}
		if(y > 720 - 25)
		{
			velocityY = -velocityY;
		}
	}
	
	public void render(Graphics g)
	{
		g.drawImage(textures.ball, (int)x, (int)y, null);
	}
}
