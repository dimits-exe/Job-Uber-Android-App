package gr.aueb.sweng22.team09.domainlogic.memorydao;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import gr.aueb.sweng22.team09.domainlogic.dao.IJobDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.User;

/**DAO implementation for class Job
 *
 * @author Ioannis Gkionis
 */
public class MemoryJobDAO implements IJobDAO {

    private static final Map<Long, Job> jobs = new HashMap<>();

    /**replaces hashmap jobs with a new hashmap
     *
     * @param jobs the hashmap to replace the current dao collection
     */
    public static void setJobs(Map<Long, Job> jobs){
        MemoryJobDAO.jobs.clear();
        MemoryJobDAO.jobs.putAll(jobs);
    }

    public MemoryJobDAO() { }

    @Override
    public Job get(long id) {
        checkExists(id);

        return jobs.get(id);
    }

    @Override
    public void delete(Job job){
        checkExists(job);
        jobs.remove(job.getId());
    }

    @Override
    public void saveJob(Job job) { jobs.put(job.getId(), job); }

    @Override
    public Collection<Job> getJobsByPoster(User user) {
        return jobs.values().stream()
                .filter(job -> job.getPoster().equals(user))
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Collection<Job> getJobsByWorker(User user) {
        return jobs.values().stream()
                .filter(Job::isWorkerAssigned)
                .filter(job -> job.getAssignedWorker().equals(user))
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Collection<Job> getAllJobs() {
        return new HashSet<>(jobs.values());
    }

    void checkExists(Long id) throws NoSuchElementException {
        if (!jobs.containsKey(id))
            throw new NoSuchElementException("job id " + id + " does not exist");
    }

    void checkExists(Job job) throws NoSuchElementException{
        checkExists(job.getId());
    }

}
