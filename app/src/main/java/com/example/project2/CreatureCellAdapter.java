package com.example.project2;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  This class exists to help form the list that
 *  will display all of the creatures available
 *  in the BuildCreatureToAddToTeam activities ListView
 *  information on how this is done was found on the Android
 *  Developers Documentation website
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

public class CreatureCellAdapter extends ArrayAdapter<String> {
    /**
     * constructor that accepts a list of names that will be used to populate a scrolling list
     * @param context
     * @param resource
     * @param creatureNames
     */
    public CreatureCellAdapter(Context context, int resource, List<String> creatureNames) {
        super(context, resource, creatureNames);
    }

    /**
     *
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return
     */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_creature_preview, parent, false);
        }

        TextView creatureNameTextView = convertView.findViewById(R.id.creatureNameCellTextView);
        creatureNameTextView.setText(name);

        return convertView;
    }
}
