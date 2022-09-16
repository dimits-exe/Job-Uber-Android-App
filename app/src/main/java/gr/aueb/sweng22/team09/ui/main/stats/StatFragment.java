package gr.aueb.sweng22.team09.ui.main.stats;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import gr.aueb.sweng22.team09.R;
import gr.aueb.sweng22.team09.domainlogic.dao.ITransactionDAO;
import gr.aueb.sweng22.team09.domainlogic.dao.IUserDAO;

/**
 * A Fragment that displays Statistics.
 *
 * @author Ioannis Gkionis
 */
public class StatFragment extends Fragment {

    private StatPresenter presenter;
    private IUserDAO userDAO;
    private ITransactionDAO transactionDAO;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StatFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userDAO        the IUserDAO object to use
     * @param transactionDAO the ITransactionDAO object to use
     * @return A new instance of fragment StatFragment.
     */
    public static StatFragment newInstance(IUserDAO userDAO, ITransactionDAO transactionDAO) {
        StatFragment fragment = new StatFragment();
        fragment.userDAO = userDAO;
        fragment.transactionDAO = transactionDAO;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StatPresenter(userDAO, transactionDAO);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stat, container, false);

        ((TextView) view.findViewById(R.id.monthlyVOTView)).setText(presenter.calculateMonthlyVOT());
        ((TextView) view.findViewById(R.id.totalVOTView)).setText(presenter.calculateTotalVOT());
        ((TextView) view.findViewById(R.id.userAmountView)).setText(presenter.calculateAmountOfUsers());

        return view;
    }
}
