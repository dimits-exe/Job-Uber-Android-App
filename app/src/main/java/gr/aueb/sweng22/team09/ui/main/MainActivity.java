package gr.aueb.sweng22.team09.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import gr.aueb.sweng22.team09.R;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryUserDAO;

/**
 * The activity that contains information about the user's profile and most of the
 * app's functionality.
 *
 * @author Dimitris Tsirmpas
 */
public class MainActivity extends AppCompatActivity {

    public static final String ARG_USERNAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra(ARG_USERNAME);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        User user = new MemoryUserDAO().get(username);
        TabbedFragment fragment = TabbedFragment.newInstance(user);

        fragmentTransaction.add(R.id.tabbed_fragment_container, fragment, "Tabbed Fragment");
        fragmentTransaction.commit();
    }
}
