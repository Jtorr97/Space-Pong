import java.awt.Graphics;

public class Computer 
{
	private static double x;
	private static double y;
	private double COMPUTER_MAX_SPEED = 10;
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
		// Track the y movement of the ball and move accordingly
		if(ball.getY() < y)
		{
			y -= COMPUTER_MAX_SPEED;
		}
		else if(ball.getY() > y + 75)
		{
			y += COMPUTER_MAX_SPEED;
		}
		
		
		// Handle bounds
		if(y <= 0)
		{
			y = 0;
		}
            
        if(y >= Game.W_HEIGHT - 150)
        {
        	y = Game.W_HEIGHT - 150;
        }
	}
	
	public void render(Graphics g)
	{
		g.drawImage(textures.computer, (int)x, (int)y, null);
	}
	
	public static double getX()
	{
		return x;
	}
	
	public static double getY()
	{
		return y;
	}
}
