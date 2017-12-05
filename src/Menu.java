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
	public Rectangle playButton = new Rectangle(Game.W_WIDTH / 2 - 100, 300, 200, 50);
	public Rectangle aboutButton = new Rectangle(Game.W_WIDTH / 2 - 100, 400, 200, 50);
	public Rectangle quitButton = new Rectangle(Game.W_WIDTH / 2 - 100, 500, 200, 50);
	private Fonts fonts;
	
	public Menu(Fonts fonts)
	{
		Music.MENU_THEME.play(true);
		this.fonts = fonts;
	}
	
	public void render(Graphics g)
	{
		String titleText = "Space Pong";
        Graphics2D g2d = (Graphics2D) g;
		
		// For drawing the title
        g.setFont(fonts.getFont1());
        FontMetrics fm = g2d.getFontMetrics();
        int x = ((Game.W_WIDTH - fm.stringWidth(titleText)) / 2);
        int y = 200;
        g.setColor(new Color(92, 240, 225));
        g.drawString(titleText, x, y);
        
        // For drawing the buttons
        g.setFont(fonts.getFont2());
        g2d.draw(playButton);
        g.drawString("Play", playButton.x + 60, playButton.y + 35);
        g2d.draw(aboutButton);
        g.drawString("Settings", aboutButton.x + 30, aboutButton.y + 35);
        g2d.draw(quitButton);
        g.drawString("Quit", quitButton.x + 60, quitButton.y + 35);
	}
}
