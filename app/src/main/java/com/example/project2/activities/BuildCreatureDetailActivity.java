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
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivityBuildCreatureDetailBinding;
import com.example.project2.utilities.Converters;

import java.util.concurrent.Executors;

public class BuildCreatureDetailActivity extends AppCompatActivity {

    ActivityBuildCreatureDetailBinding binding;

    private int slot;
    Creature selectedCreature;
    String userId;
    private AccountStatusCheck accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuildCreatureDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        accountManager = AccountStatusCheck.getInstance();
        CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();

        userId = String.valueOf(accountManager.getUserID());
        binding.usernameDisplayTextView.setText(accountManager.getUserName());
        //store the passed in slot number
        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }
        
        getSelectedCreature();

        binding.addToTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is all just for testing at the moment
                Executors.newSingleThreadExecutor().execute(() -> {
                    selectedCreature.setCreatureId(0);
                    UserTeamData.getInstance().addCreatureToSlot(slot, selectedCreature);

                    Intent intent = TeamViewerActivity.TeamViewerIntentFactory(getApplicationContext());
                    startActivity(intent);
                    //CreatureEntity entity = Converters.convertCreatureToEntity(selectedCreature, userId, slot, 0);
                    //creatureDAO.insert(entity);
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
        int creatureId = getIntent().getIntExtra("creatureId", -1);

        Executors.newSingleThreadExecutor().execute(() -> {
            CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();
            AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

            CreatureEntity entity = creatureDAO.getCreatureById(creatureId);
            selectedCreature = Converters.convertEntityToCreature(entity, abilityDAO);

            runOnUiThread(this::setValues);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setValues() {
        if (selectedCreature == null) {
            Toast.makeText(this, "Failed to load creature data.", Toast.LENGTH_SHORT).show();
            return;
        }

        //update the text view values to that of the creatures
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