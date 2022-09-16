package gr.aueb.sweng22.team09.domainlogic.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * An object representing the request by a worker to fulfill a certain Job.
 *
 * @author Dimitris Tsirmpas
 */
public class WorkOffer implements Serializable {

    /**
     * The current status of the application.
     */
    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    private final long id;
    private final Job job;
    private final User interestedWorker;
    private Status status;

    /**
     * Constructs a new request for a worker to fulfill a certain Job.
     * @param job the job
     * @param interestedWorker the worker
     */
    WorkOffer(Job job, User interestedWorker) { //restrict construction to Job and User class
        this(Objects.hash(job, interestedWorker), job, interestedWorker, Status.PENDING);
    }

    /**
     * Constructs an already defined request for a worker to fulfill a certain Job.
     * To be used by the database only.
     * @param job the job
     * @param interestedWorker the worker
     * @param status the status of the ongoing request
     */
    public WorkOffer(long id, Job job, User interestedWorker, Status status) {
        this.id = id;
        this.job = job;
        this.interestedWorker = interestedWorker;
        this.status = status;
    }


    /**
     * Get the request's job.
     * @return the job
     */
    public Job getJob() {
        return job;
    }

    /**
     * Get the worker interested in fulfilling the Job.
     * @return the candidate worker
     */
    public User getInterestedWorker() {
        return interestedWorker;
    }

    /**
     * Get the current status of the request.
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Accept this work offer.
     * @throws IllegalStateException if the request isn't pending
     */
    public void accept() throws IllegalStateException {
       if(status != Status.PENDING){
           throw new IllegalStateException("This request has already been closed");
       }

       status = Status.ACCEPTED;
    }

    /**
     * Reject this work offer.
     * @throws IllegalStateException if the request isn't pending
     */
    public void reject() throws IllegalStateException {
        if(status != Status.PENDING){
            throw new IllegalStateException("This request has already been closed");
        }

        status = Status.REJECTED;
    }

    /**
     * Returns the unique identifier for this work offer.
     * A new work offer with the same worker and job fields
     * will have the same id as this one.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOffer workOffer = (WorkOffer) o;
        return id == workOffer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
