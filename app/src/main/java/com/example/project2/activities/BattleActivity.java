package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.ActivityBattleBinding;

public class BattleActivity extends AppCompatActivity {

    ActivityBattleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBattleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.abilityOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMaker("You used ability 1");
            }
        });

        binding.abilityTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMaker("You used ability 2");
            }
        });

        binding.abilityThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMaker("You used ability 3");
            }
        });

        binding.abilityFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMaker("You used ability 4");
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toastMaker(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public static Intent BattleIntentFactory(Context context) {
        Intent intent = new Intent(context, BattleActivity.class);

        return intent;
    }
}