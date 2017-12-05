import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable 
{
	public static final int W_WIDTH = 1280;
	public static final int W_HEIGHT = 720;
	public final static String TITLE = "Space Pong";
	
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
	private Menu menu;
	private Settings settings;
	private GameOver gameOver;
	private Fonts fonts;
	
	public static enum STATE
	{
		MENU,
		GAME,
		SETTINGS,
		GAMEOVER
	};
	
	public static STATE state = STATE.MENU;
	
	private void init()
	{
		requestFocus();
		
		// Init sprites
		BufferedImageLoader loader = new BufferedImageLoader();
		try 
		{
			// Sprites by Nicolás A. Ortega (Deathsbreed) https://opengameart.org/content/pong-graphics
			spriteSheet = loader.loadImage("assets/sprites.png");
			background = loader.loadImage("assets/bg.png");
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// Add our keylistener
		this.addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput(this));
		
		// Init game objects
		fonts = new Fonts();
		textures = new Textures(this);
		playerPaddle = new PlayerPaddle(0, W_HEIGHT / 2 - 75, textures);
		ball = new Ball(W_WIDTH / 2 - 25, W_HEIGHT / 2 - 25, textures);
		computer = new Computer(W_WIDTH - 50, W_HEIGHT / 2 - 75, textures, ball);
		score = new Score(0, 0, fonts);
		menu = new Menu(fonts);
		settings = new Settings(fonts);
		gameOver = new GameOver(score, fonts);
	}
	
	public synchronized void start()
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
		
		final double ONE_SECOND = 1000;
		final int MAX_FPS = 60;
		final double TARGET_FPS = ONE_SECOND / MAX_FPS;
		double fps = 0;
		double ticks = 0;
		long timer = System.currentTimeMillis();
		
        while (running) 
        {
        	ticks++;
        	fps++;
        	
        	if(System.currentTimeMillis() - timer >= 1000)
        	{
        		System.out.println("Ticks: " + ticks + " | " + "FPS: " + fps);
        		timer = System.currentTimeMillis();
        		ticks = 0;
        		fps = 0;
        	}
        	
        	tick();
            render();
            
            try 
            {
				Thread.sleep((long) TARGET_FPS);
			}
            catch (InterruptedException e) 
            {
				e.printStackTrace();
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
		
		//////////////////////////////////////////////////////////////
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(background, 0, 0, null);
		if(state == STATE.GAME)
		{
			playerPaddle.render(g);
			ball.render(g);
			computer.render(g);
			score.render(g);
		}	
		else if(state == STATE.MENU)
		{
			menu.render(g);
		}
		else if(state == STATE.SETTINGS)
		{
			settings.render(g);
		}
		else if(state == STATE.GAMEOVER)
		{
			gameOver.render(g);
		}
		/////////////////////////////////////////////////////////////
		
		g.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(state == STATE.GAME)
		{
			switch (key)
			{
			case KeyEvent.VK_W:
				// Move the player paddle up
	            playerPaddle.setUpAcceleration(true);
				break;
				
			case KeyEvent.VK_S:
				// Move the player paddle down
	            playerPaddle.setDownAcceleration(true);
				break;
	
			case KeyEvent.VK_ESCAPE:
				state = STATE.MENU;
				Music.GAME_THEME.stop();
				Music.MENU_THEME.play(true);
				resetGame();
				break;
			}
		}
		if(state == STATE.SETTINGS)
		{
			switch (key)
			{
			case KeyEvent.VK_ESCAPE:
				state = STATE.MENU;
				break;
			}
		}
	}
	
	private void resetGame() 
	{
		playerPaddle = new PlayerPaddle(0, W_HEIGHT / 2 - 75, textures);
		ball = new Ball(W_WIDTH / 2 - 25, W_HEIGHT / 2 - 25, textures);
		computer = new Computer(W_WIDTH - 50, W_HEIGHT / 2 - 75, textures, ball);
		score = new Score(0, 0, fonts);
	}
	
	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		if(state == STATE.GAMEOVER)
		{
			// Play again button
			if(mx >= (Game.W_WIDTH / 2) - 275 && mx <= Game.W_WIDTH / 2 - 25)
			{	
				if(my >= 300 && my <= 350)
				{
					// Pressed play button
					state = STATE.GAME;
					resetGame();
				}
			}

			// Quit button
			if(mx >= Game.W_WIDTH / 2 + 50 && mx <= Game.W_WIDTH / 2 + 300)
			{
							
				if(my >= 300 && my <= 350)
				{
					state = STATE.MENU;
					Music.GAME_THEME.stop();
					Music.MENU_THEME.play(true);
					resetGame();
				}
			}
		}
		if(state == STATE.MENU)
		{
			// Play button
			if(mx >= Game.W_WIDTH / 2 - 100 && mx <= Game.W_WIDTH / 2 + 200)
			{
				
				if(my >= 300 && my <= 350)
				{
					// Pressed play button
					state = STATE.GAME;
					
					// Start the bg music for the game and stop the menu music
					Music.GAME_THEME.play(true);
					Music.MENU_THEME.stop();
				}
			}

			// About button
			if(mx >= Game.W_WIDTH / 2 - 100 && mx <= Game.W_WIDTH / 2 + 200)
			{
				
				if(my >= 400 && my <= 450)
				{
					state = STATE.SETTINGS;
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
		if(state == STATE.SETTINGS)
		{
			// Decrease the max score
			if(mx >= 50 && mx <= 100)
			{
				if(my >= 100 && my <= 150)
				{
					Score.MAX_SCORE -= 1;
				}
			}
			// Increase the max score
			if(mx >= 450 && mx <= 500)
			{
				if(my >= 100 && my <= 150)
				{
					Score.MAX_SCORE += 1;
				}
			}
			System.out.println(mx + " " + my);
		}
	}

	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(state == STATE.GAME)
		{
			switch (key)
			{
			case KeyEvent.VK_W:
				// Move the player paddle up
	            playerPaddle.setUpAcceleration(false);
				break;
				
			case KeyEvent.VK_S:
				// Move the player paddle down
	            playerPaddle.setDownAcceleration(false);
				break;
			}
		}
	}
	
	private void tick()
	{
		if(state == STATE.GAME)
		{
			playerPaddle.tick();
			ball.tick();
			computer.tick();
			score.tick();
		}
	}
	
	// Driver
	public static void main(String[] args) 
	{
		new Window(W_WIDTH, W_HEIGHT, TITLE, new Game());
	}
	
	public BufferedImage getSpriteSheet()
	{
		return spriteSheet;
	}

}
