package gr.aueb.sweng22.team09.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import gr.aueb.sweng22.team09.R;
import gr.aueb.sweng22.team09.domainlogic.dao.IJobDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryJobDAO;
import gr.aueb.sweng22.team09.ui.job.JobFragment;
import gr.aueb.sweng22.team09.ui.jobform.JobFormActivity;

/**
 * A wrapper fragment for {@link gr.aueb.sweng22.team09.ui.job.JobFragment JobFragment},
 * which gives the user the ability to post a new job and refresh his results.
 *
 * @author Dimitris Tsirmpas
 */
public class PosterJobsFragment extends Fragment {
    public static final String ARG_USER = "USER";

    private IJobDAO jobDAO;
    private JobFragment jobFragment;
    private User user;

    public PosterJobsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user the user
     * @return A new instance of fragment PosterJobsFragment.
     */
    public static PosterJobsFragment newInstance(User user) {
        PosterJobsFragment fragment = new PosterJobsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User)getArguments().get(ARG_USER);
        }
        jobDAO = new MemoryJobDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poster_jobs, container, false);

        // add child fragment to display posted jobs
        FragmentContainerView containerView = view.findViewById(R.id.posterJobsFragment);
        FragmentManager fragMan = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();

        jobFragment = JobFragment.newInstance(
                new LinkedList<>(jobDAO.getJobsByPoster(user)), user);
        fragTransaction.replace(containerView.getId(), jobFragment, "posterJobDisplayFragment");
        fragTransaction.commit();

        Button createJobButton = view.findViewById(R.id.postJobMainButton);
        createJobButton.setOnClickListener(this::goToJobCreationForm);

        Button refreshButton = view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(this::refresh);

        return view;
    }

    /**
     * Start a {@link JobFormActivity} which will carry out the Job Creation request.
     * @param v not used
     */
    private void goToJobCreationForm(View v) {
        Intent intent = new Intent(this.getContext(), JobFormActivity.class);
        intent.putExtra(JobFormActivity.ARG_USER, user);
        startActivity(intent);
    }

    /**
     * Refresh the contents of the inner
     * {@link gr.aueb.sweng22.team09.ui.job.JobFragment JobFragment}.
     *
     * @param v not used
     */
    private void refresh(View v) {
        jobFragment.setJobs(new ArrayList<>(jobDAO.getJobsByPoster(user)));
    }
}