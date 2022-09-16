package gr.aueb.sweng22.team09.domainlogic.entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.joda.money.Money;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class that encapsulates the details, the status and all the interested members of a Job
 * Request.
 *
 * @author Dimitris Tsirmpas
 */
public class Job implements Serializable {

    /**
     * Describes the current status of the Job.
     */
    public enum Status {
        AVAILABLE, PENDING, IN_PROGRESS, COMPLETE, ABORTED
    }

    private final long id;
    private final User poster;
    private final String title;
    private final String description;
    private final Money compensation;
    private final Collection<WorkOffer> workOffers;

    private TimeConstraint duration;
    private boolean deadlineChanged;
    private Status status;

    /**
     * Creates a new job with a randomly generated id, marking it as available.
     * @param poster the user that posted the job
     * @param title the title of the job
     * @param description the description of the job
     * @param compensation the monetary compensation offered at the successful completion of the job
     * @param duration the duration of the job
     */
    public Job(User poster, String title, String description, Money compensation,
               TimeConstraint duration) {
        this(ThreadLocalRandom.current().nextLong(), poster, title, description, compensation,
                duration, new HashSet<>());
    }

    /**
     * Creates a job object out of an already created job, marking it as available.
     * @param id the job's id
     * @param poster the user that posted the job
     * @param title the title of the job
     * @param description the description of the job
     * @param compensation the monetary compensation offered at the successful completion of the job
     * @param duration the duration of the job
     */
    public Job(long id, User poster, String title, String description, Money compensation,
               TimeConstraint duration, Collection<WorkOffer> workOffers) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.description = description;
        this.compensation = compensation;
        this.duration = duration;
        this.workOffers = workOffers;

        status = Status.AVAILABLE;
        deadlineChanged = false;
    }


    /**
     * Registers a user as interested for this job and returns the associated work offer.
     * @param interestedWorker the user interested in the job
     * @return the work offer object associated with that user
     */
    public WorkOffer addInterested(User interestedWorker){
        WorkOffer wo = new WorkOffer(this, interestedWorker);
        workOffers.add(wo);
        return wo;
    }

    /**
     * Checks whether the job has a worker assigned to it
     * @return boolean value based on whether the job has a worker or not
     */
    public boolean isWorkerAssigned(){
        User worker = getAssignedWorkerImpl();
        return (worker != null);
    }

    /**
     * Returns the worker assigned to fulfill this job.
     * @return the assigned worker
     * @throws NoSuchElementException if no worker has been assigned
     */
    public User getAssignedWorker() throws NoSuchElementException {
        User worker = getAssignedWorkerImpl();

        if(worker == null)
            throw new NoSuchElementException("No user has been assigned to this job as of yet");
        else
            return worker;
    }

    /**
     * Get the current work offer for a specific user
     * @param user the user who has expressed interest in the job
     * @return the work offer associated with this user
     * @throws NoSuchElementException if the user hasn't expressed interest in the job
     */
    public WorkOffer getUserOffer(User user) throws NoSuchElementException {
        for(WorkOffer wo: workOffers){
            if(wo.getInterestedWorker().equals(user)){
                return wo;
            }
        }
        throw new NoSuchElementException("This user hasn't expressed any interest in this job");
    }

    /**
     * Get all the work offers for this job.
     * @return a copied collection containing the work offers
     */
    public Collection<WorkOffer> getWorkOffers() { return new HashSet<>(workOffers); }

    /**
     * Unregisters a work offer from this job.
     * @param workOffer the work offer
     * @throws NoSuchElementException if the work offer isn't registered for this job
     */
    public void removeInterest(WorkOffer workOffer) throws NoSuchElementException {
        if(!workOffers.contains(workOffer)){
            throw new NoSuchElementException(
                    String.format("User %s had not expressed interest in this job",workOffer));
        }
        workOffers.remove(workOffer);
    }

    /**
     * Change the current deadline to a new one. This can only be done once.
     * @param newDeadline the new deadline
     * @throws IllegalStateException if the deadline was already changed
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void changeDeadline(LocalDate newDeadline) throws IllegalStateException {
        if(deadlineChanged){
            throw new IllegalStateException("The deadline can only be changed once");
        } else {
            this.duration = new TimeConstraint(this.duration.getStartingDate(), newDeadline);
            this.deadlineChanged = true;
        }
    }

    /**
     * Returns whether or not the Job's deadline can be changed. This can only be done once, so if
     * a second attempt is made to change it, a runtime exception will be thrown by the
     * @see #changeDeadline(LocalDate) method
     *
     * @return true if the deadline can be changed, false otherwise
     */
    public boolean deadlineCanBeChanged(){
        return !deadlineChanged;
    }

    /**
     * Get the user that posted the Job
     * @return the poster
     */
    public User getPoster() {
        return poster;
    }

    /**
     * Get the title of the Job.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the description of the Job.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the monetary compensation offered at the successful completion of the job.
     * @return the compensation
     */
    public Money getCompensation() {
        return compensation;
    }

    /**
     * Get the duration of the job.
     * @return the duration
     */
    public TimeConstraint getDuration() {
        return duration;
    }

    /**
     * Get the current status of the job
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Update the current status of the job
     * @param status the new status
     * @throws IllegalStateException if no worker has been assigned
     */
    public void setStatus(Status status) throws IllegalStateException {
        if(status == Status.IN_PROGRESS && getAssignedWorkerImpl() == null){
            throw new IllegalStateException("A worker must be assigned for the job to be marked " +
                    "as IN_PROGRESS");
        }
        this.status = status;
    }

    /**
     * Get the object's unique identifier.
     * @return the id
     */
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return getId() == job.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s posted by %s", title, poster);
    }

    private User getAssignedWorkerImpl() {
        for(WorkOffer wo : workOffers){
            if(wo.getStatus() == WorkOffer.Status.ACCEPTED){
                return wo.getInterestedWorker();
            }
        }
        return null;
    }
}
