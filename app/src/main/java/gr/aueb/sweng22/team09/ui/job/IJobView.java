package gr.aueb.sweng22.team09.ui.job;

/**
 * A view that manages communication from a {@link JobRecyclerViewAdapter} to a {@link JobFragment}.
 *
 * @author Dimitris Tsirmpas
 */
interface IJobView {

    /**
     * Returns whether the User is interested in a specific Job.
     *
     * @param position the index of the job in the provided List
     * @return {@code true} if they are, {@code false} otherwise
     */
    boolean isInterested(int position);

    /**
     * Marks a Job as interesting for the User.
     *
     * @param position the index of the Job in the provided List
     */
    void markAsInterested(int position);

    /**
     * Marks a Job as not interesting for the User.
     *
     * @param position the index of the Job in the provided List
     */
    void markAsNotInterested(int position);
}
