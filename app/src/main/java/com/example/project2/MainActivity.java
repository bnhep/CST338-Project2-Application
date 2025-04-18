package com.example.project2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityMainBinding;

/**
 * Activity Menu where user will choose the options to battle, view monsters, view recent battles
 * //TODO implement the other activity layouts, A logout button should be here
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Application_LOG";
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2.MAIN_ACTIVITY_USER_ID";
    private ActivityMainBinding binding;
    private ApplicationRepository appRepository; //Performs the queries for the database

    private AccountStatusCheck accountManager;

    private static final int LOGGED_OUT_STATUS = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appRepository = ApplicationRepository.getRepository(getApplication());
        accountManager = AccountStatusCheck.getInstance(getApplication());
        //If we want it to say "Welcome [whatever the username]"
        /*
        LiveData<User> userObserver = appRepository.getUsernameByID(accountManager.getUserID());
        userObserver.observe(this, new Observer<>() {
            @Override
            public void onChanged(User user) {
                binding.welcomeFighterLoginTextView.setText("Welcome " + user.getUsername());
                userObserver.removeObserver(this);
            }
        });
         */
        int temporaryStatusChecker = accountManager.getUserID();

        if(temporaryStatusChecker == LOGGED_OUT_STATUS) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        //button functionality for Start Battle
        //TODO should have factory method to swap to StartBattle Activity
        binding.startBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = OpponentSelectActivity.OpponentSelectIntentFactory(getApplicationContext());
                startActivity(intent);

            }
        });

        //button functionality for View Monsters
        //TODO should have factory method to swap to View Monsters(BASICALLY A STUB BUTTON)
        binding.viewMonstersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = TeamBuilderActivity.TeamBuilderIntentFactory(getApplicationContext());
                startActivity(intent);

                //Testing button please remove later
                //Toast.makeText(MainActivity.this, "View Monster Button works", Toast.LENGTH_SHORT).show();
            }
        });

        //button functionality for View recent battles
        //TODO should have factory method to swap to Recent Battles(BASICALLY A STUB BUTTON)
        binding.viewRecentBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Testing button please remove later
                Toast.makeText(MainActivity.this, "View Recent Button works", Toast.LENGTH_SHORT).show();
            }
        });


        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutAlertDialog();
            }
        });

    }



    //MainIntentFactory that takes in a
    static Intent MainIntentFactory(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    /**
     * placeholder dialog may create a custom dialog
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

    private void logoutMain(){
        accountManager.logout();
        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
    }

}