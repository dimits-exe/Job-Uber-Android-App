package gr.aueb.sweng22.team09.domainlogic.entities;

import junit.framework.TestCase;

import org.junit.Assert;

import java.sql.Time;
import java.time.LocalDate;

public class TimeConstraintTest extends TestCase {
    private static final LocalDate NOW = LocalDate.now();
    private static final TimeConstraint TIME_CONSTRAINT = new TimeConstraint(NOW, NOW.plusDays(2));

    public static TimeConstraint generateTestTimeConstraint(){
        return new TimeConstraint(TIME_CONSTRAINT);
    }

    /**
     * Tests if the constructor detects non-logical parameters
     * (a deadline earlier than the starting date).
     */
    public void testConstructor() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new TimeConstraint(TIME_CONSTRAINT.getDeadline(), TIME_CONSTRAINT.getStartingDate()));
    }

    /**
     * Tests if a TimeConstraint correctly retains the starting date given by its constructor.
     */
    public void testGetStartingDate() {
        Assert.assertEquals(NOW, TIME_CONSTRAINT.getStartingDate());
    }

    /**
     * Tests if a TimeConstraint correctly retains the deadline given by its constructor.
     */
    public void testGetDeadline() {
        Assert.assertEquals(NOW.plusDays(2), TIME_CONSTRAINT.getDeadline());
    }

    /**
     * Tests if the equals method works as intended.
     */
    public void testEquals() {
        TimeConstraint timeConstraint = new TimeConstraint(generateTestTimeConstraint());
        TimeConstraint otherTimeConstraint = new TimeConstraint(timeConstraint.getStartingDate(),
                timeConstraint.getDeadline().plusDays(1));

        Assert.assertEquals(timeConstraint, generateTestTimeConstraint());
        Assert.assertNotEquals(otherTimeConstraint, timeConstraint);
    }
}
