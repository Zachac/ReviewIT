package tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This is the main test suite for the ReviewIt application. The purpose of this class
 * is to gather all of the unit tests that are contained in separate classes and create 
 * the ability to run them all at once.
 * @author Harlan Stewart
 * @author Zachary Chandler
 * @version 1.0
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
        AuthorRemovePaperTests.class,
        ConferenceDataTest.class,
        ConferenceModelTests.class,
        ConferenceTest.class,
        GetAuthorsTest.class,
        PaperSubmissionLimitTests.class,
        ReccomendationTests.class,
        ReviewerDeadlineAssignTests.class,
        ReviewerIsAuthorTests.class,
        ReviewerMaxPaperLimitTests.class,
        ReviewerUtilitiesTest.class,
        SubmissionDeadlineTests.class,
        SubprogramUtilitiesTest.class,
        UserProfileTest.class,
        UserUtilitiesTest.class 
    })

public class MainTestSuite {
}
