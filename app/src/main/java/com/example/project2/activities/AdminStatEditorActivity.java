package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.creatures.Creature;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivityAdminEditStatsBinding;
import com.example.project2.databinding.ActivityAdminStatEditorBinding;

import com.example.project2.R;
import com.example.project2.utilities.Converters;

import java.util.concurrent.Executors;

public class AdminStatEditorActivity extends AppCompatActivity {
    private Creature selectedCreature;
    private int creatureID;
    private String creatureName = "";
    private int atkStat = 0;
    private int speedStat = 0;
    private int dfenseStat = 0;
    private int hpStat = 0;
    private CreatureDAO creatureDAO;
    private AbilityDAO abilityDAO;
    private ActivityAdminStatEditorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminStatEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        creatureDAO = DAOProvider.getCreatureDAO();
        abilityDAO = DAOProvider.getAbilityDAO();

        getSelectedCreature();

        binding.confirmStatEditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputStats();
                setCreatureStats();
            }
        });

        binding.backButton.setOnClickListener(v -> finish());
    }

    private void getSelectedCreature() {
        int creatureId = getIntent().getIntExtra("creatureId", -1);

        Executors.newSingleThreadExecutor().execute(() -> {
            CreatureEntity entity = creatureDAO.getCreatureById(creatureId);
            selectedCreature = Converters.convertEntityToCreature(entity, abilityDAO);

            runOnUiThread(() -> {
                if (selectedCreature != null) {
                    binding.creatureNameViewer.setText(selectedCreature.getName());
                    creatureID = selectedCreature.getCreatureId();
                } else {
                    binding.creatureNameViewer.setText("Creature not found.");
                }
            });
        });
    }

    private void getInputStats() {
        try {
            atkStat = Integer.parseInt(binding.attackStat2.getText().toString().trim());
            speedStat = Integer.parseInt(binding.speedStat2.getText().toString().trim());
            dfenseStat = Integer.parseInt(binding.defenseStat2.getText().toString().trim());
            hpStat = Integer.parseInt(binding.healthStat2.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast toast = Toast.makeText(AdminStatEditorActivity.this, "Invalid Number in Edit Stats", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void setCreatureStats() {
        Executors.newSingleThreadExecutor().execute(() -> {

            selectedCreature.setBaseAttack(atkStat);
            selectedCreature.setBaseSpeed(speedStat);
            selectedCreature.setBaseDefense(dfenseStat);
            selectedCreature.setBaseHealth(hpStat);

            creatureDAO.insert(Converters.convertCreatureToEntity(selectedCreature, "NONE",-1, creatureID));
            runOnUiThread(() -> {Toast.makeText(this, "Creature stats updated.", Toast.LENGTH_SHORT).show();});
        });
    }


    public static Intent AdminStatEditorIntentFactory(Context context) {
        return new Intent(context, AdminStatEditorActivity.class);
    }
}