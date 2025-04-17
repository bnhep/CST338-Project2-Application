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

import com.example.project2.databinding.ActivityOpponentSelectBinding;
import com.example.project2.databinding.ActivityTeamBuilderBinding;

public class TeamBuilderActivity extends AppCompatActivity {

    ActivityTeamBuilderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamBuilderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.teamSlotOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Button 1 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.teamSlotTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Button 2 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.teamSlotThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Button 3 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.teamSlotFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Button 4 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.teamSlotFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Button 5 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.teamSlotSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Button 6 works", Toast.LENGTH_SHORT).show();
            }
        });

        binding.saveTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamBuilderActivity.this, "Team (will be) saved!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainIntentFactory(getApplicationContext(), -1);
                startActivity(intent);
            }
        });
    }



    public static Intent TeamBuilderIntentFactory(Context context) {
        Intent intent = new Intent(context, TeamBuilderActivity.class);

        return intent;
    }
}