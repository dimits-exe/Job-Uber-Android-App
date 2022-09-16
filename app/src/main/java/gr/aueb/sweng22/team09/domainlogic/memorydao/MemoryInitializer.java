package gr.aueb.sweng22.team09.domainlogic.memorydao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashSet;
import gr.aueb.sweng22.team09.domainlogic.dao.IJobDAO;
import gr.aueb.sweng22.team09.domainlogic.dao.ITransactionDAO;
import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.dao.Initializer;
import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.Transaction;
import gr.aueb.sweng22.team09.domainlogic.entities.User;

/**Subclass of abstract class initializer that contains
 * the implementation of a few methods
 *
 * @author Ioannis Gkionis
 */
public class MemoryInitializer extends Initializer {

    private static boolean prepared = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public MemoryInitializer() {
        if (!prepared) {
            prepareData();
            prepared = true;
        }
    }
    
    @Override
    protected void eraseData() {

        HashSet<Job> jobs = (HashSet<Job>) getJobDAO().getAllJobs();
        for (Job job : jobs){
            getJobDAO().delete(job);
        }

        HashSet<Transaction> transactions = (HashSet<Transaction>) getTransactionDAO().getAll();
        for (Transaction transaction : transactions){
            getTransactionDAO().delete(transaction);
        }

        HashSet<User> users = (HashSet<User>) getUserDAO().getAllUsers();
        for (User user : users){
            getUserDAO().delete(user);
        }

        prepared = false;
    }

    @Override
    protected ITransactionDAO getTransactionDAO() {
        return new MemoryTransactionDAO();
    }

    @Override
    protected IJobDAO getJobDAO() {
        return new MemoryJobDAO();
    }

    @Override
    protected  IUserDAO getUserDAO() {
        return new MemoryUserDAO();
    }


}
