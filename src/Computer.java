import java.awt.Graphics;

public class Computer 
{
	private double x;
	private double y;
	private Ball ball;
	
	private Textures textures;
	
	Computer(double x, double y, Textures textures, Ball ball)
	{
		this.ball = ball;
		this.x = x;
		this.y = y;
		this.textures = textures;
	}
	
	public void tick()
	{
		if(ball.getX() < 640)
		{
			if(ball.getY() < y)
			{
				y -= 5;
			}
			else if(ball.getY() > y)
			{
				y += 5;
			}
		}
		if(ball.getX() > 640)
		{
			if(ball.getY() < y)
			{
				y -= 9;
			}
			else if(ball.getY() > y)
			{
				y += 9;
			}
		}
		
		
		if(y <= 0)
			y = 0;
		if(y >= 585)
			y = 585;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(textures.computer, (int)x, (int)y, null);
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
}
