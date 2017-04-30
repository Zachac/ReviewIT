package tests;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.ErrorException;
import model.Paper;
import model.UserProfile;

/**
 * A test unit for business rule 2a):
 * A reviewer can't be assigned to review a paper he/she has authored or coauthored.
 * @author Dimitar Kumanov
 * @version 4/29/2017
 */
public class Rule2a {

	private Conference aConference;
	private UserProfile aUserProfile;
	private Paper anAuthoredPaper;
	private Paper aCoauthoredPaper;
	private Paper aNonAuthoredPaper;
	
	
	@Before
	public void setUp(){
		aUserProfile = new UserProfile("reviewer@uw.edu", "John Reviewer Doe");
		aConference = Conference.createConference("Sample Conference",
													new Date(),
													5,
													8);
		anAuthoredPaper = Paper.createPaper(new File(""),
											new ArrayList<>(Arrays.asList(new String[]{"John Reviewer Doe",
																						"Some Coauthor"})),
											"Sample Paper Title",
											"someid@uw.edu");
		aCoauthoredPaper = Paper.createPaper(new File(""),
												new ArrayList<>(Arrays.asList(new String[]{"Some Author",
																							"John Reviewer Doe"})),
												"Sample Paper Title",
												"someid@uw.edu");
		aCoauthoredPaper = Paper.createPaper(new File(""),
												new ArrayList<>(Arrays.asList(new String[]{"Some Author",
																							"Some Coauthor"})),
												"Sample Paper Title",
												"someid@uw.edu");
	}
	

	@Test(expected = ErrorException.class)
	public void assignAuthoredPaperForReview() throws ErrorException{
//		assertNotNull(aConference);
		aConference.assignReviewer(aUserProfile.getUID(), anAuthoredPaper);
		
//		fail("Unimplemented");
	}
	

}
