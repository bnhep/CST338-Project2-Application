package com.example.project2.activities;

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
import com.example.project2.databinding.ActivityCreatureDetailBinding;
import com.example.project2.utilities.Converters;

import java.util.concurrent.Executors;

public class CreatureDetailActivity extends AppCompatActivity {

    ActivityCreatureDetailBinding binding;

    private int slot;
    Creature selectedCreature;
    String userId;
    private AccountStatusCheck accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatureDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        accountManager = AccountStatusCheck.getInstance();

        userId = String.valueOf(accountManager.getUserID());
        binding.usernameDisplayTextView.setText(accountManager.getUserName());
        //store the passed in slot number
        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }

        //pull Creature by ID
        getSelectedCreature();

        binding.addToTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add creature to team
                Executors.newSingleThreadExecutor().execute(() -> {
                    selectedCreature.setCreatureId(0);
                    UserTeamData.getInstance().addCreatureToSlot(slot, selectedCreature);

                    Intent intent = new Intent(getApplicationContext(), TeamViewerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                });
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

            //set the UI to display creature stats
            runOnUiThread(this::setValues);
        });
    }

    private void setValues() {
        if (selectedCreature == null) {
            Toast.makeText(this, "Failed to load creature data.", Toast.LENGTH_SHORT).show();
            return;
        }

        //update the text view values to that of the creatures
        binding.creatureNameTextView.setText(selectedCreature.getName());
        binding.healthStatTextView.setText("Health: " + selectedCreature.getBaseHealth());
        binding.attackStatTextView.setText("Attack: " + selectedCreature.getBaseAttack());
        binding.defenseStatTextView.setText("Defense: " + selectedCreature.getBaseDefense());
        binding.speedStatTextView.setText("Speed: " + selectedCreature.getBaseSpeed());
    }

    public static Intent CreatureDetailIntentFactory(Context context) {
        Intent intent = new Intent(context, CreatureDetailActivity.class);
        return intent;
    }
}