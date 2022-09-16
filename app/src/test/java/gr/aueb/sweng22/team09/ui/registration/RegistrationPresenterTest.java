package gr.aueb.sweng22.team09.ui.registration;

import junit.framework.TestCase;

import org.junit.Assert;

import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryUserDAO;
import gr.aueb.sweng22.team09.ui.util.Field;

/**
 * Test the logic of the registration form, as well as the error messages it produces.
 * @author Dimitris Tsirmpas
 */
public class RegistrationPresenterTest extends TestCase {
    private static final String USERNAME = "TESTUSER";
    private IUserDAO userDAO;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        userDAO = new MemoryUserDAO();
    }

    /**
     * Test if a user is saved if the registration form produces no errors.
     */
    public void testCorrectRegister() {
        IRegistrationView view = new TestView();
        RegistrationPresenter presenter = new RegistrationPresenter(view, userDAO);

        Assert.assertTrue(presenter.register());

        User newUser = userDAO.get(view.getUsername());
        Assert.assertEquals(view.getUsername(), newUser.getUsername());
        Assert.assertNotEquals(view.getPassword(), newUser.getPassword());

        Assert.assertEquals(view.getFirstName(), newUser.getFirstName());
        Assert.assertEquals(view.getLastName(), newUser.getLastName());
        Assert.assertEquals(view.getAddress(), newUser.getAddress());
        Assert.assertEquals(view.getCity(), newUser.getCity());
        Assert.assertEquals(view.getTelephone(), newUser.getTelephone());
        Assert.assertEquals(view.getEmail(), newUser.getEmail());

        userDAO.delete(newUser);

        //test if the user can be created again
        Assert.assertTrue(presenter.register());

        userDAO.delete(newUser);
    }

    /**
     * Test the error message and DAO side effects produced when an invalid email address is given.
     */
    public void testRegisterEmail() {
        TestView view = new WrongEmailView();
        RegistrationPresenter presenter = new RegistrationPresenter(view, userDAO);

        System.out.println("Email: Has user:" + userDAO.userExists(USERNAME));
        Assert.assertFalse(presenter.register());

        Assert.assertSame(Field.EMAIL, view.lastErrorField);
    }

    /**
     * Test the error messagea nd DAO side effects produced when an invalid password is given.
     */
    public void testRegisterPassword() {
        TestView view = new WrongPasswordView();
        RegistrationPresenter presenter = new RegistrationPresenter(view, userDAO);
        System.out.println("Password: Has user:" + userDAO.userExists(USERNAME));
        Assert.assertFalse(presenter.register());

        Assert.assertSame(Field.PASSWORD, view.lastErrorField);
    }

    /**
     * Test the error message and DAO side effects produced when trying to register an
     * already existing username.
     */
    public void testRegisterUsername() {
        TestView view = new TestView();
        RegistrationPresenter presenter = new RegistrationPresenter(view, userDAO);
        System.out.println("Username1: Has user:" + userDAO.userExists(USERNAME));
        Assert.assertTrue(presenter.register());

        Assert.assertThrows(IllegalStateException.class, presenter::register);

        userDAO.delete(userDAO.get(USERNAME));
    }

    private static class TestView implements IRegistrationView {
        protected Field lastErrorField;

        @Override
        public void showErrorOn(Field field, String message) {
            lastErrorField = field;
            System.out.printf("%s: %s%n", field, message);
        }

        @Override
        public void showError(String errorMessage) {
            throw new IllegalStateException("Illegal general error " + errorMessage);
        }

        @Override
        public void clearErrors() {}

        @Override
        public String getUsername() {
            return USERNAME;
        }

        @Override
        public String getPassword() {
            return "TestPass";
        }

        @Override
        public String getFirstName() {
            return "FirstName";
        }

        @Override
        public String getLastName() {
            return "LastName";
        }

        @Override
        public String getEmail() {
            return "test@email.com";
        }

        @Override
        public String getTelephone() {
            return "6900000000";
        }

        @Override
        public String getCity() {
            return "TestCity";
        }

        @Override
        public String getAddress() {
            return "TestAddress";
        }
    }

    private static class WrongEmailView extends TestView {
        @Override
        public String getEmail() {
            return "MISO_TA_NULL_MISO_TA_NULL@AAAAAAAAAAA.com";
        }
    }

    private static class WrongPasswordView extends TestView {
        @Override
        public String getPassword() {
            return "";
        }
    }
}