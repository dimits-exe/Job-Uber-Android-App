package gr.aueb.sweng22.team09.ui.strategies;

/**
 * A strategy that encrypts a user's password.
 * <p>
 * Note that changing the strategy used requires a full rewrite of all the encrypted data in
 * persistent storage.
 * </p>
 *
 * @author Dimitris Tsirmpas
 */
public interface IEncryptStrategy {

    /**
     * Securely encrypts a password.
     *
     * @param password the password
     * @return the encrypted password
     */
    String encrypt(String password);
}
