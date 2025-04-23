package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.CreatureCellAdapter;
import com.example.project2.R;
import com.example.project2.creatures.*;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivityBuildCreatureToAddToTeamBinding;
import com.example.project2.utilities.Converters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class BuildCreatureToAddToTeamActivity extends AppCompatActivity {

    ActivityBuildCreatureToAddToTeamBinding binding;
    private int slot;
    private List<CreatureEntity> templateEntities = new ArrayList<>();
    private ListView listView;
    private AccountStatusCheck accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuildCreatureToAddToTeamBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        accountManager = AccountStatusCheck.getInstance();
        binding.usernameDisplayTextView.setText(accountManager.getUserName());
        //store the passed in slot number
        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }

        /**
         *  TODO: this is only temporary. eventually this will be replaced with a call
         *   to a table containing all creature types.
         */
        setUpData();

        //
        listView = binding.creatureTypeListView;

        //get reference to the CreatureCellAdapter
        //CreatureCellAdapter adapter = new CreatureCellAdapter(getApplicationContext(), 0, creatureList);
        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int creatureId = templateEntities.get(position).getCreatureId();

                Intent intent = new Intent(getApplicationContext(), BuildCreatureDetailActivity.class);
                intent.putExtra("slotNumber", slot);
                intent.putExtra("creatureId", creatureId);
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

    private void setUpData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();

            List<CreatureEntity> templates = creatureDAO.getCreaturesByUserId("NONE");

            templateEntities.clear();
            templateEntities.addAll(templates);

            runOnUiThread(() -> {
                List<String> creatureNames = new ArrayList<>();
                for (CreatureEntity e : templateEntities) {
                    creatureNames.add(e.getName());
                }

                CreatureCellAdapter adapter = new CreatureCellAdapter(getApplicationContext(), 0, creatureNames);
                listView.setAdapter(adapter);
            });
        });
    }

    public static Intent BuildCreatureToAddToTeamIntentFactory(Context context) {
        Intent intent = new Intent(context, BuildCreatureToAddToTeamActivity.class);

        return intent;
    }
}