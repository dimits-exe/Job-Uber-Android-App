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


/**Abstract class that initializes the database with a few users and jobs
 *
 * @author Ioannis Gkionis
 */
public abstract class Initializer {


    protected abstract void eraseData();
    protected abstract ITransactionDAO getTransactionDAO();
    protected abstract IJobDAO getJobDAO();
    protected abstract IUserDAO getUserDAO();



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void prepareData(){

        eraseData();

        User testUser = new User.Builder("testuser").password("12345678").
                firstName("testname").lastName("testlastname").email("test@email.com").
                telephone("696969696969").city("test city").address("test address").buildNew();

        User lamproulos = new User.Builder("lamproulos").password("glorytoarstotzka").
                firstName("panagiwths").lastName("vatraxopoulos").email("ponos@email.com").
                telephone("6932456711").city("meta city").address("artemis 9").buildNew();

        User gewrgios = new User.Builder("mush246").password("datacube").
                firstName("kyvos").lastName("liarakos").email("kyvos@email.com").
                telephone("6932321711").city("athens").address("OLAP 1").buildNew();

        User georgia = new User.Builder("georgette").password("123456789").
                firstName("gewrgia").lastName("redacted").email("tsirb@email.com").
                telephone("697823101").city("metamorfosi").address("artemis 10").buildNew();

        User ponos = new User.Builder("ponos").password("zivelabosna").
                firstName("iwannhs").lastName("gkiwnhs").email("thelwvodka@email.com").
                telephone("6971235512").city("argyroupolh").address("charilaou trikoupi 4").buildNew();

        User peace = new User.Builder("peace").password("tikanwmeshmeriat").
                firstName("eirhnh").lastName("ymin").email("aaaaaa@email.com").
                telephone("6932817241").city("metamorfosi").address("rimini 9").buildNew();

        User electra = new User.Builder("electra").password("ventiventi").
                firstName("maria").lastName("papadopoulou").email("ventiventiventi@email.com").
                telephone("6932817232").city("greek").address("jasonidou 23").buildNew();

        final LocalDate NOW = LocalDate.now();
        Job douleia = new Job(23L, lamproulos, "boithia me frontend dev",
        "o project manager mou exei teleiwsei meteorologos voithia o typos den kserei oute kan python",
                Money.parse("USD 15.45") ,new TimeConstraint(NOW, NOW.plusDays(4)), new HashSet<>());

        Job douleia2 = new Job(20L, testUser, "vatraxos galhnhs",
                "theleis enan vatraxo galhnhs apo thn babylonia",
                Money.parse("USD 2000") ,new TimeConstraint(NOW, NOW.plusDays(6)), new HashSet<>());

        Job douleia3 = new Job(50L, ponos, "voitheia se metakomish",
                "thelw na metakomhsw kai xreiazomai voitheia sto kouvalhma",
                Money.parse("USD 400") ,new TimeConstraint(NOW, NOW.plusDays(14)), new HashSet<>());

        Job douleia4 = new Job(540L, testUser, "voitheia se douleies tou spitiou",
                "thelw kapoios na mageirepsei kai na sfouggarisei to spiti",
                Money.parse("USD 100") ,new TimeConstraint(NOW, NOW.plusDays(5)), new HashSet<>());

        Job douleia5 = new Job(200L, testUser, "ergasia sdad",
                "thelw kapoion na mou ekshghsei ti einai o kyvos dedomenwn",
                Money.parse("USD 400") ,new TimeConstraint(NOW, NOW.plusDays(6)), new HashSet<>());

        Job douleia6 = new Job(222L, electra, "rants",
                "aplws thelw kapoion na akouei ta 3wra rants mou",
                Money.parse("USD 40") ,new TimeConstraint(NOW, NOW.plusDays(8)), new HashSet<>());

        Transaction transaction = new Transaction(45, lamproulos, peace, Money.parse("USD 14"), LocalDate.now().minusDays(2));

        Transaction transaction2 = new Transaction(23, peace, lamproulos, Money.parse("USD 23"), NOW.minusMonths(2));

        Transaction transaction3 = new Transaction(11, gewrgios, georgia, Money.parse("USD 28"), NOW.minusDays(23));

        Transaction transaction4 = new Transaction(64, peace, ponos, Money.parse("USD 82"), NOW.minusMonths(6));

        Transaction transaction5 = new Transaction(278, testUser, electra, Money.parse("USD 53"), NOW.minusDays(14));

        getUserDAO().save(testUser);
        getUserDAO().save(lamproulos);
        getUserDAO().save(peace);
        getUserDAO().save(gewrgios);
        getUserDAO().save(georgia);
        getUserDAO().save(ponos);
        getUserDAO().save(electra);
        getJobDAO().saveJob(douleia);
        getJobDAO().saveJob(douleia2);
        getJobDAO().saveJob(douleia3);
        getJobDAO().saveJob(douleia4);
        getJobDAO().saveJob(douleia5);
        getJobDAO().saveJob(douleia6);
        getTransactionDAO().save(transaction);
        getTransactionDAO().save(transaction);
        getTransactionDAO().save(transaction2);
        getTransactionDAO().save(transaction3);
        getTransactionDAO().save(transaction4);
        getTransactionDAO().save(transaction5);
        testUser.addJobInterest(douleia5);
    }
}
