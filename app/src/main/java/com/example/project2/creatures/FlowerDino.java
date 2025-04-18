package com.example.project2.creatures;
/**
 * Name: Austin Shatswell
 * Date: --/--/25
 * Explanation: Project 2: Creature Coliseum
 */

import com.example.project2.ElementalType;

public class FlowerDino extends Creature {

    private final String PHRASE = "'Boaba!";
    private final int HEALTH_MAX = 90;
    private final int ATTACK_MAX = 40;
    private final int DEFENSE_MAX = 45;
    private final int SPEED_MAX = 25;

    public FlowerDino() {

    }

    /**
     * This is the constructor for the creatures.FlowerDino class
     * @param name is what the monster object will be called
     */
    public FlowerDino(String name, int level) {
        super(name, level, ElementalType.GRASS);
        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        setBaseStats(HEALTH_MAX, ATTACK_MAX, DEFENSE_MAX, SPEED_MAX);
        updateStats();
        setCurHealth(this.getHealthStat());
    }
}
