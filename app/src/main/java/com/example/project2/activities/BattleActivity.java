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
        opponentCreature = UserTeamData.getInstance().getUserTeam().get(6);

        opponentName = getIntent().getStringExtra("name");

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
        binding.battleDisplayTextView.setText("You have challenged " + opponentName);


        //set up button text
        updateAbilityButtons();
        //set up health
        updateHealth();
        //set up the battle prompt
        updateBattlePrompt("What will " + playerCreature.getName() + " do?");
        //toggle the UI
        handler.postDelayed(() -> toggleBattleView(), 2000);
    }

    private void updateHealth() {
        binding.playerHealthTextView.setText("Health: " + playerCreature.getCurHealth() + "/" + playerCreature.getHealthStat());
        binding.opponentHealthTextView.setText("Health: " + opponentCreature.getCurHealth() + "/" + opponentCreature.getHealthStat());
    }

    private void battleUiUpdate() {

    }

    private void updateBattlePrompt(String text) {
        binding.battlePromptTextView.setText(text);
    }

    private void battleLogic(Ability selectedAbility) {
        //choose a random ability for the opponent creature to use
        Ability opponentAbility = opponentCreature.getAbilityList().get(Dice.roll(0, (opponentCreature.getAbilityList().size()-1)));

        //check whose speed is faster
        if (playerCreature.getSpeedStat() >= opponentCreature.getSpeedStat()) {
            //player attacks first, targeting opponent with selected ability
            handler.postDelayed(() -> updateBattlePrompt(playerCreature.getName() + " used " + selectedAbility.getAbilityName()), 2000);
            playerCreature.attack(opponentCreature, selectedAbility);
            //check if the opponent has fainted
            if (opponentCreature.isFainted()) {
                handler.postDelayed(() -> updateHealth(), 4000);
                handler.postDelayed(() -> updateBattlePrompt(opponentCreature.getName() + " has fainted!"), 4000);
                //TODO:add win condition
                handler.postDelayed(() -> updateBattlePrompt("You win!"), 6000);
                //opponent doesnt attack if fainted
                return;
            }
            //opponent attacks second, targeting player with randomly selected ability
            handler.postDelayed(() -> updateBattlePrompt(opponentCreature.getName() + " used " + opponentAbility.getAbilityName()), 4000);
            opponentCreature.attack(playerCreature, opponentAbility);
            //check if player has fainted
            if (playerCreature.isFainted()) {
                handler.postDelayed(() -> updateHealth(), 6000);
                handler.postDelayed(() -> updateBattlePrompt(playerCreature.getName() + " has fainted!"), 6000);
                //TODO:add lose condition
                handler.postDelayed(() -> updateBattlePrompt("You lose"), 8000);
                return;
            }
            handler.postDelayed(() -> updateHealth(), 6000);
            handler.postDelayed(() -> updateBattlePrompt("creatures take took damage!"), 6000); //temp
        }
        else {
            //opponent attacks first, targeting player with randomly selected ability
            handler.postDelayed(() -> updateBattlePrompt(opponentCreature.getName() + " used " + opponentAbility.getAbilityName()), 2000);
            opponentCreature.attack(playerCreature, opponentAbility);
            //check if player has fainted
            if (playerCreature.isFainted()) {
                handler.postDelayed(() -> updateHealth(), 4000);
                handler.postDelayed(() -> updateBattlePrompt(playerCreature.getName() + " has fainted!"), 4000);
                //TODO:add lose condition
                handler.postDelayed(() -> updateBattlePrompt("You win!"), 6000);
                //player doesnt attack if fainted
                return;
            }
            //player attacks second, targeting opponent with selected ability
            handler.postDelayed(() -> updateBattlePrompt(playerCreature.getName() + " used " + selectedAbility.getAbilityName()), 4000);
            playerCreature.attack(opponentCreature, selectedAbility);
            //check if the opponent has fainted
            if (opponentCreature.isFainted()) {
                handler.postDelayed(() -> updateHealth(), 6000);
                handler.postDelayed(() -> updateBattlePrompt(opponentCreature.getName() + " has fainted!"), 6000);
                //TODO:add win condition
                handler.postDelayed(() -> updateBattlePrompt("You win!"), 8000);
                return;
            }
            handler.postDelayed(() -> updateHealth(), 6000);
            handler.postDelayed(() -> updateBattlePrompt("creatures take took damage!"), 6000); //temp
        }
    }

    private void toastMaker(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public static Intent BattleIntentFactory(Context context) {
        Intent intent = new Intent(context, BattleActivity.class);

        return intent;
    }
}