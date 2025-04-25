package com.example.project2.activities;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  activity that will display a list of all available
 *  Abilities that can be added to the creature
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.Ability;
import com.example.project2.AbilityCellAdapter;
import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.AbilityEntity;
import com.example.project2.databinding.ActivitySelectAbilityToAddBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SelectAbilityToAddActivity extends AppCompatActivity {

    ActivitySelectAbilityToAddBinding binding;
    private List<AbilityEntity> abilityEntities = new ArrayList<>();
    private ListView listView;
    private int slot;
    private Creature playerCreature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectAbilityToAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }

        //get reference to chosen creature
        playerCreature = UserTeamData.getInstance().getUserTeam().get(slot);

        setUpData();

        listView = binding.abilityTypeListView;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String abilityId = abilityEntities.get(position).getAbilityID();

                //pass the abilityID as well as the creature slot
                Intent intent = new Intent(getApplicationContext(), AbilityDetailActivity.class);
                intent.putExtra("abilityId", abilityId);
                intent.putExtra("slotNumber", slot);
                startActivity(intent);
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
     * used to call the cell adapter so that it can populate
     * the screen with a list of available abilities to select from
     * abilities already on the creatures list are not added
     */
    private void setUpData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

            //get a full list of ability entities
            List<AbilityEntity> abilities = abilityDAO.getAll();

            //clear the list if if already has data
            abilityEntities.clear();
            //new arraylist to hold the IDs for all the abilities already in the creatures abilityList
            List<String> existingAbilityIds = new ArrayList<>();
            //loop through the abilityList and add all IDs to the existingAbilityIds arraylist
            for (Ability ability : playerCreature.getAbilityList()) {
                existingAbilityIds.add(ability.getAbilityID());
            }
            //loop through all ability entities in the list and check for matching IDs
            for (AbilityEntity entity : abilities) {
                if (!existingAbilityIds.contains(entity.getAbilityID())) {
                    abilityEntities.add(entity);
                }
            }

            runOnUiThread(() -> {
                List<String> abilityNames = new ArrayList<>();
                //collect all the names found in the list of abilities
                for (AbilityEntity e : abilityEntities) {
                    abilityNames.add(e.getAbilityName());
                }
                //pass them to the adaptor to make a scrollable list
                AbilityCellAdapter adapter = new AbilityCellAdapter(getApplicationContext(), 0, abilityNames);
                listView.setAdapter(adapter);
            });
        });
    }

    /**
     * intent factory
     * @param context
     * @return
     */
    public static Intent SelectAbilityToAddIntentFactory(Context context) {
        Intent intent = new Intent(context, SelectAbilityToAddActivity.class);
        return intent;
    }
}