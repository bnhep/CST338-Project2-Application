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

import com.example.project2.OpponentTeamData;
import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.creatures.ElectricRat;
import com.example.project2.creatures.FireLizard;
import com.example.project2.creatures.FlowerDino;
import com.example.project2.creatures.WeirdTurtle;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivityOpponentSelectBinding;
import com.example.project2.utilities.Converters;
import com.example.project2.utilities.Dice;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class OpponentSelectActivity extends AppCompatActivity {

    ActivityOpponentSelectBinding binding;
    private AccountStatusCheck accountManager;
    private boolean opponentSelectButtonsVisible = true;
    private boolean creatureSelectButtonsVisible = false;

    private String opponentIntro;
    private Creature opponentCreature;
    Button[] teamSlotButtons;
    Button[] opponentButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpponentSelectBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        accountManager = AccountStatusCheck.getInstance();
        binding.usernameDisplayTextView.setText(accountManager.getUserName());

        teamSlotButtons = new Button[] {
                binding.teamSlotOneButton,
                binding.teamSlotTwoButton,
                binding.teamSlotThreeButton,
                binding.teamSlotFourButton,
                binding.teamSlotFiveButton,
                binding.teamSlotSixButton,
        };

        opponentButtons = new Button[] {
                binding.opponentOneButton,
                binding.opponentTwoButton,
                binding.opponentThreeButton,
                binding.opponentFourButton,
        };

        binding.opponentOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    generateRandomEncounter();

                    runOnUiThread(() -> {
                        toggleOpponentSelectButtons();
                        updateTeamSlotButtons();
                        opponentIntro = "A wild " + opponentCreature.getName() + " appears!";
                        updateTextView("Select your creature");
                        toggleCreatureSelectButtons();
                    });
                });
            }
        });

        binding.opponentTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    opponentCreature = new FlowerDino("Flower Dino", 4);
                });

                toggleOpponentSelectButtons();
                updateTeamSlotButtons();
                opponentIntro = "You challenged Rock!";
                updateTextView("Select your creature");
                toggleCreatureSelectButtons();
            }
        });

        binding.opponentThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    opponentCreature = new WeirdTurtle("Weird Turtle", 8);
                });

                toggleOpponentSelectButtons();
                updateTeamSlotButtons();
                opponentIntro = "You challenged Christy!";
                updateTextView("Select your creature");
                toggleCreatureSelectButtons();
            }
        });

        binding.opponentFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    opponentCreature = new ElectricRat("Electric Rat", 12);
                });

                toggleOpponentSelectButtons();
                updateTeamSlotButtons();
                opponentIntro = "You challenged The Champ!";
                updateTextView("Select your creature");
                toggleCreatureSelectButtons();
            }
        });

        binding.teamSlotOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(1, opponentIntro);
            }
        });

        binding.teamSlotTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(2, opponentIntro);
            }
        });

        binding.teamSlotThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(3, opponentIntro);
            }
        });

        binding.teamSlotFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(4, opponentIntro);
            }
        });

        binding.teamSlotFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(5, opponentIntro);
            }
        });

        binding.teamSlotSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattleOpponent(6, opponentIntro);
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void generateRandomEncounter() {
        //get DAOs
        CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();
        AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

        //create a list of all template creature entities
        List<CreatureEntity> creatureEntities = creatureDAO.getCreaturesByUserId("NONE");
        //roll for which one based on list size
        int encounterRoll = Dice.roll(0, creatureEntities.size()-1);
        //get the creature entity from the list associated with the number rolled
        CreatureEntity generatedCreature = creatureEntities.get(encounterRoll);
        //convert that entity into a full creature object and set it to be the opponent
        opponentCreature = Converters.convertEntityToCreature(generatedCreature, abilityDAO);
    }

    private void startBattleOpponent(int slot, String opponentIntro) {
        Creature creature = UserTeamData.getInstance().getUserTeam().get(slot);
        OpponentTeamData.setOpponentCreature(opponentCreature);

        if (creature != null) {
            Intent intent = new Intent(this, BattleActivity.class);
            //add opponent info here
            intent.putExtra("opponentIntro", opponentIntro);
            //add creature chosen here
            intent.putExtra("slotNumber", slot);
            startActivity(intent);
        }
        else {
            toastMaker("Slot empty!");
        }
    }

    private void toggleOpponentSelectButtons() {
        //flip boolean
        opponentSelectButtonsVisible = !opponentSelectButtonsVisible;

        //iterate through the buttons
        for (Button opponentButton : opponentButtons) {
            if (opponentSelectButtonsVisible) {
                opponentButton.setVisibility(View.VISIBLE);
            } else {
                opponentButton.setVisibility(View.GONE);
            }
        }
    }

    private void toggleCreatureSelectButtons() {
        //flip boolean
        creatureSelectButtonsVisible = !creatureSelectButtonsVisible;

        //iterate through the buttons
        for (Button teamSlotButton : teamSlotButtons) {
            if (creatureSelectButtonsVisible) {
                teamSlotButton.setVisibility(View.VISIBLE);
            } else {
                teamSlotButton.setVisibility(View.GONE);
            }
        }
    }

    /**
     * This method collects all of the buttons and assigns their
     * text value to either display and empty slot or display
     * the name of the creature that currently occupies the slot
     */
    private void updateTeamSlotButtons() {
        Map<Integer, Creature> userTeam = UserTeamData.getInstance().getUserTeam();

        //iterate through the buttons
        for (int i = 0; i < teamSlotButtons.length; i++) {
            //set the creature based from userTeam array
            Creature creature = userTeam.get(i+1);
            if (creature != null) {
                //set name if exists
                teamSlotButtons[i].setText(creature.getName());
            }
            else {
                //show empty slot if it doesn't
                teamSlotButtons[i].setText("Team Slot: " + (i+1));
            }
        }
    }

    private void updateTextView(String text) {
        binding.opponentSelectTextView.setText(text);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //reset the UI back to opponent selection mode
        opponentSelectButtonsVisible = false;
        toggleOpponentSelectButtons();

        creatureSelectButtonsVisible = true;
        toggleCreatureSelectButtons();

        updateTextView("Select opponent");
    }

    private void toastMaker(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public static Intent OpponentSelectIntentFactory(Context context) {
        Intent intent = new Intent(context, OpponentSelectActivity.class);

        return intent;
    }
}