package model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
/**
 * A class for storing all the information associated with a Paper.
 * For instantiation use to .createPaper() method.(see factory pattern)
 * 
 * INVARIANT: All getters return non-null objects.
 * All strings are non-empty.
 * @author Dimitar Kumanov
 * @version 04/24/2017
 */
public class Paper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1981414852704428147L;
	private final File myPaperFile;
	private Date mySubmissionDate;
	private final List<String> myAuthors;
	private final List<RecommendationPapers> myRecommendations;
	private final String myTitle;
	private final UserProfile mySubmitter;
	//private boolean myRecommendation;
	private int myAmountOfReviews;
	
	/**
	 * Private; see createPaper()
	 */
	private Paper(
			final File thePaper,
			final Date theSubmissionDate,
			final int theAmountOfReviews,
			final List<String> theAuthors,
			final List<RecommendationPapers> theRecommendation,
			final String thePaperTitle,
			final UserProfile theSubmitterUserProfile
			) throws IllegalArgumentException{
		
		myPaperFile = Objects.requireNonNull(thePaper);
		mySubmissionDate = Objects.requireNonNull(theSubmissionDate);
		myRecommendations = Objects.requireNonNull(theRecommendation);
		myAuthors = Objects.requireNonNull(theAuthors);
		myAmountOfReviews = Objects.requireNonNull(theAmountOfReviews);
		myTitle = Objects.requireNonNull(thePaperTitle);
		mySubmitter = Objects.requireNonNull(theSubmitterUserProfile);
		if(thePaperTitle.isEmpty())
			throw new IllegalArgumentException();
	}
	
	/**
	 * A factory method for creating a Paper Object.
	 * PRECONDITION: All parameters must be non-null,
	 * Strings can't be empty.
	 * @param thePaperFile The file associated with the text itself.
	 * @param theAuthors The list of Authors of this paper.
	 * @param thePaperTitle The title of the paper.
	 * @param theSubmitterUserProfile The System UserProfile of the person actually submitting the paper.
	 * @exception When the precondition is violated.
	 * @return a Paper Object that has all the information associated with the paper.
	 */
	public static Paper createPaper(
			final File thePaperFile,
			final List<String> theAuthors,
			final String thePaperTitle,
			final List<RecommendationPapers> theRecommendations,
			//final boolean theRecommendation,
			final int theAmountOfReviews,
			final UserProfile theSubmitterUserProfile
			) throws IllegalArgumentException{

		return new Paper(
				thePaperFile,
				new Date(),
				theAmountOfReviews,
				theAuthors, 
				theRecommendations,
				thePaperTitle,
				theSubmitterUserProfile
				);
	}
	
	/**
	 * Gets the (non-null) file pointing to the Paper.
	 * @return the (non-null) file pointing to the Paper.
	 */
	public File getPaperFile(){
		return myPaperFile;
	}
	
	/**
	 * Gets the Date this paper was submitted(Date of createPaper()).
	 * @return the Date this paper was submitted(Date of createPaper()).
	 */
	public Date getSubmitDate(){
		return mySubmissionDate;
	}
	
	/**
	 * Gets the (non-null, non-empty) Paper title's String.
	 * @return the (non-null, non-empty) Paper title's String.
	 */
	public String getTitle(){
		return myTitle;
	}
	
	/**
	 * Gets the (non-null) UserProfile of the user submitting this Paper.
	 * @return the (non-null) UserProfile of the user submitting this Paper.
	 */
	public UserProfile getSubmitterUserProfile(){
		return mySubmitter;
	}
	
	/**
	 * Gets a List(non-null but possibly empty) of Strings with the names of all the authors of this Paper. 
	 * @return a List of Strings(non-null but possibly empty) with the names of all the authors of this Paper.
	 */
	public List<String> getAuthors(){
		return myAuthors;
	}

	/**
	 * Changes the submission Date of this paper to the newSubDate provided.
	 * @param newSubDate The Date to change the submission Date of this Paper.
	 */
	public void setSubmissionDate(final Date newSubDate) {
		mySubmissionDate = newSubDate;
	}
	/**
	 * Checks if the paper is recommended for the subprogram chair.
	 * Added this in to associate the recommendation by paper. Subject to change.
	 * @param theRecommendation The boolean variable that the subprogram chair either recommends or doesn't.
	 *
	public void setRecommendation(boolean theRecommendation) {
		myRecommendation = theRecommendation; 
	}*/
	/**
	 * Gets the amount of reviews this manuscript has. 
	 * Added it in the paper class for now to help with subprogram chair utilities.
	 * Might possibly be done with a hashmap (keys with paper) did it this way for now.
	 * @return The amount of reviews this paper has.
	 */
	public int getReviewCount() {
		return myAmountOfReviews;
	}
	/**
	 * Sets the amount of reviews the paper has.
	 * @param theAmountOfReviews theReviewCount to change how much reviews the manuscript has.
	 */
	public void setReviewCount(int theAmountOfReviews) {
		myAmountOfReviews = theAmountOfReviews;
	}
}

