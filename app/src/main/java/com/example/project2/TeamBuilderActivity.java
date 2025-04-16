package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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