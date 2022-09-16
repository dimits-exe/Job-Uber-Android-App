package gr.aueb.sweng22.team09.domainlogic.entities;

import junit.framework.TestCase;

import org.joda.money.Money;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.NoSuchElementException;

public class JobTest extends TestCase {
    private static final User POSTER = UserTest.generateTestUser2();
    private static final String TITLE = "Frontend stuff";
    private static final String DESCRIPTION = "javascript spaghetti code";
    private static final Money COMPENSATION = Money.parse("USD 15.45");
    private static final TimeConstraint TIME_CONSTRAINT =
            TimeConstraintTest.generateTestTimeConstraint();

    private Job job;

    public static Job generateTestJob() {
        return new Job(POSTER, TITLE,DESCRIPTION, COMPENSATION, TIME_CONSTRAINT);
    }

    @Override
    public void setUp() {
        job = generateTestJob();
    }

    /**
     * Tests if the object starts with no worker and later when assigned, retains the correct
     * worker.
     */
    public void testAssignWorker() {
        // no assigned worker
        Assert.assertThrows(NoSuchElementException.class, () -> job.getAssignedWorker());

        //get worker
        User worker = UserTest.generateTestUser();
        WorkOffer offer = job.addInterested(worker);
        offer.accept();
        Assert.assertEquals(worker, job.getAssignedWorker());
    }

    /**
     * Tests if a job keeps track of the users that express interest in fulfilling them.
     */
    public void testAddInterested() {
        User user = UserTest.generateTestUser();
        Assert.assertTrue(job.getWorkOffers().isEmpty());

        WorkOffer wo = job.addInterested(user);
        Assert.assertTrue(job.getWorkOffers().contains(wo));
    }

    /**
     * Tests if a job stops keeping track of users that
     * no longer express interest in fulfilling them.
     */
    public void testRemoveInterest() {
        User user = UserTest.generateTestUser();
        Assert.assertThrows(NoSuchElementException.class, () -> job.removeInterest(new WorkOffer(null, null)));

        WorkOffer wo = job.addInterested(user);
        job.removeInterest(wo);
        Assert.assertFalse(job.getWorkOffers().contains(wo));
    }


    /**
     * Tests if a deadline can be changed ONLY ONCE correctly.
     */
    public void testChangeDeadline() {
        Assert.assertTrue(job.deadlineCanBeChanged());

        LocalDate oldDeadline = job.getDuration().getDeadline();
        LocalDate newDeadline = oldDeadline.plusDays(5);
        job.changeDeadline(newDeadline);

        Assert.assertFalse(job.deadlineCanBeChanged());
        Assert.assertThrows(IllegalStateException.class, () -> job.changeDeadline(newDeadline));
    }

    /**
     * Tests if the job correctly retains the poster given by its constructor.
     */
    public void testGetPoster() {
        Assert.assertEquals(POSTER, job.getPoster());
    }

    /**
     * Tests if the job correctly retains the title given by its constructor.
     */
    public void testGetTitle() {
        Assert.assertEquals(TITLE, job.getTitle());
    }

    /**
     * Tests if the job correctly retains the description given by its constructor.
     */
    public void testGetDescription() {
        Assert.assertEquals(DESCRIPTION, job.getDescription());
    }

    /**
     * Tests if the job correctly retains the amount of compensation given by its constructor.
     */
    public void testGetCompensation() {
        Assert.assertEquals(COMPENSATION, job.getCompensation());
    }

    /**
     * Tests if the job correctly changes its current status.
     */
    public void testSetStatus() {
        job.setStatus(Job.Status.PENDING);
        Assert.assertSame(job.getStatus(), Job.Status.PENDING);
    }

}
