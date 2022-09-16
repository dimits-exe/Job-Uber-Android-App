package gr.aueb.sweng22.team09.ui.jobform;

import junit.framework.TestCase;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Assert;

import gr.aueb.sweng22.team09.domainlogic.dao.IJobDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryJobDAO;
import gr.aueb.sweng22.team09.ui.strategies.CustomDateParsingStrategy;
import gr.aueb.sweng22.team09.ui.strategies.IDateParsingStrategy;

/**
 * Test the logic of the job creation form, as well as the error messages it produces.
 * @author Dimitris Tsirmpas
 */
public class JobFormPresenterTest extends TestCase {
    private final IDateParsingStrategy dateParsingStrategy = new CustomDateParsingStrategy();
    private User poster;
    private IJobDAO jobDao;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        poster = new User.Builder("POSTER").password("12345678").
                email("poster@email.com").buildNew();
        jobDao = new MemoryJobDAO();
    }

    /**
     * Test if a job is saved if the creation form produces no errors.
     */
    public void testCorrectCreation() {
        TestView view = new TestView();
        JobFormPresenter presenter = new JobFormPresenter(view, poster, jobDao);
        Assert.assertTrue(presenter.registerJob());

        Job job = (Job) jobDao.getJobsByPoster(poster).toArray()[0];
        Assert.assertEquals(view.getTitle(), job.getTitle());
        Assert.assertEquals(view.getDescription(), job.getDescription());
        Assert.assertEquals(dateParsingStrategy.parseDate(view.getStartingDate()), job.getDuration().getStartingDate());
        Assert.assertEquals(dateParsingStrategy.parseDate(view.getDeadline()), job.getDuration().getDeadline());
        Assert.assertEquals(Money.of(CurrencyUnit.USD, Double.parseDouble(view.getCompensation()))
                , job.getCompensation());

        jobDao.delete(job);
    }

    /**
     * Test the error message and DAO side effects produced when an invalid title is given.
     */
    public void testWrongTitle() {
        TestView view = new WrongTitleView();
        JobFormPresenter presenter = new JobFormPresenter(view, poster, jobDao);

        Assert.assertFalse(presenter.registerJob());

        Assert.assertSame(JobFormField.TITLE, view.lastErrorField);
        Assert.assertSame(0 ,jobDao.getJobsByPoster(poster).size());
    }

    /**
     * Test the error message and DAO side effects produced when an invalid deadline is given.
     */
    public void testWrongDeadline() {
        TestView view = new WrongDeadlineView();
        JobFormPresenter presenter = new JobFormPresenter(view, poster, jobDao);

        Assert.assertFalse(presenter.registerJob());

        Assert.assertSame(JobFormField.DEADLINE, view.lastErrorField);
        Assert.assertSame(0 ,jobDao.getJobsByPoster(poster).size());
    }

    /**
     * Test the error message and DAO side effects produced when the wrong compensation is given.
     */
    public void testWrongCompensation() {
        TestView view = new WrongCompensationView();
        JobFormPresenter presenter = new JobFormPresenter(view, poster, jobDao);

        Assert.assertFalse(presenter.registerJob());

        Assert.assertSame(JobFormField.COMPENSATION, view.lastErrorField);
        System.out.println(jobDao.getJobsByPoster(poster));
        Assert.assertSame(0 ,jobDao.getJobsByPoster(poster).size());
    }

    private static class TestView implements IJobFormView {
        protected JobFormField lastErrorField;

        @Override
        public void showError(String errorMessage) {
            throw new IllegalStateException("Illegal general error " + errorMessage);
        }

        @Override
        public void showErrorOn(JobFormField field, String errorMessage) {
            lastErrorField = field;
            System.out.printf("%s: %s%n", field, errorMessage);
        }

        @Override
        public void clearErrors() {}

        @Override
        public String getTitle() {
            return "TestTitle";
        }

        @Override
        public String getDescription() {
            return "Test description";
        }

        @Override
        public String getStartingDate() {
            return "2-3-2022";
        }

        @Override
        public String getDeadline() {
            return "7-6-2022";
        }

        @Override
        public String getCompensation() {
            return "34";
        }

    }

    private static class WrongTitleView extends TestView {
       @Override
       public String getTitle() { return "  "; }
    }

    private static class WrongDeadlineView extends TestView {
        @Override
        public String getDeadline() {
            return "8/9098089jiofshs";
        }
    }

    private static class WrongCompensationView extends  TestView {
        @Override
        public String getCompensation() { return "-1"; }
    }
}