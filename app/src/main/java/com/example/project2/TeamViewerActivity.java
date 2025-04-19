package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.creatures.*;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.ApplicationDatabase;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivityTeamViewerBinding;
import com.example.project2.utilities.Converters;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class TeamViewerActivity extends AppCompatActivity {

    ActivityTeamViewerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamViewerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        updateTeamSlotButtons();

        binding.teamSlotOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(1);
            }
        });

        binding.teamSlotTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(2);
            }
        });

        binding.teamSlotThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(3);
            }
        });

        binding.teamSlotFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(4);
            }
        });

        binding.teamSlotFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(5);
            }
        });

        binding.teamSlotSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(6);
            }
        });

        //TODO:  *************************** SAVE  ***************************
        binding.saveTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeam();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        //TODO:  *************************** LOAD ***************************
        binding.loadTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This button currently is being used to act as a "load" button for testing
                loadTeam();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTeamSlotButtons(); //refresh names every time activity regains focus
    }

    public void contextualButtonChoice(int slot) {
        if (UserTeamData.getInstance().getCreatureAtSlot(slot) != null) {
            //if not empty launch creature viewer
            Intent intent = new Intent(this, CreatureViewAndEditorActivity.class);
            intent.putExtra("slotNumber", slot);
            startActivity(intent);

        }
        else {
            //if empty launch creature creator
            Intent intent = new Intent(this, BuildCreatureToAddToTeamActivity.class);
            intent.putExtra("slotNumber", slot);
            startActivity(intent);
        }
    }

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
            Creature creature = userTeam.get(i+1);
            if (creature != null) {
                buttons[i].setText(creature.getName());
            }
            else {
                buttons[i].setText("Team Slot: " + (i+1));
            }
        }
    }

    public void saveTeam(){
        Executors.newSingleThreadExecutor().execute(() -> {
            ApplicationDatabase db = ApplicationDatabase.getDatabase(getApplicationContext());
            CreatureDAO creatureDAO = db.CreatureDAO();

            for (Map.Entry<Integer, Creature> entry : UserTeamData.getInstance().getUserTeam().entrySet()) {
                int slot = entry.getKey();
                Creature creature = entry.getValue();
                int id = creature.getCreatureId();

                //later on we want to pass in the actual users generated id here
                CreatureEntity entity = Converters.convertCreatureToEntity(creature, "testUserId", slot, id);
                creatureDAO.insert(entity);
            }
            runOnUiThread(() ->
                    Toast.makeText(TeamViewerActivity.this, "Team saved!", Toast.LENGTH_SHORT).show()
            );
        });
    }

    public void loadTeam(){
        //TODO: This is just here for testing. this should be moved to when the user logs in later
        try {
            Executors.newSingleThreadExecutor().execute(() -> {

                //clear any current data
                UserTeamData.getInstance().clearTeam();

                ApplicationDatabase db = ApplicationDatabase.getDatabase(getApplicationContext());
                CreatureDAO creatureDAO = db.CreatureDAO();
                AbilityDAO abilityDAO = db.AbilityDAO();

                //later on we want to pass in the actual users generated id here
                List<CreatureEntity> creatureEntities = creatureDAO.getCreaturesByUserId("testUserId");

                for (CreatureEntity entity : creatureEntities) {
                    Creature creature = Converters.convertEntityToCreature(entity, abilityDAO);
                    int slot = entity.getTeamSlot();
                    UserTeamData.getInstance().addCreatureToSlot(slot, creature);
                }
                //i really hope i see this
                runOnUiThread(() ->
                                updateTeamSlotButtons()
                );
            });
        } catch (Exception e) {
            //im going to be sad if i see this
            Log.e("TeamBuilder", "Error loading team", e);
            runOnUiThread(() ->
                    Toast.makeText(TeamViewerActivity.this, "Failed to load", Toast.LENGTH_SHORT).show()
            );
        }
    }

    public static Intent TeamViewerIntentFactory(Context context) {
        Intent intent = new Intent(context, TeamViewerActivity.class);

        return intent;
    }
}