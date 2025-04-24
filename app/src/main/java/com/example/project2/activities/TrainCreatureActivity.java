package com.example.project2.activities;

import com.example.project2.creatures.ElectricRat;
import com.example.project2.creatures.FireLizard;
import com.example.project2.creatures.FlowerDino;
import com.example.project2.creatures.WeirdTurtle;

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

public class TrainCreatureActivity extends AppCompatActivity {
    private Button buttonMash;
    private Button buttonBackToMain;
    private TextView timer;
    private TextView goal;
    private TextView counter;
    private TextView result;
    private Creature trainee;
    private String selectedAttribute = "";
    private int tapGoal;
    private int tapCount = 0;
    private CountDownTimer countDownTimer;
    private static final int TIME_LIMIT = 20000;
    private SoundPool soundPool;
    private int tapSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_select);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        tapSoundId = soundPool.load(this, R.raw.tap_sound, 1);

        new Thread(() -> {
            trainee = new ElectricRat(); //temporary before team select
            runOnUiThread(() -> {
                setupUI();
            });
        }).start();
    }

    private void setupUI() {
        findViewById(R.id.btn_train_attack).setOnClickListener(v -> tryAttribute("attack"));
        findViewById(R.id.btn_train_defense).setOnClickListener(v -> tryAttribute("defense"));
        findViewById(R.id.btn_train_health).setOnClickListener(v -> tryAttribute("health"));
        findViewById(R.id.btn_train_speed).setOnClickListener(v -> tryAttribute("speed"));
    }

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

    private boolean isMaxed(String attribute){
        if (trainee instanceof ElectricRat) {
            ElectricRat cr = (ElectricRat) trainee;
            switch (attribute) {
                case "attack": return cr.getAttackStat() >= cr.getATTACK_MAX();
                case "defense": return cr.getDefenseStat() >= cr.getDEFENSE_MAX();
                case "health": return cr.getHealthStat() >= cr.getHEALTH_MAX();
                case "speed": return cr.getSpeedStat() >= cr.getSPEED_MAX();
            }
        } else if (trainee instanceof FireLizard) {
            FireLizard cr = (FireLizard) trainee;
            switch (attribute) {
                case "attack": return cr.getAttackStat() >= cr.getATTACK_MAX();
                case "defense": return cr.getDefenseStat() >= cr.getDEFENSE_MAX();
                case "health": return cr.getHealthStat() >= cr.getHEALTH_MAX();
                case "speed": return cr.getSpeedStat() >= cr.getSPEED_MAX();
            }
        } else if (trainee instanceof FlowerDino) {
            FlowerDino cr = (FlowerDino) trainee;
            switch (attribute) {
                case "attack": return cr.getAttackStat() >= cr.getATTACK_MAX();
                case "defense": return cr.getDefenseStat() >= cr.getDEFENSE_MAX();
                case "health": return cr.getHealthStat() >= cr.getHEALTH_MAX();
                case "speed": return cr.getSpeedStat() >= cr.getSPEED_MAX();
            }
        } else if (trainee instanceof WeirdTurtle) {
            WeirdTurtle cr = (WeirdTurtle) trainee;
            switch (attribute) {
                case "attack": return cr.getAttackStat() >= cr.getATTACK_MAX();
                case "defense": return cr.getDefenseStat() >= cr.getDEFENSE_MAX();
                case "health": return cr.getHealthStat() >= cr.getHEALTH_MAX();
                case "speed": return cr.getSpeedStat() >= cr.getSPEED_MAX();
            }
        }
        return false;
    }

    private void initTrainingViews(){
        buttonMash = findViewById(R.id.button_mash);
        timer = findViewById(R.id.timer);
        goal = findViewById(R.id.goal);
        counter = findViewById(R.id.counter);
        result = findViewById(R.id.result);
        buttonBackToMain = findViewById(R.id.button_back_to_main);
        buttonBackToMain.setVisibility(View.GONE); // Hide initially

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

    private void startTraining(){
        tapCount = 0;
        result.setText("");
        int levelScale = getAttributeLevel(selectedAttribute);
        tapGoal = 30 + (levelScale * 3);//check if too hard lol
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

    private int getAttributeLevel(String attribute){
        switch(attribute){
            case "attack": return trainee.getAttackStat();
            case "defense": return trainee.getDefenseStat();
            case "health": return trainee.getHealthStat();
            case "speed": return trainee.getSpeedStat();
        }
        return 0;
    }

    private void evaluateTraining(){
        if(tapCount >= tapGoal){
            switch (selectedAttribute){
                case "attack": trainee.setAttackStat(trainee.getAttackStat() + 1);
                break;
                case "defense": trainee.setDefenseStat(trainee.getDefenseStat() + 1);
                break;
                case "health": trainee.setHealthStat(trainee.getHealthStat() + 1);
                break;
                case "speed": trainee.setSpeedStat(trainee.getSpeedStat() + 1);
                break;
            }
            result.setText("Success! " + capitalize(selectedAttribute) + " increased!");
            goal.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            result.setText("Failed! You needed " + tapGoal + " taps, but got " + tapCount);
            goal.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        buttonBackToMain.setVisibility(View.VISIBLE);
    }
    private String capitalize(String s){
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

}
