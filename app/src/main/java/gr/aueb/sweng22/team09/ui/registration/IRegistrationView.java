package gr.aueb.sweng22.team09.ui.registration;

import gr.aueb.sweng22.team09.ui.util.Field;

/**
 * A view that facilitates communication from a {@link RegistrationActivity} to a
 * {@link RegistrationPresenter}.
 *
 * @author Dimitris Tsirmpas
 */
interface IRegistrationView {

    /**
     * Displays a error message to the end-user.
     *
     * @param errorMessage the error message
     */
    void showError(String errorMessage);

    /** Clears all error messages */
    void clearErrors();

    /**
     * Displays an error on a specific field in the Registration Form.
     *
     * @param field        the field on which the error occurred
     * @param errorMessage the error message
     */
    void showErrorOn(Field field, String errorMessage);

    /**
     * Returns the Username of this View.
     *
     * @return the Username
     */
    String getUsername();

    /**
     * Returns the Password of this View.
     *
     * @return the Password
     */
    String getPassword();

    /**
     * Returns the FirstName of this View.
     *
     * @return the FirstName
     */
    String getFirstName();

    /**
     * Returns the LastName of this View.
     *
     * @return the LastName
     */
    String getLastName();

    /**
     * Returns the Email of this View.
     *
     * @return the Email
     */
    String getEmail();

    /**
     * Returns the Telephone of this View.
     *
     * @return the Telephone
     */
    String getTelephone();

    /**
     * Returns the City of this View.
     *
     * @return the City
     */
    String getCity();

    /**
     * Returns the Address of this View.
     *
     * @return the Address
     */
    String getAddress();
}
