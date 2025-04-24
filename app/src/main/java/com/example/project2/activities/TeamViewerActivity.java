package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.UserTeamData;
import com.example.project2.creatures.*;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivityTeamViewerBinding;
import com.example.project2.utilities.Converters;

import java.util.Map;
import java.util.concurrent.Executors;

public class TeamViewerActivity extends AppCompatActivity {

    ActivityTeamViewerBinding binding;
    private AccountStatusCheck accountManager;

    //TODO: just for testing. this will be removed later
    private final String TEMP_USER_ID = "testUserId";

    //TODO:this should be assigned the users unique ID as its used for saving and loading creatures
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamViewerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        accountManager = AccountStatusCheck.getInstance();
        binding.usernameDisplayTextView.setText(accountManager.getUserName());
        //update buttons to display team members
        updateTeamSlotButtons();

        /**
         * TODO:this is where we should be picking up and assigning the
         *  the users proper randomly generated userId
         */
        userId = String.valueOf(AccountStatusCheck.getInstance().getUserID());

        binding.teamSlotOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(1);
            }
        });

        binding.teamSlotTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(2);
            }
        });

        binding.teamSlotThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(3);
            }
        });

        binding.teamSlotFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(4);
            }
        });

        binding.teamSlotFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(5);
            }
        });

        binding.teamSlotSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextualButtonChoice(6);
            }
        });

        // *************************** SAVE  ***************************
        /**
         * This button will be used to save the changes the user makes
         * to their team roster
         */
        binding.saveTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeam();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserLandingActivity.UserLandingPageIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /**
     * This method makes sure that any time the user returns to this page
     * the buttons are updated to resemble the current team members
     */
    @Override
    protected void onResume() {
        super.onResume();
        //refresh names every time activity regains focus
        updateTeamSlotButtons();
    }

    /**
     * This method accepts a number based on the button selected and
     * will send the user to a new activity dependant on whether the
     * team slot associated with that button is currently holding
     * creature information
     * @param slot
     */
    public void contextualButtonChoice(int slot) {
        if (UserTeamData.getInstance().getCreatureAtSlot(slot) != null) {
            //if not empty launch creature viewer
            Intent intent = new Intent(this, CreatureViewAndEditorActivity.class);
            intent.putExtra("slotNumber", slot);
            startActivity(intent);

        }
        else {
            //if empty launch creature creator
            Intent intent = new Intent(this, SelectCreatureToAddActivity.class);
            intent.putExtra("slotNumber", slot);
            startActivity(intent);
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

    public void saveTeam(){
        //run on a background thread when making changes to the database
        Executors.newSingleThreadExecutor().execute(() -> {

            //get reference to the CreatureDAO singleton
            CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();

            //delete the old saved data
            creatureDAO.deleteAllCreaturesByUserId(userId);

            //for each creature found in the userTeam map
            for (Map.Entry<Integer, Creature> entry : UserTeamData.getInstance().getUserTeam().entrySet()) {
                //store the key (creatures current slot number)
                int slot = entry.getKey();
                //store the creature located in that slot
                Creature creature = entry.getValue();
                //store the creatures unique ID for retrieval
                int id = creature.getCreatureId();

                //TODO:later on we want to pass in the actual users generated id here
                /**
                 * passing the information about the creature along with user ID into
                 * the converter to 'dehydrate' the creature class into simple data
                 * that the database can parse correctly
                 */
                CreatureEntity entity = Converters.convertCreatureToEntity(creature, userId, slot, id);
                //insert that converted creature entity into the creature database
                creatureDAO.insert(entity);
            }
            runOnUiThread(() ->
                    Toast.makeText(TeamViewerActivity.this, "Team saved!", Toast.LENGTH_SHORT).show()
            );
        });
    }

    public static Intent TeamViewerIntentFactory(Context context) {
        Intent intent = new Intent(context, TeamViewerActivity.class);

        return intent;
    }
}