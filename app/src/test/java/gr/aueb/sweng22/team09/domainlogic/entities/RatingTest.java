package gr.aueb.sweng22.team09.domainlogic.entities;

import junit.framework.TestCase;

public class RatingTest extends TestCase {
    private static final int SCORE = 4;
    private static final String DESCRIPTION = "data cube";
    private static final WorkOffer WORK_OFFER = new WorkOffer(JobTest.generateTestJob(), UserTest.generateTestUser());
    private Rating rating;


    public static Rating generateTestRating() {
        return new Rating(SCORE, DESCRIPTION, WORK_OFFER);
    }


    public void setUp() throws Exception {
        super.setUp();
        rating = generateTestRating();
    }

    /**
     * Tests if a rating correctly retains the score given by its constructor.
     */
    public void testGetScore() {
        assertEquals(SCORE, rating.getScore());
    }

    /**
     * Tests if a rating correctly retains the reviewer given by its constructor.
     */
    public void testGetDescription() {
        assertEquals(DESCRIPTION, rating.getDescription());
    }

    /**
     * Tests if a rating correctly retains the reviewed user given by its constructor.
     */
    public void testGetReviewer() {
        assertEquals(WORK_OFFER.getJob().getPoster(), rating.getReviewer());
    }

    /**
     * Tests if a rating correctly retains the description given by its constructor.
     */
    public void testGetReviewee() {
        assertEquals(WORK_OFFER.getInterestedWorker(), rating.getReviewed());
    }

    /**
     * Tests if a rating correctly retains the job's title given by its constructor.
     */
    public void testGetJobTitle() {
        assertEquals(WORK_OFFER.getJob().getTitle(), rating.getJobTitle());
    }

}
