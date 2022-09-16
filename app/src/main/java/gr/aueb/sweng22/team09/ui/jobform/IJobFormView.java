package gr.aueb.sweng22.team09.ui.jobform;

/**
 * An adapter that facilitates communication from a {@link JobFormActivity} to a
 * {@link JobFormPresenter}.
 *
 * @author Dimitris Tsirmpas
 */
interface IJobFormView {

    /**
     * Displays a error message to the end-user.
     *
     * @param errorMessage the error message
     */
    void showError(String errorMessage);

    /**
     * Displays an error to a specific field in the form.
     *
     * @param field        the field on which the error occured
     * @param errorMessage the error's cause
     */
    void showErrorOn(JobFormField field, String errorMessage);

    /**
     * Clear all previous errors on the form's fields.
     */
    void clearErrors();

    /**
     * Get the title provided in the job creation form.
     */
    String getTitle();

    /**
     * Get the description provided in the job creation form.
     */
    String getDescription();

    /**
     * Get the starting date provided in the job creation form.
     */
    String getStartingDate();

    /**
     * Get the deadline provided in the job creation form.
     */
    String getDeadline();

    /**
     * Get the compensation provided in the job creation form.
     */
    String getCompensation();

}
