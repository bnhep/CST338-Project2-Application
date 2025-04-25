package com.example.project2.activities;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  activity that will display all of a specific
 *  creatures stats
 */

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
import com.example.project2.utilities.ImageUtil;

import java.util.concurrent.Executors;

public class CreatureDetailActivity extends AppCompatActivity {

    ActivityCreatureDetailBinding binding;

    private int slot;
    private Creature selectedCreature;
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

    /**
     * used to retrieve a creature from the database depending on
     * which creature was selected in the previous activity
     */
    private void getSelectedCreature() {
        int creatureId = getIntent().getIntExtra("creatureId", -1);

        Executors.newSingleThreadExecutor().execute(() -> {
            CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();
            AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

            //create a CreatureEntity based on the ID that was passed in
            CreatureEntity entity = creatureDAO.getCreatureById(creatureId);
            //convert the entity into a full creature
            selectedCreature = Converters.convertEntityToCreature(entity, abilityDAO);

            //set the UI to display creature stats
            runOnUiThread(this::setValues);
        });
    }

    /**
     * used to set the UI to display the stats of the creature
     * retrieved from the database
     */
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

        //set the image to the creatures
        binding.creatureImage.setImageResource(ImageUtil.getCreatureImage(selectedCreature, getApplicationContext()));
    }

    /**
     * intent factory
     * @param context
     * @return
     */
    public static Intent CreatureDetailIntentFactory(Context context) {
        Intent intent = new Intent(context, CreatureDetailActivity.class);
        return intent;
    }
}