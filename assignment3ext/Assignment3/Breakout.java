/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */ 
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	 private int lives = NTURNS;
	 private GRect paddle;
	 private GOval ball;
	 private GRect brick;
	 private double vx, vy;
	

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		
		setup();
		addMouseListeners();
		game();
		
		
		
		
	}
	

	private void setup(){
	
		
		setBackground(Color.black);
		
	
		//start pos for paddle
		double paddle_x = WIDTH/2 - PADDLE_WIDTH/2;
		double paddle_y = getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET;
		
		//creating paddle
	    paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.white);
		add(paddle,paddle_x,paddle_y);
		
		
		//creating ball
		ball = new GOval(BALL_RADIUS*2,BALL_RADIUS*2);
		ball.setFilled(true);
		ball.setColor(Color.white);
		add(ball,WIDTH/2 - BALL_RADIUS, HEIGHT/2 - BALL_RADIUS);
		
		//creating bricks
		for(int i=0; i < NBRICK_ROWS; i++ ){
			for(int j=0; j < NBRICKS_PER_ROW; j++ ){
				
				double x = j*(BRICK_WIDTH + BRICK_SEP);
				double y = i*(BRICK_HEIGHT + BRICK_SEP);
				
			    brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				brick.setColor(Color.red);
				
				//coloring bricks
				if ( i > 1){
				  brick.setFilled(true);
				  brick.setColor(Color.orange);
				}
				
				if ( i > 3){
					  brick.setFilled(true);
					  brick.setColor(Color.yellow);
					}
				
				if ( i > 5){
					  brick.setFilled(true);
					  brick.setColor(Color.green);
					}
				
				if ( i > 7){
					  brick.setFilled(true);
					  brick.setColor(Color.cyan);
					}
				
				add(brick, x,BRICK_Y_OFFSET + y );
				
				
			}
		}
	}
	
	
	private void game() {
		
		
		initialSpeed();
			
		while(true){
			ball.move(0, 0);
			pause(12);
			CheckForCollision();
			if(ball.getY() >= getHeight() - BALL_RADIUS*2){
				resetGame();
				lives -= 1;
			}
			if(lives == 0){
				break;
			}
		}
}
	
	private void resetGame() {
		ball.setLocation(WIDTH/2 - BALL_RADIUS, HEIGHT/2 - BALL_RADIUS);
	//	paddle.setLocation((getWidth() - PADDLE_WIDTH)/2, getHeight()- PADDLE_Y_OFFSET);
	}


	//starting speed for ball
	private void initialSpeed() {
		vy = -3;
//		vx = rgen.nextDouble(1.0, 3.0);
//		if (rgen.nextBoolean(0.5)) vx = -vx;			
	}
	
	//checking for collisions of ball
	
	//mouse controls for paddle
	public void mouseMoved(MouseEvent e){
		double paddle_x = e.getX() - PADDLE_HEIGHT/2;
		if(paddle_x< 0){
		   paddle_x = 0;
		}
		
		if(paddle_x > WIDTH - PADDLE_WIDTH){
			paddle_x = WIDTH - PADDLE_WIDTH;
		}
		paddle.setLocation(paddle_x, paddle.getY());
		
	}
	
	public void mouseClicked(MouseEvent e){
		
	}
	
	private GObject getCollidingObject(){
		GObject CollidingObject = getElementAt(ball.getX(), ball.getY());
		
		for (int i = 0; i < 4; i++) {
	        double x = (i % 2 == 0) ? ball.getX() : ball.getX() + BALL_RADIUS * 2;
	        double y = (i < 2) ? ball.getY() : ball.getY() + BALL_RADIUS * 2;

	        GObject collidingObject = getElementAt(x, y);
		}
		
		
		
		
		
		
		
		
		
	}
		
		
		
		
		
		
	
	
        private void CheckForCollision() {
		
		//check ball to not exceed the canvas
		if(ball.getY() <=  0) {
			vy = -vy;
			ball.setLocation(ball.getX(), 0);
		}
		
		if(ball.getX() >= getWidth() - BALL_RADIUS*2){
			vx = -vx;
			ball.setLocation(getWidth() - ball.getWidth(), ball.getY());
		}
		
		if(ball.getX() <= 0){
			vx = -vx;
			ball.setLocation(0, ball.getY());
		}
		
		

		GObject collider = getCollidingObject();
		
		while(collider != null){
		if( collider == paddle){
			vy = -vy;
		}  else{
				vy = -vy;
				remove(collider);
			
			}
        }
			
		}
	
	
	
	

}
