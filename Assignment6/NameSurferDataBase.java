import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import acm.util.ErrorException;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 * 
 */
	
	private HashMap<String, NameSurferEntry> data = new HashMap<String,NameSurferEntry>();
	
	//reading file, outputing each line and giving to entry, returning name for each entry.
	public NameSurferDataBase(String filename) {
		// You fill this in //
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while(true){
				String line = rd.readLine();
				if(line == null){
					break;
				}
				//extracting entry and name and putting them in data
				NameSurferEntry entry = new NameSurferEntry(line); 
				String name = entry.getName();
				data.put(name, entry);
			}
			rd.close();
		} catch(IOException ex){
			throw new ErrorException(ex);
			}
		}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		// You need to turn this stub into a real implementation //
		//changing input so it is not case sensitive and matches the database
        name = name.toLowerCase();
		String str = String.valueOf(name.charAt(0));
		name = str.toUpperCase() + name.substring(1,name.length());
		
		//checking if data contains the input
		if(data.containsKey(name)) {
			return data.get(name);
		}
		else{
			return null;
		}
	}
}

