package gr.aueb.sweng22.team09.ui.login;

import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryUserDAO;
import gr.aueb.sweng22.team09.ui.strategies.IEncryptStrategy;
import gr.aueb.sweng22.team09.ui.strategies.PBEEncryptStrategy;

/**
 * A presenter for logging in.
 *
 * @author Dimitris Tsirmpas
 */
public class LoginPresenter {
    private final IUserDAO userDAO;
    private final IEncryptStrategy encryptStrategy = new PBEEncryptStrategy();

    public LoginPresenter() {
        userDAO = new MemoryUserDAO();
    }

    /**
     * Checks whether the user exists.
     *
     * @param username the user's name
     * @return true if the user exists, false otherwise
     */
    public boolean userExists(String username) {
        return userDAO.userExists(username);
    }

    /**
     * Checks the validity of the user's credentials.
     *
     * @param username the user's name
     * @param expected the provided password in plain-text form
     * @return true if the credentials match up, false otherwise
     */
    public boolean checkPassword(String username, String expected) {
        // note that this WILL crash if another encryption method was used to encrypt the password
        return encryptStrategy.encrypt(expected).equals(userDAO.getPassword(username));
    }
}
