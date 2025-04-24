package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.Ability;
import com.example.project2.R;
import com.example.project2.UserTeamData;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.AbilityEntity;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivityAbilityDetailBinding;
import com.example.project2.utilities.Converters;

import java.util.concurrent.Executors;

public class AbilityDetailActivity extends AppCompatActivity {

    ActivityAbilityDetailBinding binding;
    Ability selectedAbility;
    private int slot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbilityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }

        //pull Ability by ID
        getSelectedAbility();

        binding.addAbilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTeamData.getInstance().getUserTeam().get(slot).getAbilityList().add(selectedAbility);

                Intent intent = CreatureViewAndEditorActivity.CreatureViewAndEditorIntentFactory(getApplicationContext());
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

    private void getSelectedAbility() {
        String abilityId = getIntent().getStringExtra("abilityId");

        Executors.newSingleThreadExecutor().execute(() -> {
            AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

            AbilityEntity entity = abilityDAO.getAbilityById(abilityId);
            selectedAbility = Converters.convertEntityToAbility(entity);

            //set the UI to display ability stats
            runOnUiThread(this::setValues);
        });
    }

    private void setValues() {
        if (selectedAbility == null) {
            Toast.makeText(this, "Failed to load ability data.", Toast.LENGTH_SHORT).show();
            return;
        }

        //update the text view values to that of the creatures
        binding.abilityNameTextView.setText(selectedAbility.getAbilityName());
        binding.elementStatTextView.setText("Type: " + selectedAbility.getAbilityElement());
        binding.powerStatTextView.setText("Power: " + selectedAbility.getPower());
        binding.critStatTextView.setText("Crit Chance: " + selectedAbility.getCritChance());
        binding.accuracyStatTextView.setText("Accuracy: " + selectedAbility.getAccuracy());
    }

    public static Intent AbilityDetailIntentFactory(Context context) {
        Intent intent = new Intent(context, AbilityDetailActivity.class);
        return intent;
    }
}