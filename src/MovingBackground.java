import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MovingBackground 
{
    private BufferedImage image;
	private int x;
	private int y;
	
	public MovingBackground()
	{
        this(0,0);
    }
	
	public MovingBackground(int x, int y)
	{
        this.x = x;
        this.y = y;

        BufferedImageLoader loader = new BufferedImageLoader();
		try 
		{
			image = loader.loadImage("assets/bg.png");
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
    }

	public void render(Graphics g) 
	{

        // Draw the image onto the Graphics reference
        g.drawImage(image, getX(), getY(), image.getWidth(), image.getHeight(), null);

        // Move the x position left for next time
        this.x -= 1;

        // Check to see if the image has gone off stage left
        if (this.x <= -1 * image.getWidth()) 
        {

            // If it has, line it back up so that its left edge is
            // lined up to the right side of the other background image
            this.x = this.x + image.getWidth() * 2;
        }
    }
	
	public int getX() 
	{
        return this.x;
    }
	
    public int getY() 
    {
        return this.y;
    }

    public int getImageWidth() 
    {
        return image.getWidth();
    }
}
