import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Ball 
{
	private double x;
	private double y;
	private double velocityX = 15;
	private double velocityY = -2;
	
	private Textures textures;
	
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
		int rng = ThreadLocalRandom.current().nextInt(1, 13);
		System.out.println(rng);
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
	
	public void checkCollision()
	{
		// Check if ball collides with left paddle
		if(this.x <= PlayerPaddle.getX())
		{
			if(this.y >= PlayerPaddle.getY() && y <= PlayerPaddle.getY() + SpriteSheet.PIXEL_SIZE * 3)
			{
				Sound.PADDLE_HIT.play();
				this.velocityX = -velocityX; 
				this.velocityY = getRandomSpeed() * getRandomDirection();
			}
		}
		// Check if ball collides with right paddle
		if(this.x >= Computer.getX())
		{
			if(this.y >= Computer.getY() && this.y <= Computer.getY() + SpriteSheet.PIXEL_SIZE * 3)
			{
				Sound.PADDLE_HIT.play();
				this.velocityX = -velocityX;
				this.velocityY = getRandomSpeed() * getRandomDirection();
			}
		}
	}
	
	public void checkBounds()
	{
		// Bounce ball off top/bottom walls
		if(y < 0)
		{
			velocityY = -velocityY;
			Sound.WALL_HIT.play();
		}
		if(y > Game.W_HEIGHT - SpriteSheet.PIXEL_SIZE)
		{
			velocityY = -velocityY;
			Sound.WALL_HIT.play();
		}
			
		// Check if ball goes out of the field
		if(this.x < 0)
		{
			out_of_bounds = OUT_OF_BOUNDS.WEST;
			this.x = Game.W_WIDTH / 2 - 25; 
			this.y = Game.W_HEIGHT / 2 - 25;
			this.velocityX = -velocityX;
			this.velocityY = 2;
		}
		else if(this.x > Game.W_WIDTH)
		{
			out_of_bounds = OUT_OF_BOUNDS.EAST;
			this.x = Game.W_WIDTH / 2 - 25; 
			this.y = Game.W_HEIGHT / 2 - 25;
			this.velocityX = -velocityX;
			this.velocityY = 2;
		}
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
