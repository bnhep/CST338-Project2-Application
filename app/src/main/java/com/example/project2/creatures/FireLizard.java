package com.example.project2.creatures;
/**
 * Name: Austin Shatswell
 * Date: --/--/25
 * Explanation: Project 2: Creature Coliseum
 */

import com.example.project2.ElementalType;

public class FireLizard extends Creature {

    private final String PHRASE = "'Char!";
    private final int HEALTH_MAX = 50;
    private final int ATTACK_MAX = 70;
    private final int DEFENSE_MAX = 40;
    private final int SPEED_MAX = 40;

    public FireLizard() {

    }

    /**
     * This is the constructor for the creatures.FireLizard class
     * @param name is what the monster object will be called
     */
    public FireLizard(String name, int level) {
        super(name, level, ElementalType.FIRE);
        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        setBaseStats(HEALTH_MAX, ATTACK_MAX, DEFENSE_MAX, SPEED_MAX);
        updateStats();
        setCurHealth(this.getHealthStat());
    }
}
