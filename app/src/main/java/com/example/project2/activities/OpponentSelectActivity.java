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
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.databinding.ActivityOpponentSelectBinding;

import java.util.Map;

public class OpponentSelectActivity extends AppCompatActivity {

    ActivityOpponentSelectBinding binding;
    private ApplicationRepository appRepository;
    private AccountStatusCheck accountManager;
    private boolean opponentSelectButtonsVisible = true;
    private boolean creatureSelectButtonsVisible = false;

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
                toggleOpponentSelectButtons();
                updateTeamSlotButtons();
                toggleCreatureSelectButtons();
            }
        });

        binding.opponentTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOpponentSelectButtons();
                updateTeamSlotButtons();
                toggleCreatureSelectButtons();
            }
        });

        binding.opponentThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOpponentSelectButtons();
                updateTeamSlotButtons();
                toggleCreatureSelectButtons();
            }
        });

        binding.randomBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOpponentSelectButtons();
                updateTeamSlotButtons();
                toggleCreatureSelectButtons();
            }
        });

        binding.teamSlotOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(1);
            }
        });

        binding.teamSlotTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(2);
            }
        });

        binding.teamSlotThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(3);
            }
        });

        binding.teamSlotFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(4);
            }
        });

        binding.teamSlotFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(5);
            }
        });

        binding.teamSlotSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(6);
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startBattleOpponent(int slot) {
        Creature creature = UserTeamData.getInstance().getUserTeam().get(slot);

        if (creature != null) {
            Intent intent = new Intent(this, BattleActivity.class);
            //add opponent info here
            //intent.putExtra("name", value);
            //add creature chosen here
            intent.putExtra("slotNumber", slot);
            startActivity(intent);
        }
        else {
            toastMaker("Slot empty!");
        }
    }

    private void toggleOpponentSelectButtons() {
        if (opponentSelectButtonsVisible) {
            opponentSelectButtonsVisible = false;
            binding.opponentOneButton.setVisibility(View.GONE);
            binding.opponentTwoButton.setVisibility(View.GONE);
            binding.opponentThreeButton.setVisibility(View.GONE);
            binding.randomBattleButton.setVisibility(View.GONE);
        }
        else {
            opponentSelectButtonsVisible = true;
            binding.opponentOneButton.setVisibility(View.VISIBLE);
            binding.opponentTwoButton.setVisibility(View.VISIBLE);
            binding.opponentThreeButton.setVisibility(View.VISIBLE);
            binding.randomBattleButton.setVisibility(View.VISIBLE);
        }
    }

    private void toggleCreatureSelectButtons() {
        if (creatureSelectButtonsVisible) {
            creatureSelectButtonsVisible = false;
            binding.teamSlotOneButton.setVisibility(View.GONE);
            binding.teamSlotTwoButton.setVisibility(View.GONE);
            binding.teamSlotThreeButton.setVisibility(View.GONE);
            binding.teamSlotFourButton.setVisibility(View.GONE);
            binding.teamSlotFiveButton.setVisibility(View.GONE);
            binding.teamSlotSixButton.setVisibility(View.GONE);
        }
        else {
            creatureSelectButtonsVisible = true;
            binding.teamSlotOneButton.setVisibility(View.VISIBLE);
            binding.teamSlotTwoButton.setVisibility(View.VISIBLE);
            binding.teamSlotThreeButton.setVisibility(View.VISIBLE);
            binding.teamSlotFourButton.setVisibility(View.VISIBLE);
            binding.teamSlotFiveButton.setVisibility(View.VISIBLE);
            binding.teamSlotSixButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method collects all of the buttons and assigns their
     * text value to either display and empty slot or display
     * the name of the creature that currently occupies the slot
     */
    private void updateTeamSlotButtons() {
        Map<Integer, Creature> userTeam = UserTeamData.getInstance().getUserTeam();

        //array of buttons
        Button[] buttons = {
                binding.teamSlotOneButton,
                binding.teamSlotTwoButton,
                binding.teamSlotThreeButton,
                binding.teamSlotFourButton,
                binding.teamSlotFiveButton,
                binding.teamSlotSixButton,
        };

        //iterate through the buttons
        for (int i = 0; i < buttons.length; i++) {
            //set the creature based from userTeam array
            Creature creature = userTeam.get(i+1);
            if (creature != null) {
                //set name if exists
                buttons[i].setText(creature.getName());
            }
            else {
                //show empty slot if it doesn't
                buttons[i].setText("Team Slot: " + (i+1));
            }
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