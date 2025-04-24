package com.example.project2.creatures;
/**
 * Name: Austin Shatswell
 * Date: --/--/25
 * Explanation: Project 2: Creature Coliseum
 */

import com.example.project2.ElementalType;

public class CustomCreature extends Creature {

    private final String PHRASE = "'Gah!";
    //these are unneeded
    private int HEALTH_MAX;
    private int ATTACK_MAX;
    private int DEFENSE_MAX;
    private int SPEED_MAX;

    public CustomCreature(String name, int level) {
        super(name, level);

        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        updateStats();
        setCurHealth(this.getHealthStat());
    }

    //used for creating a new creature type
    public CustomCreature(String name, ElementalType elementalType, int baseHealth, int baseAttack, int baseDefense, int baseSpeed) {
        super(name, 1, elementalType);

        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        setBaseStats(baseHealth, baseAttack, baseDefense, baseSpeed);
        updateStats();
        setCurHealth(this.getHealthStat());
    }

    public int getHEALTH_MAX(){
        return HEALTH_MAX;
    }
    public int getATTACK_MAX(){
        return ATTACK_MAX;
    }
    public int getDEFENSE_MAX(){
        return DEFENSE_MAX;
    }
    public int getSPEED_MAX(){
        return SPEED_MAX;
    }
}
