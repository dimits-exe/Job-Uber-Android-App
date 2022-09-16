package gr.aueb.sweng22.team09.domainlogic.dao;

import java.util.Collection;

import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.User;

/**
 * An interface for Data Access Objects concerning the User entity class.
 */
public interface IJobDAO {

    /**
     * Get the job with the specified ID.
     * @param id the id of the job
     * @return the job
     */
    Job get(long id);

    /**Permanently deletes the job from the persistent storage.
     *
     * @param job the job to be deleted
     */
    void delete(Job job);

    /**
     * Commit the job to the persistent storage.
     * @param job the job to be saved
     */
    void saveJob(Job job);

    /**
     * Get all the jobs posted by a given User.
     * @param user the user that posted the jobs
     * @return the jobs
     */
    Collection<Job> getJobsByPoster(User user);

    /**
     * Get all the jobs assigned to a given Worker
     * @param user the assigned worker
     * @return the jobs
     */
    Collection<Job> getJobsByWorker(User user);

    /**
     * Get all current and completed jobs.
     * @return the jobs
     */
    Collection<Job> getAllJobs();

}
