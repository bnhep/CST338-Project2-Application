package com.example.project2.activities;
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

import com.example.project2.UserTeamData;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
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

        binding.opponentOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleWithOpponent();
            }
        });

        binding.opponentTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleWithOpponent();
            }
        });

        binding.opponentThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleWithOpponent();
            }
        });

        binding.randomBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Random
                startBattleWithOpponent();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startBattleWithOpponent() {
        if (!UserTeamData.getInstance().getUserTeam().isEmpty()) {
            Intent intent = new Intent(this, BattleActivity.class);
            //add opponent info here
            //intent.putExtra("name", value);
            startActivity(intent);
        }
        else {
            toastMaker("You don't have a creature to battle with!");
        }

    }

    private void toastMaker(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public static Intent OpponentSelectIntentFactory(Context context) {
        Intent intent = new Intent(context, OpponentSelectActivity.class);

        return intent;
    }
}