package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A singleton system class which is responsible for holding
 * global information such as user profiles and conferences.
 * 
 * Additionally, this class can be made to
 * serialize and deserialize all pertinent information when needed.
 * @author Dimitar Kumanov
 * @version 4/27/2017
 */
public class ConferenceSystem {
	
	private static final String CONFERENCE_MAP_SER = "./data/ConferenceMap.ser";

    private static final String USER_MAP_SER = "./data/UserMap.ser";

    /**
	 * My only Object instance. This is what is returned from getInstance().
	 */
	private static ConferenceSystem myInstance;
	
	/**
	 * Maps Conference name to a Conference.
	 */
	private Map<String, Conference> myConferenceMap;
	
	/**
	 * Maps UserID to a UserProfile.
	 */
	private Map<String, UserProfile> myUserMap;
	
	private ConferenceSystem(){
		myConferenceMap = new HashMap<>();
		myUserMap = new HashMap<>();
	}
	
	/**
	 * Gets THE RSystem instance guaranteed to be the only one in existence.
	 * @return a RSystem Object which keeps track of our Conferences/UserProfiles/etc.
	 * @author Dimitar Kumanov
	 */
	public static ConferenceSystem getInstance(){
		if(myInstance == null){
			myInstance = new ConferenceSystem();
		}
		return myInstance;
	}
	
	/**
	 * Loads up all Conference/UserProfile objects into the RSYstem.
	 */
	public void deserializeData() {
		/*
		 * TODO: Code for deserializing Data, aka loading up our
		 * Conference/UserProfile objects should happen here
		 */
		if((new File(USER_MAP_SER)).exists() && (new File(CONFERENCE_MAP_SER)).exists()){
			try {
				FileInputStream fisUser = new FileInputStream(USER_MAP_SER);
				ObjectInputStream oisUser = new ObjectInputStream(fisUser);
				FileInputStream fisCon = new FileInputStream(CONFERENCE_MAP_SER);
				ObjectInputStream oisCon = new ObjectInputStream(fisCon);
				
				@SuppressWarnings("unchecked")
				Map<String, UserProfile> newUserMap = new HashMap<>((HashMap<String, UserProfile>) oisUser.readObject());
				
				@SuppressWarnings("unchecked")
				Map<String, Conference> newConfMap = new HashMap<>((HashMap<String, Conference>) oisCon.readObject());
	
				if (newUserMap != null && newConfMap != null) {
					myUserMap = new HashMap<>(newUserMap);
					myConferenceMap = new HashMap<>(newConfMap);
				}
				
				oisUser.close(); fisUser.close();
				oisCon.close(); fisCon.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
//			System.out.print("Deserialization successful.");
		}
	}
	
	/**
	 * Saves all state(data) into files for a later session of the program.
	 * This method should be called before closing the
	 * application to save all of its Conference/UserProfile Objects.
	 */
	public void serializeModel() {
		/*
		 * TODO: Code for serializing Data, aka saving up our
		 * Conference/UserProfile objects should happen here
		 */
		try {
			FileOutputStream fosUser = new FileOutputStream(USER_MAP_SER);
			ObjectOutputStream oosUser = new ObjectOutputStream(fosUser);
			FileOutputStream fosCon = new FileOutputStream(CONFERENCE_MAP_SER);
			ObjectOutputStream oosCon = new ObjectOutputStream(fosCon);

			oosUser.writeObject(myUserMap);
			oosCon.writeObject(myConferenceMap);
			
			oosUser.flush();
			oosUser.close();
			fosUser.flush();
			fosUser.close();
			oosCon.flush();
			oosCon.close();
			fosCon.flush();
			fosCon.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.print("Serialization successful.");
	}
	
	/**
	 * Gets all of the Conferences in the system.
	 * @return a (non-null) List of Conferences in the system.
	 */
	public List<Conference> getConferences(){
		return new ArrayList<>(myConferenceMap.values());
	}
	
	/**
	 * Gets a specific Conference in system based on theConferenceName
	 * @param theConferenceName the Conference name to match a Conference with
	 * @return the specific Conference in system based on theConferenceName. null if no Conference matches.
	 * @author Dimitar Kumanov
	 */
	public Conference getConference(final String theConferenceName){
		return myConferenceMap.get(theConferenceName);
	}
	
	/**
	 * Adds a Conference to the System.
	 * @param theConference the Conference to add to the RSystem.
	 * @throws IllegalArgumentException if theConference name matches a Conference already in the RSystem.
	 */
	public void addConference(final Conference theConference) throws IllegalArgumentException{
		if(myConferenceMap.containsKey(theConference.getInfo().getName())){
			throw new IllegalArgumentException("There exists a Conference with this name in the RSystem already!");
		}
		myConferenceMap.put(theConference.getInfo().getName(), theConference);
	}
	
	/**
	 * Gets a UserProfile based on the User ID.
	 * If no profile exists returns null
	 * @param theUserID corresponding to the profile
	 * @return the Profile of the User. null if no profile found.
	 */
	public UserProfile getUserProfile(final String theUserID){
		return myUserMap.get(theUserID);
	}
	
	/**
	 * Adds a UserProfile to the RSystem.
	 * @param theUserProfile  the Conference to add to the RSystem.
	 * @throws IllegalArgumentException  if theUserProfile's userID matches a UserProfile's userID already in the RSystem.
	 */
	public void addUserProfile(final UserProfile theUserProfile) throws IllegalArgumentException{
		if(myUserMap.containsKey(theUserProfile.getName())){
			throw new IllegalArgumentException("There exists a UserProfile with userID in the RSystem already!");
		}
		myUserMap.put(theUserProfile.getUID(), theUserProfile);
	}
	
	/**
	 * Gets ALL Papers from ALL Conferences submitted by theUserProfile
	 * @param theUserProfile The UserProfile of the person who submitted the papers
	 * @return ALL Papers from ALL Conferences submitted by theUserProfile 
	 */
	public List<Paper> getAllPapersSubmitted(final UserProfile theUserProfile){
		final List<Paper> submittedPapers = new ArrayList<>();
		for(final Conference currentConference: myConferenceMap.values()){
			submittedPapers.addAll(currentConference.getInfo().getPapersSubmittedBy(theUserProfile));
		}
		return submittedPapers;
	}
	
	/**
	 * Gets ALL Papers from ALL Conferences assigned for Review to theUserProfile
	 * @param theUserProfile The UserProfile of assigned person
	 * @return ALL Papers from ALL Conferences assigned for review to theUserProfile 
	 */
	public List<Paper> getAllPapersAssignedTo(final UserProfile theUserProfile){
		final List<Paper> submittedPapers = new ArrayList<>();
		for(final Conference currentConference: myConferenceMap.values()){
			submittedPapers.addAll(currentConference.getInfo().getPapersAssignedToReviewer(theUserProfile));
		}
		return submittedPapers;
	}
}
