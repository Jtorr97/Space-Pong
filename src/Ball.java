import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

public class Ball 
{
	private double x;
	private double y;
	private double velocityX = 16;
	private double velocityY = -2;
	
	private Textures textures;
	
	// Enum for handling the ball going out of the left/right bounds
	public static enum OUT_OF_BOUNDS
	{
		WEST,
		EAST
	}
	
	public static OUT_OF_BOUNDS out_of_bounds;
	
	public Ball(double x, double y, Textures textures)
	{
		this.x = x;
		this.y = y;
		this.textures = textures;
	}
	
	public void tick()
	{
		checkBounds();
		checkCollision();
		
		// Update movement
		x += velocityX;
		y += velocityY;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(textures.ball, (int)x, (int)y, null);
	}
	
	// For giving the ball various speed
	public int getRandomSpeed()
	{
		int rng = ThreadLocalRandom.current().nextInt(1, 15);
		return rng;
	}
	
	// For moving the ball in variable directions
	public int getRandomDirection()
	{
		int rng = (int)(Math.random() * 2);
        if(rng == 1)
        {
           return 1;
        }
        else
        {
            return -1;
        }
	}
	
	// Checks if the ball has collided with either paddle
	public void checkCollision()
	{
		// Check if ball collides with left paddle
		if(x <= Player.getX())
		{
			if(y >= Player.getY() && y <= Player.getY() + SpriteSheet.PIXEL_SIZE * 3)
			{
				hitBall();
			}
		}
		// Check if ball collides with right paddle
		if(x >= Computer.getX())
		{
			if(y >= Computer.getY() && y <= Computer.getY() + SpriteSheet.PIXEL_SIZE * 3)
			{
				hitBall();
			}
		}
	}
	
	// Reverses the direction when the paddle hits the ball
	public void hitBall()
	{
		Sound.PADDLE_HIT.play();
		velocityX = -velocityX; 
		velocityY = getRandomSpeed() * getRandomDirection();
	}
	
	// Check the bounds of the ball
	public void checkBounds()
	{
		// Bounce ball off top/bottom walls
		if(y < 0)
		{
			velocityY = -velocityY;
			Sound.WALL_HIT.play();
		}
		if(y > Game.W_HEIGHT - SpriteSheet.PIXEL_SIZE / 2)
		{
			velocityY = -velocityY;
			Sound.WALL_HIT.play();
		}
			
		// Check if ball goes out of the field
		if(x < -SpriteSheet.PIXEL_SIZE / 2)
		{
			out_of_bounds = OUT_OF_BOUNDS.WEST;
			resetBall();
		}
		else if(x > Game.W_WIDTH + SpriteSheet.PIXEL_SIZE / 2)
		{
			out_of_bounds = OUT_OF_BOUNDS.EAST;
			resetBall();
		}
	}
	
	// Used to reset position of ball after it goes out of bounds
	public void resetBall()
	{
		x = Game.W_WIDTH / 2 - SpriteSheet.PIXEL_SIZE / 2; 
		y = Game.W_HEIGHT / 2 - SpriteSheet.PIXEL_SIZE / 2; 
		velocityX = -velocityX;
		velocityY = 2;
	}

	public double getY() 
	{
		return y;
	}

	public double getX() 
	{
		return x;
	}
}
