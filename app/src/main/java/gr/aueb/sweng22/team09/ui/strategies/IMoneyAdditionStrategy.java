package gr.aueb.sweng22.team09.ui.strategies;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;

/**
 * A strategy that handles the addition of Money objects that may have different Currency.
 *
 * @author Dimitris Tsirmpas
 */
public interface IMoneyAdditionStrategy {

    /**
     * Returns a new Money object with the Currency of currentMoney and the amount of the sum of the
     * two parameters. If the currency of the moneyToBeAdded is different than that of currentMoney,
     * its amount is converted to the currency of the currentMoney before performing the addition.
     *
     * @param currentMoney   the original sum
     * @param moneyToBeAdded the amount to be added
     * @return the amount of money after the addition, in the currentMoney's currency
     */
    Money add(Money currentMoney, Money moneyToBeAdded);

    /**
     * Returns the conversion rate between two currencies.
     *
     * @param from the currency from which to convert
     * @param to   the currency to which to convert
     * @return the conversion rate
     */
    BigDecimal convert(CurrencyUnit from, CurrencyUnit to);
}
