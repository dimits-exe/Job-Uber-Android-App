package gr.aueb.sweng22.team09.domainlogic.dao;

import java.util.Collection;

import gr.aueb.sweng22.team09.domainlogic.entities.User;

/**
 * An interface for Data Access Objects concerning the User entity class.
 */
public interface IUserDAO {

    /**
     * Commits the user to the persistent storage.
     * @param user the user to be saved
     */
    void save(User user);

    /**
     * Get the user with the unique username from the persistent storage.
     * @param username the username of the desired user
     * @return the user
     */
    User get(String username);

    /**
     * Check if the user exists
     * @param username the user's name
     * @return true if the user exists, false otherwise
     */
    boolean userExists(String username);

    /**
     * Updates the user's record in the persistent storage.
     * @param user the user to be updated
     */
    void update(User user);

    /**
     * Permanently deletes the user from the persistent storage.
     * @param user the user to be deleted
     */
    void delete(User user);

    /**
     * Get the encrypted password of the user in the persistent storage.
     * @param username the username of the to-be-authenticated
     * @return the encrypted password
     */
    String getPassword(String username);

    /**Gets all users from dao
     *
     * @return collection of all users
     */
    Collection<User> getAllUsers();
}
