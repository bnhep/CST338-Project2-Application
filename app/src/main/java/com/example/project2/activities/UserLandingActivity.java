package com.example.project2.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.project2.R;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.UserTeamData;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.databinding.ActivityUserLandingBinding;


/**
 * Activity Menu where user will choose the options to battle, view creatures, train their creatures
 * @author Brandon Nhep
 * Date: 4/21/25
 */
public class UserLandingActivity extends AppCompatActivity {
    public static final String TAG = "Application_LOG";

    private ActivityUserLandingBinding binding;
    private ApplicationRepository appRepository; //Performs the queries for the database

    private AccountStatusCheck accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appRepository = ApplicationRepository.getInstance();
        accountManager = AccountStatusCheck.getInstance();
        //Changes user name text view in the corner to display current logged in user
        binding.usernameDisplayTextView.setText(accountManager.getUserName());
        //If we want it to say "Welcome [whatever the username]"
        binding.welcomeFighterLoginTextView.setText("Welcome " + accountManager.getUserName());

        /*
            button for the Start Battle activity
         */
        binding.startBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OpponentSelectActivity
                        .OpponentSelectIntentFactory(getApplicationContext());
                startActivity(intent);

            }
        });

        /*
            button for the view monsters activity
         */
        binding.viewMonstersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = TeamViewerActivity.TeamViewerIntentFactory(getApplicationContext());
                startActivity(intent);


            }
        });

        /*
            button for the train creatures activity
         */
        Button trainButton = findViewById(R.id.trainCreaturesButton);
        trainButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserLandingActivity.this,
                    TrainTeamViewerActivity.class);
            startActivity(intent);
        });

        /*
            button for the logout, calls logOutAlertDialog() to prompt user if they want to logout
            or continue to stay logged in
         */
        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutAlertDialog();
            }
        });

    }

    /**
     * Creates an alert dialog to prompt the user to stay logged in or to log out and quit the
     * application and go back to the login screen. Calls the logoutMain() method.
     */
    private void logOutAlertDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setTitle("Confirm logout");
        alertBuilder.setMessage("Are you sure you want to log out?");

        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutMain();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.show();
    }

    /**
     * Allows the user to logout, calls the accountManager object method logout to clear the
     * shared preferences file associated with the user. Clears the user's team data. Swaps to
     * the login screen
     */
    private void logoutMain(){
        accountManager.logout();
        UserTeamData.getInstance().clearTeam();
        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
        finish();
    }

    /**
     * This is the intent factory method for this activity. If the method is called and the return
     * value is stored in an intent. This will be passed to a start activity method in order to
     * swap to this activity.
     * @param context
     * @return new instantiated intent
     */
    public static Intent UserLandingPageIntentFactory(Context context){
        Intent intent = new Intent(context, UserLandingActivity.class);
        return intent;
    }
}