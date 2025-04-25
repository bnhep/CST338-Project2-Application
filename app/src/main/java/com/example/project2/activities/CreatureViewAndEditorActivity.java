package com.example.project2.activities;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  activity that will display a selected creatures
 *  stats and abilities
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.Ability;
import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.databinding.ActivityCreatureViewAndEditorBinding;

import java.util.List;

public class CreatureViewAndEditorActivity extends AppCompatActivity {

    ActivityCreatureViewAndEditorBinding binding;
    private int slot;
    private AccountStatusCheck accountManager;
    private Button[] abilityButtons;
    private Creature playerCreature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatureViewAndEditorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        accountManager = AccountStatusCheck.getInstance();

        binding.usernameDisplayTextView.setText(accountManager.getUserName());
        //store the passed in slot number
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

        //get reference to chosen creature
        playerCreature = UserTeamData.getInstance().getUserTeam().get(slot);

        //set UI with creature information
        setUiStats();
        //and creature abilities
        updateAbilityButtons();

        binding.abilityOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAbilityAlertDialog(0);
            }
        });

        binding.abilityTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAbilityAlertDialog(1);
            }
        });

        binding.abilityThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAbilityAlertDialog(2);
            }
        });

        binding.abilityFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAbilityAlertDialog(3);
            }
        });

        binding.addAbilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerCreature.getAbilityList().size() < 4) {
                    Intent intent = new Intent(CreatureViewAndEditorActivity.this, SelectAbilityToAddActivity.class);
                    intent.putExtra("slotNumber", slot);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CreatureViewAndEditorActivity.this, "Must remove an ability first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.deleteCreatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCreatureAlertDialog();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * used to set the UI to display the stats of the creature
     * selected from the users available team
     */
    private void setUiStats() {
        /**
         *  after passing in the slot# from TeamViewer get reference
         *  to UserData to get the hashmap userTeam and set creature stats
         */
        //name
        binding.creatureNameTextView.setText(playerCreature.getName());
        //type
        binding.typeTextView.setText(playerCreature.getType());
        //elements
        binding.elementTextView.setText(playerCreature.getElements().toString());
        //level
        binding.levelTextview.setText("Level: " + playerCreature.getLevel());
        //current XP
        binding.curXpTextView.setText("XP: " + playerCreature.getCurExperiencePoints() + "/" + playerCreature.getExperienceNeededToLevel());
        //health
        binding.healthStatTextView.setText("Health: " + playerCreature.getHealthStat());
        //attack
        binding.attackStatTextView.setText("Attack: " + playerCreature.getAttackStat());
        //defense
        binding.defenseStatTextView.setText("Defense: " + playerCreature.getDefenseStat());
        //speed
        binding.speedStatTextView.setText("Speed: " + playerCreature.getSpeedStat());
    }

    /**
     * updates the ability buttons so that names of the
     * abilities are displayed and disables them if
     * there is no ability associated with it
     */
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

    /**
     * updates the buttons when the activity is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        //refresh names every time activity regains focus
        updateAbilityButtons();
    }

    /**
     * alert dialog to ask the user if they wish to remove
     * an ability from the selected creature
     * @param ability
     */
    private void removeAbilityAlertDialog(int ability){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setTitle("Remove Ability");
        alertBuilder.setMessage("Are you sure you want to remove " + playerCreature.getAbilityList().get(ability).getAbilityName() + "?");

        alertBuilder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playerCreature.getAbilityList().remove(ability);
                updateAbilityButtons();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.show();
    }

    /**
     * alert dialog to ask the user if they with to remove
     * the creature from the users team
     */
    private void removeCreatureAlertDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setTitle("Remove Creature");
        alertBuilder.setMessage("Are you sure you want to remove " + playerCreature.getName() + " from your team?");

        alertBuilder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserTeamData.getInstance().removeCreatureFromSlot(slot);
                finish();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.show();
    }

    /**
     * intent factory
     * @param context
     * @return
     */
    public static Intent CreatureViewAndEditorIntentFactory(Context context) {
        Intent intent = new Intent(context, CreatureViewAndEditorActivity.class);

        return intent;
    }
}