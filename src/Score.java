import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Score 
{
	private static int compScore = 0;
	private static int playerScore = 0;
	public static int MAX_SCORE = 10;
	private Fonts fonts;
		
	public Score(int compScore, int playerScore, Fonts fonts)
	{
		this.compScore = compScore;
		this.playerScore = playerScore;
		this.fonts = fonts;
	}
	
	public void updateScore()
	{
		if(Ball.out_of_bounds == Ball.OUT_OF_BOUNDS.WEST)
		{
			compScore += 1;
			Sound.COMPUTER_SCORED.play();
		}
		if(Ball.out_of_bounds == Ball.OUT_OF_BOUNDS.EAST)
		{
			playerScore += 1;
			Sound.PLAYER_SCORED.play();
		}
		Ball.out_of_bounds = null;
	}
	
	public void checkScore()
	{
		if(playerScore == MAX_SCORE || compScore == MAX_SCORE)
		{
			Game.state = Game.STATE.GAMEOVER;
		}
	}
	
	public void tick()
	{
		updateScore();
		checkScore();
	}
	
	public void render(Graphics g)
	{
        Graphics2D g2d = (Graphics2D) g;

		// Scoreboard
		g.setFont(fonts.getFont2().deriveFont(125f));
        g.setColor(Color.WHITE);
        g.drawString("You: " + String.valueOf(playerScore), Game.W_WIDTH / 2 - Game.W_HEIGHT / 2, 75);
        g.drawString("Computer: " + String.valueOf(compScore), Game.W_WIDTH / 2 + Game.W_HEIGHT / 5, 75);
        
        // Draw some lines down the middle of the screen
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(Game.W_WIDTH / 2, 0, Game.W_WIDTH / 2, Game.W_HEIGHT);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.WHITE);
        g2d.drawLine(0, Game.W_HEIGHT, Game.W_WIDTH, Game.W_HEIGHT);
        //g2d.drawLine(0, 0, Game.W_WIDTH, 0);
        g2d.drawOval(Game.W_WIDTH / 2 - 250 / 2, Game.W_HEIGHT / 2 - 250 / 2, 250, 250);
	}

	public int getPlayerScore() 
	{
		return playerScore;
	}

	public int getComputerScore() 
	{
		return compScore;
	}
}
