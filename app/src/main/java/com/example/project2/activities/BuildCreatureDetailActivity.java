package com.example.project2.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.databinding.ActivityBuildCreatureDetailBinding;

import java.util.concurrent.Executors;

public class BuildCreatureDetailActivity extends AppCompatActivity {

    ActivityBuildCreatureDetailBinding binding;

    private int slot;
    int creatureArrayPosition;

    Creature selectedCreature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuildCreatureDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            finish();
        }
        
        getSelectedCreature();

        setValues();

        binding.addToTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is all just for testing at the moment
                //ApplicationDatabase db = ApplicationDatabase.getDatabase(getApplicationContext());
                //AbilityDAO abilityDAO = db.AbilityDAO();

                Executors.newSingleThreadExecutor().execute(() -> {
                    UserTeamData.getInstance().addCreatureToSlot(slot, selectedCreature);

                    runOnUiThread(() ->
                            Toast.makeText(BuildCreatureDetailActivity.this, selectedCreature.getName() + " added to team in slot " + slot, Toast.LENGTH_SHORT).show()
                    );

                    Intent intent = TeamViewerActivity.TeamViewerIntentFactory(getApplicationContext());
                    startActivity(intent);
                });
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BuildCreatureToAddToTeamActivity.BuildCreatureToAddToTeamIntentFactory(getApplicationContext());
                intent.putExtra("slotNumber", slot);
                startActivity(intent);
            }
        });
    }

    private void getSelectedCreature() {
        creatureArrayPosition = getIntent().getIntExtra("positionInArray", -1);
        selectedCreature = BuildCreatureToAddToTeamActivity.creatureList.get(creatureArrayPosition);
    }

    @SuppressLint("SetTextI18n")
    private void setValues() {
        binding.creatureNameTextView.setText(selectedCreature.getName());
        binding.creatureBaseHealthStatTextView.setText("Health: " + selectedCreature.getBaseHealth());
        binding.creatureBaseAttackStatTextView.setText("Attack: " + selectedCreature.getBaseAttack());
        binding.creatureBaseDefenseStatTextView.setText("Defense: " + selectedCreature.getBaseDefense());
        binding.creatureBaseSpeedStatTextView.setText("Speed: " + selectedCreature.getBaseSpeed());
    }

    public static Intent BuildCreatureDetailIntentFactory(Context context) {
        Intent intent = new Intent(context, BuildCreatureDetailActivity.class);
        return intent;
    }
}