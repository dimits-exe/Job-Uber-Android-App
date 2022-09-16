package gr.aueb.sweng22.team09.ui.strategies;

import junit.framework.TestCase;

import org.joda.money.Money;
import org.junit.Assert;

import java.math.BigDecimal;

/**
 * Tests the operations of the default money addition strategy.
 *
 * @author Dimitris Tsirmpas
 */
public class DefaultMoneyAdditionStrategyTest extends TestCase {
        private IMoneyAdditionStrategy strategy;

    @Override
    public void setUp() {
        strategy = new DefaultMoneyAdditionStrategy();
    }


    /**
     * Tests if money of the same currency can be added correctly.
     */
    public void testAddMoneySameCurrency() {
        Money original = Money.parse("USD 45");
        Money toBeAdded = Money.parse("USD 15");
        Money finalMoney = strategy.add(original, toBeAdded);

        // do NOT change this to assert equals, it doesn't work as intended here
        //noinspection SimplifiableAssertion
        Assert.assertTrue(new BigDecimal(60L).compareTo(finalMoney.getAmount()) == 0);
        Assert.assertEquals(original.getCurrencyUnit(), finalMoney.getCurrencyUnit());
    }

    /**
     * Tests if money of foreign currencies can be correctly added.
     */
    public void testAddMoneyForeignCurrency() {
        Money original = Money.parse("EUR 7");
        Money toBeAdded = Money.parse("USD 15");
        Money finalMoney = strategy.add(original, toBeAdded);

        BigDecimal rate = strategy.convert(original.getCurrencyUnit(), toBeAdded.getCurrencyUnit());
        BigDecimal expectedAmount = rate.multiply(original.getAmount().add(toBeAdded.getAmount()));
        Assert.assertEquals(expectedAmount, finalMoney.getAmount());

        Assert.assertEquals(original.getCurrencyUnit(), finalMoney.getCurrencyUnit());
    }
}