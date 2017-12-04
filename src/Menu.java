import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

public class Menu 
{
	//public Rectangle playButton = new Rectangle(Game.W_WIDTH / 2 + 120, 150, 100, 50);
	public Rectangle playButton = new Rectangle(Game.W_WIDTH / 2 - 100, 300, 200, 50);
	public Rectangle aboutButton = new Rectangle(Game.W_WIDTH / 2 - 100, 400, 200, 50);
	public Rectangle quitButton = new Rectangle(Game.W_WIDTH / 2 - 100, 500, 200, 50);

	
	public void render(Graphics g)
	{
		String titleText = "Space Pong";
        Graphics2D g2d = (Graphics2D) g;
        Font font1 = null;
        Font font2 = null;
        
        // Load custom fonts
		try 
		{
			font1 = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/8-BIT WONDER.TTF")).deriveFont(100f);
			font2 = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/thin_pixel-7.ttf")).deriveFont(75f);
		}
		catch (FontFormatException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		// For drawing the title
        g.setFont(font1);
        FontMetrics fm = g2d.getFontMetrics();
        int x = ((Game.W_WIDTH - fm.stringWidth(titleText)) / 2);
        int y = 200;
        g.setColor(new Color(92, 240, 225));
        g.drawString(titleText, x, y);
        
        // For drawing the buttons
        g.setFont(font2);
        g2d.draw(playButton);
        g.drawString("Play", playButton.x + 60, playButton.y + 35);
        g2d.draw(aboutButton);
        g.drawString("About", aboutButton.x + 60, aboutButton.y + 35);
        g2d.draw(quitButton);
        g.drawString("Quit", quitButton.x + 60, quitButton.y + 35);
	}
}
