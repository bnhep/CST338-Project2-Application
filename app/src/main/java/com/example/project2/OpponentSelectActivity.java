package com.example.project2;
/**
 * Name: Austin Shatswell
 * Date: --/--/25
 * Explanation: Project 2: Creature Coliseum
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityOpponentSelectBinding;

public class OpponentSelectActivity extends AppCompatActivity {

    ActivityOpponentSelectBinding binding;
    private ApplicationRepository appRepository;
    private AccountStatusCheck accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpponentSelectBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        appRepository = ApplicationRepository.getRepository(getApplication());
        accountManager = AccountStatusCheck.getInstance(getApplication());
        binding.usernameDisplayTextView.setText(accountManager.getUserName());
        /*
        LiveData<User> userObserver = appRepository.getUsernameByID(accountManager.getUserID());
        userObserver.observe(this, new Observer<>() {
            @Override
            public void onChanged(User user) {
                binding.usernameDisplayTextView.setText(user.getUsername());
                userObserver.removeObserver(this);
            }
        });
        */

        binding.opponentOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is placeholder
                Toast.makeText(OpponentSelectActivity.this, "You challenged Billy", Toast.LENGTH_SHORT).show();
            }
        });

        binding.opponentTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is placeholder
                Toast.makeText(OpponentSelectActivity.this, "You challenged Hana", Toast.LENGTH_SHORT).show();
            }
        });

        binding.opponentThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is placeholder
                Toast.makeText(OpponentSelectActivity.this, "You challenged Da Champ", Toast.LENGTH_SHORT).show();
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