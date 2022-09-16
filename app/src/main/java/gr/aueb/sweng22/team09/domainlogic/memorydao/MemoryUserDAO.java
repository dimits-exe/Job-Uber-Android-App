package gr.aueb.sweng22.team09.domainlogic.memorydao;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.User;

/**DAO implementation for class User
 *
 * @author Ioannis Gkionis
 */
public class MemoryUserDAO implements IUserDAO {

    private static final Map<String, User> users = new HashMap<>();

    /**replaces hashmap users with a new hashmap
     *
     * @param users the hashmap to replace the current dao collection
     */
    public static void setUsers(Map<String, User> users){
        MemoryUserDAO.users.clear();
        MemoryUserDAO.users.putAll(users);
    }

    public MemoryUserDAO() { }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public boolean userExists(String username) {
        return users.get(username) != null;
    }

    @Override
    public void update(User user) {
        checkExists(user);

        users.remove(user.getUsername());
        users.put(user.getUsername(), user);
    }

    @Override
    public void delete(User user) {
        checkExists(user);

        users.remove(user.getUsername());
    }

    @Override
    public String getPassword(String username) {
        checkExists(username);

        return Objects.requireNonNull(users.get(username)).getPassword();
    }

    void checkExists(String username) throws NoSuchElementException {
        if (!users.containsKey(username))
            throw new NoSuchElementException("User " + username + " does not exist");
    }

    void checkExists(User user) throws NoSuchElementException {
        checkExists(user.getUsername());
    }

    @Override
    public Collection<User> getAllUsers(){
        return new HashSet<>(users.values());
    }
}
