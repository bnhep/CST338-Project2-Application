package com.example.project2;
/**
 * This class exists to help form the list that
 * will display all of the abilities available
 * to a creature in the ViewAndEditCreature activity
 * -Austin
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AbilityCellAdapter extends ArrayAdapter<String> {
    public AbilityCellAdapter(Context context, int resource, List<String> abilityNames) {
        super(context, resource, abilityNames);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_ability_preview, parent, false);
        }

        TextView creatureNameTextView = convertView.findViewById(R.id.abilityNameCellTextView);
        creatureNameTextView.setText(name);

        return convertView;
    }
}
