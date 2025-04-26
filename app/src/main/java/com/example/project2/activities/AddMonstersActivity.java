package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.ElementalType;
import com.example.project2.R;
import com.example.project2.creatures.CustomCreature;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.databinding.ActivityAddMonstersBinding;
import com.example.project2.utilities.Converters;

public class AddMonstersActivity extends AppCompatActivity {
   private String creatureName = "";
    private ElementalType creatureType;
    private int atkStat = 0;
    private int speedStat = 0;
    private int dfenseStat = 0;
    private int hpStat = 0;

    ActivityAddMonstersBinding binding;

    private AccountStatusCheck accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMonstersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        accountManager = AccountStatusCheck.getInstance();
        binding.usernameDisplayTextView.setText(accountManager.getUserName());

        binding.fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureType = ElementalType.FIRE;
                binding.typeViewer.setText("FIRE");
                Toast toast = Toast.makeText(AddMonstersActivity.this, "Type Changed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                toast.show();
            }
        });
        binding.electricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureType = ElementalType.ELECTRIC;
                binding.typeViewer.setText("ELECTRIC");
                Toast toast = Toast.makeText(AddMonstersActivity.this, "Type Changed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                toast.show();
            }
        });
        binding.grassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureType = ElementalType.GRASS;
                binding.typeViewer.setText("GRASS");
                Toast toast = Toast.makeText(AddMonstersActivity.this, "Type Changed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                toast.show();
            }
        });
        binding.waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureType = ElementalType.WATER;
                binding.typeViewer.setText("WATER");
                Toast toast = Toast.makeText(AddMonstersActivity.this, "Type Changed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                toast.show();
            }

        });
        binding.normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureType = ElementalType.NORMAL;
                binding.typeViewer.setText("NORMAL");
                Toast toast = Toast.makeText(AddMonstersActivity.this, "Type Changed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                toast.show();
            }

        });
        binding.createMonsterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStats();
                addNewCreatureToDatabase();
            }

        });
        binding.backButton.setOnClickListener(v -> finish());
    }

private void getStats(){
    creatureName = ((EditText) findViewById(R.id.creaturesName)).getText().toString().trim();

    try {
        atkStat = Integer.parseInt(binding.attackStat.getText().toString().trim());
        speedStat = Integer.parseInt(binding.speedStat.getText().toString().trim());
        dfenseStat = Integer.parseInt(binding.defenseStat.getText().toString().trim());
        hpStat = Integer.parseInt(binding.healthStat.getText().toString().trim());

        Intent intent = AdminLandingActivity.adminLandingIntentFactory(getApplicationContext());
        startActivity(intent);

    } catch (NumberFormatException e) {
        Toast toast = Toast.makeText(AddMonstersActivity.this, "Invalid Number in Edit Stats", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
private void addNewCreatureToDatabase(){
    new Thread(() -> {
        CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();
        creatureDAO.insert(Converters.convertCreatureToEntity(new CustomCreature(creatureName, creatureType, hpStat, atkStat, dfenseStat, speedStat), "NONE", -1, 0));
    }).start();
}

    public static Intent AddMonsterIntentFactory(Context context) {
        return new Intent(context, AddMonstersActivity.class);
    }
}