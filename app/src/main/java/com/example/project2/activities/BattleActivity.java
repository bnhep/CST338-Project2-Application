package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.Ability;
import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.databinding.ActivityBattleBinding;

import java.util.List;
import java.util.Map;

public class BattleActivity extends AppCompatActivity {

    ActivityBattleBinding binding;
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

        //store the passed in slot number
        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }

        playerCreature = UserTeamData.getInstance().getUserTeam().get(slot);
        //opponentCreature = something that well pass in from opponent select

        opponentName = getIntent().getStringExtra("name");
        binding.battleDisplayTextView.setText("You have challenged " + opponentName);

        updateAbilityButtons();

        toggleBattleView(); //flips the currently being worked on more complex battle screen into just a text view

        new Handler().postDelayed(() -> {
            toggleBattleView(); //after 2 second swaps back to the battle screen
        }, 2000);

        binding.abilityOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMaker("You used ability 1");
            }
        });

        binding.abilityTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMaker("You used ability 2");
            }
        });

        binding.abilityThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMaker("You used ability 3");
            }
        });

        binding.abilityFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMaker("You used ability 4");
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

    private void toastMaker(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public static Intent BattleIntentFactory(Context context) {
        Intent intent = new Intent(context, BattleActivity.class);

        return intent;
    }
}