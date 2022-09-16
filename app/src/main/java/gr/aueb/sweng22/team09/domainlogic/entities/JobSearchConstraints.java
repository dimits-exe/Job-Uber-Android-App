package gr.aueb.sweng22.team09.domainlogic.entities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.joda.money.Money;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A class holding all the search parameters for a user
 * search on open job requests.
 *
 * @author Dimitris Tsirmpas
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class JobSearchConstraints {
    /**
     * A constant used if the user has no preferences towards the
     * time constraints of his assignment.
     */
    public static final TimeConstraint NO_TIME_CONSTRAINT =
            new TimeConstraint(LocalDate.now(), LocalDate.now().plusDays(3));

    /**
     * A constant used if the user has no preferences towards the
     * amount of compensation for his work.
     */
    public static final Money NO_COMPENSATION_LIMIT = Money.parse("USD 0");

    private final TimeConstraint duration;
    private final Money compensation;

    /**
     * Constructs a new parameter set for both duration and compensation limits.
     * @param duration the minimum starting date and maximum deadline of the job
     * @param compensation the minimum amount of money offered as compensation for the job
     */
    public JobSearchConstraints(TimeConstraint duration, Money compensation) {
        this.duration = duration;
        this.compensation = compensation;
    }

    /**
     * Constructs a new parameter set for duration limits only.
     * @param duration the minimum starting date and maximum deadline of the job
     */
    public JobSearchConstraints(TimeConstraint duration){
        this(duration, NO_COMPENSATION_LIMIT);
    }

    /**
     * Constructs a new parameter set for compensation only.
     * @param compensation the minimum amount of money offered as compensation for the job
     */
    public JobSearchConstraints(Money compensation){
        this(NO_TIME_CONSTRAINT, compensation);
    }

    /**
     * Constructs an empty parameter set, where all results will be returned.
     */
    public JobSearchConstraints(){
        this(NO_TIME_CONSTRAINT, NO_COMPENSATION_LIMIT);
    }

    /**
     * Get the desired duration of the job.
     * @return the minimum starting date and maximum deadline of the job
     */
    public TimeConstraint getDuration() {
        return duration;
    }

    /**
     * Get the minimum desired compensation of the job.
     * @return the minimum amount of money offered as compensation for the job
     */
    public Money getCompensation() {
        return compensation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobSearchConstraints that = (JobSearchConstraints) o;
        return Objects.equals(duration, that.duration) && Objects.equals(compensation, that.compensation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, compensation);
    }
}
