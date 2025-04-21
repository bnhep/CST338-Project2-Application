package com.example.project2.activities;

import android.content.Context;
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
        accountManager = AccountStatusCheck.getInstance(getApplicationContext());
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
                UserTeamData.getInstance().removeCreatureFromSlot(slot);
                finish();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static Intent CreatureViewAndEditorIntentFactory(Context context) {
        Intent intent = new Intent(context, CreatureViewAndEditorActivity.class);

        return intent;
    }
}