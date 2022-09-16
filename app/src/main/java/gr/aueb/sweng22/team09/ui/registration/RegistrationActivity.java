package gr.aueb.sweng22.team09.ui.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.NoSuchElementException;

import gr.aueb.sweng22.team09.R;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryUserDAO;
import gr.aueb.sweng22.team09.ui.main.MainActivity;
import gr.aueb.sweng22.team09.ui.strategies.DefaultErrorMessageStrategy;
import gr.aueb.sweng22.team09.ui.strategies.IErrorMessageStrategy;
import gr.aueb.sweng22.team09.ui.util.Field;
import gr.aueb.sweng22.team09.ui.util.FieldManager;

/**
 * An activity for Registering using a form with the User's details.
 *
 * @author Dimitris Tsirmpas
 */
public class RegistrationActivity extends AppCompatActivity implements IRegistrationView {

    private final IErrorMessageStrategy errorMessageStrategy = new DefaultErrorMessageStrategy(
            this, R.string.ok);

    private final FieldManager<Field> fieldManager = new FieldManager<>();

    private RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        presenter = new RegistrationPresenter(this, new MemoryUserDAO());

        fieldManager.add(Field.USERNAME, findViewById(R.id.registration_username));
        fieldManager.add(Field.PASSWORD, findViewById(R.id.registration_password));
        fieldManager.add(Field.FIRSTNAME, findViewById(R.id.registration_first_name));
        fieldManager.add(Field.LASTNAME, findViewById(R.id.registration_last_name));
        fieldManager.add(Field.EMAIL, findViewById(R.id.registration_email));
        fieldManager.add(Field.PHONE, findViewById(R.id.registration_telephone));
        fieldManager.add(Field.CITY, findViewById(R.id.registration_city));
        fieldManager.add(Field.ADDRESS, findViewById(R.id.registration_address));

        findViewById(R.id.registration_submit_button).setOnClickListener(view -> onSubmit());
    }

    private void onSubmit() {
        boolean success = presenter.register();
        if (success) {
            // note that this currently avoids the login cache
            String newUsername = getField(Field.USERNAME);
            setResult(Activity.RESULT_OK);

            // start main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.ARG_USERNAME, newUsername);
            startActivity(intent);

            finish();
        }
        setResult(Activity.RESULT_CANCELED);
    }

    @Override
    public void showError(String errorMessage) {
        errorMessageStrategy.showError(errorMessage);
    }

    @Override
    public void showErrorOn(Field field, String errorMessage) {
        fieldManager.showError(field, errorMessage);
    }

    @Override
    public void clearErrors() {
        for (Field field : Field.values())
            try {
                fieldManager.clearError(field);
            } catch (NoSuchElementException e) {
                // ignore fields that aren't set in the manager
            }
    }

    @Override
    public String getUsername() {
        return getField(Field.USERNAME);
    }

    @Override
    public String getPassword() {
        return getField(Field.PASSWORD);
    }

    @Override
    public String getFirstName() {
        return getField(Field.FIRSTNAME);
    }

    @Override
    public String getLastName() {
        return getField(Field.LASTNAME);
    }

    @Override
    public String getEmail() {
        return getField(Field.EMAIL);
    }

    @Override
    public String getTelephone() {
        return getField(Field.PHONE);
    }

    @Override
    public String getCity() {
        return getField(Field.CITY);
    }

    @Override
    public String getAddress() {
        return getField(Field.ADDRESS);
    }

    private String getField(Field field) {
        return fieldManager.get(field);
    }
}
