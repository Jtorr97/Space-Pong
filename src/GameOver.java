import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

public class GameOver
{
	private Rectangle playAgainButton = new Rectangle(Game.W_WIDTH / 2 - 275, 300, 250, 50);
	private Rectangle quitToMenuButton = new Rectangle(Game.W_WIDTH / 2 + 50, 300, 250, 50);
	private Score score;
	private Fonts fonts;
	private String[] text = {"YOU WIN","GAME OVER", "Rematch", "Quit"};
	
	public GameOver(Score score, Fonts fonts)
	{
		this.score = score;
		this.fonts = fonts;
	}
	
	public void render(Graphics g)
	{
        Graphics2D g2d = (Graphics2D) g;
        g.setFont(fonts.getFont1());
        g.setColor(Color.WHITE);
        FontMetrics fm = g2d.getFontMetrics();
        int x = ((Game.W_WIDTH - fm.stringWidth(text[0])) / 2);
        int y = 200;
 
        // If the player wins, they get a message
        if(score.getPlayerScore() == Score.MAX_SCORE)
        {
        	// Win message
            g.drawString(text[0], x, y);
        }
        else if(score.getComputerScore() == Score.MAX_SCORE)
        {
        	// Game Over message      
            g.drawString(text[1], x - 100, y);
        }
        
        // For drawing the buttons
        g.setFont(fonts.getFont2());
        g2d.draw(playAgainButton);
        g.drawString(text[2], playAgainButton.x + 35, playAgainButton.y + 35);
        g2d.draw(quitToMenuButton);
        g.drawString(text[3], quitToMenuButton.x + 90, quitToMenuButton.y + 35);
	}
}
