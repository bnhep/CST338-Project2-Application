package com.example.project2.utilities;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  small class that I built to deal with converting a
 *  creatures type into a int that can be used to retrieve
 *  the appropriate image. This could be expanded to include
 *  other utilities in the future
 */

import android.content.Context;

import com.example.project2.creatures.Creature;

import java.util.Locale;

public class ImageUtil {

    /**
     * used to return an int that can be used to retrieve
     * a creatures image based on the passed in creatures type
     * @param creature
     * @param context
     * @return
     */
    public static int getCreatureImage(Creature creature, Context context) {
        if (creature == null || creature.getType() == null) {
            return 0;
        }

        String imageName = creature.getType().toLowerCase(Locale.ROOT);

        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
