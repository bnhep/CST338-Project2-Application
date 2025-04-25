package com.example.project2.utilities;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  slightly altered version of the Dice class that was used
 *  in the original Legally Distinct Pocket Monsters project
 */

import java.util.Random;

public class Dice {
    private static final Random random = new Random();
    /**
     * Returns a random value between min and max. Also does a small check to ensure
     * that min and max are used properly. :)
     * @param min the minimum possible value to return
     * @param max the maximum possible value to return
     * @return a random int between min and max (inclusive)
     */
    public static int roll(int min, int max){
        if(min > max){
            int temp = min;
            min = max;
            max = temp;
        }

        return random.nextInt(max-min+1) + min;
    }

    public static int roll(int max){
        return Dice.roll(max,1);
    }
}
