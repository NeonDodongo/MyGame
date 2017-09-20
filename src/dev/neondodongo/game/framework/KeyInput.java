package dev.neondodongo.game.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import dev.neondodongo.game.window.Handler;

public class KeyInput extends KeyAdapter {
	
	Handler handler;
	private boolean doubleJump = false;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		
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
				if(key == KeyEvent.VK_SPACE && !tempObject.isJumping() && doubleJump == false) {
					tempObject.setJumping(true);
					tempObject.setVelY(-9);
					doubleJump = true;
					System.out.println("Jumped: " + doubleJump);
				}
				else if(key == KeyEvent.VK_SPACE && doubleJump == true) {
					doubleJump = false;
					tempObject.setVelY(-10);
					System.out.println("Jumped: " + doubleJump);
					
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
				if(key == KeyEvent.VK_SPACE) {

				}
			}
		}
	}
}
