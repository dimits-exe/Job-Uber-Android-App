package gr.aueb.sweng22.team09.domainlogic.dao;

import junit.framework.TestCase;

import org.joda.money.Money;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.TimeConstraint;
import gr.aueb.sweng22.team09.domainlogic.entities.Transaction;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.domainlogic.entities.WorkOffer;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryInitializer;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryJobDAO;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryTransactionDAO;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryUserDAO;
import gr.aueb.sweng22.team09.domainlogic.memorydao.TestMemoryInitializer;

/**Tests whether or not the DAOs are working as intended
 *
 * @author Ioannis Gkionis
 */
public class DAOTest extends TestCase {

    private IUserDAO userDAO;
    private ITransactionDAO transactionDAO;
    private IJobDAO jobDAO;
    private User gkiwnhs;
    private Job eloboosting;
    final LocalDate NOW = LocalDate.now();


    @Override
    protected void setUp(){
        TestInitializer dataHelper = new TestMemoryInitializer();
        dataHelper.eraseData();
        dataHelper.prepareData();
        userDAO = new MemoryUserDAO();
        jobDAO = new MemoryJobDAO();
        transactionDAO = new MemoryTransactionDAO();

        User gkiwnhs = new User.Builder("gkiwnhs").password("kharnifex").
                firstName("iwannhs").lastName("denantexwallo").email("stamatanabazeisnull@gmail.com").
                telephone("6992801310").city("argyroupolh").address("iwontsay 5").buildNew();

        Job eloboosting = new Job(2L, gkiwnhs, "boostare me", "den antexw allo na eimai d1",
                Money.parse("USD 150"), new TimeConstraint(NOW, NOW.plusDays(10)), new HashSet<>());
        this.gkiwnhs=gkiwnhs;
        this.eloboosting=eloboosting;
    }

    @Override
    protected void tearDown(){
        TestInitializer dataHelper = new TestMemoryInitializer();
        dataHelper.eraseData();
        Initializer actualInit = new MemoryInitializer();
    }

    /**Tests if jobs get properly deleted
     *
     */
    public void testDeleteJob(){
        jobDAO.delete(jobDAO.get(23L));
        Assert.assertThrows(NoSuchElementException.class, () -> jobDAO.get(23L));
    }

    /**Tests if users get properly deleted
     *
     */
    public void testDeleteUser(){
        userDAO.delete(userDAO.get("lamproulos"));
        Assert.assertFalse(userDAO.getAllUsers().contains(userDAO.get("lamproulos")));
    }

    /**Tests whether transactions can be deleted and saved properly and if the getall and getallsince methods work as intended
     *
     */
    public void testTransaction(){
        for (Transaction t : transactionDAO.getAll()){
            transactionDAO.delete(t);
        }
        Assert.assertTrue(transactionDAO.getAll().isEmpty());
        Transaction transaction = new Transaction(45, userDAO.get("lamproulos"), userDAO.get("fasaia"), Money.parse("USD 14"), LocalDate.now().minusDays(2));
        transactionDAO.save(transaction);
        Assert.assertTrue(transactionDAO.getAll().contains(transaction));
        Assert.assertTrue(transactionDAO.getAllSince(LocalDate.now().minusMonths(1)).contains(transaction));
    }

    /**Tests whether jobs can be saved
     *
     */
    public void testSave(){
        userDAO.save(gkiwnhs);
        jobDAO.saveJob(eloboosting);
        Assert.assertTrue(jobDAO.getAllJobs().contains(eloboosting));
    }

    /**Tests whether users can be saved
     *
     */
    public void testSaveUser(){
        userDAO.save(gkiwnhs);
        Assert.assertTrue(userDAO.userExists("gkiwnhs"));
        Assert.assertTrue((userDAO.getAllUsers().contains(gkiwnhs)));
    }

    /**Tests if we can get jobs using the poster as a parameter
     *
     */
    public void testGetJobsByPoster(){
        userDAO.save(gkiwnhs);
        jobDAO.saveJob(eloboosting);
        Collection<Job> jobs = jobDAO.getJobsByPoster(gkiwnhs);
        Assert.assertTrue(jobs.contains(eloboosting));
    }

    /**Tests if we can get jobs using the worker as a parameter
     *
     */
    public void testGetJobsByWorker(){
        userDAO.save(gkiwnhs);
        jobDAO.saveJob(eloboosting);
        WorkOffer wo = eloboosting.addInterested(userDAO.get("lamproulos"));
        wo.accept();
        Collection<Job> jobs = jobDAO.getJobsByWorker(userDAO.get("lamproulos"));
        Assert.assertTrue(jobs.contains(eloboosting));
    }

    /**Tests if users can be searched for using password as a parameter
     *
     */
    public void testGetPassword(){
        userDAO.save(gkiwnhs);
        Assert.assertEquals(userDAO.get("gkiwnhs").getPassword(), gkiwnhs.getPassword());
    }

}
