package gr.aueb.sweng22.team09.ui.registration;

import android.util.Log;

import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.ui.util.Field;

/**
 * A Presenter that manages the data of a Registration View.
 *
 * @author Dimitris Tsirmpas
 */
class RegistrationPresenter {

    private final IRegistrationView view;

    private final IUserDAO userDAO;

    public RegistrationPresenter(IRegistrationView view, IUserDAO userDAO) {
        this.view = view;
        this.userDAO = userDAO;
    }

    /**
     * Extracts data from the Registration View and attempts to create a new User.
     *
     * @return {@code true} if the registration was successful, {@code false} otherwise
     */
    public boolean register() {
        view.clearErrors();

        String username = view.getUsername();
        String password = view.getPassword();
        String firstName = view.getFirstName();
        String lastName = view.getLastName();
        String email = view.getEmail();
        String telephone = view.getTelephone();
        String city = view.getCity();
        String address = view.getAddress();

        if (userDAO.userExists(username)) {
            view.showError("Username " + username + " is taken");
            return false;
        }

        try {
            User user = new User.Builder(username).password(password).firstName(firstName).
                    lastName(lastName).email(email).telephone(telephone).city(city).address(address).
                    buildNew();

            userDAO.save(user);
            return true;

        } catch (User.PasswordInvalidException e1) {
            view.showErrorOn(Field.PASSWORD, e1.getMessage());
        } catch (User.EmailInvalidException e2) {
            view.showErrorOn(Field.EMAIL, e2.getMessage());
        } catch (RuntimeException e) {
            view.showError("An unexpected error has occurred.");
            Log.wtf("Registration Error", e); // I love this method
        }

        return false;
    }
}
