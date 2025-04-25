package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.Ability;
import com.example.project2.OpponentTeamData;
import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.databinding.ActivityBattleBinding;
import com.example.project2.utilities.Dice;
import com.example.project2.utilities.ImageUtil;

import java.util.List;

public class BattleActivity extends AppCompatActivity {

    ActivityBattleBinding binding;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int slot;
    private String opponentName;
    private Button[] abilityButtons;
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

        //assign array of buttons
        abilityButtons = new Button[] {
                binding.abilityOneButton,
                binding.abilityTwoButton,
                binding.abilityThreeButton,
                binding.abilityFourButton,
        };

        playerCreature = UserTeamData.getInstance().getUserTeam().get(slot);

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
                //TODO: eventually can turn this into a chance to escape the battle like in the real game
                //exit
                handler.postDelayed(() -> exitBattle(), 2000);;
            }
        });
    }

    private void setBattleViewVisible(boolean visible) {
        //advanced if/else statement makes this look way cleaner
        binding.battleDisplayTextView.setVisibility(visible ? View.GONE : View.VISIBLE);
        binding.backButton.setVisibility(visible ? View.VISIBLE : View.GONE);
        binding.usernameDisplayTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
        binding.xpBarTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
        binding.creatureViewLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        binding.battlePromptTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
        binding.abilityButtonsLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void updateAbilityButtons() {
        List<Ability> abilities = playerCreature.getAbilityList();

        //iterate through the buttons
        for (int i = 0; i < abilityButtons.length; i++) {
            if (i < abilities.size() && abilities.get(i) != null) {
                abilityButtons[i].setText(abilities.get(i).getAbilityName());
                //enable only if there's an ability
                abilityButtons[i].setEnabled(true);
            } else {
                abilityButtons[i].setText("——————");
                //disable if there's no ability
                abilityButtons[i].setEnabled(false);
            }
        }
    }

    private void battleUiSetup() {
        //show opponent intro
        setBattleViewVisible(false);
        //set opponent challenge text
        binding.battleDisplayTextView.setText(getIntent().getStringExtra("opponentIntro"));

        //set up button text
        updateAbilityButtons();
        //set creature names and level
        battleUiUpdate();
        //set creatures current health
        updatePlayerHealth();
        updateOpponentHealth();
        //set creature images
        binding.playerCreatureImage.setImageResource(ImageUtil.getCreatureImage(playerCreature, getApplicationContext()));
        binding.opponentCreatureImage.setImageResource(ImageUtil.getCreatureImage(opponentCreature, getApplicationContext()));

        //set the default battle prompt
        updateBattlePrompt("What will " + playerCreature.getName() + " do?");
        //toggle the UI
        handler.postDelayed(() -> setBattleViewVisible(true), 2000);
    }

    private void updatePlayerHealth() {
        handler.postDelayed(() -> binding.playerHealthTextView.setText("Health: " + playerCreature.getCurHealth() + "/" + playerCreature.getHealthStat()), battleTextPromptStep);

    }

    private void updateOpponentHealth() {
        handler.postDelayed(() -> binding.opponentHealthTextView.setText("Health: " + opponentCreature.getCurHealth() + "/" + opponentCreature.getHealthStat()), battleTextPromptStep);
    }

    private void battleUiUpdate() {
        //set names and levels
        handler.postDelayed(() -> binding.playerNameTextView.setText(playerCreature.getName() + " [lvl: " + playerCreature.getLevel() + "]"), battleTextPromptStep);
        handler.postDelayed(() -> binding.opponentNameTextView.setText(opponentCreature.getName() + " [lvl: " + opponentCreature.getLevel() + "]"), battleTextPromptStep);
        //set user xp
        handler.postDelayed(() -> binding.xpBarTextView.setText("[XP: " + playerCreature.getCurExperiencePoints() + "/" + playerCreature.getExperienceNeededToLevel() + "]"), battleTextPromptStep);
    }

    private void setButtonsClickable(boolean enabled) {
        List<Ability> abilities = playerCreature.getAbilityList();

        binding.backButton.setEnabled(enabled);

        //dia
        for (int i = 0; i < abilityButtons.length; i++) {
            boolean abilityExists = (i < abilities.size()) && (abilities.get(i) != null);
            abilityButtons[i].setEnabled(enabled && abilityExists);
        }
    }

    private void updateBattlePrompt(String text) {
        //increment another 2 second delay from last prompt
        handler.postDelayed(() -> binding.battlePromptTextView.setText(text), battleTextPromptStep);
        battleTextPromptStep += 2000;
    }

    private void battleLogic(Ability selectedAbility) {
        //variable to keep track of ability damage
        double[] damageDealt;
        //reset the step counter
        battleTextPromptStep = 0;
        handler.postDelayed(() -> setButtonsClickable(false), battleTextPromptStep);
        //choose a random ability for the opponent creature to use
        Ability opponentAbility = opponentCreature.getAbilityList().get(Dice.roll(0, (opponentCreature.getAbilityList().size()-1)));

        //check whose speed is faster
        if (playerCreature.getSpeedStat() >= opponentCreature.getSpeedStat()) {
            //player attacks first, targeting opponent with selected ability
            updateBattlePrompt(playerCreature.getName() + " used " + selectedAbility.getAbilityName());
            damageDealt = playerCreature.attack(opponentCreature, selectedAbility);
            updateOpponentHealth();
            creatureAttackResultPrompt(damageDealt);
            //check if the opponent has fainted
            if (opponentCreature.isFainted()) {
                playerWin();
                //opponent doesnt attack if fainted
                return;
            }
            //opponent attacks second, targeting player with randomly selected ability
            updateBattlePrompt(opponentCreature.getName() + " used " + opponentAbility.getAbilityName());
            damageDealt = opponentCreature.attack(playerCreature, opponentAbility);
            updatePlayerHealth();
            creatureAttackResultPrompt(damageDealt);
            //check if player has fainted
            if (playerCreature.isFainted()) {
                playerLose();
                //player doesnt attack if fainted
                return;
            }
        }
        else {
            //opponent attacks first, targeting player with randomly selected ability
            updateBattlePrompt(opponentCreature.getName() + " used " + opponentAbility.getAbilityName());
            damageDealt = opponentCreature.attack(playerCreature, opponentAbility);
            updatePlayerHealth();
            creatureAttackResultPrompt(damageDealt);
            //check if player has fainted
            if (playerCreature.isFainted()) {
                playerLose();
                //player doesnt attack if fainted
                return;
            }
            //player attacks second, targeting opponent with selected ability
            updateBattlePrompt(playerCreature.getName() + " used " + selectedAbility.getAbilityName());
            damageDealt = playerCreature.attack(opponentCreature, selectedAbility);
            updateOpponentHealth();
            creatureAttackResultPrompt(damageDealt);
            //check if the opponent has fainted
            if (opponentCreature.isFainted()) {
                playerWin();
                //opponent doesnt attack if fainted
                return;
            }
        }
        handler.postDelayed(() -> setButtonsClickable(true), battleTextPromptStep);
        updateBattlePrompt("What will " + playerCreature.getName() + " do?");
    }

    private void creatureAttackResultPrompt(double[] damageDealt) {
        //check if attack missed
        if (damageDealt[2] > 0) {
            //check for critical hit
            if (damageDealt[2] > 1) {
                updateBattlePrompt("Its a critical hit!");
            }

            //check for super effective
            if (damageDealt[1] > 1) {
                updateBattlePrompt("Its super effective!");
            } //check for not very effective
            else if (damageDealt[1] < 1) {
                updateBattlePrompt("Its not very effective...");
            }

            //show damage
            updateBattlePrompt(opponentCreature.getName() + " took " + Math.round(damageDealt[0]) + " damage");
        }
        else {
            //show attack missed
            updateBattlePrompt(opponentCreature.getName() + " avoided the attack!");
        }
    }

    private void playerWin() {
        updateBattlePrompt(opponentCreature.getName() + " has fainted!");

        int xpGained = Math.round(opponentCreature.getLevel() * 10);

        updateBattlePrompt(playerCreature.getName() + " gains " + xpGained + "XP!");

        if ((xpGained + playerCreature.getCurExperiencePoints()) >= playerCreature.getExperienceNeededToLevel()) {
            updateBattlePrompt(playerCreature.getName() + " has leveled up!");
        }

        playerCreature.gainExperience(xpGained);

        battleUiUpdate();
        updatePlayerHealth();

        //end battle screen
        binding.battleDisplayTextView.setText("You Win!");
        handler.postDelayed(() ->  setBattleViewVisible(false), battleTextPromptStep);

        //exit
        handler.postDelayed(() -> exitBattle(), battleTextPromptStep+2000);
    }

    private void playerLose() {
        updateBattlePrompt(playerCreature.getName() + " has fainted!");

        //end battle screen
        binding.battleDisplayTextView.setText("You Lose");
        handler.postDelayed(() ->  setBattleViewVisible(false), battleTextPromptStep);

        //exit
        handler.postDelayed(() -> exitBattle(), battleTextPromptStep+2000);
    }

    private void exitBattle() {
        //reset player creature health and fainted status
        playerCreature.setCurHealth(playerCreature.getHealthStat());
        playerCreature.setFainted(false);
        finish();
    }

    public static Intent BattleIntentFactory(Context context) {
        Intent intent = new Intent(context, BattleActivity.class);

        return intent;
    }
}