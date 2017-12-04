import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener
{
	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		/*
		public Rectangle playButton = new Rectangle(Game.W_WIDTH / 2 - 100, 300, 200, 50);
		public Rectangle aboutButton = new Rectangle(Game.W_WIDTH / 2 - 100, 400, 200, 50);
		public Rectangle quitButton = new Rectangle(Game.W_WIDTH / 2 - 100, 500, 200, 50);
		*/
		
		// Play button
		if(mx >= Game.W_WIDTH / 2 - 100 && mx <= Game.W_WIDTH / 2 + 200)
		{
			
			if(my >= 300 && my <= 350)
			{
				// Pressed play button
				Game.state = Game.STATE.GAME;
			}
		}

		// About button
		if(mx >= Game.W_WIDTH / 2 - 100 && mx <= Game.W_WIDTH / 2 + 200)
		{
			
			if(my >= 400 && my <= 450)
			{
				// TODO: Make about screen
				// Inform about the making of this project and the controls
			}
		}
		
		// Quit button
		if(mx >= Game.W_WIDTH / 2 - 100 && mx <= Game.W_WIDTH / 2 + 200)
		{
			
			if(my >= 500 && my <= 550)
			{
				// Pressed quit button
				System.exit(1);
			}
		}
	}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}	
}
