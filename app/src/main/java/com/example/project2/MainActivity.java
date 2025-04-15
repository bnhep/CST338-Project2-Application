package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.project2.database.ApplicationRepository;
import com.example.project2.databinding.ActivityMainBinding;

/**
 * Activity Menu where user will choose the options to battle, view monsters, view recent battles
 * //TODO implement the other activity layouts,logout button should be here
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Application_LOG";
    private ActivityMainBinding binding;
    private ApplicationRepository appRepository; //Performs the queries for the database

    private final int userStatus = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appRepository = ApplicationRepository.getRepository(getApplication());

        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
        //button functionality for Start Battle
        //TODO should have factory method to swap to StartBattle Activity
        binding.startBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO should have factory method call to the battle options menu(BASICALLY A STUB BUTTON)
                Toast.makeText(MainActivity.this, "Start Button works", Toast.LENGTH_SHORT).show();
            }
        });

        //button functionality for View Monsters
        //TODO should have factory method to swap to View Monsters(BASICALLY A STUB BUTTON)
        binding.viewMonstersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Testing button please remove later
                Toast.makeText(MainActivity.this, "View Monster Button works", Toast.LENGTH_SHORT).show();
            }
        });

        //button functionality for View recent battles
        //TODO should have factory method to swap to Recent Battles(BASICALLY A STUB BUTTON)
        binding.viewRecentBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Testing button please remove later
                Toast.makeText(MainActivity.this, "View Recent Button works", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //TemporaryMainActivity intent factory.
    static Intent MainIntentFactory(Context context){
        return new Intent(context, MainActivity.class);
    }


}