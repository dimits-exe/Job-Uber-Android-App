package gr.aueb.sweng22.team09.ui.main.search;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import gr.aueb.sweng22.team09.domainlogic.dao.IJobDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.JobSearchConstraints;
import gr.aueb.sweng22.team09.domainlogic.entities.TimeConstraint;
import gr.aueb.sweng22.team09.ui.strategies.CustomDateParsingStrategy;
import gr.aueb.sweng22.team09.ui.strategies.IDateParsingStrategy;

/**
 * A Presenter that manages the data of a Search View.
 *
 * @author Dimitris Tsirmpas
 */
@RequiresApi(api = Build.VERSION_CODES.O)
class SearchPresenter {

    private static final LocalDate DEFAULT_EARLIEST_DATE = LocalDate.MIN;
    private static final LocalDate DEFAULT_DEADLINE = LocalDate.MAX;
    private static final double DEFAULT_COMPENSATION = 0;

    private final IDateParsingStrategy dateParsingStrategy = new CustomDateParsingStrategy();
    private final ISearchResultsView view;

    private final IJobDAO jobDao;

    public SearchPresenter(ISearchResultsView view, IJobDAO jobDAO) {
        this.view = view;
        this.jobDao = jobDAO;
    }

    /**
     * Returns a collection of all jobs meeting the search criteria.
     *
     * @param earliestDateString the User's 'earliest day' criteria
     * @param deadlineString     the User's deadline criteria
     * @param compensationString the User's compensation criteria
     * @return the jobs or an empty collection if unsuccessful.
     */
    public Collection<Job> searchJobs(String earliestDateString, String deadlineString, String compensationString) {
        JobSearchConstraints jobSearchConstraints = parseArguments(
                earliestDateString, deadlineString, compensationString);

        return jobSearchConstraints == null
                ? new LinkedList<>()
                : filterJobs(jobSearchConstraints);
    }

    /**
     * Parses the user-provided criteria in a {@link JobSearchConstraints} object. This method also
     * notifies the view of any errors while parsing.
     *
     * @param earliestDateString the User's 'earliest day' criterion
     * @param deadlineString     the User's deadline criterion
     * @param compensationString the User's compensation criterion
     * @return a {@link JobSearchConstraints} object or null if any criteria are invalid
     */
    private JobSearchConstraints parseArguments(String earliestDateString, String deadlineString,
                                                String compensationString) {
        LocalDate earliestDate;
        LocalDate deadline;
        double compensation = 0;
        boolean success = true;

        if (earliestDateString.isEmpty()) {
            earliestDate = DEFAULT_EARLIEST_DATE;
        } else {
            earliestDate = dateParsingStrategy.parseDate(earliestDateString);
            if (earliestDate == null) {
                view.showErrorOn(SearchFormField.EARLIEST_DATE, "Wrong Date Format");
                success = false;
            }
        }

        if (deadlineString.isEmpty()) {
            deadline = DEFAULT_DEADLINE;
        } else {
            deadline = dateParsingStrategy.parseDate(deadlineString);
            if (deadline == null) {
                view.showErrorOn(SearchFormField.DEADLINE, "Wrong Date Format");
                success = false;
            }
        }

        if (compensationString.isEmpty()) {
            compensation = DEFAULT_COMPENSATION;
        } else {
            try {
                compensation = Double.parseDouble(compensationString);

                if (compensation < 0)
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                view.showErrorOn(SearchFormField.COMPENSATION, compensationString
                        + " is not a valid value for compensation");
                success = false;
            }
        }

        if (!success)
            return null;

        TimeConstraint tc = new TimeConstraint(earliestDate, deadline);
        Money money = Money.of(CurrencyUnit.USD, compensation);
        return new JobSearchConstraints(tc, money);
    }

    private Collection<Job> filterJobs(JobSearchConstraints jobSearchConstraints) {
        List<Job> list = new ArrayList<>();
        for (Job job : jobDao.getAllJobs()) {
            if (jobSearchConstraints.getCompensation().compareTo(job.getCompensation()) <= 0) {
                if (jobSearchConstraints.getDuration().getStartingDate().
                        compareTo(job.getDuration().getStartingDate()) <= 0) {
                    if (jobSearchConstraints.getDuration().getDeadline().
                            compareTo(job.getDuration().getDeadline()) >= 0) {
                        list.add(job);
                    }
                }
            }
        }
        return list;
    }
}
