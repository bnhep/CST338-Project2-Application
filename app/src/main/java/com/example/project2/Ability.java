package com.example.project2;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  An class used to store information about the
 *  types of attacks a creature can make. this will
 *  be used to determine things like damage and effectiveness
 */

public class Ability {

    private String abilityID;
    private String abilityName;
    private ElementalType abilityElement;
    private int power;
    private int critChance;
    private int accuracy;

    /**
     * default constructor that was not used. At some point
     * I had plans for this but found simpler ways to do
     * what I needed
     */
    public Ability() {}

    /**
     * constructor that is used when defining a new ability
     * also used when restructuring an ability from an entity
     * so that it can be stared properly with all its information
     * in a creatures abilityList
     * @param abilityID
     * @param abilityName
     * @param abilityElement
     * @param power
     * @param critChance
     * @param accuracy
     */
    public Ability(String abilityID, String abilityName, ElementalType abilityElement, int power, int critChance, int accuracy) {
        this.abilityID = abilityID;
        this.abilityName = abilityName;
        this.abilityElement = abilityElement;
        this.power = power;
        this.critChance = critChance;
        this.accuracy = accuracy;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public String getAbilityID() {
        return abilityID;
    }

    public ElementalType getAbilityElement() {
        return abilityElement;
    }

    public int getPower() {
        return power;
    }

    public int getCritChance() {
        return critChance;
    }

    public int getAccuracy() {
        return accuracy;
    }
}
