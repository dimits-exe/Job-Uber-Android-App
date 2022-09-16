package gr.aueb.sweng22.team09.ui.main.search;

import junit.framework.TestCase;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;

import gr.aueb.sweng22.team09.domainlogic.dao.IJobDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.TimeConstraint;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryJobDAO;

/**
 * Tests if the search logic functions correctly.
 *
 * @author Dimitris Tsirmpas
 */
public class SearchPresenterTest extends TestCase {
    private SearchPresenter searchPresenter;
    private IJobDAO jobDAO;

    private final TimeConstraint constraint1 =
            new TimeConstraint(LocalDate.now().minusDays(2), LocalDate.now().plusDays(2));
    private final TimeConstraint constraint2 =
            new TimeConstraint(LocalDate.now().minusDays(5), LocalDate.now().plusDays(1));
    private final Money compensation1 = Money.of(CurrencyUnit.USD, 1);
    private final User poster = new User.Builder("poster").password("12345678").email("test@email.com").buildNew();

    private final Job testJob1 = new Job(23, poster, "Kyria Paltoglou",
            "ti kaname gia ayto to martyrio", compensation1, constraint1, new HashSet<>());
    private final Job testJob2 = new Job(56, poster, "8elo na pe8ano",
            "aaaaaaaaa", compensation1.plus(Money.of(CurrencyUnit.USD, 10)), constraint2, new HashSet<>());

    @Override
    public void setUp() {
        jobDAO = new MemoryJobDAO();
        jobDAO.saveJob(testJob1);
        jobDAO.saveJob(testJob2);

        searchPresenter = new SearchPresenter(new TestView(), jobDAO);
    }

    @Override
    public void tearDown(){
        jobDAO.delete(testJob1);
        jobDAO.delete(testJob2);
    }

    /**
     * Test the default parameters of the search function.
     */
    public void testDefaultParameters() {
        Collection<Job> jobs;
        jobs = searchPresenter.searchJobs("", "", "");

        Assert.assertEquals(2, jobs.size());
    }

    public void testCustomParameters() {
        Collection<Job> jobs;
        jobs = searchPresenter.searchJobs(LocalDate.now().minusDays(3).
                        format(DateTimeFormatter.ofPattern("d-MM-yyyy")),
                LocalDate.MAX.format(DateTimeFormatter.ofPattern("d-MM-yyyy")), "0");

        System.out.println("TOTAL JOBS 3" + jobs);
        Assert.assertEquals(0, jobs.size());
    }

    private static final class TestView implements ISearchResultsView {
        @Override
        public void showErrorOn(SearchFormField field, String errorMessage) {}
    }
}