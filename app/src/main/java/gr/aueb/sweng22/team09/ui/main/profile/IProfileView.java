package gr.aueb.sweng22.team09.ui.main.profile;

import gr.aueb.sweng22.team09.ui.util.Field;

interface IProfileView {

    /**
     * Displays an error on a specific field in the Profile Form.
     *
     * @param field        the field on which the error occurred
     * @param errorMessage the error message
     */
    void showErrorOn(Field field, String errorMessage);

    /** Clears all error messages */
    void clearErrors();

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
