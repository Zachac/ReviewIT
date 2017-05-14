package model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

/**
 * This creates the recommendations associated with the reviews 
 * Modeled after the paper class. 
 * @author Kevin Nguyen
 * 
 */
public class RecommendationPapers implements Serializable{

	/**
	 * 
	 */
		private static final long serialVersionUID = -263608460768652035L;
		private Path theFilePath;
	    private String thePaperTitle;
	    //The date class gets the specified time of the submission down to milliseconds.
	    private Date theSubmissionDate;	    
		private final UserProfile mySubmitter;
	    public RecommendationPapers(Path filePath, String theTitle, UserProfile submitter) {
	        theFilePath = filePath;
	        thePaperTitle = theTitle;
	        mySubmitter = submitter;
	        theSubmissionDate = new Date();
	    }
	    /**
	     * Gets the title of the paper.
	     * @return title of paper
	     */
	    public String getTitle() {
	        return thePaperTitle;
	    }
	    /**
	     * Gets the path of the paper.
	     * @return the path of the paper
	     */
	    public Path getDocumentPath() {
	        return theFilePath;
	    }
	    /**
	     * This returns the submission date of the file. 
	     * This might be set to change since we might have to change to UTC-12 or something.
	     * @return the submission date of the file
	     */
	    public Date getSubmissionDate() {
	        return (Date) theSubmissionDate.clone();
	    }

		/**
		 * @author Zachary Chandler
		 * @return the theSubmitter
		 */
		public UserProfile getTheSubmitter() {
			return mySubmitter;
		}

}
