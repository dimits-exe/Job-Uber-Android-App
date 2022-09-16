package gr.aueb.sweng22.team09.ui.main.profile;

import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.ui.strategies.IEncryptStrategy;
import gr.aueb.sweng22.team09.ui.strategies.PBEEncryptStrategy;
import gr.aueb.sweng22.team09.ui.util.Field;

/**
 * A Presenter that manages the data of a Profile View.
 *
 * @author Alex Mandelias
 */
class ProfilePresenter {

    private final IEncryptStrategy encryptStrategy = new PBEEncryptStrategy();

    private final IProfileView view;
    private final IUserDAO userDao;

    public ProfilePresenter(IProfileView view, IUserDAO userDao) {
        this.view = view;
        this.userDao = userDao;
    }

    /**
     * Updates the User with the given username with the data obtained from the View
     */
    public void updateUserProfile() {
        view.clearErrors();

        User user = userDao.get(view.getUsername());
        user.setFirstName(view.getFirstName());
        user.setLastName(view.getLastName());
        user.setTelephone(view.getTelephone());

        try {
            user.setEmail(view.getEmail());
        } catch (User.EmailInvalidException e) {
            view.showErrorOn(Field.EMAIL, e.getMessage());
        }

        user.setCity(view.getCity());
        user.setAddress(view.getAddress());

        try {
            user.setPassword(view.getPassword());
        } catch (User.PasswordInvalidException e) {
            view.showErrorOn(Field.PASSWORD, e.getMessage());
        }

        user.setPassword(encrypt(user.getPassword()));

        userDao.update(user);
    }

    private String encrypt(String password) {
        return encryptStrategy.encrypt(password);
    }
}
