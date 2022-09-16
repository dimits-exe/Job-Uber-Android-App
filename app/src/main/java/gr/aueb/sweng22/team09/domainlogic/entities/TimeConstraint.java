package gr.aueb.sweng22.team09.domainlogic.entities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A class encapsulating the starting date and the deadline of any event
 * in the System.
 *
 * @author Dimitris Tsirmpas
 */
public class TimeConstraint {
    private final LocalDate startingDate;
    private final LocalDate deadline;

    /**
     * Copy-Construct a new TimeConstraint
     * @param timeConstraint the to-be-copied TimeConstraint
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TimeConstraint(TimeConstraint timeConstraint){
        this(timeConstraint.getStartingDate(), timeConstraint.getDeadline());
    }

    /**
     * Constructs a new TimeConstraint for an event.
     * @param startingDate the starting date of the event
     * @param deadline the deadline of the event
     * @throws IllegalArgumentException if the deadline is earlier than the starting date
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TimeConstraint(LocalDate startingDate, LocalDate deadline) throws IllegalArgumentException {
        if(startingDate.isAfter(deadline)) {
            throw new IllegalArgumentException("Starting date can't be later than the deadline");
        }
        this.startingDate = startingDate;
        this.deadline = deadline;
    }

    /**
     * Get the starting Date.
     * @return the starting date
     */
    public LocalDate getStartingDate() {
        return startingDate;
    }

    /**
     * Get the deadline.
     * @return the deadline
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeConstraint that = (TimeConstraint) o;
        return Objects.equals(getStartingDate(), that.getStartingDate()) &&
                Objects.equals(getDeadline(), that.getDeadline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartingDate(), getDeadline());
    }
}

