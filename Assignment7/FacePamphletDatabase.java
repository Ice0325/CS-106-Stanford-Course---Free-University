/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.sql.Array;
import java.util.*;

import com.sun.xml.internal.fastinfoset.util.StringArray;

public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	private HashMap<String, FacePamphletProfile> data = new HashMap<String,FacePamphletProfile>();
	
	public FacePamphletDatabase() {
		// You fill this in
		
		
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		// You fill this in
		//getting name of profile and storing it as key 
		String name = profile.getName();
		if(!data.containsKey(name)){
			data.put(name, profile);
		}else {
			data.replace(name, profile);
		}
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		// You fill this in.  Currently always returns null.
		if(data.containsKey(name)){
			return data.get(name);
		}
		return null;
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		// You fill this in
		if(data.containsKey(name)){
			data.remove(name);
		}
		
	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		// You fill this in.  Currently always returns false.
		if(data.containsKey(name)){
			return true;
		}
		return false;
	}

}
