/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.TextField;
import java.awt.event.*;
import javax.swing.*;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	//gui init and adding database,canvas, profiles
	private FacePamphletCanvas Graphics;
	private FacePamphletDatabase Database;
	private FacePamphletProfile Profile,NullProfile;
	private JButton Add,Delete,Lookup,Status, Picture, Friend;
	private JTextField Search,StatusText, PictureTextField, FriendText;
	private JLabel label;
	public void init() {
		// You fill this in
		//adding label, buttons and textfields
		Graphics = new FacePamphletCanvas();
		add(Graphics);
		Database = new FacePamphletDatabase();
		
		 label = new JLabel("name");
		add(label, NORTH);
		 Search = new JTextField(TEXT_FIELD_SIZE);
		add(Search, NORTH);
		 Add = new JButton("Add");
		add(Add,NORTH);
		 Delete = new JButton("Delete");
		add(Delete,NORTH);
		 Lookup = new JButton("Lookup");
		add(Lookup, NORTH);
		
		
		 StatusText = new JTextField(TEXT_FIELD_SIZE);
		add(StatusText, WEST);
		 Status = new JButton("Change Status");
		add(Status, WEST);
		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST); 
		
		 PictureTextField = new JTextField(TEXT_FIELD_SIZE);
		add(PictureTextField, WEST);
		 Picture = new JButton("Change Picture");
		add(Picture, WEST);
		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST); 
		
		 FriendText = new JTextField(TEXT_FIELD_SIZE);
		add(FriendText, WEST);
		 Friend = new JButton("Add Friend");
		add(Friend, WEST);
		
		addActionListeners();
		StatusText.addActionListener(this);
		PictureTextField.addActionListener(this);
		FriendText.addActionListener(this);
	
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		// You fill this in as well as add any additional methods
    	String name =  Search.getText();
    	
    	Profile = new FacePamphletProfile(name);
    	 
    	//check for lookup clicked
    	if( e.getSource() == Lookup && !name.equals("")){
    		
    		//check if such name is in database
    		if(Database.containsProfile(name)){
    			//display profile
    			Profile = Database.getProfile(name);
    			Graphics.removeAll();
    			Graphics.displayProfile(Profile);
    			Graphics.showMessage("Displaying " + name);
    			//nullprofile is profile that we keep track of (amjamindeli profili)
    			NullProfile = Profile;
    		}else {
    			//show message for no profile
    			NullProfile = null;
    			Graphics.removeAll();
    			Graphics.showMessage("Profile " + name + " doesnt exist");
    		}
    		
    		//check for add button click
    	}  else if (e.getSource() == Add && !name.equals("")) {
    		//check database
    	    if (Database.containsProfile(name)) {
    	    	//if already added show message
    	        Graphics.displayProfile(Profile);
    	    	Graphics.showMessage("Profile " + Profile.getName() + " has already been created");
    	    } else {
    	    	//if not add
    	        Database.addProfile(Profile);
    	        Graphics.displayProfile(Profile);
    	        Graphics.showMessage("Profile " + Profile.getName() + " created");
    	    }
    	    //keep track
    	    NullProfile = Profile;
    	   
    	    //check for delete
    	} else if(e.getSource() == Delete && !name.equals("")){
    		
    		Profile = Database.getProfile(name);
    		//check in database
    		if(Database.containsProfile(name)){
    			//delete if in database
    		 Database.deleteProfile(name);
    		 NullProfile = null;
    		 Graphics.removeAll();
    		 Graphics.showMessage("Profile " + name + " has been deleted");
    		
    		
    		} else {
    			//if not show message
    			Graphics.removeAll();
    			Graphics.showMessage("Profile " + name + " not found");
    		}
    		
    	}
    	
    	
    	String status = StatusText.getText();
    	Profile = Database.getProfile(name);
    	
    	//chekc for status button
    	if((e.getSource() == Status || e.getSource() == StatusText) && !status.equals("")){
    		//check for nullprofile if it is not null set status
    		if(NullProfile != null){
    			Profile.setStatus(status);
    			Graphics.displayProfile(Profile);
    			Graphics.showMessage("Status Changed");
    		}else {
    			Graphics.showMessage("Please Choose Profile to set status");
    		}
    	} 
    	
    	String pic = PictureTextField.getText();
    	Profile = Database.getProfile(name);
    	
    	//check for pic
    	if((e.getSource() == Picture || e.getSource() == PictureTextField) && !pic.equals("")){
    		//check if image is null
    		if(Profile != null){
    			GImage image = null; 
    			try { 
    				//display image
    			image = new GImage(pic); 
    			Profile.setImage(image);
    			} catch (ErrorException ex) { 
    			// Code that is executed if the filename cannot be opened. 
    				
    				Graphics.showMessage("Unable to open image file: " + pic);
    			}
    			
    			Graphics.displayProfile(Profile);
    			Graphics.showMessage("Picture Updated");
    		} else {
    			Graphics.showMessage("Please select a profile to change picture");    		}
    	}
    	
    	String FriendName = FriendText.getText();
    	
    	//check for friend button
    	if((e.getSource() == Friend || e.getSource() == FriendText )&& !FriendName.equals("")){
    		if(!FriendName.equals(NullProfile.getName())){
    			//checkdata
    		if(Database.containsProfile(FriendName)){
    			//check for nullprofile
    		  if(NullProfile != null){
    			  
    			Profile = Database.getProfile(NullProfile.getName());
    			if(Profile.addFriend(FriendName)){
    				//add friendlist for both 
    				Profile.addFriend(FriendName);
    				FacePamphletProfile Profile2 = Database.getProfile(FriendName);
    				Profile2.addFriend(Profile.getName());
    				Graphics.displayProfile(Profile);
    				Graphics.showMessage(FriendName + " added as Friend");
    			} else {
    				Graphics.showMessage("Youa are already friends with " + FriendName);
    			}
    		}else {
    			Graphics.showMessage("Please Choose Profile to add friend to");
    		}
    		}else {
    			Graphics.showMessage("There is no such Profile as " + FriendName);
    		}
    	}else {
    		Graphics.showMessage("You cant add yourself as a friend");
    	}
	}
    }
}
