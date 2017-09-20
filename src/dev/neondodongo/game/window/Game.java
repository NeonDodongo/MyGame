package dev.neondodongo.game.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import dev.neondodongo.game.framework.KeyInput;
import dev.neondodongo.game.framework.ObjectId;
import dev.neondodongo.game.objects.Player;


public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 3381615074586055501L;
	
	private boolean running = false;
	private Thread thread;
	
	public static int WIDTH, HEIGHT;
	
	//Object
	Handler handler;
	
	Random rand = new Random();
	
	
	private void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		handler = new Handler();
		handler.createLevel();
		handler.addObject(new Player(100, 100, handler, ObjectId.Player));
		
		this.addKeyListener(new KeyInput(handler)); //Keyboard input
	}
	
	public synchronized void start() {
		if(running) { //prevent thread from starting if already running 
			return;
		}
		running = true;
		thread = new Thread(this); //refers to Canvas
		thread.start();
	}

	public void run() {	
		init();
		
		this.requestFocus();
		
		long lastTime = System.nanoTime(); //initialize current run-time
		double amountOfTicks = 60.0; //ticks per second
		double ns = 1000000000 / amountOfTicks; //nano-second conversion
		double delta = 0; //delta time
		long timer = System.currentTimeMillis(); //initialize timer milliseconds
		int updates = 0; //initialize update counter
		int frames = 0; //initialize frames counter
		
		System.out.println("Thread started.");
		
		while(running) {
			
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
			
		}
				
	}
	
	private void tick() {
		handler.tick();
	}
	
	private void render() { //renders as fast as pc will allow
		
		BufferStrategy bs = this.getBufferStrategy(); //refers to Canvas
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		//Draw here
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		handler.render(g);
		
		g.dispose();
		bs.show();
		
		
	}
	
	public static void main(String[] args) {
		
		new Window(800, 600, "Game Window", new Game());
		
	}
}
