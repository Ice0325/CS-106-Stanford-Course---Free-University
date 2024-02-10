/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	private boolean messageDisplayed = false;
	private boolean statusSet = false;
	private GObject lastStatus;
	private GLabel message;
	private GObject lastMessage;
	private double ProfileNameOffset  = 20;
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
		
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		// You fill this in
		message = new GLabel(msg);
		double x = getWidth()/2 - message.getWidth()/2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		//keep track when message is displayed
		if(!messageDisplayed){
			messageDisplayed = true;
		} else {
			messageDisplayed = false;
			remove(lastMessage);
		}
		message.setFont(MESSAGE_FONT);
		add(message, x, y);
		//for keeping track and deleteing
		lastMessage = message;
		
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		// You fill this in
		removeAll();
		displayName(profile);
		displayPic(profile);
		displayStatus(profile);
		displayFriends(profile);
	}


	private void displayFriends(FacePamphletProfile profile) {
		// TODO Auto-generated method stub
		//displaying friends so making friendlist iterator
		Iterator<String> FriendList = profile.getFriends();
		GLabel label = new GLabel("Friends: ");
		double x = getWidth()/2;
		double y = TOP_MARGIN + ProfileNameOffset;
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(label ,x ,y);
		//keeping track of number of friends so they dont overlap
		int n =0;
		while(FriendList.hasNext()){
			n++;
			String name = FriendList.next();
			GLabel friendsLabel = new GLabel(name);
			//set new height for each friend name
			double labelHeight = friendsLabel.getHeight()*n;
			friendsLabel.setFont(PROFILE_FRIEND_FONT);
			add(friendsLabel, x ,y + labelHeight );
			
		}
		
	}


	private void displayStatus(FacePamphletProfile profile) {
		// TODO Auto-generated method stub
		//get status for profile
		String status = profile.getStatus();
		GLabel label = new GLabel(status);
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + ProfileNameOffset + IMAGE_HEIGHT + IMAGE_MARGIN +
				label.getHeight();
		//keeping track of status sets
		if(!statusSet){
			statusSet = true;
		}else {
			statusSet = false;
			remove(lastStatus);
		}
		label.setFont(PROFILE_STATUS_FONT);
		add(label,x ,y);
		//same as message
		lastStatus = label;
	}


	private void displayPic(FacePamphletProfile profile) {
		// TODO Auto-generated method stub
		//setting y as this because it is needed in too many places and is too big
		double y = TOP_MARGIN + ProfileNameOffset + IMAGE_MARGIN;
		//check if there is pic then display it if not show the grect instead of it
		//and show the label that displys no image 
		if(profile.getImage() != null){
			
			GImage image = profile.getImage();
			image.setBounds(LEFT_MARGIN, y,IMAGE_WIDTH,IMAGE_HEIGHT);
			add(image);
		}else {
			GRect NullImage = new GRect(LEFT_MARGIN,y,IMAGE_WIDTH,IMAGE_HEIGHT);
			GLabel label = new GLabel("No Image ");
			double label_x = LEFT_MARGIN + IMAGE_WIDTH/2 - label.getWidth();
			double label_y = y + IMAGE_HEIGHT/2;
			label.setFont(PROFILE_IMAGE_FONT);
			add(NullImage);
			add(label, label_x,label_y);
		}
	}


	private void displayName(FacePamphletProfile profile) {
		// TODO Auto-generated method stub
		//displaying name above picture 
		String name = profile.getName();
		GLabel label = new GLabel(name);
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + label.getHeight();
		label.setFont(PROFILE_NAME_FONT);
		label.setColor(Color.blue);
		add(label, x , y);
		
	}

	
	
}
