package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.creatures.*;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.ApplicationDatabase;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivityTeamBuilderBinding;
import com.example.project2.utilities.Converters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class TeamBuilderActivity extends AppCompatActivity {

    ActivityTeamBuilderBinding binding;

    private final List<Creature> userTeam = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamBuilderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.teamSlotOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDatabase db = ApplicationDatabase.getDatabase(getApplicationContext());
                AbilityDAO abilityDAO = db.AbilityDAO();

                Executors.newSingleThreadExecutor().execute(() -> {
                    ElectricRat sparks = new ElectricRat("Sparks", 5);
                    sparks.getAbilityList().add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("TACKLE")));
                    sparks.getAbilityList().add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("SHOCK")));

                    userTeam.add(sparks);

                    runOnUiThread(() ->
                            Toast.makeText(TeamBuilderActivity.this, userTeam.get(0).getName() + " added to team!", Toast.LENGTH_SHORT).show()
                    );
                });
            }
        });

        binding.teamSlotTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Button 2 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.teamSlotThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Button 3 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.teamSlotFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Button 4 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.teamSlotFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userTeam.isEmpty()) {
                    Toast.makeText(TeamBuilderActivity.this, "No creatures loaded", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TeamBuilderActivity.this, "Creature ability " +
                            userTeam.get(0).getAbilityList().get(0).getAbilityName() +
                            " has " + userTeam.get(0).getAbilityList().get(0).getPower() +
                            " power!", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(TeamBuilderActivity.this, "Button 5 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.teamSlotSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    try {
                        //clear the current ArrayList
                        userTeam.clear();

                        //add in DAOs
                        ApplicationDatabase db = ApplicationDatabase.getDatabase(getApplicationContext());
                        CreatureDAO creatureDAO = db.CreatureDAO();
                        AbilityDAO abilityDAO = db.AbilityDAO();

                        //collecting all creatures and passing in the testUserId
                        List<CreatureEntity> creatureEntities = creatureDAO.getCreaturesByUserId("testUserId");

                        //populating the userTeam array with creatures converted from creature entities
                        for (CreatureEntity entity : creatureEntities) {
                            Creature creature = Converters.convertEntityToCreature(entity, abilityDAO);
                            userTeam.add(creature);
                        }

                        //hopefully this works
                        runOnUiThread(() -> {
                            Toast.makeText(TeamBuilderActivity.this, "Loaded " + userTeam.size() + " creature(s)!", Toast.LENGTH_SHORT).show();
                        });
                    } catch (Exception e) {
                        Log.e("TeamBuilder", "Error loading team", e);
                        runOnUiThread(() -> {
                            //ill be sad if I see this
                            Toast.makeText(TeamBuilderActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });

        binding.saveTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDatabase db = ApplicationDatabase.getDatabase(getApplicationContext());
                CreatureDAO creatureDAO = db.CreatureDAO();

                Executors.newSingleThreadExecutor().execute(() -> {
                    for (Creature creature: userTeam) {
                        CreatureEntity entity = Converters.convertCreatureToEntity(creature, "testUserId");
                        creatureDAO.insert(entity);
                    }

                    runOnUiThread(() ->
                            Toast.makeText(TeamBuilderActivity.this, "Team saved!", Toast.LENGTH_SHORT).show()
                    );
                });
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }



    public static Intent TeamBuilderIntentFactory(Context context) {
        Intent intent = new Intent(context, TeamBuilderActivity.class);

        return intent;
    }
}