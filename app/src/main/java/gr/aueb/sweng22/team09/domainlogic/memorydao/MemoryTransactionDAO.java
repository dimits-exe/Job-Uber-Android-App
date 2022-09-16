package gr.aueb.sweng22.team09.domainlogic.memorydao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import gr.aueb.sweng22.team09.domainlogic.dao.ITransactionDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.Transaction;

/**DAO implementation for class Transaction
 *
 * @author Ioannis Gkionis
 */
public class MemoryTransactionDAO implements ITransactionDAO {

    private static final Map<Long, Transaction> transactions = new HashMap<>();

    /**replaces hashmap transactions with a new hashmap
     *
     * @param transactions the hashmap to replace the current dao collection
     */
    public static void setTransactions(Map<Long, Transaction> transactions){
        MemoryTransactionDAO.transactions.clear();
        MemoryTransactionDAO.transactions.putAll(transactions);
    }

    public MemoryTransactionDAO() { }

    @Override
    public Transaction get(long id) {
        checkExists(id);

        return transactions.get(id);
    }

    @Override
    public void delete(Transaction transaction){
        checkExists(transaction);
        transactions.remove(transaction.getId());
    }

    @Override
    public void save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Collection<Transaction> getAllSince(LocalDate date) {
        return transactions.values().stream()
                .filter(tr -> tr.getDate().compareTo(date) >= 0)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Collection<Transaction> getAll() {
        return new HashSet<>(transactions.values());
    }

    void checkExists(Long id) throws NoSuchElementException {
        if (!transactions.containsKey(id))
            throw new NoSuchElementException("transaction id " + id + " does not exist");
    }

    void checkExists(Transaction transaction) throws NoSuchElementException{
        checkExists(transaction.getId());
    }

}
