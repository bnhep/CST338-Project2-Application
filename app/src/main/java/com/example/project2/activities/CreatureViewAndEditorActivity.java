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

        //set UI with creature information
        setUiStats();

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

    private void setUiStats() {
        /**
         *  after passing in the slot# from TeamViewer get reference
         *  to UserData to get the hashmap userTeam and set creature stats
         */

        //name
        binding.creatureNameTextView.setText(UserTeamData.getInstance().getCreatureAtSlot(slot).getName());
        //type
        binding.typeTextView.setText(UserTeamData.getInstance().getCreatureAtSlot(slot).getType());
        //elements
        binding.elementTextView.setText(UserTeamData.getInstance().getCreatureAtSlot(slot).getElements().toString());
        //level
        binding.levelTextview.setText("Level: " + UserTeamData.getInstance().getCreatureAtSlot(slot).getLevel());
        //current XP
        binding.curXpTextView.setText("XP: " + UserTeamData.getInstance().getCreatureAtSlot(slot).getCurExperiencePoints() +
                "/" +UserTeamData.getInstance().getCreatureAtSlot(slot).getExperienceNeededToLevel());
        //health
        binding.healthStatTextView.setText("Health: " + UserTeamData.getInstance().getCreatureAtSlot(slot).getHealthStat());
        //attack
        binding.attackStatTextView.setText("Attack: " + UserTeamData.getInstance().getCreatureAtSlot(slot).getAttackStat());
        //defense
        binding.defenseStatTextView.setText("Defense: " + UserTeamData.getInstance().getCreatureAtSlot(slot).getDefenseStat());
        //speed
        binding.speedStatTextView.setText("Speed: " + UserTeamData.getInstance().getCreatureAtSlot(slot).getSpeedStat());

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