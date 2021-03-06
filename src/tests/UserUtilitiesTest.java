package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Paper;
import model.UserProfile;

/**
 * Unit testing to cover UserUtilities model class.
 * Tests various methods not tested by 'feature' unit tests.
 * @author Ian Jury 
 * @author Harlan Stewart
 * @version 5/29/2017
 *
 */
public class UserUtilitiesTest {
	/* Conference object used for creating mock conference for tests.*/
	private Conference testConference;
	
	/*Paper objects used for creating mock papers for tests.*/
	private Paper testPaper1;
	private Paper testPaper2;
	private Paper testPaper3;
	private Paper testPaper4;
	private Paper testPaper5;
	private Paper testPaper6;
	
	/*String constants for author, co-author, conference name, and paper title.*/
	private static final String TEST_AUTHOR = "John Doe";
	private static final String TEST_CO_AUTHOR = "Sally Doe";
	private static final String TEST_CON_NAME = "A test con";
	private static final String TEST_TITLE = "Some Paper Title";
	
	/*Integer constants for submission limit, under submission limit, and assignment limit.*/
	private static final int SUBMISSION_LIMIT = 5;
	private static final int ASSIGNMENT_LIMIT = 8;
	
	/*String constants for date format and test date.*/
	private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	private static final String TEST_DATE = "4017/04/30 23:59:59";
	
	
	/*ArrayList used to store mock papers.*/
	private static final ArrayList<Paper> TEST_PAPER_LIST = new ArrayList<>();
	
	 /* Mock user profile used to represent an author submitting papers to a conference.*/
	private static final UserProfile testUserProfile = new UserProfile("UID1", "Some Name1");
	
	/*Date format used to create mock date strings for testing.*/
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT);;
    /*Date object used to represent a mock deadline for testing.*/
    Date deadline;
	
	/**
	 * Setup method that helps setup the mock conference environment and test objects.
	 * @throws ParseException
	 */
	@Before
	public void setUp() throws ParseException {       
        deadline = FORMAT.parse(TEST_DATE);
        
		testConference = Conference.createConference(TEST_CON_NAME, deadline, SUBMISSION_LIMIT,ASSIGNMENT_LIMIT);
		
		testPaper1 = Paper.createPaper(new File(""), 
				new ArrayList<>(Arrays.asList(new String[]{TEST_AUTHOR, TEST_CO_AUTHOR})), TEST_TITLE, testUserProfile);
		testPaper2 = Paper.createPaper(new File(""), 
				new ArrayList<>(Arrays.asList(new String[]{TEST_AUTHOR, TEST_CO_AUTHOR})), TEST_TITLE, testUserProfile);
		testPaper3 = Paper.createPaper(new File(""), 
				new ArrayList<>(Arrays.asList(new String[]{TEST_AUTHOR, TEST_CO_AUTHOR})), TEST_TITLE, testUserProfile);
		testPaper4 = Paper.createPaper(new File(""), 
				new ArrayList<>(Arrays.asList(new String[]{TEST_AUTHOR, TEST_CO_AUTHOR})), TEST_TITLE, testUserProfile);
		testPaper5 = Paper.createPaper(new File(""), 
				new ArrayList<>(Arrays.asList(new String[]{TEST_AUTHOR, TEST_CO_AUTHOR})), TEST_TITLE, testUserProfile);
		testPaper6 = Paper.createPaper(new File(""), 
				new ArrayList<>(Arrays.asList(new String[]{TEST_AUTHOR, TEST_CO_AUTHOR})), TEST_TITLE, testUserProfile);
		TEST_PAPER_LIST.add(testPaper1);
		TEST_PAPER_LIST.add(testPaper2);
		TEST_PAPER_LIST.add(testPaper3);
		TEST_PAPER_LIST.add(testPaper4);
		TEST_PAPER_LIST.add(testPaper5);
		TEST_PAPER_LIST.add(testPaper6);
	}

	@Test 
	public void paperSubmittedRemovedAndAnotherSubmitted_IsSubmitted() {
		for(int i = 0; i < SUBMISSION_LIMIT; i++) {
			testConference.getUserRole().addPaper(testUserProfile, TEST_PAPER_LIST.get(i));
		}
		//check if valid size
		assertTrue(testConference.getInfo().getPapersSubmittedBy(testUserProfile).size() == SUBMISSION_LIMIT);
		
		//remove one of the submitted papers
		testConference.getUserRole().removePaper(testUserProfile,  TEST_PAPER_LIST.get(0));
		assertTrue(testConference.getInfo().getPapersSubmittedBy(testUserProfile).size() == SUBMISSION_LIMIT - 1);
		
		//add it back and check if size has increased
		testConference.getUserRole().addPaper(testUserProfile, TEST_PAPER_LIST.get(0));
		assertTrue(testConference.getInfo().getPapersSubmittedBy(testUserProfile).size() == SUBMISSION_LIMIT);
	}
	
	@Test
	public void paperRemovedAndCorrespondingDataStructuresAreDecrememted_IsRemoved() {
		for(int i = 0; i < SUBMISSION_LIMIT; i++) {
			testConference.getUserRole().addPaper(testUserProfile, TEST_PAPER_LIST.get(i));
		}
		testConference.getUserRole().removePaper(testUserProfile,  TEST_PAPER_LIST.get(0));
		
		int numberOfPaperAuthorHasAuthoredAfterOneRemoval = testConference.getInfo().getPapersAuthoredBy(TEST_AUTHOR).size();
		
		assertTrue(numberOfPaperAuthorHasAuthoredAfterOneRemoval == SUBMISSION_LIMIT - 1);
	}
	
	@Test
	public void submittedPaperIsAssignedToReviewer_IsAssigned() {
		UserProfile testReviewerProfile = new UserProfile("reviewerUID", "John Reviewer");
		Conference assignReviewerTestConference = Conference.createConference("Sample Conference", new Date(0L), 5, 8);

		assignReviewerTestConference.getSubprogramRole().assignReviewer(testReviewerProfile, TEST_PAPER_LIST.get(0));
		
		int sizeOfAssignedPapersToTestReviewerProfile 
			= assignReviewerTestConference.getInfo().getPapersAssignedToReviewer(testReviewerProfile).size();
		
		assertTrue(sizeOfAssignedPapersToTestReviewerProfile == 1);
	}

}
