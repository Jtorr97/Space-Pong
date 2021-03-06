import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player 
{
	private static final double GRAVITY = 0.98;
	private static double x;
	private static double y;
	private double velocityY;
	private boolean upAcceleration = false;
	private boolean downAcceleration = false;
	private final double ACCELERATION = 5;
	private final double P_MAX_SPEED = 10;
		
	private Textures textures;
	
	// Default constructor
	public Player(double x, double y, Textures textures)
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
		{
			velocityY = P_MAX_SPEED;
		}		
		else if(velocityY <= -P_MAX_SPEED)
		{
			velocityY = -P_MAX_SPEED;
		}	
		
		// Handle bounds
		if(y <= 0)
		{
			y = 0;
		}          
        if(y >= Game.W_HEIGHT - SpriteSheet.PIXEL_SIZE * 3)
        {
        	y = Game.W_HEIGHT - SpriteSheet.PIXEL_SIZE * 3;
        }
                 
        // Update paddle velocity/position
        y += velocityY;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(textures.player, (int)x, (int)y, null);
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

	public static double getX()
	{
		return x;
	}
}
