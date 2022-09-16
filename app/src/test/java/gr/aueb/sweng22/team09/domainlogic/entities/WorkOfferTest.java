package gr.aueb.sweng22.team09.domainlogic.entities;

import junit.framework.TestCase;

import org.junit.Assert;

public class WorkOfferTest extends TestCase {
    private final Job JOB = JobTest.generateTestJob();
    private final User WORKER = UserTest.generateTestUser2();
    private WorkOffer workOffer;

    @Override
    public void setUp(){
        workOffer = new WorkOffer(JOB, WORKER);
    }

    /**
     * Test if the work offer starts as pending.
     */
    public void testInitialState() {
        Assert.assertEquals(WorkOffer.Status.PENDING, workOffer.getStatus());
    }

    /**
     * Tests if a work offer correctly retains the job given by its constructor.
     */
    public void testGetJob() {
        Assert.assertEquals(JOB, workOffer.getJob());
    }

    /**
     * Tests if a work offer correctly retains the worker given by its constructor.
     */
    public void testGetInterestedWorker() {
        Assert.assertEquals(WORKER, workOffer.getInterestedWorker());
    }

    /**
     * Tests if a work offer's state correctly changes when accepted, and whether subsequent calls
     * throw an exception.
     */
    public void testAccept() {
        workOffer.accept();
        Assert.assertEquals(WorkOffer.Status.ACCEPTED, workOffer.getStatus());
        Assert.assertThrows(IllegalStateException.class, () -> workOffer.accept());
    }

    /**
     * Tests if a work offer's state correctly changes when rejected, and whether subsequent calls
     * throw an exception.
     */
    public void testReject() {
        workOffer.reject();
        Assert.assertEquals(WorkOffer.Status.REJECTED, workOffer.getStatus());
        Assert.assertThrows(IllegalStateException.class, () -> workOffer.reject());
    }
}