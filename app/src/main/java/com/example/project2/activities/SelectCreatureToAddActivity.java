package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.CreatureCellAdapter;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivitySelectCreatureToAddBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SelectCreatureToAddActivity extends AppCompatActivity {

    ActivitySelectCreatureToAddBinding binding;
    private int slot;
    private List<CreatureEntity> templateEntities = new ArrayList<>();
    private ListView listView;
    private AccountStatusCheck accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectCreatureToAddBinding.inflate(getLayoutInflater());
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

        //populate list with creatures saved in database
        setUpData();

        listView = binding.creatureTypeListView;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int creatureId = templateEntities.get(position).getCreatureId();

                Intent intent = new Intent(getApplicationContext(), CreatureDetailActivity.class);
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

    public static Intent SelectCreatureToAddIntentFactory(Context context) {
        Intent intent = new Intent(context, SelectCreatureToAddActivity.class);

        return intent;
    }
}