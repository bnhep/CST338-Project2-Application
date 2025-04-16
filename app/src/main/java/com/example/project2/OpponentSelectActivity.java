package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.ActivityOpponentSelectBinding;

public class OpponentSelectActivity extends AppCompatActivity {

    ActivityOpponentSelectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpponentSelectBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.opponentOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is placeholder
                Toast.makeText(OpponentSelectActivity.this, "You challenged Brock", Toast.LENGTH_SHORT).show();
            }
        });

        binding.opponentTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is placeholder
                Toast.makeText(OpponentSelectActivity.this, "You challenged Misty", Toast.LENGTH_SHORT).show();
            }
        });

        binding.opponentThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is placeholder
                Toast.makeText(OpponentSelectActivity.this, "You challenged Red", Toast.LENGTH_SHORT).show();
            }
        });

        binding.randomBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is placeholder
                Toast.makeText(OpponentSelectActivity.this, "Loading random battle...", Toast.LENGTH_SHORT).show();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent OpponentSelectIntentFactory(Context context) {
        Intent intent = new Intent(context, OpponentSelectActivity.class);

        return intent;
    }
}