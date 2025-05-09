package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.UserTeamData;
import com.example.project2.creatures.*;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.databinding.ActivityTeamViewerBinding;
import com.example.project2.databinding.ActivityTrainTeamViewerBinding;

import java.util.Map;

/**
 * Activity that allows for selection of a creature from one's team to be used for training
 * @author Alexis Wogoman, version of Austin's original team viewer
 * @date 24 April 2025
 */
public class TrainTeamViewerActivity extends AppCompatActivity {

    ActivityTrainTeamViewerBinding binding;
    private AccountStatusCheck accountManager;
    private String userId;

    /**
     * Creation method to set up content view, team display, and button options
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrainTeamViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View view = binding.getRoot();
        setContentView(view);

        accountManager = AccountStatusCheck.getInstance();
        binding.usernameDisplayTextView.setText(accountManager.getUserName());
        updateTeamSlotButtons();

        userId = String.valueOf(accountManager.getUserID());

        binding.teamSlotOneButton.setOnClickListener(v -> contextualButtonChoice(1));
        binding.teamSlotTwoButton.setOnClickListener(v -> contextualButtonChoice(2));
        binding.teamSlotThreeButton.setOnClickListener(v -> contextualButtonChoice(3));
        binding.teamSlotFourButton.setOnClickListener(v -> contextualButtonChoice(4));
        binding.teamSlotFiveButton.setOnClickListener(v -> contextualButtonChoice(5));
        binding.teamSlotSixButton.setOnClickListener(v -> contextualButtonChoice(6));

        binding.backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTeamSlotButtons();
    }

    /**
     * Contextualizes the slot int into a Creature choice
     * @param slot the slot number of the creature that was selected
     */
    public void contextualButtonChoice(int slot) {
        Creature selected = UserTeamData.getInstance().getCreatureAtSlot(slot);
        if (selected != null) {
            Intent intent = new Intent(this, TrainCreatureActivity.class);
            intent.putExtra("slotNumber", slot);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No creature in this slot!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Updates the buttons to contain user's saved creatures
     */
    private void updateTeamSlotButtons() {
        Map<Integer, Creature> userTeam = UserTeamData.getInstance().getUserTeam();
        Button[] buttons = {
                binding.teamSlotOneButton,
                binding.teamSlotTwoButton,
                binding.teamSlotThreeButton,
                binding.teamSlotFourButton,
                binding.teamSlotFiveButton,
                binding.teamSlotSixButton,
        };
        for (int i = 0; i < buttons.length; i++) {
            Creature creature = userTeam.get(i + 1);
            if (creature != null) {
                buttons[i].setText(creature.getName());
            } else {
                buttons[i].setText("Team Slot: " + (i + 1));
            }
        }
    }

    public static Intent TrainTeamViewerIntentFactory(Context context) {
        return new Intent(context, TrainTeamViewerActivity.class);
    }
}