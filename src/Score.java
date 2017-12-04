import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Score 
{
	private double x;
	private double y;
	private static int compScore = 0;
	private static int playerScore = 0;
	private static int MAX_SCORE = 10;
		
	public Score()
	{
	}
	
	public void updateScore()
	{
		if(Ball.out_of_bounds == Ball.OUT_OF_BOUNDS.WEST)
		{
			compScore += 1;
		}
		if(Ball.out_of_bounds == Ball.OUT_OF_BOUNDS.EAST)
		{
			playerScore += 1;
		}
		Ball.out_of_bounds = null;
	}
	
	public void checkScore()
	{
		// TODO: Implement game over screens and game states
		if(playerScore == MAX_SCORE)
		{
			System.out.println("PLAYER WINS!");
		}
		if(compScore == MAX_SCORE)
		{
			System.out.println("COMPUTER WINS!");
		}
	}
	
	public void tick()
	{
		updateScore();
		checkScore();
	}
	
	public void render(Graphics g)
	{
		// Scoreboard
		Font font = new Font("COURIER", Font.PLAIN, 75);
		g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(playerScore), 540, 75);
        g.drawString(String.valueOf(compScore), 700, 75);
        
        // Draw some lines down the middle of the screen
        for(int i = 20; i < 720; i += 20)
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(5));
            g2d.drawLine(1280 / 2, i, 1280 /2, i);
        }
	}
}
