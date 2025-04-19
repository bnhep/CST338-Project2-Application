package com.example.project2;

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

import com.example.project2.creatures.*;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.ApplicationDatabase;
import com.example.project2.databinding.ActivityBuildCreatureToAddToTeamBinding;
import com.example.project2.utilities.Converters;

import java.util.concurrent.Executors;

public class BuildCreatureToAddToTeamActivity extends AppCompatActivity {

    ActivityBuildCreatureToAddToTeamBinding binding;
    private int slot;

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

        binding.addCreatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //this is all just for testing at the moment
                ApplicationDatabase db = ApplicationDatabase.getDatabase(getApplicationContext());
                AbilityDAO abilityDAO = db.AbilityDAO();

                Executors.newSingleThreadExecutor().execute(() -> {
                    ElectricRat sparks = new ElectricRat("Sparks", 5);

                    sparks.getAbilityList().add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("TACKLE")));
                    sparks.getAbilityList().add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("SHOCK")));

                    UserData.getInstance().addCreaturetoSlot(slot, sparks);

                    runOnUiThread(() ->
                            Toast.makeText(BuildCreatureToAddToTeamActivity.this, sparks.getName() + " added to team in slot " + slot, Toast.LENGTH_SHORT).show()
                    );
                });
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TeamViewerActivity.TeamBuilderIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent BuildCreatureToAddToTeamIntentFactory(Context context) {
        Intent intent = new Intent(context, BuildCreatureToAddToTeamActivity.class);

        return intent;
    }
}