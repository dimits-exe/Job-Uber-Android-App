package gr.aueb.sweng22.team09.ui.main;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import gr.aueb.sweng22.team09.domainlogic.dao.IJobDAO;
import gr.aueb.sweng22.team09.domainlogic.dao.ITransactionDAO;
import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryJobDAO;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryTransactionDAO;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryUserDAO;
import gr.aueb.sweng22.team09.ui.job.JobFragment;
import gr.aueb.sweng22.team09.ui.main.profile.ProfileFragment;
import gr.aueb.sweng22.team09.ui.main.search.SearchFragment;
import gr.aueb.sweng22.team09.ui.main.stats.StatFragment;

/**
 * A fragment state adapter that manages the current shown tab.
 *
 * @author Dimitris Tsirmpas
 */
class MainAdapter extends FragmentStateAdapter {

    private final IUserDAO userDAO = new MemoryUserDAO();
    private final IJobDAO jobDAO = new MemoryJobDAO();
    private final ITransactionDAO transactionDAO = new MemoryTransactionDAO();

    private final User user;

    public MainAdapter(@NonNull Fragment fragment, User user) {
        super(fragment);
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ProfileFragment.newInstance(user, userDAO);
            case 1:
                return SearchFragment.newInstance(user, jobDAO);
            case 2:
                return JobFragment.newInstance(null, user); // null indicates the user's jobs
            case 3:
                return PosterJobsFragment.newInstance(user);
            case 4:
                return StatFragment.newInstance(userDAO, transactionDAO);
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
