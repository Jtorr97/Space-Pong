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
	private final static String TITLE = "Space Pong";
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage image = new BufferedImage(W_WIDTH, W_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private Textures textures;
	
	private PlayerPaddle playerPaddle;
	private Ball ball;
	private Computer computer;
	private Score score;
	private Menu menu;
	private Settings settings;
	private GameOver gameOver;
	private Help help;
	private Fonts fonts;
	
	// Two copies of the background image to scroll
    private MovingBackground backOne;
    private MovingBackground backTwo;

    private BufferedImage back;

    // enum for handling the state of the game
	public static enum STATE
	{
		MENU,
		GAME,
		SETTINGS,
		HELP,
		GAMEOVER
	};
	
	public static STATE state = STATE.MENU;	// Default game state
	
	private void initialize()
	{
		requestFocus();
	
		// Load sprite sheet
		BufferedImageLoader loader = new BufferedImageLoader();
		try 
		{
			spriteSheet = loader.loadImage("assets/sprites.png");
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// Add our Key/MouseListeners
		this.addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput(this));
		
		// Initialize game objects
		fonts = new Fonts();
		textures = new Textures(this);
		playerPaddle = new PlayerPaddle(0, W_HEIGHT / 2 - (SpriteSheet.PIXEL_SIZE * 3 / 2), textures);
		ball = new Ball(W_WIDTH / 2 - (SpriteSheet.PIXEL_SIZE / 2), W_HEIGHT / 2 - (SpriteSheet.PIXEL_SIZE / 2), textures);
		computer = new Computer(W_WIDTH - SpriteSheet.PIXEL_SIZE, W_HEIGHT / 2 - (SpriteSheet.PIXEL_SIZE * 3 / 2), textures, ball);
		score = new Score(0, 0, fonts);
		menu = new Menu(fonts);
		settings = new Settings(fonts);
		help = new Help(fonts);
		gameOver = new GameOver(score, fonts);
		backOne = new MovingBackground();
        backTwo = new MovingBackground(backOne.getImageWidth(), 0);
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
			e.printStackTrace();
		}
		System.exit(1);
	}

	// Main Game loop
	public void run() 
	{	
		initialize();
		long lastTime = System.nanoTime();
		final double AMOUNT_OF_TICKS = 60.0;
		double ns = 1000000000 / AMOUNT_OF_TICKS;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		// Game loop 
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

            if(System.currentTimeMillis() - timer >= 1000) 
            {
                timer += 1000;
                System.out.println(updates + " Ticks, FPS " + frames);
                updates = 0;
                frames = 0;
            }
        }
	    stop();
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
	
	private void render() 
	{
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null)
		{
			// Triple buffering
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		/*
		 * Begin rendering here
		 */
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

		// Always render the moving background 
		renderMovingBackground(g);
		
		if(state == STATE.GAME)
		{
	        // Render game objects
			playerPaddle.render(g);
			ball.render(g);
			computer.render(g);
			score.render(g);
		}	
		else if(state == STATE.MENU)
		{	    
			// Render the menu
			menu.render(g);
		}
		else if(state == STATE.SETTINGS)
		{
			// Render the settings screen
			settings.render(g);
		}
		else if(state == STATE.HELP)
		{
			// Render the help screen
			help.render(g);
		}
		else if(state == STATE.GAMEOVER)
		{			
			// Render the game over screen
			gameOver.render(g);
			playerPaddle.render(g);
			ball.render(g);
			computer.render(g);
			score.render(g);
		}
		/*
		 * End rendering here
		 */
		
		g.dispose();
		bs.show();
	}
	
	// Used to handle keyinput in the GAME and SETTING state
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
		if(state == STATE.SETTINGS || state == STATE.HELP)
		{
			switch (key)
			{
			case KeyEvent.VK_ESCAPE:
				state = STATE.MENU;	// Go back to menu
				break;
			}
		}
	}
	
	// Used to handle key released inputs in GAME state
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(state == STATE.GAME)
		{
			switch (key)
			{
			case KeyEvent.VK_W:
	            playerPaddle.setUpAcceleration(false);
				break;
				
			case KeyEvent.VK_S:
	            playerPaddle.setDownAcceleration(false);
				break;
			}
		}
	}
	
	// Used to start a new game
	private void resetGame() 
	{
		playerPaddle = new PlayerPaddle(0, W_HEIGHT / 2 - (SpriteSheet.PIXEL_SIZE * 3 / 2), textures);
		ball = new Ball(W_WIDTH / 2 - (SpriteSheet.PIXEL_SIZE / 2), W_HEIGHT / 2 - (SpriteSheet.PIXEL_SIZE / 2), textures);
		computer = new Computer(W_WIDTH - SpriteSheet.PIXEL_SIZE, W_HEIGHT / 2 - (SpriteSheet.PIXEL_SIZE * 3 / 2), textures, ball);
		score = new Score(0, 0, fonts);
	}
	
	// Small method for rendering the moving background
	private void renderMovingBackground(Graphics g)
	{
		// Put the two copies of the background image onto the buffer
        backOne.render(g);
        backTwo.render(g);

        // Draw the image onto the window
        g.drawImage(back, 0, 0, null);     
	}
	
	// Used to handle mouse input in the MENU, GAMEOVER, and SETTINGS states
	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		if(state == STATE.MENU)
		{
			if(mx >= Game.W_WIDTH / 2 - 100 && mx <= Game.W_WIDTH / 2 + 200)
			{
				// Play button
				if(my >= 300 && my <= 350)
				{
					// Pressed play button
					state = STATE.GAME;
				
					// Start the background music for the game and stop the menu music
					Music.GAME_THEME.play(true);
					Music.MENU_THEME.stop();
				}
			

				// Settings button
				if(my >= 400 && my <= 450)
				{
					// Pressed settings button
					state = STATE.SETTINGS;
				}
		
			
				// Help button	
				if(my >= 500 && my <= 550)
				{
					// Pressed the help button
					state = STATE.HELP;
				}
				
				// Quit button
				if(my >= 600 && my <= 650)
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
					// Pressed quit button
					state = STATE.MENU;
					Music.GAME_THEME.stop();
					Music.MENU_THEME.play(true);
					resetGame();
				}
			}
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
