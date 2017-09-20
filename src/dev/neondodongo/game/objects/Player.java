package dev.neondodongo.game.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import dev.neondodongo.game.framework.GameObject;
import dev.neondodongo.game.framework.ObjectId;
import dev.neondodongo.game.window.Handler;

public class Player extends GameObject {
	
	private float width = 48;
	private float height = 96;
	private float gravity = 0.37f;
	
	private final float MAX_SPEED = 23;
	
	private Handler handler;
	
	public Player(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		x += velX;
		y += velY;
		
		if(falling || jumping) {
			velY += gravity;
			
			if (velY > MAX_SPEED) {
				velY = MAX_SPEED;
			}
			
		}
		
		collision(object);
		
	}

	private void collision(LinkedList<GameObject> object) {
		for(int i=0; i<handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);			
			 //***Detect object collision***\\
			if(tempObject.getId() == ObjectId.Block) {
				if(this.getBoundsTop().intersects(tempObject.getBounds())) {
					y = tempObject.getY() + (height/2); //snaps player to bottom of block
					velY = 0;
				}
				
				if(this.getBounds().intersects(tempObject.getBounds())) {
					y = tempObject.getY() - height; //snaps player to top of block
					velY = 0;
					falling = false;
					jumping = false;
				}
			}
			else {
				falling = true;
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		/**Work on simplifying this
		*
		*/	
		g.setColor(Color.BLUE);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsTop());
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)((int)x+(width/10)), (int) ((int)y+height/2), (int)width/2+(int)width/4, (int)height/2);
	}
	public Rectangle getBoundsTop() {
		return new Rectangle((int)((int)x+(width/10)), (int)y, (int)width/2+(int)width/4, (int)height/2);
	}
	public Rectangle getBoundsRight() {
		return new Rectangle((int)((int)x+width-8), (int)y+5, (int)5, (int)height-10);
	}
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
	}
}
