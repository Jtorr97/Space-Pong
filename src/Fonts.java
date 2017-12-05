import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Fonts
{
	private Font font1;
	private Font font2;
	
	public Fonts()
	{
		// Load custom fonts
		try 
		{
			font1 = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/8-BIT WONDER.TTF")).deriveFont(100f);
			font2 = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/thin_pixel-7.ttf")).deriveFont(75f);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            // Register the font
            ge.registerFont(font1);
            ge.registerFont(font2);
		}
		catch (IOException | FontFormatException e)
		{
            e.printStackTrace();
            System.out.println("Error: Font not found.");
        }
	}
	
	public Font getFont1()
	{
		return font1;
	}
	
	public Font getFont2()
	{
		return font2;
	}
	
	
}
