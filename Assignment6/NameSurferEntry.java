/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.program.ConsoleProgram;
import acm.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NameSurferEntry  implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	private String name;
	private int[] Rank = new int[NDECADES];
	
	public NameSurferEntry(String line) {
		// You fill this in //
		//use tokenizer to split name and ranks
		StringTokenizer str = new StringTokenizer(line);

		//keeping numbers of each token
		int n =0;
		while(str.hasMoreTokens()){
			//check if its first token to set the name
			if(n == 0){
				 name = str.nextToken();
				 n++;
			} else {
				//set ranks for each decade
				Rank[n - 1] = Integer.parseInt(str.nextToken());
				n++;
			}
		}
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		// You need to turn this stub into a real implementation //
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		// You need to turn this stub into a real implementation //
		
		return Rank[decade];
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String res = name;
		for(int i = 0; i<NDECADES; i++) {
			res += getRank(i) + " ";
		}
		return res;
	}
}

