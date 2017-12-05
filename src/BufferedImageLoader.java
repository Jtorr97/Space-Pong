import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BufferedImageLoader
{
	private BufferedImage image;
	
	public BufferedImage loadImage(String path) throws IOException 
	{
		System.out.println("PATH = " + path);
        File file = new File(path);
        if (file.exists()) 
        { // First, make sure the path exists
            System.out.println("File exists!");
        }
        if (file.isDirectory()) 
        { // This will tell you if it is a directory
            System.out.println("File is a directory!");
        }
        if (file.isFile()) 
        {     // Similarly, this will tell you if it's a file
            System.out.println("File has a path!");
        }
        System.out.println("Trying to load image:");
        image = ImageIO.read(new File(path));
        System.out.println("Image path found. Returning...");
        return image;		
	}
}
