package gr.aueb.sweng22.team09.ui.strategies;

import junit.framework.TestCase;

import org.junit.Assert;

import java.time.LocalDate;
import java.time.temporal.TemporalField;

/**
 * Test if the date parsing runs as intended.
 *
 * @author Dimitris Tsirmpas
 */
public class CustomDateParsingStrategyTest extends TestCase {
    private IDateParsingStrategy dateParsingStrategy;

    public void setUp() throws Exception {
        super.setUp();
        dateParsingStrategy = new CustomDateParsingStrategy();
    }

    /**
     * Test if the parsing fails with incorrect string data.
     */
    public void testFalseParseDate() {
        Assert.assertNull(dateParsingStrategy.parseDate("45/32/a1111"));
    }

    /**
     * Test if the returned local date object's fields correspond to the given string.
     */
    public void testCorrectParseData() {
        LocalDate date = dateParsingStrategy.parseDate("4-03-2022");
        Assert.assertNotNull("message", date);

        Assert.assertEquals(2022, date.getYear());
        Assert.assertEquals(3, date.getMonthValue());
        Assert.assertEquals(4, date.getDayOfMonth());
    }
}