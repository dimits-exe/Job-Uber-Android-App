package gr.aueb.sweng22.team09.domainlogic.entities;

import junit.framework.TestCase;

import org.joda.money.Money;
import org.junit.Assert;

import java.time.LocalDate;

public class TransactionTest extends TestCase {
    private final User POSTER = UserTest.generateTestUser();
    private final User WORKER = UserTest.generateTestUser2();
    private final Money AMOUNT = Money.parse("USD 14");
    private final LocalDate DATE = LocalDate.now().minusDays(2);
    private Transaction transaction;

    public void setUp() throws Exception {
        super.setUp();
        transaction = new Transaction(45, POSTER, WORKER, AMOUNT, DATE);
    }

    /**
     * Tests if the constructor detects a transaction that takes place in the future.
     */
    public void testConstructor() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Transaction(23, POSTER, WORKER, AMOUNT, LocalDate.now().plusDays(2)));
    }

    /**
     * Tests if a transaction correctly retains the benefactor given by its constructor.
     */
    public void testGetBenefactor() {
        Assert.assertEquals(POSTER, transaction.getBenefactor());
    }

    /**
     * Tests if a transaction correctly retains the receiver given by its constructor.
     */
    public void testGetReceiver() {
        Assert.assertEquals(WORKER, transaction.getReceiver());
    }

    /**
     * Tests if a transaction correctly retains the compensation given by its constructor.
     */
    public void testGetAmount() {
        Assert.assertEquals(AMOUNT, transaction.getAmount());
    }

    /**
     * Tests if a transaction correctly retains the date given by its constructor.
     */
    public void testGetDate() { Assert.assertEquals(DATE, transaction.getDate());}

    /**
     * Tests if the equals method works as intended.
     */
    public void testEquals() {
        // different id, same fields
        Transaction transaction2 = new Transaction(POSTER, WORKER, AMOUNT);
        // different fields, same id
        Transaction transaction3 = new Transaction(transaction.getId(), POSTER, WORKER, AMOUNT, DATE);

        Assert.assertEquals(transaction3, transaction);
        Assert.assertNotEquals(transaction2, transaction);
    }
}