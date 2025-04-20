package com.example.project2;

/**
 * This class exists to help form the list that
 * will display all of the creatures available
 * in the BuildCreatureToAddToTeam activities ListView
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project2.creatures.Creature;

import java.util.List;

public class CreatureCellAdapter extends ArrayAdapter<Creature> {

    public CreatureCellAdapter(Context context, int resource, List<Creature> creatureList) {
        super(context, resource, creatureList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Creature creature = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_creature_preview, parent, false);
        }

        TextView creatureNameTextView = convertView.findViewById(R.id.creatureNameCellTextView);

        //use the creature that was passed in and grab their name
        creatureNameTextView.setText(creature.getName());

        return convertView;
    }
}
