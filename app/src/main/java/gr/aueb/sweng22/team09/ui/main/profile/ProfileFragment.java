package gr.aueb.sweng22.team09.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.NoSuchElementException;

import gr.aueb.sweng22.team09.R;
import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.ui.util.Field;
import gr.aueb.sweng22.team09.ui.util.FieldManager;

/**
 * A Fragment that displays a Profile of a User.
 *
 * @author Alex Mandelias
 */
public class ProfileFragment extends Fragment implements IProfileView {

    private static final String ARG_USER = "USER";

    private final FieldManager<Field> fieldManager = new FieldManager<>();

    private IUserDAO userDao;
    private ProfilePresenter presenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfileFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user    the current logged-in user
     * @param userDao the IUserDAO object to use
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance(User user, IUserDAO userDao) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.userDao = userDao;

        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ProfilePresenter(this, userDao);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() == null)
            throw new IllegalStateException("No arguments found for this Profile Fragment");

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        view.findViewById(R.id.profile_update_button).setOnClickListener(
                v -> presenter.updateUserProfile());

        fieldManager.add(Field.PASSWORD, view.findViewById(R.id.profile_password));
        fieldManager.add(Field.FIRSTNAME, view.findViewById(R.id.profile_firstname));
        fieldManager.add(Field.LASTNAME, view.findViewById(R.id.profile_lastname));
        fieldManager.add(Field.EMAIL, view.findViewById(R.id.profile_email));
        fieldManager.add(Field.PHONE, view.findViewById(R.id.profile_phone));
        fieldManager.add(Field.CITY, view.findViewById(R.id.profile_city));
        fieldManager.add(Field.ADDRESS, view.findViewById(R.id.profile_address));

        User user = (User) getArguments().getSerializable(ARG_USER);
        ((TextView) view.findViewById(R.id.profile_username)).setText(user.getUsername());
        fieldManager.set(Field.PASSWORD, user.getPassword());
        fieldManager.set(Field.FIRSTNAME, user.getFirstName());
        fieldManager.set(Field.LASTNAME, user.getLastName());
        ((TextView) view.findViewById(R.id.profile_wallet_amount)).setText(user.getWallet().toString());
        fieldManager.set(Field.EMAIL, user.getEmail());
        fieldManager.set(Field.PHONE, user.getTelephone());
        fieldManager.set(Field.CITY, user.getCity());
        fieldManager.set(Field.ADDRESS, user.getAddress());

        return view;
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
    public String getTelephone() {
        return getField(Field.PHONE);
    }

    @Override
    public String getEmail() {
        return getField(Field.EMAIL);
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
