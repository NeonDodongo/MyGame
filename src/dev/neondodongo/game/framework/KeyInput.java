package dev.neondodongo.game.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import dev.neondodongo.game.window.Handler;

public class KeyInput extends KeyAdapter {
	
	Handler handler;
	private boolean pressed = false;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		pressed = true;
		
		int key = e.getKeyCode(); //get code of key pressed 
		
		for(int i=0; i<handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Player) { //check if player
				if(key == KeyEvent.VK_D) {
					tempObject.setVelX(6);
				}
				if(key == KeyEvent.VK_A) {
					tempObject.setVelX(-6);
				}
				if(key == KeyEvent.VK_SPACE && !tempObject.isJumping()) {
					tempObject.setJumping(true);
					tempObject.setVelY(-8);
				}
				
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode(); 
		
		for(int i=0; i<handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Player) { //check if player
				if(key == KeyEvent.VK_D && tempObject.getVelX() > 0) {
					tempObject.setVelX(0);
				}
				if(key == KeyEvent.VK_A && tempObject.getVelX() < 0) {
					tempObject.setVelX(0);
				}
			}
		}
	}
}
