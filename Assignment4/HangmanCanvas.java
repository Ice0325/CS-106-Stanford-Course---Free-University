/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.util.ArrayList;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {


/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		removeAll();
		GLine Scaffold = new GLine(getWidth()/2 - BEAM_LENGTH, OFFSET + SCAFFOLD_HEIGHT, getWidth()/2 - BEAM_LENGTH, OFFSET);
		add(Scaffold);
		GLine Beam = new GLine(getWidth()/2 - BEAM_LENGTH, OFFSET, getWidth()/2, OFFSET);
		add(Beam);
		GLine Rope = new GLine(getWidth()/2, OFFSET, getWidth()/2, OFFSET + ROPE_LENGTH);
		add(Rope);
	
	    // Resetting variables for a new game
		guess.clear();
		n = 8;
		count = 0;
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		/* You fill this in */
		//count if its first time displaying or if there is still word drawn on canvas
		count ++;
		if(count > 1){
			remove(correct);
			updateWord(word);
		} else{
			updateWord(word);
		}
	}
	
	//update the word
	private void updateWord(String word){
		correct = new GLabel(word);
		correct.setFont("Default-40");
	    add(correct, LABEL_OFFSET_X,getHeight() - LABEL_OFFSET_Y - correct.getHeight() - WORD_OFFSET);
	}


/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
	
		//check if the letter has already been guessed, if not add to list
		if(!guess.contains(String.valueOf(letter))){
		     guess.add(String.valueOf(letter));
		}
		//add new wrong guess to canvas
		addWrongGuesses(guess.size() - 1);
		//number of wrong guesses
		n--;
		//as number of guesses rise, the new body pars are displayed
		if(n == 7){
			GOval head = new GOval(HEAD_RADIUS*2, HEAD_RADIUS*2);
			endoffset = OFFSET + ROPE_LENGTH;
			add(head,getWidth()/2 - HEAD_RADIUS, endoffset);
			
		}
		
		if(n == 6){
			endoffset += HEAD_RADIUS*2;
			GLine body = new GLine(getWidth()/2, endoffset, getWidth()/2, endoffset  + BODY_LENGTH);
			add(body);
			
		}
		if(n == 5){
			 ArmOffset = endoffset + ARM_OFFSET_FROM_HEAD;
			GLine LeftUpperArm = new GLine(getWidth()/2, ArmOffset, getWidth()/2 - UPPER_ARM_LENGTH, ArmOffset);
			add(LeftUpperArm);
			GLine LeftLowerArm = new GLine(getWidth()/2 - UPPER_ARM_LENGTH, ArmOffset, getWidth()/2 - UPPER_ARM_LENGTH, ArmOffset + LOWER_ARM_LENGTH);
			add(LeftLowerArm);
			
		}
		if(n == 4){
			GLine RightUpperArm = new GLine(getWidth()/2, ArmOffset, getWidth()/2 + UPPER_ARM_LENGTH, ArmOffset);
			add(RightUpperArm);
			GLine RightLowerArm = new GLine(getWidth()/2 + UPPER_ARM_LENGTH, ArmOffset, getWidth()/2 + UPPER_ARM_LENGTH, ArmOffset + LOWER_ARM_LENGTH);
			add(RightLowerArm);
		
		}
		if(n == 3){
			GLine RightHip = new GLine(getWidth()/2, bodyEndOffset, getWidth()/2 - HIP_WIDTH, bodyEndOffset );
			add(RightHip);
			GLine RightLeg = new GLine(getWidth()/2 - HIP_WIDTH, bodyEndOffset, getWidth()/2 - HIP_WIDTH, bodyEndOffset + LEG_LENGTH);
			add(RightLeg);
		
		}
		if(n == 2){
			GLine RightHip = new GLine(getWidth()/2, bodyEndOffset, getWidth()/2 + HIP_WIDTH, bodyEndOffset );
			add(RightHip);
			GLine RightLeg = new GLine(getWidth()/2 + HIP_WIDTH, bodyEndOffset, getWidth()/2 + HIP_WIDTH, bodyEndOffset + LEG_LENGTH);
			add(RightLeg);
		
		}
		if(n == 1){
			GLine LeftFoot = new GLine(getWidth()/2 - HIP_WIDTH, bodyEndOffset + LEG_LENGTH, getWidth()/2 - HIP_WIDTH - FOOT_LENGTH, bodyEndOffset + LEG_LENGTH);
			add(LeftFoot);
			
		}
		if(n == 0){
			GLine LeftFoot = new GLine(getWidth()/2 + HIP_WIDTH, bodyEndOffset + LEG_LENGTH, getWidth()/2 + HIP_WIDTH + FOOT_LENGTH, bodyEndOffset + LEG_LENGTH);
			add(LeftFoot);
		
		}
	}
	
	//adding wrong guesses to canvas 
	private void addWrongGuesses(int i){
		incorrectGuess = new GLabel(guess.get(i));
		incorrectGuess.setFont("Default-20");
		add(incorrectGuess,LABEL_OFFSET_X + i * LETTER_OFFSET, getHeight() - LABEL_OFFSET_Y - incorrectGuess.getHeight());
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int OFFSET = 10;
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int LABEL_OFFSET_Y = 10;
	private static final int LABEL_OFFSET_X = 20;
	private static final int LETTER_OFFSET = 16;
	private static final int WORD_OFFSET = 5;


	private int count = 0;
	private int n;
	
	private ArrayList<String> guess = new ArrayList<String>();


	private GLabel correct;
	private GLabel incorrectGuess;
	
	private int ArmOffset;
	private int endoffset;
	private int bodyEndOffset = OFFSET + HEAD_RADIUS*2 + BODY_LENGTH + ROPE_LENGTH;

}
