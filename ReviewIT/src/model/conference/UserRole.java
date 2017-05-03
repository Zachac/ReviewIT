package model.conference;

import java.util.ArrayList;

import model.ErrorException;
import model.Paper;
import model.UserProfile;

/**
 * A class containing all the functionality any User has related to a Conference.
 * @author Dimitar Kumanov
 * @version 5/2/2017
 */
public class UserRole {
	private final ConferenceData myConferenceInfo;
    
    public UserRole(final ConferenceData theConferenceInfo){
    	myConferenceInfo = theConferenceInfo;
    }
    
	/**
	 * Adds a Paper to the associated Conference.
	 * PRECONDITION: thePaper is isPaperInAuthorSubmissionLimit()
	 * AND isPaperInSubmissionDeadline()
	 * @param theUserProfile The UserProfile of the user submitting the paper.
	 * @param thePaper The paper being submitted.
	 * @throws ErrorException If the precondition is violated.
	 * @author Kevin Ravana
	 * @author Dimitar Kumanov
	 */
    public void addPaper(
    		final UserProfile theUserProfile,
    		final Paper thePaper
    		) throws ErrorException {
    	if(!myConferenceInfo.isPaperInAuthorSubmissionLimit(thePaper)){
    		throw new ErrorException("Paper exceeds paper submission limit.");
    	}
    	else if(!myConferenceInfo.isPaperInSubmissionDeadline(thePaper)){
    		throw new ErrorException("Paper exceeds submission deadline.");
    	}
    	//Add paper to submission map:
        addPaperToSubmissionMap(theUserProfile.getUID(), thePaper);

    	//Add paper to author map:
    	addPaperToAuthorshipMap(thePaper);
    }
    
    /**
     * Adds paper to ConferenceData's paperSubmissionMap
     * If the Author has not yet submitted a paper
     * to this Conference, the Author's userID will be put into the map along with a
     * list for storing their papers. If a paper with the same name has already been
     * submitted, it will be replaced. (Add prompt to ask user if this should occur).
     * @param theUserID
     * @param thePaper
     * @author Kevin Ravana
     * @author Dimitar Kumanov
     */
    private void addPaperToSubmissionMap(final String theUserID, final Paper thePaper){
    	if(!myConferenceInfo.getPaperSubmissionMap().containsKey(theUserID)){
    		myConferenceInfo.getPaperSubmissionMap().put(theUserID, new ArrayList<>());
    	}
    	myConferenceInfo.getPaperSubmissionMap().get(theUserID).add(thePaper);
    }
    
    /**
     * Adds the paper to ConferenceData's paperAuthorshipMap.
     * This is a map that records all coauthors of
     * a particular paper.
     * @param thePaper
     * @author Kevin Ravana
     * @author Dimitar Kumanov
     */
    private void addPaperToAuthorshipMap(final Paper thePaper) {
        for(final String currentAuthor: thePaper.getAuthors()){
            if(!myConferenceInfo.getPaperAuthorshipMap().containsKey(currentAuthor)){
            	myConferenceInfo.getPaperAuthorshipMap().put(currentAuthor, new ArrayList<>());
            }
            myConferenceInfo.getPaperAuthorshipMap().get(currentAuthor).add(thePaper);
        }
    }
}
