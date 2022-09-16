package gr.aueb.sweng22.team09.ui.strategies;


/**
 * A strategy that shows an error message to the user.
 *
 * @author Dimitris Tsirmpas
 */
public interface IErrorMessageStrategy {

    /**
     * Displays an error message.
     *
     * @param errorMessage the error message
     */
    void showError(String errorMessage);
}
