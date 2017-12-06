import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Settings
{
	private Rectangle currentMaxScore = new Rectangle(100, 100, 400, 50);
	private Rectangle minusMaxScore = new Rectangle(50, 100, 50, 50);
	private Rectangle plusMaxScore = new Rectangle(450, 100, 50, 50);
	private Fonts fonts;
	private String[] text = {"Max Score: ", "-", "+", "[Esc] to return to menu"};

	public Settings(Fonts fonts)
	{
		this.fonts = fonts;
	}
	
	public void render(Graphics g)
	{		
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(new Color(92, 240, 225));
		g.setFont(fonts.getFont2());
		
		// Draw the buttons
        g2d.draw(currentMaxScore);
        g2d.draw(minusMaxScore);
        g2d.draw(plusMaxScore);
        
        // Draw strings 
        g.drawString("Game Settings", 50, 50);
        g.drawString(text[0] + Score.MAX_SCORE, currentMaxScore.x + 60, currentMaxScore.y + 35);
        g.drawString(text[1], minusMaxScore.x + 15, minusMaxScore.y + 40);
        g.drawString(text[2], plusMaxScore.x + 15, plusMaxScore.y + 40);
        g.drawString(text[3], 10, Game.W_HEIGHT - 10);
	}
}
