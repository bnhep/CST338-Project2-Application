package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.R;
import com.example.project2.databinding.ActivityAddMonstersBinding;

public class AddMonstersActivity extends AppCompatActivity {
   String creatureName = "";
    String creatureType = "";
    int atkStat = 0;
    int dfenseStat = 0;
    int modStat = 0;
    int hpStat = 0;

    ActivityAddMonstersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMonstersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureType = "Fire";
                binding.typeViewer.setText(creatureType);
                Toast toast = Toast.makeText(AddMonstersActivity.this, "Type Changed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                toast.show();
            }
        });
        binding.electricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureType = "Electric";
                binding.typeViewer.setText(creatureType);
                Toast toast = Toast.makeText(AddMonstersActivity.this, "Type Changed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                toast.show();
            }
        });
        binding.grassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureType = "Grass";
                binding.typeViewer.setText(creatureType);
                Toast toast = Toast.makeText(AddMonstersActivity.this, "Type Changed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                toast.show();
            }
        });
        binding.waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureType = "Water";
                binding.typeViewer.setText(creatureType);
                Toast toast = Toast.makeText(AddMonstersActivity.this, "Type Changed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                toast.show();
            }

        });
        binding.createMonsterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureName = ((EditText) findViewById(R.id.creaturesName)).getText().toString().trim();

                try {
                    atkStat = Integer.parseInt(((EditText) findViewById(R.id.attackStat)).getText().toString().trim());
                    dfenseStat = Integer.parseInt(((EditText) findViewById(R.id.defenseStat)).getText().toString().trim());
                    modStat = Integer.parseInt(((EditText) findViewById(R.id.modifierStat)).getText().toString().trim()) ;
                    hpStat = Integer.parseInt(((EditText) findViewById(R.id.healthStat)).getText().toString().trim());
                    // code will stop here if an exception is thrown
                    Intent intent = AdminLandingActivity.adminLandingIntentFactory(getApplicationContext());
                    startActivity(intent);
                    //make add monster function - take stats and add to database
                    //return to the previous menu
                }catch (NumberFormatException e){
                    Toast toast = Toast.makeText(AddMonstersActivity.this, "Invalid Number in Edit Stats", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast
                    toast.show();
                }
            }
        });
    }

    public static Intent AddMonsterIntentFactory(Context context) {
        return new Intent(context, AddMonstersActivity.class);
    }
}