package gr.aueb.sweng22.team09.ui.job;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.aueb.sweng22.team09.databinding.JobBinding;
import gr.aueb.sweng22.team09.domainlogic.entities.Job;

/**
 * {@link RecyclerView.Adapter} that can display a
 * {@link gr.aueb.sweng22.team09.domainlogic.entities.Job}.
 *
 * @author Alex Mandelias
 */
class JobRecyclerViewAdapter extends RecyclerView.Adapter<JobRecyclerViewAdapter.JobViewHolder> {

    private final List<Job> mValues;
    private final IJobView jobView;

    public JobRecyclerViewAdapter(IJobView jobView, List<Job> items) {
        mValues = items;
        this.jobView = jobView;
    }

    /**
     * Updates the List of items of this adapter.
     *
     * @param jobs the jobs to be displayed
     */
    @SuppressLint("NotifyDataSetChanged") // we are changing the whole list so it's efficient
    public void setItems(List<Job> jobs) {
        mValues.clear();
        mValues.addAll(jobs);
        Log.i("SEARCH", "Updated job view with " + jobs.size() + " items");
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobViewHolder(JobBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(final JobViewHolder holder, int position) {
        holder.jobTitle.setText(mValues.get(position).getTitle());
        holder.jobPoster.setText(mValues.get(position).getPoster().getUsername());
        holder.jobDescription.setText(mValues.get(position).getDescription());

        holder.jobMarkInterestedSwitch.setChecked(jobView.isInterested(position));

        holder.jobMarkInterestedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                jobView.markAsInterested(position);
            } else {
                jobView.markAsNotInterested(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        private final TextView jobTitle, jobPoster, jobDescription;
        private final Switch jobMarkInterestedSwitch;

        public JobViewHolder(JobBinding binding) {
            super(binding.getRoot());
            jobTitle = binding.jobTitle;
            jobPoster = binding.jobPoster;
            jobDescription = binding.jobDescription;
            jobMarkInterestedSwitch = binding.jobMarkInterestedSwitch;
        }
    }
}