package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.creatures.*;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.ApplicationDatabase;
import com.example.project2.databinding.ActivityBuildCreatureToAddToTeamBinding;
import com.example.project2.utilities.Converters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class BuildCreatureToAddToTeamActivity extends AppCompatActivity {

    ActivityBuildCreatureToAddToTeamBinding binding;
    private int slot;
    public static ArrayList<Creature> creatureList = new ArrayList<Creature>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuildCreatureToAddToTeamBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            finish();
        }

        setUpData();

        setUpList();

        setUpOnClickListener();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TeamViewerActivity.TeamViewerIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void setUpData() {
        /** TODO: this will all be handles properly later. Eventually all of the creatures
         *   will be instantiated from a table called something like CREATURE_TYPE_TABLE
         */
        if (creatureList.isEmpty()) {
            Executors.newSingleThreadExecutor().execute(() -> {
                ElectricRat electricRat = new ElectricRat("Electric Rat", 1);
                FireLizard fireLizard = new FireLizard("Fire Lizard", 1);
                FlowerDino flowerDino = new FlowerDino("Flower Dino", 1);
                WeirdTurtle weirdTurtle = new WeirdTurtle("Weird Turtle", 1);
                //this is just to populate the list with more options
                WeirdTurtle weirdTurtle1 = new WeirdTurtle("Another Weird Turtle", 1);
                WeirdTurtle weirdTurtle2 = new WeirdTurtle("And another", 1);
                WeirdTurtle weirdTurtle3 = new WeirdTurtle("Just for testing", 1);
                WeirdTurtle weirdTurtle4 = new WeirdTurtle("The scrolling on", 1);
                WeirdTurtle weirdTurtle5 = new WeirdTurtle("This Menu", 1);

                creatureList.add(electricRat);
                creatureList.add(fireLizard);
                creatureList.add(flowerDino);
                creatureList.add(weirdTurtle);
                creatureList.add(weirdTurtle1);
                creatureList.add(weirdTurtle2);
                creatureList.add(weirdTurtle3);
                creatureList.add(weirdTurtle4);
                creatureList.add(weirdTurtle5);
            });
        }
    }

    private void setUpList() {
        listView = (ListView) findViewById(R.id.creatureTypeListView);

        CreatureCellAdapter adapter = new CreatureCellAdapter(getApplicationContext(), 0, creatureList);
        listView.setAdapter(adapter);
    }

    private void setUpOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Creature selectedCreature = (Creature) listView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), BuildCreatureDetailActivity.class);
                intent.putExtra("positionInArray", position);
                intent.putExtra("slotNumber", slot);
                startActivity(intent);
            }
        });
    }

    public static Intent BuildCreatureToAddToTeamIntentFactory(Context context) {
        Intent intent = new Intent(context, BuildCreatureToAddToTeamActivity.class);

        return intent;
    }
}