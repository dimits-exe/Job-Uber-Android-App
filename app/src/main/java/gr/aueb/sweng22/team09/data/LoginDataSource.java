package gr.aueb.sweng22.team09.data;

import gr.aueb.sweng22.team09.data.model.LoggedInUser;
import gr.aueb.sweng22.team09.ui.login.LoginPresenter;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            LoggedInUser loggedInUser = new LoggedInUser(username, username);
            LoginPresenter presenter = new LoginPresenter();

            if(!presenter.userExists(username)){
                throw new RuntimeException(String.format("Username %s does not exist.", username));
            }

            if(!presenter.checkPassword(username, password)) {
                throw new RuntimeException("Provided password doesn't match up.");
            }

            return new Result.Success<>(loggedInUser);
        } catch (Exception e) {
            return new Result.Error(e);
        }
    }

    public void logout() {
        // do nothing
    }
}