package gr.aueb.sweng22.team09.ui.jobform;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import gr.aueb.sweng22.team09.R;
import gr.aueb.sweng22.team09.domainlogic.entities.User;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryJobDAO;
import gr.aueb.sweng22.team09.ui.main.MainActivity;
import gr.aueb.sweng22.team09.ui.strategies.DefaultErrorMessageStrategy;
import gr.aueb.sweng22.team09.ui.strategies.IErrorMessageStrategy;
import gr.aueb.sweng22.team09.ui.util.FieldManager;

/**
 * An activity providing the user with a form with which he can create a new
 * {@link gr.aueb.sweng22.team09.domainlogic.entities.Job Job}
 *
 * @author Dimitris Tsirmpas
 */
public class JobFormActivity extends AppCompatActivity {
    public static final String ARG_USER = "USER";

    private final FieldManager<JobFormField> fieldManager = new FieldManager<>();

    private JobFormPresenter presenter;
    private User user;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_form);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(ARG_USER);

        fieldManager.add(JobFormField.TITLE, (TextInputLayout) findViewById(R.id.jobTitleInput));
        fieldManager.add(JobFormField.DESCRIPTION, (TextInputLayout) findViewById(R.id.jobDescriptionInput));
        fieldManager.add(JobFormField.STARTING_DATE, (TextInputLayout) findViewById(R.id.jobStartingDateInput));
        fieldManager.add(JobFormField.DEADLINE, (TextInputLayout) findViewById(R.id.jobDeadlineInput));
        fieldManager.add(JobFormField.COMPENSATION, (TextInputLayout) findViewById(R.id.jobCompensationInput));
        presenter = new JobFormPresenter(new JobFormView(), user, new MemoryJobDAO());

        Button submitButton = findViewById(R.id.createJobSumbitButton);
        submitButton.setOnClickListener(this::createJob);
    }

    /**
     * Attempt to create a job, and if successful, return to Main Activity
     * @param v not used
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createJob(View v) {
        boolean success = presenter.registerJob();

        if(success){
            // start main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.ARG_USERNAME, user.getUsername());
            startActivity(intent);

            finish();
        }
    }

    private final class JobFormView implements IJobFormView {
        private final IErrorMessageStrategy errorMessageStrategy =
                new DefaultErrorMessageStrategy(JobFormActivity.this, R.string.ok);

        @Override
        public void showError(String errorMessage) {
            errorMessageStrategy.showError(errorMessage);
        }

        @Override
        public void showErrorOn(JobFormField field, String errorMessage) {
            fieldManager.showError(field, errorMessage);
        }

        @Override
        public void clearErrors() {
            for(JobFormField field : JobFormField.values())
                fieldManager.clearError(field);
        }

        @Override
        public String getTitle() {
            return fieldManager.get(JobFormField.TITLE);
        }

        @Override
        public String getDescription() {
            return fieldManager.get(JobFormField.DESCRIPTION);
        }

        @Override
        public String getStartingDate() {
            return fieldManager.get(JobFormField.STARTING_DATE);
        }

        @Override
        public String getDeadline() {
            return fieldManager.get(JobFormField.DEADLINE);
        }

        @Override
        public String getCompensation() {
            return fieldManager.get(JobFormField.COMPENSATION);
        }
    }


}