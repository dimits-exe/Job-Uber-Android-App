package gr.aueb.sweng22.team09.ui.job;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import gr.aueb.sweng22.team09.R;
import gr.aueb.sweng22.team09.domainlogic.entities.Job;
import gr.aueb.sweng22.team09.domainlogic.entities.User;

/**
 * A Fragment that displays a list of Jobs.
 *
 * @author Alex Mandelias
 */
public class JobFragment extends Fragment implements IJobView {

    private static final String ARG_USER = "USER";
    private static final String ARG_JOB_LIST = "JOB_LIST";

    private User user;
    private List<Job> jobs;
    private JobRecyclerViewAdapter recyclerViewAdapter;

    private boolean showingUserJobs = false;
    private RecyclerView rv;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param jobs the jobs that resulted from a Search Fragment or {@code null} if the interested
     *             jobs of the User should be displayed instead
     * @param user the current logged-in User
     * @return A new instance of fragment SearchFragment.
     */
    public static JobFragment newInstance(Collection<Job> jobs, User user) {
        JobFragment fragment = new JobFragment();

        if (jobs == null) {
            fragment.showingUserJobs = true;
            jobs = new LinkedList<>();
        }

        Bundle args = new Bundle();
        LinkedList<Job> jobList = new LinkedList<>(jobs);
        args.putSerializable(ARG_JOB_LIST, jobList);
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null)
            throw new IllegalStateException("No arguments found for this Job Fragment");

        user = (User) getArguments().getSerializable(ARG_USER);
        jobs = (List<Job>) getArguments().getSerializable(ARG_JOB_LIST);

        recyclerViewAdapter = new JobRecyclerViewAdapter(this, jobs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_job_list, container, false);

        // display button only when showing the User's jobs
        // and not the jobs that resulted from a search fragment
        Button button = view.findViewById(R.id.job_list_button);
        button.setAlpha(showingUserJobs ? 1.0f : 0.0f);
        button.setOnClickListener(e -> {
            if (showingUserJobs)
                setJobs(new LinkedList<>(user.getInterestingJobs()));
        });

        rv = view.findViewById(R.id.job_list_recycler_view);
        rv.setAdapter(recyclerViewAdapter);

        return view;
    }

    /**
     * Updates the list of Jobs for this Fragment.
     *
     * @param jobs the jobs to be displayed
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;

        recyclerViewAdapter.setItems(jobs);
        rv.post(() -> recyclerViewAdapter.notifyDataSetChanged());
    }

    @Override
    public boolean isInterested(int position) {
        final Job target = jobs.get(position);
        return user.getInterestingJobs().contains(target);
    }

    @Override
    public void markAsInterested(int position) {
        final Job target = jobs.get(position);
        user.addJobInterest(target);
    }

    @Override
    public void markAsNotInterested(int position) {
        final Job target = jobs.get(position);
        user.removeJobInterest(target);
    }
}