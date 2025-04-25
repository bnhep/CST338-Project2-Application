package com.example.project2.utilities;

import android.content.Context;

import com.example.project2.creatures.Creature;

import java.util.Locale;

public class ImageUtil {
    public static int getCreatureImage(Creature creature, Context context) {
        if (creature == null || creature.getType() == null) {
            return 0;
        }

        String imageName = creature.getType().toLowerCase(Locale.ROOT);

        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
