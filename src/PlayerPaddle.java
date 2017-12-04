import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PlayerPaddle 
{
	private static final double GRAVITY = 0.98;
	private double x;
	private static double y;
	private double velocityY;
	private boolean upAcceleration = false;
	private boolean downAcceleration = false;
	private final int P_WIDTH = 45;
	private final int P_HEIGHT = 135;
	private final double ACCELERATION = 2.5;
	private final double P_MAX_SPEED = 10;
		
	private Textures textures;
	
	// Default constructor
	public PlayerPaddle(double x, double y, Textures textures)
	{
		this.x = x;
		this.y = y;
		this.textures = textures;
	}
	
	public void tick()
	{
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
		g.drawImage(textures.player, (int)x, (int)y, null);
	}
	
	public int getPlayerPaddleHeight()
	{
		return P_HEIGHT;
	}
	
	public int getPlayerPaddleWidth()
	{
		return P_WIDTH;
	}
	
	public static double getY()
	{
		return y;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public void setUpAcceleration(boolean upAcceleration)
    {
        this.upAcceleration = upAcceleration;
    }

    public void setDownAcceleration(boolean downAcceleration)
    {
        this.downAcceleration = downAcceleration;
    }
}
