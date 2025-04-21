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
import com.example.project2.databinding.ActivityCreatureViewAndEditorBinding;

public class CreatureViewAndEditorActivity extends AppCompatActivity {

    ActivityCreatureViewAndEditorBinding binding;
    private int slot;
    private AccountStatusCheck accountManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatureViewAndEditorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        accountManager = AccountStatusCheck.getInstance();

        binding.usernameDisplayTextView.setText(accountManager.getUserName());
        //store the passed in slot number
        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }

        //after passing in the slot# from TeamViewer get reference to UserData to get the hashmap userTeam and get the creature from that slot#'s name
        binding.creatureNameTextView.setText(UserTeamData.getInstance().getCreatureAtSlot(slot).getName());

        binding.deleteCreatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCreatureAlertDialog();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void removeCreatureAlertDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setTitle("Confirm logout");
        alertBuilder.setMessage("Are you sure you want to remove " + UserTeamData.getInstance().getUserTeam().get(slot).getName() + "?");

        alertBuilder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserTeamData.getInstance().removeCreatureFromSlot(slot);
                finish();
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

    public static Intent CreatureViewAndEditorIntentFactory(Context context) {
        Intent intent = new Intent(context, CreatureViewAndEditorActivity.class);

        return intent;
    }
}