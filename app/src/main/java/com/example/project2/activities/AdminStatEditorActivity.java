package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.content.Intent;
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
    private CreatureDAO creatureDAO;
   //CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();

    AbilityDAO abilityDAO;
    //AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

    ActivityAdminStatEditorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        creatureDAO = DAOProvider.getCreatureDAO();
        abilityDAO = DAOProvider.getAbilityDAO();

        getSelectedCreature();
        creatureID = selectedCreature.getCreatureId();
        //selectedCreature.

        // inside of finalization button : new Thread(() -> {  [code]   }).start();
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getSelectedCreature(){
        int creatureId = getIntent().getIntExtra("creatureId", -1);

        Executors.newSingleThreadExecutor().execute(() -> {
            CreatureEntity entity = creatureDAO.getCreatureById(creatureId);
            selectedCreature = Converters.convertEntityToCreature(entity, abilityDAO);
        });
    }

    private void editedCreatureStats(){
        new Thread(() -> {
            creatureDAO.insert(Converters.convertCreatureToEntity(selectedCreature, "NONE", -1, creatureID));
        }).start();
    }
    public static Intent AdminStatEditorIntentFactory(Context context) {
        return new Intent(context, AdminStatEditorActivity.class);
    }
}