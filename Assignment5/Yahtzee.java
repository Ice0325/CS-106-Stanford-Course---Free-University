/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		//check for correct input
		while(nPlayers < 1 || nPlayers > 4){
			nPlayers = dialog.readInt("Enter number of players");
		}
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		selectedCategories = new int[nPlayers][N_CATEGORIES];
		scoreResult = new int[nPlayers][N_CATEGORIES];
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		// Play the game for all rounds and players
		for(int j=0; j < N_SCORING_CATEGORIES; j++){
			for(int i=1; i <= nPlayers; i++){
		    	Rolls(i);
			}
		}
		GiveResults();
	}	
	
	//calculate whos the winner and display
	private void GiveResults() {
		int winner = 10;
		int winnerScore = 0;
		for(int i=1; i <= nPlayers;i++){
		   int totalScore = upperScore(i) + lowerScore(i);
		   if(totalScore > winnerScore){
			   winnerScore = totalScore;
			   winner = i;
		   }
		   display.updateScorecard(TOTAL, i, totalScore);
		}
		display.printMessage("Congratulations, " + playerNames[winner] + ", you're the winner with total score " + winnerScore);
	}

	//method for calculating lower score
	private int lowerScore(int player) {
		int result =0;
		for(int i=THREE_OF_A_KIND; i <= CHANCE; i++ ){
			result += scoreResult[player - 1][i - 1];
		}
		display.updateScorecard(LOWER_SCORE, player, result);
		return result;
	}

	//method for upper score and bonus
	private int upperScore(int player) {
		int result =0;
		for(int i=ONES; i <= SIXES; i++ ){
			result += scoreResult[player - 1][i - 1];
		}
		display.updateScorecard(UPPER_SCORE, player, result);
		if(result > 63){
			result += 35;
			display.updateScorecard(UPPER_BONUS, player, 35);
		} else {
			display.updateScorecard(UPPER_BONUS, player, 0);
		}
		return result;
	}

	//all of the rolls that has to be made (main part of the game)
	private void Rolls(int player) {
		FirstRoll(player);
		OtherRolls();
		OtherRolls();
		categorySelection(player);
	}

	//selecting categoty, checking, counting score and displaying it
    private void categorySelection(int player) {
        category = display.waitForPlayerToSelectCategory();
    	checkCategory( player);
    	scoreCount( player);
    	display.updateScorecard(category, player, score);
    	score = 0;
    	
	}

    //what do if category was already selected
	private void checkCategory( int player) {
		while(selectedContains( player)){
			display.printMessage("Category already selected");
			category = display.waitForPlayerToSelectCategory();
			 if (!selectedContains(player)) {
		            break;
		        }
		}
	}

	//boolean to check if it was already selected
	private boolean selectedContains( int player) {
		   if (selectedCategories[player -1 ][category - 1] == category){
			   return true;
		   }else {
			   selectedCategories[player -1 ][category - 1] = category;
		   }
		return false;
	}

	//counting score anc checking categories
	private void scoreCount(int player) {
		sortDice();
		//for every category from ones to sixes
		if(category >= ONES && category <= SIXES){
			for(int i=0; i < N_DICE; i++){
				if(dice[i] == category){
					score += category;
				}
			}
			
			
			//for three of a kind four of a kind and fullhouse
		} else if(category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND || category == FULL_HOUSE){
			int m = 1;
			int n = 1;
			//after sorting there should be same numbers in a row so it counts
			for(int i=0; i < N_DICE - 1; i++){
				if(dice[i] == dice[i+1]){
					n++;
				}else {
					//this part is for when there are two or more other same numbers
					for(int j=i + 1; j < N_DICE - 1; j++){
						if(dice[j] == dice[j+1]){
							m++;
						}
					}
					break;
				}
			}


			//now checking how many elements were repeated and check categories
			if( ((m == 3 && n == 2) || (m == 2 && n == 3)) && category == FULL_HOUSE){
				score  = 25;
			} else if(((m >= 3 || n >= 3) && category == THREE_OF_A_KIND) || ((m >= 4 || n >= 4) && category == FOUR_OF_A_KIND)){
				 for(int k=0; k < N_DICE; k++){
					score += dice[k];
				 }
			} 
	
			
			//checking for small straight and large
		} else if(category == SMALL_STRAIGHT || category == LARGE_STRAIGHT){
			int count = 1;
			for(int i=0; i < N_DICE - 1; i++){
				if(dice[i] == dice[i+1] - 1){
					count++;
				}
			}
			if(count >= 4 && category == SMALL_STRAIGHT){
				score = 30;
			}else if(count == 5 && category == LARGE_STRAIGHT) {
				score = 40;
			}
			
			//checking for yahtzee
		} else if(category == YAHTZEE){
			boolean yahtzee = true;
			for(int i=0; i < N_DICE -1; i++){
				if(dice[i] != dice[i+1]){
					yahtzee = false;
				}
			}
			if(yahtzee){
				score = 50;
			}
			
			//checking for chance
		} else if(category == CHANCE){
			for(int i=0; i < N_DICE; i++){
				score += dice[i];
			}
		}
		//storing results
		scoreResult[player - 1][category - 1] = score;
	}

	

	//sort the dice to make everything easier
	private void sortDice() {
		int temp =0;
		while(NotSorted()){
	    	for(int i=0; i < N_DICE - 1; i++){
			  if( dice[i] > dice[i+1]){
				 temp = dice[i];
				 dice[i] = dice[i+1];
				 dice[i+1] = temp;
			  }
	    	}
		}
	}
	
	//check if not sored so it can do again(could have used bogosort in there small numbers :D)
	private boolean NotSorted(){
		for(int i=0; i<N_DICE -1; i++){
			if(dice[i] > dice[i+1]){
				return true;
			}
		}
		return false;
	}

	//first roll is different from others so, seperate method for it
	private void FirstRoll(int player) {
		for(int j=0; j < N_DICE; j++){
			dice[j] = rgen.nextInt(1,6);
			
		}
		display.waitForPlayerToClickRoll(player);
		display.displayDice(dice);
		display.waitForPlayerToSelectDice();
		
	}

	//second and third rolls
    private void OtherRolls() {
    	for(int j=0; j < N_DICE; j++){
    		if(display.isDieSelected(j)){
		    	dice[j] = rgen.nextInt(1,6);
	    	}
    	}
    
		display.displayDice(dice);
		if(secondRoll){
			display.waitForPlayerToSelectDice();
			secondRoll = false;
		} else {
			secondRoll = true;
		}
    }

  
	

	

	





	/* Private instance variables */
    
    private int[] dice = new int [N_DICE];
	private int nPlayers;
	private int[][] selectedCategories;
	private int[][] scoreResult;
	private int score = 0;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();


	private int category;
	private boolean secondRoll = true;
}
