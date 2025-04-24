package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.AbilityCellAdapter;
import com.example.project2.CreatureCellAdapter;
import com.example.project2.R;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.AbilityEntity;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivitySelectAbilityToAddBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SelectAbilityToAddActivity extends AppCompatActivity {

    ActivitySelectAbilityToAddBinding binding;
    private List<AbilityEntity> abilityEntities = new ArrayList<>();
    private ListView listView;
    private int slot;

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

        setUpData();

        listView = binding.abilityTypeListView;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String abilityId = abilityEntities.get(position).getAbilityID();

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

    private void setUpData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

            List<AbilityEntity> abilities = abilityDAO.getAll();

            abilityEntities.clear();
            abilityEntities.addAll(abilities);

            runOnUiThread(() -> {
                List<String> abilityNames = new ArrayList<>();
                for (AbilityEntity e : abilityEntities) {
                    abilityNames.add(e.getAbilityName());
                }

                AbilityCellAdapter adapter = new AbilityCellAdapter(getApplicationContext(), 0, abilityNames);
                listView.setAdapter(adapter);
            });
        });
    }

    public static Intent SelectAbilityToAddIntentFactory(Context context) {
        Intent intent = new Intent(context, SelectAbilityToAddActivity.class);
        return intent;
    }
}