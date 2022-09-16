package gr.aueb.sweng22.team09.ui.main.search;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Collection;
import java.util.LinkedList;

import gr.aueb.sweng22.team09.R;
import gr.aueb.sweng22.team09.domainlogic.dao.IJobDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.ui.job.JobFragment;
import gr.aueb.sweng22.team09.ui.util.FieldManager;

/**
 * A Fragment which allows the user to define search constraints for available Jobs and display all
 * Jobs that match said constraints.
 *
 * @author Dimitris Tsirmpas
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class SearchFragment extends Fragment implements ISearchResultsView {
    private static final String ARG_USER = "USER";
    private final FieldManager<SearchFormField> fieldManager = new FieldManager<>();

    private JobFragment jobFragment;
    private SearchPresenter presenter;


    private User user;
    private IJobDAO jobDAO;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user   the current logged-in user
     * @param jobDAO the IJobDAO object to use
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance(User user, IJobDAO jobDAO) {
        SearchFragment fragment = new SearchFragment();
        fragment.jobDAO = jobDAO;

        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null)
            throw new IllegalStateException("No arguments found for this Profile Fragment");

        user = (User) getArguments().getSerializable(ARG_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View inputFragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        fieldManager.add(SearchFormField.EARLIEST_DATE, inputFragmentView.findViewById(R.id.startingDateInput));
        fieldManager.add(SearchFormField.DEADLINE, inputFragmentView.findViewById(R.id.deadlineInput));
        fieldManager.add(SearchFormField.COMPENSATION, inputFragmentView.findViewById(R.id.compensationInput));

        // add child fragment to display search results
        FragmentContainerView containerView = inputFragmentView.findViewById(R.id.fragmentContainerView);
        FragmentManager fragMan = getParentFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();

        JobFragment jobDisplayFragment = JobFragment.newInstance(new LinkedList<>(), user);
        fragTransaction.add(containerView.getId(), jobDisplayFragment, "jobDisplayFragment");
        fragTransaction.commit();

        jobFragment = jobDisplayFragment;
        presenter = new SearchPresenter(this, jobDAO);

        inputFragmentView.findViewById(R.id.searchButton).setOnClickListener(v -> onSearch());
        return inputFragmentView;
    }

    /**
     * Initiates a search using the parameters provided by the user and transition to a job list
     * display fragment.
     */
    private void onSearch() {
        String earliestDateString = fieldManager.get(SearchFormField.EARLIEST_DATE);
        String deadlineString = fieldManager.get(SearchFormField.DEADLINE);
        String compensationString = fieldManager.get(SearchFormField.COMPENSATION);

        Collection<Job> jobs = presenter.searchJobs(earliestDateString, deadlineString, compensationString);

        jobFragment.setJobs(new LinkedList<>(jobs));
    }

    @Override
    public void showErrorOn(SearchFormField field, String errorMessage) {
        fieldManager.showError(field, errorMessage);
    }

}
