package com.example.project2;

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
