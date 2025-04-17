package com.example.project2;
/**
 * Name: Austin Shatswell
 * Date: --/--/25
 * Explanation: Project 2: Creature Coliseum
 */

public class Ability {

    private String abilityID;
    private String abilityName;
    private ElementalType abilityElement;
    private int power;
    private int critChance;
    private int accuracy;
    //could add in "Uses" so that powerful abilities can only be used a limited number of times

    public Ability() {}

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
