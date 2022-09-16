package gr.aueb.sweng22.team09.ui.main.stats;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.joda.money.Money;

import java.time.LocalDate;

import gr.aueb.sweng22.team09.domainlogic.dao.ITransactionDAO;
import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.Transaction;
import gr.aueb.sweng22.team09.ui.strategies.DefaultMoneyAdditionStrategy;
import gr.aueb.sweng22.team09.ui.strategies.IMoneyAdditionStrategy;

/**
 * A Presenter that manages the data of a Stat Fragment.
 *
 * @author Ioannis Gkionis
 */
class StatPresenter {
    private final IMoneyAdditionStrategy moneyAdditionStrategy = new DefaultMoneyAdditionStrategy();

    private final IUserDAO userDAO;
    private final ITransactionDAO transactionDAO;

    public StatPresenter(IUserDAO userDAO, ITransactionDAO transactionDAO) {
        this.userDAO = userDAO;
        this.transactionDAO = transactionDAO;
    }

    /**
     * Calculates the monthly volume of transactions.
     *
     * @return the monthly volume of transactions as a String
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateMonthlyVOT() {
        Money total = Money.parse("USD 0");
        for (Transaction t : transactionDAO.getAllSince(LocalDate.now().minusMonths(1)))
            total = addMoney(total, t.getAmount());

        return total.toString();
    }

    /**
     * Calculates the total volume of transactions.
     *
     * @return the total volume of transactions as a String
     */
    public String calculateTotalVOT() {
        Money total = Money.parse("USD 0");
        for (Transaction t : transactionDAO.getAll())
            total = addMoney(total, t.getAmount());

        return total.toString();
    }

    /**
     * Returns the amount of Users.
     *
     * @return the amount of users as a String
     */
    public String calculateAmountOfUsers() {
        return Integer.toString(userDAO.getAllUsers().size());
    }

    private Money addMoney(Money wallet, Money compensation) {
        return moneyAdditionStrategy.add(wallet, compensation);
    }
}
