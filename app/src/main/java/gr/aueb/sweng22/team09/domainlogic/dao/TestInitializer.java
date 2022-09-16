package gr.aueb.sweng22.team09.domainlogic.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.joda.money.Money;

import java.time.LocalDate;
import java.util.HashSet;

import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.TimeConstraint;
import gr.aueb.sweng22.team09.domainlogic.entities.Transaction;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.ui.strategies.IEncryptStrategy;
import gr.aueb.sweng22.team09.ui.strategies.PBEEncryptStrategy;


/**Abstract class that initializes the database with a few users and jobs and transactions for testing purposes
 *
 * @author Ioannis Gkionis
 */
public abstract class TestInitializer {


    public abstract void eraseData();
    protected abstract ITransactionDAO getTransactionDAO();
    protected abstract IJobDAO getJobDAO();
    protected abstract IUserDAO getUserDAO();



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void prepareData(){
        eraseData();

        IEncryptStrategy encryptStrategy = new PBEEncryptStrategy();

        User testUser = new User.Builder("testuser").password("12345678").
                firstName("testname").lastName("testlastname").email("test@email.com").
                telephone("696969696969").city("test city").address("test address").buildNew();


        User lamproulos = new User.Builder("lamproulos").password("glorytoarstotzka").
                firstName("panagiwths").lastName("vatraxopoulos").email("ponos@email.com").
                telephone("6932456711").city("meta city").address("artemis 9").buildNew();

        User fasaia = new User.Builder("fasaia").password("tikanwmeshmeriat").
                firstName("eirhnh").lastName("delemeonomata").email("aaaaaa@email.com").
                telephone("6932817241").city("meta city").address("demouleeiodhmhtrhs 9").buildNew();

        final LocalDate NOW = LocalDate.now();
        Job douleia = new Job(23L, lamproulos, "boithia me frontend dev",
                "o project manager mou exei teleiwsei meteorologos voithia o typos den kserei oute kan python",
                Money.parse("USD 15.45") ,new TimeConstraint(NOW, NOW.plusDays(4)), new HashSet<>());

        Job douleia2 = new Job(20L, fasaia, "vatraxos galhnhs",
                "theleis enan vatraxo galhnhs apo thn babylonia",
                Money.parse("USD 2000") ,new TimeConstraint(NOW, NOW.plusDays(6)), new HashSet<>());

        Transaction transaction = new Transaction(45, lamproulos, fasaia, Money.parse("USD 14"), NOW.minusDays(2));

        Transaction transaction2 = new Transaction(23, fasaia, lamproulos, Money.parse("USD 23"), NOW.minusMonths(2));

        Transaction transaction3 = new Transaction(11, lamproulos, fasaia, Money.parse("USD 28"), NOW.minusDays(23));

        Transaction transaction4 = new Transaction(64, fasaia, testUser, Money.parse("USD 82"), NOW.minusMonths(6));

        Transaction transaction5 = new Transaction(278, testUser, lamproulos, Money.parse("USD 53"), NOW.minusDays(14));

        getUserDAO().save(testUser);
        getUserDAO().save(lamproulos);
        getUserDAO().save(fasaia);
        getJobDAO().saveJob(douleia);
        getJobDAO().saveJob(douleia2);
        getTransactionDAO().save(transaction);
        getTransactionDAO().save(transaction2);
        getTransactionDAO().save(transaction3);
        getTransactionDAO().save(transaction4);
        getTransactionDAO().save(transaction5);
    }
}
