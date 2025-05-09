package com.example.project2.activities;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  activity that will display all of a specific
 *  Abilities stats
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.Ability;
import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.AbilityEntity;
import com.example.project2.databinding.ActivityAbilityDetailBinding;
import com.example.project2.utilities.Converters;

import java.util.concurrent.Executors;

public class AbilityDetailActivity extends AppCompatActivity {

    ActivityAbilityDetailBinding binding;
    Ability selectedAbility;
    private int slot;
    private Creature playerCreature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbilityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }

        //get reference to chosen creature
        playerCreature = UserTeamData.getInstance().getUserTeam().get(slot);

        //pull Ability by ID
        getSelectedAbility();

        binding.addAbilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playerCreature.getAbilityList().contains(selectedAbility)) {
                    playerCreature.getAbilityList().add(selectedAbility);

                    Intent intent = new Intent(getApplicationContext(), CreatureViewAndEditorActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("slotNumber", slot);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AbilityDetailActivity.this, "Creature already has this ability", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * used to retrieve an ability from the database depending on
     * which ability was selected in the previous activity
     */
    private void getSelectedAbility() {
        String abilityId = getIntent().getStringExtra("abilityId");

        Executors.newSingleThreadExecutor().execute(() -> {
            AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

            AbilityEntity entity = abilityDAO.getAbilityById(abilityId);
            selectedAbility = Converters.convertEntityToAbility(entity);

            //set the UI to display ability stats
            runOnUiThread(this::setValues);
        });
    }

    /**
     * used to set the UI to display the stats of the creature
     * retrieved from the database
     */
    private void setValues() {
        if (selectedAbility == null) {
            Toast.makeText(this, "Failed to load ability data.", Toast.LENGTH_SHORT).show();
            return;
        }

        //update the text view values to that of the creatures
        binding.abilityNameTextView.setText(selectedAbility.getAbilityName());
        binding.elementStatTextView.setText("Type: " + selectedAbility.getAbilityElement());
        binding.powerStatTextView.setText("Power: " + selectedAbility.getPower());
        binding.critStatTextView.setText("Crit Chance: " + selectedAbility.getCritChance());
        binding.accuracyStatTextView.setText("Accuracy: " + selectedAbility.getAccuracy());
    }

    /**
     * intent factory
     * @param context
     * @return
     */
    public static Intent AbilityDetailIntentFactory(Context context) {
        Intent intent = new Intent(context, AbilityDetailActivity.class);
        return intent;
    }
}