import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu 
{
	private Rectangle playButton = new Rectangle(Game.W_WIDTH / 2 - 100, 300, 200, 50);
	private Rectangle settingsButton = new Rectangle(Game.W_WIDTH / 2 - 100, 400, 200, 50);
	private Rectangle helpButton = new Rectangle(Game.W_WIDTH / 2 - 100, 500, 200, 50);
	private Rectangle quitButton = new Rectangle(Game.W_WIDTH / 2 - 100, 600, 200, 50);
	private Fonts fonts;
	
	public Menu(Fonts fonts)
	{
		// Menu music plays by default since Menu state is the primary state
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
        g2d.draw(settingsButton);
        g.drawString("Settings", settingsButton.x + 30, settingsButton.y + 35);
        g2d.draw(helpButton);
        g.drawString("Help", helpButton.x + 60, helpButton.y + 35);
        g2d.draw(quitButton);
        g.drawString("Quit", quitButton.x + 60, quitButton.y + 35);
	}
}
