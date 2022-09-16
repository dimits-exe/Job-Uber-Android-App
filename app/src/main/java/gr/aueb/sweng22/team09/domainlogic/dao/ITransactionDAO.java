package gr.aueb.sweng22.team09.domainlogic.dao;

import java.time.LocalDate;
import java.util.Collection;

import gr.aueb.sweng22.team09.domainlogic.entities.Transaction;

/**
 * An interface for Data Access Objects concerning the Transaction entity class.
 */
public interface ITransactionDAO {

    /**
     * Get the transaction with the specified ID from the persistent storage.
     * @param id the transaction's id
     * @return the transaction
     */
    Transaction get(long id);

    /**Permanently deletes the transaction from the persistent storage.
     *
     * @param transaction the transaction to be deleted
     */
    void delete(Transaction transaction);

    /**
     * Save the transaction to the persistent storage.
     * @param transaction the transaction to be saved
     */
    void save(Transaction transaction);

    /**
     * Get all the transactions since the specified date from the persistent storage.
     * @return the transactions completed after the given date
     */
    Collection<Transaction> getAllSince(LocalDate date);

    /**
     * Get all the transactions from the persistent storage.
     * @return all the transactions
     */
    Collection<Transaction> getAll();
}
