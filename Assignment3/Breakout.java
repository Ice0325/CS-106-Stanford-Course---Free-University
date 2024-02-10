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

    //Delay for each animation of ball
	private static final int DELAY = 10;
	
    //random generator
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	//number of lives as NTURNS so we can change the lives since NTURNS is static
	private int lives = NTURNS;
	 
	//paddle object
	private GRect paddle;
	 
	//ball object
	private GOval ball;
	 
	//brick object
	private GRect brick;
	 
	//initial velocity
	private double vx, vy;
	
	//number of bricks remaining
	private int brickCount = NBRICK_ROWS * NBRICKS_PER_ROW;
	
	private GLabel loss;
	
	private GLabel LivesLabel = null;
	
	private GOval Coins = null;
	
	private int PaddleHitCount = 0;
	
	private int coins_n = 0;
	
	private GLabel CoinsNumber = null;
	

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		setup();
		addMouseListeners();
		addKeyListeners();
		game();
	}
	
	private void setup(){
		setBackground(Color.black);
		addPaddle();
		addBall();
		addBricks();
		
		Coins();
		CoinsLabel();
	}
	
	//adding paddle
	private void addPaddle(){
		//start pos for paddle
		double paddle_x = WIDTH/2 - PADDLE_WIDTH/2;
		double paddle_y = getHeight()- PADDLE_HEIGHT - PADDLE_Y_OFFSET;
				
		
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.white);
		add(paddle,paddle_x,paddle_y);
		
	}
	
	//adding ball
	private void addBall(){
		ball = new GOval(BALL_RADIUS*2,BALL_RADIUS*2);
		ball.setFilled(true);
		ball.setColor(Color.white);
		add(ball,WIDTH/2 - BALL_RADIUS, HEIGHT/2 - BALL_RADIUS);
	}
	
	private void addBricks(){
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
		
		StartingScreen();
		ballInitialSpeed();
		LivesScreen();
		while(true){
			ball.move(vx, vy);
			CheckForCollision();
			pause(DELAY);
			
			
			
			//check if ball contacts bottom wall
			if(ball.getY() >= HEIGHT - ball.getHeight()){
				resetGame();
				lives--;
				LivesLabel.setLabel("Lives left  " + lives);
				Coins();
				if(lives != 0){
					Resume();
				} else {
					remove(ball);
					LosingScreen();
					askToContinue();
				}
			}
			
			if(brickCount == 0 ){
				remove(ball);
				WinningScreen();
				askToContinue();
				
			}
			
			CoinsNumber.setLabel("Coins    " + coins_n);
		}
	}
	
	private void LivesScreen() {
	    LivesLabel = new GLabel("Lives left  " + lives);
	    LivesLabel.setColor(Color.WHITE);
	    LivesLabel.setFont("Serif-17");
	    add(LivesLabel, 10, getHeight() - 10);
	}


	private void askToContinue() {
		GLabel label = new GLabel("Would you like to create a new game?");
		label.setColor(Color.CYAN);
		label.setFont("Serif-30");
		int offset = 100;
		add(label, WIDTH/2 + offset - label.getWidth()/2, HEIGHT/2 - label.getHeight()/2);
		waitForClick();
		remove(label);
		NewGame();
	}
	
	private void Coins() {
	    Coins = new GOval(12, 12);
	    Coins.setColor(Color.YELLOW);
	    Coins.setFilled(true);
	    add(Coins, WIDTH - 20, getHeight()- 10 - Coins.getHeight());
	}
	
	
	private void CoinsLabel(){
	    CoinsNumber = new GLabel("Coins    " + coins_n);
	    CoinsNumber.setColor(Color.YELLOW);
	    CoinsNumber.setFont("Serif-12");
	    add(CoinsNumber, WIDTH - Coins.getWidth() - 10 - CoinsNumber.getWidth(), getHeight()- 10 );
	}

	private void NewGame(){
		removeAll();
		lives = NTURNS;
		brickCount = NBRICK_ROWS * NBRICKS_PER_ROW;
		setup();
		game();
	}

	private void Resume() {
		GLabel label = new GLabel("Click to RESUME");
		label.setColor(Color.CYAN);
		label.setFont("Serif-30");
		add(label, WIDTH/2 - label.getWidth()/2, HEIGHT/2 - label.getHeight()/2);
		waitForClick();
		remove(label);
	}

	//what to do when reseting game after bumping in bottom wall
	private void resetGame() {
		
		ball.setLocation(WIDTH/2 - BALL_RADIUS, HEIGHT/2 - BALL_RADIUS); 
		vx = rgen.nextDouble(1.0, 3.0);
	    if (rgen.nextBoolean(0.5)) vx = -vx;
	    
	}


	//starting speed for ball
	private void ballInitialSpeed() {
		vy = 3;
	    vx = rgen.nextDouble(1.0, 3.0);
	    if (rgen.nextBoolean(0.5)) vx = -vx;			
	}
	
	//mouse controls for paddle
	public void mouseMoved(MouseEvent e){
		double paddle_x = e.getX() - PADDLE_HEIGHT/2;
		
		//making sure paddle stays in window
		if(paddle_x< 0){
		   paddle_x = 0;
		}
		
		
		if(paddle_x > WIDTH - PADDLE_WIDTH){
			paddle_x = WIDTH - PADDLE_WIDTH;
		}
		
		paddle.setLocation(paddle_x, paddle.getY());
		
	}
	

	
	
	//getting colliding object
	private GObject getCollidingObject() {
	    for (int i = 0; i <= 4; i++) {
	        for (int j = 0; j <= 4; j++) {
	            GObject obj = getElementAt(ball.getX() + ball.getWidth() * i / 4, ball.getY() + ball.getHeight() * j / 4);
	            if (obj != null && obj != ball && obj != LivesLabel && obj != CoinsNumber && obj != Coins) {
	                return obj;
	            }
	        }
	    }
	    return null;
	}
	
	//check for all collisions
	private void CheckForCollision() {
		
		//check ball to not exceed the canvas
		if(ball.getY() <=  0) {
			vy = -vy;
    		ball.setLocation(ball.getX(), 0);
    	}
    		
    	if(ball.getX() >= WIDTH - ball.getWidth()){
    		vx = -vx;
    		ball.setLocation(WIDTH - ball.getWidth(), ball.getY());
    	}
    		
    	if(ball.getX() <= 0){
   			vx = -vx;
   			ball.setLocation(0, ball.getY());
   		}
    	
    		
   		// Check for collisions with the paddle and bricks
    	GObject collider = getCollidingObject();
    		
    	 // If the ball collides with the paddle, change the vertical direction
   		if(collider == paddle){
   			vy = -vy;
   			PaddleHitCount++;
   		 
   		  // check for side collisions
   		    if (ball.getX() <= (paddle.getX() + paddle.getWidth() * 1 / 3) && vx > 0) {
   		        vx = -vx; 
   		    } else {
   		    	if (ball.getX() >= (paddle.getX() + paddle.getWidth() * 2 / 3) && vx < 0) {
   		    
   		        vx = -vx;
   		        }
   		    }
         
   			if(PaddleHitCount % 7 == 0){
   				vy = 1.1 * vy;
   			}
   			// Adjust the ball position to prevent getting stuck in the paddle
   			if(ball.getY() + ball.getHeight() < paddle.getY() + paddle.getHeight()/2){
   				ball.setLocation(ball.getX(), paddle.getY() - ball.getHeight());
   			} 
   			else {
    				if (ball.getY() + ball.getHeight() > paddle.getY() + paddle.getHeight()/2){
    					ball.setLocation(ball.getX(), paddle.getY() + paddle.getHeight());
    				}
    			}
    		}  else {
// If the ball collides with a brick, remove the brick and change the vertical direction, change brick count
    			if( collider != null){
    				vy = -vy;
    				remove(collider);
    				brickCount--;
    				
    				// check for side collisions
    			    if (ball.getX() <= (collider.getX() + collider.getWidth()*9/10) && vx > 0) {
    			    	vx = -vx; 
    			    } else {
    			    		if (ball.getX() > (collider.getX() + collider.getWidth()/10) && vx < 0) {
    			            vx = -vx; 
    			        }
    			    }
    			    
    			    if( collider.getColor() == Color.CYAN){
    			    	coins_n += 1;
    			    } else{
    			    	if(collider.getColor()== Color.GREEN){
    			    		coins_n += 2;
    			    	}else{
        			    	if(collider.getColor()== Color.YELLOW){
        			    		coins_n += 3;
        			    	}else{
            			    	if(collider.getColor()== Color.ORANGE){
            			    		coins_n += 4;
            			    	}else{
                			    	if(collider.getColor()== Color.RED){
                			    		coins_n += 5;
                			    	}
            			    	}
        			    	}
    			    	}
    			    }
    			}
    		}
  
        }
	
	private void LosingScreen() {
	    loss = new GLabel("YOU LOST");
		loss.setColor(Color.RED);
		loss.setFont("Serif-46");
		add(loss, WIDTH/2 - loss.getWidth()/2, HEIGHT/2 - loss.getHeight()/2);
		
	}
	
	private void StartingScreen() {
		GLabel start = new GLabel("CLICK TO START");
		start.setColor(Color.CYAN);
		start.setFont("Serif-30");
		add(start, WIDTH/2 - start.getWidth()/2, HEIGHT/2 - start.getHeight()/2);
		waitForClick();
		remove(start);
	}
	
	private void WinningScreen() {
		GLabel label = new GLabel("YOU WON");
		label.setColor(Color.PINK);
		label.setFont("Serif-46");
		add(label, WIDTH/2 - label.getWidth()/2, HEIGHT/2 - label.getHeight()/2);
	}
}
