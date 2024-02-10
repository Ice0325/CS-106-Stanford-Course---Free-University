/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.*;
import java.util.ArrayList;

public class HangmanLexicon {

	//arraylist to store the words from hangmanlexicon
	private ArrayList <String> List = new ArrayList <String> ();
	
	//read words from hangmanlexicon file and store them in the List
	public HangmanLexicon() {
			try {
				BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
				while(true) {
					String line = rd.readLine();
					if(line == null) {
						break;
					}
					List.add(line);
				}
				rd.close();
			} catch (IOException ex) {
				throw new ErrorException(ex);
			}
	}
	
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return List.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return List.get(index);
		
	}
}
