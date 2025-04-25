package com.example.project2.creatures;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  custom creature class that was created so that
 *  an admin can pass new stats and information to
 *  build a new template creature
 */

import com.example.project2.ElementalType;

public class CustomCreature extends Creature {

    private final String PHRASE = "'Gah!";

    //TODO: these are unneeded and probably should be removed
    private int HEALTH_MAX;
    private int ATTACK_MAX;
    private int DEFENSE_MAX;
    private int SPEED_MAX;

    /**
     * constructed used when recreating an custom creature from the database
     * @param name
     * @param level
     */
    public CustomCreature(String name, int level) {
        super(name, level);

        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        updateStats();
        setCurHealth(this.getHealthStat());
    }

    /**
     * constructor built so that it can be used to pass a set of base
     * stats in from an admin page. this allows a custom creature to
     * uniquely be built with custom base stats.
     * @param name
     * @param elementalType
     * @param baseHealth
     * @param baseAttack
     * @param baseDefense
     * @param baseSpeed
     */
    public CustomCreature(String name, ElementalType elementalType, int baseHealth, int baseAttack, int baseDefense, int baseSpeed) {
        super(name, 1, elementalType);

        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        setBaseStats(baseHealth, baseAttack, baseDefense, baseSpeed);
        updateStats();
        setCurHealth(this.getHealthStat());
    }

    //TODO:these are unneeded and should probably be removed
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
