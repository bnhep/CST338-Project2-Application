package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.Ability;
import com.example.project2.OpponentTeamData;
import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.creatures.FlowerDino;
import com.example.project2.databinding.ActivityBattleBinding;
import com.example.project2.utilities.Dice;

import java.util.List;

public class BattleActivity extends AppCompatActivity {

    ActivityBattleBinding binding;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int slot;
    private String opponentName;
    private boolean battleScreenVisible = true;
    private Creature playerCreature;
    private Creature opponentCreature;
    private int battleTextPromptStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBattleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //get passed in variables
        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }

        playerCreature = UserTeamData.getInstance().getUserTeam().get(slot);
        //opponentCreature = something that well pass in from opponent select
        //TODO: temp opponent creature
        // ********** THIS IS JUST FOR TESTING **********
        opponentCreature = OpponentTeamData.getOpponentCreature();

        //update UI for battle screen
        battleUiSetup();

        binding.abilityOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battleLogic(playerCreature.getAbilityList().get(0));
            }
        });

        binding.abilityTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battleLogic(playerCreature.getAbilityList().get(1));
            }
        });

        binding.abilityThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battleLogic(playerCreature.getAbilityList().get(2));
            }
        });

        binding.abilityFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battleLogic(playerCreature.getAbilityList().get(3));
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toggleBattleView(){
        //flip bool
        battleScreenVisible = !battleScreenVisible;

        if (battleScreenVisible) {
            binding.battleDisplayTextView.setVisibility(View.GONE);
            binding.backButton.setVisibility(View.VISIBLE);
            binding.creatureViewLayout.setVisibility(View.VISIBLE);
            binding.battlePromptTextView.setVisibility(View.VISIBLE);
            binding.abilityButtonsLayout.setVisibility(View.VISIBLE);
        }
        else {
            binding.battleDisplayTextView.setVisibility(View.VISIBLE);
            binding.backButton.setVisibility(View.GONE);
            binding.creatureViewLayout.setVisibility(View.GONE);
            binding.battlePromptTextView.setVisibility(View.GONE);
            binding.abilityButtonsLayout.setVisibility(View.GONE);
        }
    }

    private void updateAbilityButtons() {
        //array of buttons
        Button[] buttons = {
                binding.abilityOneButton,
                binding.abilityTwoButton,
                binding.abilityThreeButton,
                binding.abilityFourButton,
        };

        List<Ability> abilities = playerCreature.getAbilityList();

        //iterate through the buttons
        for (int i = 0; i < buttons.length; i++) {
            if (i < abilities.size() && abilities.get(i) != null) {
                buttons[i].setText(abilities.get(i).getAbilityName());
                //enable only if there's an ability
                buttons[i].setEnabled(true);
            } else {
                buttons[i].setText("——————");
                //disable if there's no ability
                buttons[i].setEnabled(false);
            }
        }
    }

    private void battleUiSetup() {
        toggleBattleView(); //TODO: flips the currently being worked on more complex battle screen into just a text view
        //set opponent challenge text
        binding.battleDisplayTextView.setText(getIntent().getStringExtra("opponentIntro"));

        //set up button text
        updateAbilityButtons();
        //set creature names and level
        binding.playerNameTextView.setText(playerCreature.getName() + " [lvl]: " + playerCreature.getLevel());
        binding.opponentNameTextView.setText(opponentCreature.getName() + " [lvl]: " + opponentCreature.getLevel());
        //set up health
        updateHealth();
        //set up the battle prompt
        updateBattlePrompt("What will " + playerCreature.getName() + " do?");
        //toggle the UI
        handler.postDelayed(() -> toggleBattleView(), 2000);
    }

    private void updateHealth() {
        handler.postDelayed(() -> binding.playerHealthTextView.setText("Health: " + playerCreature.getCurHealth() + "/" + playerCreature.getHealthStat()), battleTextPromptStep);
        handler.postDelayed(() -> binding.opponentHealthTextView.setText("Health: " + opponentCreature.getCurHealth() + "/" + opponentCreature.getHealthStat()), battleTextPromptStep);
    }

    private void battleUiUpdate() {

    }

    private void updateBattlePrompt(String text) {
        //increment another 2 second delay from last prompt
        handler.postDelayed(() -> binding.battlePromptTextView.setText(text), battleTextPromptStep);
        battleTextPromptStep += 2000;
    }

    private void battleLogic(Ability selectedAbility) {
        //reset the step counter
        battleTextPromptStep = 0;
        //choose a random ability for the opponent creature to use
        Ability opponentAbility = opponentCreature.getAbilityList().get(Dice.roll(0, (opponentCreature.getAbilityList().size()-1)));

        //check whose speed is faster
        if (playerCreature.getSpeedStat() >= opponentCreature.getSpeedStat()) {
            //player attacks first, targeting opponent with selected ability
            updateBattlePrompt(playerCreature.getName() + " used " + selectedAbility.getAbilityName());
            playerCreature.attack(opponentCreature, selectedAbility);
            //check if the opponent has fainted
            if (opponentCreature.isFainted()) {
                updateHealth();
                updateBattlePrompt(opponentCreature.getName() + " has fainted!");
                //TODO:add win condition
                updateBattlePrompt("You win!");
                //opponent doesnt attack if fainted
                return;
            }
            //opponent attacks second, targeting player with randomly selected ability
            updateBattlePrompt(opponentCreature.getName() + " used " + opponentAbility.getAbilityName());
            opponentCreature.attack(playerCreature, opponentAbility);
            //check if player has fainted
            if (playerCreature.isFainted()) {
                updateHealth();
                updateBattlePrompt(playerCreature.getName() + " has fainted!");
                //TODO:add lose condition
                updateBattlePrompt("You lose");
                return;
            }
            updateHealth();
            updateBattlePrompt("creatures take took damage!"); //temp
        }
        else {
            //opponent attacks first, targeting player with randomly selected ability
            updateBattlePrompt(opponentCreature.getName() + " used " + opponentAbility.getAbilityName());
            opponentCreature.attack(playerCreature, opponentAbility);
            //check if player has fainted
            if (playerCreature.isFainted()) {
                updateHealth();
                updateBattlePrompt(playerCreature.getName() + " has fainted!");
                //TODO:add lose condition
                updateBattlePrompt("You win!");
                //player doesnt attack if fainted
                return;
            }
            //player attacks second, targeting opponent with selected ability
            updateBattlePrompt(playerCreature.getName() + " used " + selectedAbility.getAbilityName());
            playerCreature.attack(opponentCreature, selectedAbility);
            //check if the opponent has fainted
            if (opponentCreature.isFainted()) {
                updateHealth();
                updateBattlePrompt(opponentCreature.getName() + " has fainted!");
                //TODO:add win condition
                updateBattlePrompt("You win!");
                return;
            }
            updateHealth();
            updateBattlePrompt("creatures take took damage!"); //temp
        }

        updateBattlePrompt("What will " + playerCreature.getName() + " do?");
    }

    private void toastMaker(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public static Intent BattleIntentFactory(Context context) {
        Intent intent = new Intent(context, BattleActivity.class);

        return intent;
    }
}