import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter
{
	private Game game;
	
	public MouseInput(Game game)
	{
		this.game = game;
	}
	
	public void mousePressed(MouseEvent e)
	{
		game.mousePressed(e);
	}
}
