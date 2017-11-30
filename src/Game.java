import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Game extends Canvas implements Runnable 
{
	public static final int W_WIDTH = 800;
	public static final int W_HEIGHT = 600;
	public final String TITLE = "Space Pong (Alpha)";
	
	private Thread thread;
	private boolean running = false;
	private BufferedImage image = new BufferedImage(W_WIDTH, W_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	
	// TEST
	private BufferedImage player;
	
	private void init()
	{
		setFocusable(true);
		
		// Init sprites
		BufferedImageLoader loader = new BufferedImageLoader();
		try 
		{
			// Sprites by Nicolás A. Ortega (Deathsbreed) https://opengameart.org/content/pong-graphics
			spriteSheet = loader.loadImage("src/sprites.png");
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		player = ss.grabImage(1, 1, 45, 135);
	}
	
	private synchronized void start()
	{
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop()
	{
		if(!running)
			return;
		
		running = false;
		try
		{
			thread.join();
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}

	// Main Game loop
	public void run() 
	{	
		init();
		long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) 
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if(delta >= 1)
            {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) 
            {
                timer += 1000;
                System.out.println(updates + " Ticks, FPS " + frames);
                updates = 0;
                frames = 0;
            }
        }
        stop();
	}
	
	private void render() 
	{
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		/*
		 * Draw Stuff here!
		 */
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(player, 20, W_HEIGHT /2 - 67, this);	// Paddle height is 135px
		
		g.dispose();
		bs.show();
	}
	
	private void tick()
	{
		
	}
	
	// Driver
	public static void main(String[] args) 
	{
		Game game = new Game();
		game.setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
		
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}

}
