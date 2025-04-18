package com.example.project2.creatures;
/**
 * Name: Austin Shatswell
 * Date: --/--/25
 * Explanation: Project 2: Creature Coliseum
 */

import com.example.project2.Ability;
import com.example.project2.ElementalType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Creature {
    //general creature info
    private String type;
    private String name = "";
    private String phrase = "";
    private boolean fainted = false;

    private List<ElementalType> elements = new ArrayList<ElementalType>();
    private List<Ability> abilityList = new ArrayList<Ability>();

    private int level = 1;
    private final int LEVEL_MAX = 20;

    private int curExperiencePoints = 0;
    private int experienceNeededToLevel = 10;

    //stats
    private int curHealth;
    private int healthStat;
    private int baseHealth;

    private int attackStat;
    private int baseAttack;

    private int defenseStat;
    private int baseDefense;

    private int speedStat;
    private int baseSpeed;

    public Creature() {

    }

    public Creature(String name, int level, ElementalType... types) {
        this.name = name;
        this.level = level;
        this.elements.addAll(Arrays.asList(types));
        this.experienceNeededToLevel = calculateExperienceNeeded(level);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public boolean isFainted() {
        return fainted;
    }

    public void setFainted(boolean fainted) {
        this.fainted = fainted;
    }

    public List<ElementalType> getElements() {
        return elements;
    }

    public void setElements(List<ElementalType> elements) {
        this.elements = elements;
    }

    public List<Ability> getAbilityList() {
        return abilityList;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurExperiencePoints() {
        return curExperiencePoints;
    }

    public void setCurExperiencePoints(int curExperiencePoints) {
        this.curExperiencePoints = curExperiencePoints;
    }

    public int getExperienceNeededToLevel() {
        return experienceNeededToLevel;
    }

    public void setExperienceNeededToLevel(int experienceNeededToLevel) {
        this.experienceNeededToLevel = experienceNeededToLevel;
    }

    public int getHealthStat() {
        return healthStat;
    }

    public void setHealthStat(int healthStat) {
        this.healthStat = healthStat;
    }

    public int getCurHealth() {
        return curHealth;
    }

    public void setCurHealth(int curHealth) {
        this.curHealth = curHealth;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getAttackStat() {
        return attackStat;
    }

    public void setAttackStat(int attackStat) {
        this.attackStat = attackStat;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getDefenseStat() {
        return defenseStat;
    }

    public void setDefenseStat(int defenseStat) {
        this.defenseStat = defenseStat;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getSpeedStat() {
        return speedStat;
    }

    public void setSpeedStat(int speedStat) {
        this.speedStat = speedStat;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseStats(int baseHealth, int baseAttack, int baseDefense, int baseSpeed) {
        this.baseHealth = baseHealth;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
    }

    public int calculateStat(int baseStat) {
        return Math.round(baseStat * ((float) level / LEVEL_MAX));
    }

    public void updateStats() {
        healthStat = (calculateStat(baseHealth)*2)+this.getLevel();
        attackStat = calculateStat(baseAttack);
        defenseStat = calculateStat(baseDefense);
        speedStat = calculateStat(baseSpeed);
    }

    public void gainExperience(int experience) {
        if (level == LEVEL_MAX) {
            curExperiencePoints = 0;
            return;
        }

        System.out.println("current XP: " + curExperiencePoints);
        System.out.println("XP needed to level: " + experienceNeededToLevel);
        System.out.println(this.getName() + " gained " + experience + " XP");
        curExperiencePoints += experience;
        if (curExperiencePoints >= experienceNeededToLevel) {
            curExperiencePoints = curExperiencePoints-experienceNeededToLevel;
            levelUp();
        }
        System.out.println("current XP: " + curExperiencePoints);
        System.out.println("XP needed to level: " + experienceNeededToLevel);
    }

    public int calculateExperienceNeeded(int level) {
        int baseXP = 10;
        double growthRate = 1.4;
        return (int) Math.round(baseXP * Math.pow(growthRate, level - 1));
    }

    public void levelUp() {
        level++;
        updateStats();
        curHealth = healthStat;
        experienceNeededToLevel = calculateExperienceNeeded(this.getLevel());
        System.out.println(this.getName() + " leveled up!");
    }

    //TODO: Add accuracy and crit chance into the attack and calculateDamage modifiers respectively
    public void attack(Creature target, Ability ability) {
        //System.out.println(this.getPhrase());
        System.out.println(this.getName() + " uses " + ability.getAbilityName());

        int attackValue = (int) Math.round(calculateDamage(target, ability));
        target.takeDamage(attackValue);
    }

    public double calculateDamage(Creature target, Ability ability) {
        double damageTotal;
        double elementalModifier = 1.0;
        double STABModifier = 1.0;

        if (this.elements.contains(ability.getAbilityElement())) {
            STABModifier = 1.5;
        }

        for (int i = 0; i < target.elements.size(); i++) {
            elementalModifier *= elementalDamageModifier(target.elements.get(i), ability.getAbilityElement());
        }

        if (elementalModifier >= 2.0) {
            System.out.println("It's super effective!");
        }
        else if (elementalModifier < 1.0) {
            System.out.println("It's not very effective");
        }

        //damage formula
        double attackDefenseRatio = Math.pow((double) this.getAttackStat() / (this.getAttackStat() + target.getDefenseStat()), .85);
        double baseDamage = (this.getLevel() / 2.0) * ability.getPower();
        double modifierBonus = Math.pow(elementalModifier * STABModifier, .85);
        damageTotal = baseDamage * attackDefenseRatio * modifierBonus / 4;

        return damageTotal;
    }

    double elementalDamageModifier(ElementalType defending, ElementalType abilityElement) {
        //switch statement that checks the value of the passed in element
        switch (defending) {
            //depending on the passed in element checks are given different results
            case ELECTRIC:
                //if defending element is electric and the attack is electric
                if (abilityElement == ElementalType.ELECTRIC) {
                    return 0.5;
                }
                //No interaction
                return 1.0;

            case FIRE:
                //if defending element is fire and the attack is water
                if (abilityElement == ElementalType.WATER) {
                    return 2.0;
                }
                //if defending element is fire and the attack is fire or grass
                if (abilityElement == ElementalType.FIRE
                || abilityElement == ElementalType.GRASS) {
                    return 0.5;
                }
                //No interaction
                return 1.0;

            case GRASS:
                //if the defending element it grass and the attack is fire
                if (abilityElement == ElementalType.FIRE) {
                    return 2.0;
                }
                //if the defending element is grass and the attack is electric, grass or water
                if (abilityElement == ElementalType.ELECTRIC
                || abilityElement == ElementalType.GRASS
                || abilityElement == ElementalType.WATER) {
                    return 0.5;
                }
                //No interaction
                return 1.0;

            case WATER:
                //if the defending element it water and the attack is electric
                if (abilityElement == ElementalType.ELECTRIC
                    || abilityElement == ElementalType.GRASS) {
                return 2.0;
                }
                //if the defending element it water and the attack is fire or water
                if (abilityElement == ElementalType.FIRE
                || abilityElement == ElementalType.WATER) {
                    return 0.5;
                }
                //No interaction
                return 1.0;

            default:
                //No currently listed element
                return 1.0;
        }
    }

    public void takeDamage(int damage) {
        //make sure the attack dealt damage
        if (damage > 0) {
            System.out.println(this.getName() + " is hit for " + damage + " damage!");
            this.curHealth = this.curHealth - damage;
        }
        else {
            System.out.println(this.getName() + " avoided the attack!");
        }

        if (this.getCurHealth() <= 0) {
            System.out.println(this.getName() + " has lost consciousness. It's passed out.");
            this.setFainted(true);
        }
        else {
            //System.out.println(this.getName() + " has " + this.getCurHealth() + "/" + this.healthStat + " remaining");
        }
    }
}
