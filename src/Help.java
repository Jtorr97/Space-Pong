import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Help 
{
	// Array to hold text 
	private	String[] text = {"How to Play", "W - Move Up", "S - Move Down",
			"Credits (Programming)", "Joshua Torres", "Credits (Graphics and Assets from opengameart.org)",
			"joth",	"dst", "deathsbreed", "westbeam", "subspaceaudio", "[Esc] to return to menu"};
	private Fonts fonts;
	
	public Help(Fonts fonts)
	{
		this.fonts = fonts;
	}
	
	public void render(Graphics g)
	{	
        Graphics2D g2d = (Graphics2D) g;
        
        // For centering the text
        FontMetrics fm = g2d.getFontMetrics();
        int x = (Game.W_WIDTH / 2) - fm.stringWidth(text[0]);
        int y = 50;
		
		// For drawing the text
        g.setFont(fonts.getFont2().deriveFont(50f));
        g.setColor(new Color(92, 240, 225));
        g.drawString(text[0], x, y);
        g.drawString(text[1], x, y + 50);
        g.drawString(text[2], x, y + 100); 
        g.drawString(text[3], x, y + 200);
        g.drawString(text[4], x, y + 250);
        g.drawString(text[5], x, y + 350);
        g.drawString(text[6], x, y + 400);
        g.drawString(text[7], x, y + 450);
        g.drawString(text[8], x, y + 500);
        g.drawString(text[9], x, y + 550);
        g.drawString(text[10], x, y + 600);
        g.drawString(text[11], 10, Game.W_HEIGHT - 10);
	}
}
