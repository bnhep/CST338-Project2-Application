package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.databinding.ActivityCreatureViewAndEditorBinding;

public class CreatureViewAndEditorActivity extends AppCompatActivity {

    ActivityCreatureViewAndEditorBinding binding;
    private int slot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatureViewAndEditorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        slot = getIntent().getIntExtra("slotNumber", -1);
        if (slot == -1) {
            finish();
        }

        //after passing in the slot# from TeamViewer get reference to UserData to get the hashmap userTeam and get the creature from that slot#'s name
        binding.creatureNameTextView.setText(UserData.getInstance().getCreatureAtSlot(slot).getName());

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TeamViewerActivity.TeamBuilderIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent CreatureViewAndEditorIntentFactory(Context context) {
        Intent intent = new Intent(context, CreatureViewAndEditorActivity.class);

        return intent;
    }
}