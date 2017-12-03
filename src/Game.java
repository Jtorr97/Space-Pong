import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Game extends Canvas implements Runnable 
{
	public static final int W_WIDTH = 1280;
	public static final int W_HEIGHT = 720;
	public final String TITLE = "Space Pong (Alpha)";
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage image = new BufferedImage(W_WIDTH, W_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	private Textures textures;
	
	private PlayerPaddle playerPaddle;
	private Ball ball;
	private Computer computer;
	private Score score;
	
	private void init()
	{
		requestFocus();
		// Init sprites
		BufferedImageLoader loader = new BufferedImageLoader();
		try 
		{
			// Sprites by Nicolás A. Ortega (Deathsbreed) https://opengameart.org/content/pong-graphics
			spriteSheet = loader.loadImage("src/sprites.png");
			background = loader.loadImage("src/bg.png");
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// Add our keylistener
		addKeyListener(new KeyInput(this));
		
		// Init game objects
		textures = new Textures(this);
		playerPaddle = new PlayerPaddle(20, W_HEIGHT / 2 - 75, textures);
		ball = new Ball(W_WIDTH / 2 - 25, W_HEIGHT / 2 - 25, textures);
		computer = new Computer(W_WIDTH - 50, W_HEIGHT / 2 - 75, textures, ball);
		score = new Score();
		
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
                //System.out.println(updates + " Ticks, FPS " + frames);
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
		g.drawImage(background, 0, 0, null);
		playerPaddle.render(g);
		ball.render(g);
		computer.render(g);
		score.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch (key)
		{
		case KeyEvent.VK_UP:
			// Move the player paddle up
            playerPaddle.setUpAcceleration(true);
			break;
			
		case KeyEvent.VK_DOWN:
			// Move the player paddle down
            playerPaddle.setDownAcceleration(true);
			break;
			
		case KeyEvent.VK_N:
			ball = new Ball(W_WIDTH / 2, W_HEIGHT / 2, textures);
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch (key)
		{
		case KeyEvent.VK_UP:
			// Move the player paddle up
            playerPaddle.setUpAcceleration(false);
			break;
			
		case KeyEvent.VK_DOWN:
			// Move the player paddle down
            playerPaddle.setDownAcceleration(false);
			break;
		}
	}
	
	private void tick()
	{
		playerPaddle.tick();
		ball.tick();
		ball.checkCollision(playerPaddle, computer);
		computer.tick();
		score.tick();
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
	
	public BufferedImage getSpriteSheet()
	{
		return spriteSheet;
	}

}
