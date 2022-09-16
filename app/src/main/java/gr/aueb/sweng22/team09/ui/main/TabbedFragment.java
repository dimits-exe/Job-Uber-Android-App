package gr.aueb.sweng22.team09.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import gr.aueb.sweng22.team09.R;
import gr.aueb.sweng22.team09.domainlogic.entities.User;

/**
 * A Fragment that manages tabbed elements on the main screen.
 *
 * @author Dimitris Tsirmpas
 */
public class TabbedFragment extends Fragment {

    private static final String ARG_USER = "USER";

    private MainAdapter mainAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TabbedFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user the current logged-in user
     * @return A new instance of fragment TabbedFragment.
     */
    public static TabbedFragment newInstance(User user) {
        TabbedFragment fragment = new TabbedFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null)
            throw new IllegalStateException("No arguments found for this Tabbed Fragment");

        mainAdapter = new MainAdapter(this,
                (User) getArguments().getSerializable(ARG_USER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabbed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager2 viewPager = view.findViewById(R.id.pager);

        mainAdapter = new MainAdapter(this,
                (User) getArguments().getSerializable(ARG_USER));

        viewPager.setAdapter(mainAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(getResources().getString(R.string.tab_text_1));
                    break;
                case 1:
                    tab.setText(getResources().getString(R.string.tab_text_2));
                    break;
                case 2:
                    tab.setText(getResources().getString(R.string.tab_text_3));
                    break;
                case 3:
                    tab.setText(R.string.tab_text_4);
                    break;
                case 4:
                    tab.setText(R.string.tab_text_5);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected tab: " + position);
            }
        }).attach();
    }
}
