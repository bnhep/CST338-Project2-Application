package com.example.project2;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  Very small class just made for simplicity in passing
 *  the opponent creature in a safe way from one activity
 *  to another after being built
 */

import com.example.project2.creatures.Creature;

public class OpponentTeamData {
    private static Creature opponentCreature;

    public static Creature getOpponentCreature() {
        return opponentCreature;
    }

    public static void setOpponentCreature(Creature creature) {
        opponentCreature = creature;
    }
}
