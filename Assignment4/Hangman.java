/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Hangman extends ConsoleProgram {
	
    private RandomGenerator rgen = RandomGenerator.getInstance();
    
    private HangmanLexicon lexicon = new HangmanLexicon();
    
    private String word, s, result = "";
    private String input;
    
    //arraylist for result and guesses
    private ArrayList<String> res = new ArrayList<String>();
    private ArrayList<String> guess = new ArrayList<String>();
    
    // if guess is correct
    private boolean correct = false;
    
    //if we want to conitnue game or not
    private boolean continueGame = true;
    
    private int lives = 8;
    
    //incorrect guess count
    private int n = 0;
    
    private HangmanCanvas canvas;
    
    public void init() { 
        canvas = new HangmanCanvas(); 
        add(canvas); 
    } 
    
    public void run() {
        println("Welcome to Hangman!");
        while (continueGame) {
            reset();
            game();
            askForNewGame();
        }
    }

    //asking for new game after winning or losing
    private void askForNewGame() {
        String ask = readLine("Would you like to Continue? Y/N : ");
        while (true) {
            if (ask.equals("y") || ask.equals("Y")) {
                reset();
                break;
            } 
           
            if (ask.equals("n") || ask.equals("N")) {
                    continueGame = false;
                    break;
                }else {
            	ask = readLine("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
    }

   //reseting canvas and variables
    private void reset() {
        guess.clear();
        res.clear();
        lives = 8;
        n = 0;
        canvas.reset();
    }

    private void game() {
        int n = rgen.nextInt(0, lexicon.getWordCount() - 1);
        word = lexicon.getWord(n);

      

        //intial word display
        for (int i = 0; i < word.length(); i++) {
            res.add("-");
        }
        updateResult();
        println("The word now looks like this: " + result);

        while (true) {
            println("You have " + lives + " guesses left.");

            //display for canvas
            canvas.displayWord(result);

            Input();
            CheckGuess();
            updateWord();

            // check win or loss
            if (!res.contains("-")) {
                canvas.displayWord(word);
                println("you WON");
                break;
            }
            if (lives <= 0) {
                canvas.displayWord(word);
                println("You're completely hung.");
                println("The word was: " + word);
                println("You lose.");
                break;
            }
            updateResult();
            println("The word now looks like this: " + result);
        }
    }

    
    //method for input to check if its valid
    private void Input() {
        s = readLine("Your guess: ").trim(); 
        while (s.length() != 1 || !Character.isLetter(s.charAt(0))) {
            if (s.isEmpty()) {
                println("Please enter a letter.");
            } else {
                println("Invalid input, please type ONLY one letter");
            }
            s = readLine("Your guess: ").trim(); // Trim for spaces
        }
        input = s.toUpperCase();
    }

    //  to check if the guessed letter has already been guessed
    private void CheckGuess() {
        while (true) {
            if (guess.contains(input)) {
            	//if already guessed remember
                n++;
                //check if its 2nd time guessing same guessed letter 
                if (n == 2) {
                    lives--;
                    canvas.noteIncorrectGuess(input.charAt(0));
                    if (lives == 0) {
                        break;
                    } else {
                        n = 0;
                        println("You already guessed this letter. You will lose One life.");
                        println("You have " + lives + " guesses left.");
                    }
                }
                Input();
             
            } else {
                break;
            }
        }
    }

    //  to update word  based on  input
    private void updateWord() {
        for (int i = 0; i < word.length(); i++) {
            if (input.charAt(0) == word.charAt(i)) {
                correct = true;
                //set input instead of hyphen 
                res.set(i, input);
                //add new input to geuss list
                guess.add(input);
            }
        }
        // check if guess is correct and reset boolean
        if (correct) {
            println("you are right");
            correct = false;
        } else {
        	
            println("There are no " + input + "'s in this word.");
            lives--;
            canvas.noteIncorrectGuess(input.charAt(0));
        }
    }

    // update result to display on console
    private void updateResult() {
        result = "";
        for (int j = 0; j < res.size(); j++) {
            result += res.get(j);
        }
    }
}
