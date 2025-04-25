package com.example.project2.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.UserTeamData;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.databinding.ActivityAdminLandingBinding;


public class AdminLandingActivity extends AppCompatActivity {

    ActivityAdminLandingBinding binding;
    private AccountStatusCheck accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLandingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        accountManager = AccountStatusCheck.getInstance();
        binding.textView.setText(String.format("Welcome %s", accountManager.getUserName()));

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutAlertDialog();
            }
        });

        binding.addMonsterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddMonstersActivity.AddMonsterIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.editStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminEditStatsActivity.AdminEditStatsIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.startBattleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OpponentSelectActivity.OpponentSelectIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.viewCreaturesButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TeamViewerActivity.TeamViewerIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        //changed again
        //lex button
       /* binding.trainCreaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TrainCreatureActivity.(getApplicationContext());
                startActivity(intent);
            }
        });
        */

    }


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
    static Intent AdminLandingIntentFactory(Context context) {
        return new Intent(context, AdminLandingActivity.class);
    }

    private void logoutMain(){
        accountManager.logout();
        UserTeamData.getInstance().clearTeam();
        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    public static Intent adminLandingIntentFactory(Context context) {
        return new Intent(context, AdminLandingActivity.class);
    }
}