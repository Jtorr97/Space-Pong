import java.awt.Graphics;

public class Computer 
{
	private static double x;
	private static double y;
	private Ball ball;
	
	private Textures textures;
	private boolean upAcceleration;
	private double velocityY;
	private double ACCELERATION = 2.5;
	private boolean downAcceleration;
	private double GRAVITY = 0.98;
	private double P_MAX_SPEED = 10;
	
	Computer(double x, double y, Textures textures, Ball ball)
	{
		this.ball = ball;
		this.x = x;
		this.y = y;
		this.textures = textures;
	}
	
	public void tick()
	{
		if(ball.getY() < y + 75)
		{
			upAcceleration = true;
			downAcceleration = false;

		}
		else if(ball.getY() > y + 75)
		{
			downAcceleration = true;
			upAcceleration = false;
		}
		
		// Increment and decrement speed accordingly
		if(upAcceleration)
		{
			velocityY -= ACCELERATION;
		}
		else if(downAcceleration)
		{
			velocityY += ACCELERATION;
		}
		else if(!upAcceleration && !downAcceleration)
		{
			velocityY *= GRAVITY;
		}
		
		// Handle speed so paddle doesn't go too fast
		if(velocityY >= P_MAX_SPEED)
			velocityY = P_MAX_SPEED;
		else if(velocityY <= -P_MAX_SPEED)
			velocityY = -P_MAX_SPEED;
		
		// Handle bounds
		if(y <= 0)
            y = 0;
        if(y >= 585)
            y = 585;
        
        // Update paddle velocity/position
        y += velocityY;
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
