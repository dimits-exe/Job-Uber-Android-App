package gr.aueb.sweng22.team09.ui.strategies;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A placeholder implementation of the IMoneyAdditionStrategy which ignores exchange rates.
 *
 * @author Dimitris Tsirmpas
 */
public class DefaultMoneyAdditionStrategy implements IMoneyAdditionStrategy {

    @Override
    public Money add(Money currentMoney, Money moneyToBeAdded) {
        CurrencyUnit walletUnit = currentMoney.getCurrencyUnit();
        CurrencyUnit compensationUnit = moneyToBeAdded.getCurrencyUnit();

        Money toAdd;
        if (walletUnit.equals(compensationUnit)) {
            toAdd = moneyToBeAdded;
        } else {
            BigDecimal conversionMultiplier = convert(walletUnit, compensationUnit);
            toAdd = moneyToBeAdded.convertedTo(walletUnit, conversionMultiplier,
                    RoundingMode.CEILING);
        }

        return currentMoney.plus(toAdd);
    }

    @Override
    public BigDecimal convert(CurrencyUnit from, CurrencyUnit to) {
        return new BigDecimal(1);
    }
}
