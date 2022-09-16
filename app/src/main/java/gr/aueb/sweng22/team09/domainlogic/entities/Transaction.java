package gr.aueb.sweng22.team09.domainlogic.entities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.joda.money.Money;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An object representing a transaction that took place after the completion
 * of a Job.
 *
 * @author Dimitris Tsirmpas
 */
public class Transaction {
    private final long id;
    private final User benefactor;
    private final User receiver;
    private final Money amount;
    private final LocalDate date;

    /**
     * Create a new Transaction that took place at this time instant.
     * @param benefactor the poster of the job
     * @param receiver the worker of the job
     * @param amount the amount paid as compensation
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Transaction(User benefactor, User receiver, Money amount) {
        this(ThreadLocalRandom.current().nextLong(), benefactor, receiver, amount, LocalDate.now());
    }

    /**
     * Create a new Transaction that took place at a specified time.
     * @param id the unique id for this transaction
     * @param benefactor the poster of the job
     * @param receiver the worker of the job
     * @param amount the amount paid as compensation
     * @throws IllegalArgumentException if the given date is in the future
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Transaction(long id, User benefactor, User receiver, Money amount, LocalDate instant)
            throws IllegalArgumentException {

        if(instant.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A transaction cannot take place in the future");
        }

        this.id = id;
        this.benefactor = benefactor;
        this.receiver = receiver;
        this.amount = amount;
        this.date = instant;
    }

    /**
     * Get the user who initiated the transaction.
     * @return the poster of the completed job
     */
    public User getBenefactor() {
        return benefactor;
    }

    /**
     * Get the receiver of the funds.
     * @return the worker of the completed job
     */
    public User getReceiver() {
        return receiver;
    }

    /**
     * Get the amount of money the receiver was awarded.
     * @return the job's compensation
     */
    public Money getAmount() {
        return amount;
    }

    /**
     * Get the date this transaction took place.
     * @return the date of the transaction
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns a unique identifier for this Transaction.
     * @return the id of the transaction
     */
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
