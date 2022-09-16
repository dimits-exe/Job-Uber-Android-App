package gr.aueb.sweng22.team09.ui.jobform;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.time.LocalDate;

import gr.aueb.sweng22.team09.domainlogic.dao.IJobDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.TimeConstraint;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.ui.strategies.CustomDateParsingStrategy;
import gr.aueb.sweng22.team09.ui.strategies.IDateParsingStrategy;

/**
 * A presenter parsing user provided arguments from the form, in order to register
 * a new {@link Job} to the System.
 *
 * @author Dimitris Tsirmpas
 */
class JobFormPresenter {
    private final IJobFormView view;
    private final IJobDAO jobDAO;
    private final User user;
    private final IDateParsingStrategy dateParsingStrategy = new CustomDateParsingStrategy();

    public JobFormPresenter(IJobFormView view, User user, IJobDAO jobDAO) {
        this.view = view;
        this.user = user;
        this.jobDAO = jobDAO;
    }

    /**
     * Register a new job to the System, according to the user filled fields.
     * @return true if the operation was successful, false otherwise
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean registerJob() {
        view.clearErrors();
        boolean success = true;

        String title = view.getTitle();
        if(title.trim().isEmpty()) {
            view.showErrorOn(JobFormField.TITLE, "Title can't be empty");
            success = false;
        }

        String description = view.getDescription();

        LocalDate startingDate = dateParsingStrategy.parseDate(view.getStartingDate());
        if(startingDate == null){
            view.showErrorOn(JobFormField.STARTING_DATE, "Wrong date format");
            success = false;
        }

        LocalDate deadline = dateParsingStrategy.parseDate(view.getDeadline());
        if(deadline == null){
            view.showErrorOn(JobFormField.DEADLINE, "Wrong date format");
            success = false;
        }

        Money compensation = parseCompensation(view.getCompensation());
        if(compensation == null){
            view.showErrorOn(JobFormField.COMPENSATION,
                    view.getCompensation() + " is not a valid number");
            success = false;
        }

        if(success){
            try {
                jobDAO.saveJob(new Job(user, title, description, compensation,
                        new TimeConstraint(startingDate, deadline)));
            } catch(Throwable throwable){
                Log.wtf("JobFormPresenter", throwable);
                view.showError("An unexpected error has occurred");
                success = false;
            }
        }


        return success;
    }


    private static Money parseCompensation(String compensationString) {
        double compensation;
        try {
            compensation = Double.parseDouble(compensationString);

            if(compensation < 0)
                return null;

        } catch (NumberFormatException ne) {
            return null;
        }

        return Money.of(CurrencyUnit.USD, compensation);
    }

}
