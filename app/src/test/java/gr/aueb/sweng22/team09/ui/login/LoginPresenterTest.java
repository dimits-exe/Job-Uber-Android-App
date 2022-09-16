package gr.aueb.sweng22.team09.ui.login;

import junit.framework.TestCase;

import org.junit.Assert;

import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryUserDAO;
import gr.aueb.sweng22.team09.ui.strategies.PBEEncryptStrategy;

/**
 * Test the logic of the login form.
 * @author Dimitris Tsirmpas
 */
public class LoginPresenterTest extends TestCase {
    private static final String USERNAME = "existing_user";
    private static final String PASSWORD = "2222222222";
    private static final User USER = new User.Builder(USERNAME).password(PASSWORD)
            .email("mandelias@denxerotikano.com").buildNew();

    private LoginPresenter presenter;
    private IUserDAO userDAO;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new LoginPresenter();
        userDAO = new MemoryUserDAO();
        userDAO.save(USER);
    }

    @Override
    public void tearDown(){
        userDAO.delete(USER);
    }

    /**
     * Test if the presenter can correctly recognize existing users.
     */
    public void testUserExists() {
        assertFalse(presenter.userExists("NON-EXISTENT-USER"));
        assertTrue(presenter.userExists("existing_user"));
    }

    /**
     * Test if the presenter correctly compares the encrypted hashes of the passwords.
     */
    public void testCheckPassword() {
        Assert.assertTrue(presenter.checkPassword(USERNAME, PASSWORD));
    }
}