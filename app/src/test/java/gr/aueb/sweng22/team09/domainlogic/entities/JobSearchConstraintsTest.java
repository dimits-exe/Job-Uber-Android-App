package gr.aueb.sweng22.team09.domainlogic.entities;

import junit.framework.TestCase;

import org.joda.money.Money;
import org.junit.Assert;

public class JobSearchConstraintsTest extends TestCase {
    private static final TimeConstraint TIME_CONSTRAINT = TimeConstraintTest.generateTestTimeConstraint();
    private static final Money COMPENSATION = Money.parse("EUR 45.23");

    private JobSearchConstraints parameterSet;

    public static JobSearchConstraints getTestSearchConstraints() {
        return new JobSearchConstraints(TIME_CONSTRAINT, COMPENSATION);
    }

    public void setUp() throws Exception {
        super.setUp();
        parameterSet = getTestSearchConstraints();
    }

    /**
     * Tests if the object retains the correct duration from the constructor.
     */
    public void testGetDuration() {
        Assert.assertEquals(TIME_CONSTRAINT, parameterSet.getDuration());
    }

    /**
     * Tests if the object retains the correct compensation from the constructor.
     */
    public void testGetCompensation() {
        Assert.assertEquals(COMPENSATION, parameterSet.getCompensation());
    }

    /**
     * Tests if the equals method works as intended.
     */
    public void testEquals() {
        JobSearchConstraints good = new JobSearchConstraints(parameterSet.getDuration(),
                parameterSet.getCompensation());
        JobSearchConstraints bad = new JobSearchConstraints(parameterSet.getDuration(),
                parameterSet.getCompensation().plus(2));

        Assert.assertEquals(good, parameterSet);
        Assert.assertNotEquals(bad, parameterSet);
    }
}
