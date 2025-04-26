package com.example.project2.activities;

import com.example.project2.UserTeamData;
import com.example.project2.creatures.ElectricRat;
import com.example.project2.creatures.FireLizard;
import com.example.project2.creatures.FlowerDino;
import com.example.project2.creatures.WeirdTurtle;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project2.R;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.creatures.Creature;

/**
* Activity that trains a selected creature via a button mashing game
* @author Alexis Wogoman
* @date 24 April 2025
 */
public class TrainCreatureActivity extends AppCompatActivity {
    private Button buttonMash;
    private Button buttonBackToMain;

    private TextView timer;
    private TextView goal;
    private TextView counter;
    private TextView result;

    public Creature trainee;
    private String selectedAttribute = "";

    private int tapGoal;
    private int tapCount = 0;

    private CountDownTimer countDownTimer;
    private static final int TIME_LIMIT = 20000;

    private SoundPool soundPool;
    private int tapSoundId;

    private int slot;

    private final int MAX_OVERALL_BONUS = 30;
    private final int MAX_INDIVIDUAL_BONUS = 20;


    /**
     * Creation method to set up content view, trainee, audio, and call the UI setup
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_select);

        //get the passed selected creature from the menu
        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            //if the number failed to pass in correctly just cancel
            finish();
        }
        //set your trainee to be the creature selected from menu
        trainee = UserTeamData.getInstance().getCreatureAtSlot(slot);

        //button sound setup
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        tapSoundId = soundPool.load(this, R.raw.tap_sound, 1);

        setupUI();
    }

    /**
     * Sets up buttons to test if the chosen attribute is maxed out
     * Made separately for clarity in setting up user interface
     * could be in the onCreate, but wanted to make it look cleaner
     */
    private void setupUI() {
        findViewById(R.id.btn_train_attack).setOnClickListener(v -> tryAttribute("attack"));
        findViewById(R.id.btn_train_defense).setOnClickListener(v -> tryAttribute("defense"));
        findViewById(R.id.btn_train_health).setOnClickListener(v -> tryAttribute("health"));
        findViewById(R.id.btn_train_speed).setOnClickListener(v -> tryAttribute("speed"));
    }

    /**
     * Checks if selected attribute is maxed out by calling isMaxed, if so, display an error message
     * if not maxed, begin the training mini game
     * @param attribute the attribute selected for training from the menu options
     */
    private void tryAttribute(String attribute){
        if(isMaxed(attribute)){
            Toast.makeText(this, attribute + " is already at max level!", Toast.LENGTH_SHORT).show();
        } else {
            selectedAttribute = attribute;
            setContentView(R.layout.activity_train_creature);
            initTrainingViews();
            startTraining();
        }
    }

    /**
     * Checks if selected attribute is at max level or if the overall number of bonus points is at max
     * @param attribute the attribute being checked
     * @return true if attribute is at max, false if not
     */
    public boolean isMaxed(String attribute){
        if(trainee.getBonusAttack() >= MAX_INDIVIDUAL_BONUS || trainee.getBonusStatTotal() >= MAX_OVERALL_BONUS){
            return true;
        }
        if(trainee.getBonusDefense() >= MAX_INDIVIDUAL_BONUS || trainee.getBonusStatTotal() >= MAX_OVERALL_BONUS){
            return true;
        }
        if(trainee.getBonusHealth() >= MAX_INDIVIDUAL_BONUS || trainee.getBonusStatTotal() >= MAX_OVERALL_BONUS){
            return true;
        }
        if(trainee.getBonusSpeed() >= MAX_INDIVIDUAL_BONUS || trainee.getBonusStatTotal() >= MAX_OVERALL_BONUS){
            return true;
        }
        return false;
    }

    /**
     * Sets up the mini game page and counts the button clicks
     */
    private void initTrainingViews(){
        buttonMash = findViewById(R.id.button_mash);
        timer = findViewById(R.id.timer);
        goal = findViewById(R.id.goal);
        counter = findViewById(R.id.counter);
        result = findViewById(R.id.result);
        buttonBackToMain = findViewById(R.id.button_back_to_main);
        buttonBackToMain.setVisibility(View.GONE); // hide button initially

        buttonMash.setEnabled(true);
        buttonMash.setOnClickListener(v ->{
            tapCount++;
            soundPool.play(tapSoundId, 1, 1, 0, 0, 1);
            counter.setText("Taps: " + tapCount);
        });

        buttonBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(TrainCreatureActivity.this, UserLandingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Performs math for the scale of the goal, then calls evaluation of the result
     */
    private void startTraining(){
        tapCount = 0;
        result.setText("");
        int levelScale = getAttributeLevel(selectedAttribute);
        tapGoal = 30 + (levelScale * 5); //check if too hard or easy
        goal.setTextColor(getResources().getColor(android.R.color.black));

        goal.setText("Tap Goal: " + tapGoal);
        counter.setText("Taps: 0");

        buttonMash.setEnabled(true);
        buttonMash.setClickable(true);
        buttonMash.setAlpha(1f);

        countDownTimer = new CountDownTimer(TIME_LIMIT, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                buttonMash.setEnabled(false);
                buttonMash.setClickable(false);
                buttonMash.setAlpha(0.5f);
                evaluateTraining();
            }
        }.start();
    }

    /**
     * Simple method that returns the level of selected attribute to be used in math computation
     * @param attribute the attribute whose level is being checked
     * @return int of the current level of the selected attribute
     */
    public int getAttributeLevel(String attribute){
        switch(attribute){
            case "attack": return trainee.getAttackStat();
            case "defense": return trainee.getDefenseStat();
            case "health": return trainee.getHealthStat();
            case "speed": return trainee.getSpeedStat();
        }
        return 0;
    }

    /**
     * Evaluates the result of the training mini game, outputs success or failure and shows back button
     */
    private void evaluateTraining(){
        buttonBackToMain.setVisibility(View.VISIBLE);
        buttonBackToMain.bringToFront();

        if(tapCount >= tapGoal){
            switch (selectedAttribute){
                case "attack": trainee.setBonusAttack(trainee.getBonusAttack() + 1);
                break;
                case "defense": trainee.setBonusDefense(trainee.getBonusDefense() + 1);
                break;
                case "health": trainee.setBonusHealth(trainee.getBonusHealth() + 1);
                break;
                case "speed": trainee.setBonusSpeed(trainee.getBonusSpeed() + 1);
                break;
            }
            result.setText("Success! " + capitalize(selectedAttribute) + " increased!");
            goal.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            result.setText("Failed! You needed " + tapGoal + " taps, but got " + tapCount);
            goal.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    /**
     * Simple method to capitalize the first letter of a string
     * @param s the string to be capitalized
     * @return a string with the now capitalized letter in front
     */
    public static String capitalize(String s){
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }

    /**
     * Breakdown method
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

    /**
     * Made solely for unit testing, not to be used in gameplay
     * @param creature to be set as the trainee
     */
    public void setTrainee(Creature creature) {
        this.trainee = creature;
    }

    /**
     * Factory method to create an Intent for TrainCreatureActivity
     */
    public static Intent intentFactory(Context context, int slotNumber) {
        Intent intent = new Intent(context, TrainCreatureActivity.class);
        intent.putExtra("slotNumber", slotNumber);
        return intent;
    }
}
